package com.drevelopment.amplechatbot.core.commands.runnable;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.core.question.SimpleQuestion;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;

public class QuestionCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public QuestionCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (sender.hasPermission("ample.edit")) {
			String question = "";

			for(int i = 0; i < args.length; i++) {
				question += args[i];
				question += " ";
			}
			question = question.trim();

			if (question.length() >= 3) {
				if (Ample.getQuestionHandler().addQuestionToDatabase(new SimpleQuestion().setQuestion(question))) {
					sender.sendMessage(LocaleHandler.getString("Command.Question.Added", Ample.getQuestionHandler().getQuestion(question).getId()));
				} else sender.sendMessage(LocaleHandler.getString("Command.Question.Error"));
			} else sender.sendMessage(LocaleHandler.getString("Command.Question.Short"));
		} else sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
	}

}
