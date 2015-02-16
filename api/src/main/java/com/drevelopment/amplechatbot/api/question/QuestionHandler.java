package com.drevelopment.amplechatbot.api.question;

import java.util.List;
import java.util.TreeMap;

public interface QuestionHandler {

	public boolean addQuestionToDatabase(Question question);

	public void updateQuestion(Question question);

	public void deleteQuestion(Question question);

	public boolean questionExists(int id);

	public boolean questionExists(String question);

	public Question getQuestion(int id);

	public Question getQuestion(String question);

	public List<Question> getQuestions();

	public TreeMap<Double, TreeMap<Integer,String>> getResponses(String message);

	public void addUsage(String playerName, int id);

	public int getUsage(int id);

}
