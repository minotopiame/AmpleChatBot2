package com.drevelopment.amplechatbot.core.commands.runnable;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.api.question.Question;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;

public class QListCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public QListCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (sender.hasPermission("ample.qlist")) {
			String color = "AQUA";
			for (Question question : Ample.getQuestionHandler().getQuestions()) {
				sender.sendMessage(LocaleHandler.getString("Command.QList.Question", color, question.getId(), question.getQuestion()));
				if (question.getAnswer() != null) {
					sender.sendMessage(LocaleHandler.getString("Command.QList.Answer", color, question.getAnswer()));
				}
				if (color.equals("AQUA"))
					color = "YELLOW";
				else
					color = "AQUA";
			}
		} else sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
	}

}
