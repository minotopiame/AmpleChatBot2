package com.drevelopment.amplechatbot.bukkit.question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.question.Question;
import com.drevelopment.amplechatbot.api.question.QuestionHandler;
import com.drevelopment.amplechatbot.bukkit.BukkitPlugin;
import com.drevelopment.amplechatbot.bukkit.database.SQLDatabaseHandler;
import com.drevelopment.amplechatbot.bukkit.database.options.MySQLOptions;
import com.drevelopment.amplechatbot.core.question.SimpleQuestion;

public class BukkitQuestionHandler implements QuestionHandler {

	BukkitPlugin plugin;
	private SQLDatabaseHandler databaseHandler;

	public BukkitQuestionHandler(BukkitPlugin plugin) {
		this.plugin = plugin;
		this.databaseHandler = (SQLDatabaseHandler)Ample.getDatabaseHandler();
	}

	@Override
	public boolean addQuestionToDatabase(Question question) {
		if (questionExists(question.getQuestion())) return false;
		try {
			Connection con = databaseHandler.getConnection();
			PreparedStatement p = null;

			p = con.prepareStatement("INSERT INTO amplechatbot_Responses (keyphrase)"+"VALUES (?)");
			p.setString(1, question.getQuestion());

			p.addBatch();
			con.setAutoCommit(false);
			p.executeBatch();
			con.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public void updateQuestion(Question question) {
		try {
			databaseHandler.query("UPDATE amplechatbot_Responses SET keyphrase='"+question.getQuestion()+"' WHERE id='"+question.getId()+"'");
			databaseHandler.query("UPDATE amplechatbot_Responses SET response='"+question.getAnswer()+"' WHERE id='"+question.getId()+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteQuestion(Question question) {
		try {
			databaseHandler.query("DELETE FROM amplechatbot_Responses WHERE ID='"+question.getId()+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean questionExists(String question) {
		for (Question q : getQuestions())
			if (q.getQuestion().equalsIgnoreCase(question)) return true;
		return false;
	}

	@Override
	public boolean questionExists(int id) {
		for (Question q : getQuestions())
			if (q.getId() == id) return true;
		return false;
	}

	@Override
	public Question getQuestion(int id) {
		try {
			ResultSet rs = databaseHandler.query("SELECT * FROM amplechatbot_Responses WHERE id='"+id+"'");
			if (databaseHandler.getDatabaseOptions() instanceof MySQLOptions) rs.first();
			int qid = rs.getInt("id");
			String question = rs.getString("keyphrase");
			String response = rs.getString("response");

			return new SimpleQuestion().setId(qid).setQuestion(question).setAnswer(response);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Question getQuestion(String question) {
		try {
			ResultSet rs = databaseHandler.query("SELECT * FROM amplechatbot_Responses WHERE keyphrase='"+question+"'");
			if (databaseHandler.getDatabaseOptions() instanceof MySQLOptions) rs.first();
			int id = rs.getInt("id");
			String qquestion = rs.getString("keyphrase");
			String response = rs.getString("response");

			return new SimpleQuestion().setId(id).setQuestion(qquestion).setAnswer(response);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Question> getQuestions() {
		List<Question> q = new ArrayList<Question>();
		try {
			ResultSet rs = databaseHandler.query("SELECT * FROM amplechatbot_Responses");
			if (rs==null) return q;
			while (rs.next())
				q.add(new SimpleQuestion().setId(rs.getInt("id")).setQuestion(rs.getString("keyphrase")).setAnswer(rs.getString("response")));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return q;
	}

	@Override
	public TreeMap<Double, TreeMap<Integer, String>> getResponses(String message) {
		TreeMap<Double,TreeMap<Integer,String>> map = new TreeMap<Double,TreeMap<Integer,String>>();
		try {
			ResultSet rs = databaseHandler.query("SELECT * FROM amplechatbot_Responses ORDER BY keyphrase DESC");
			if (rs != null) {
				while (rs.next()) {
					String response = rs.getString("keyphrase").toLowerCase();
					double reslength = response.length();
					double msglength = message.length();
					double rel;
					if(reslength >= msglength) rel = ((msglength/reslength)*100);
					else rel = ((reslength/msglength)*100);
					String[] mary = message.split(" ");
					double count = 0;
					for(int i=0;i < mary.length;i++) {
						if(response.contains(mary[i])) count ++;
					}
					double wordrel;
					if (count <= mary.length) wordrel = ((count/mary.length)*100);
					else wordrel = ((mary.length/count)*100);
					double avgrel = ((wordrel+rel)/2);
					TreeMap<Integer,String> temp = new TreeMap<Integer,String>();
					temp.put(rs.getInt("id"), rs.getString("response"));
					map.put(avgrel, temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return map;
		}
		return map;
	}

	@Override
	public void addUsage(String playerName, int id) {
		try {
			Connection con = databaseHandler.getConnection();
			PreparedStatement p = null;

			p = con.prepareStatement("INSERT INTO amplechatbot_Usage (player,dtime,question)"+"VALUES (?,?,?)");
			p.setString(1, playerName);
			p.setInt(2, databaseHandler.currentEpoch());
			p.setInt(3, id);

			p.addBatch();
			con.setAutoCommit(false);
			p.executeBatch();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			 e.printStackTrace();
		}
	}

	@Override
	public int getUsage(int id) {
		int usage = 0;
		try {
			ResultSet rs = databaseHandler.query("SELECT COUNT(dtime) FROM amplechatbot_Usage WHERE question = "+id+" AND dtime < "+databaseHandler.currentEpoch()+" AND dtime > "+(databaseHandler.currentEpoch() - Ample.getConfigHandler().getAbuseRatio()[1]));
			usage = rs.getInt(1);
			return usage;
		} catch (SQLException e) {
			e.printStackTrace();
			return usage;
		}
	}

}
