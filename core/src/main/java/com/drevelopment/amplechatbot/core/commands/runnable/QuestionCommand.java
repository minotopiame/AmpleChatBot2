package com.drevelopment.amplechatbot.core.commands.runnable;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
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
		String question = "";

		for(int i = 0; i < args.length; i++) {
			question += args[i];
			question += " ";
		}
		question = question.trim();

		if (sender.hasPermission("ample.edit")) {
			if (question.length() >= 3) {
				int id = Ample.getQuestionHandler().addQuestionToDatabase(question);
				if ( id == -1) {
					sender.sendMessage(LocaleHandler.getString("Command.Question.Added"));
				} else sender.sendMessage(LocaleHandler.getString("Command.Question.Error"));
			} else sender.sendMessage(LocaleHandler.getString("Command.Question.Short"));
		} else sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
	}

}
