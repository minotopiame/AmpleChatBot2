package com.drevelopment.amplechatbot.core.commands.runnable;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;
import com.drevelopment.amplechatbot.core.util.Util;

public class AnswerCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public AnswerCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		String answer = "";

		for(int i = 1; i < args.length; i++) {
			answer += args[i];
			answer += " ";
		}
		answer = answer.trim();
		int qid = 0;
		if(args.length >= 2) {
			if(Util.isInteger(args[0])) {
				qid = Integer.parseInt(args[0]);
			}
		}

		if (sender.hasPermission("ample.edit")) {
			if (qid != 0) {
				if (Ample.getQuestionHandler().addAnswerToDatabase(answer, qid)) {
					sender.sendMessage(LocaleHandler.getString("Command.Answer.Set"));
				} else sender.sendMessage(LocaleHandler.getString("Command.Answer.NotFound", qid));
			} else sender.sendMessage(LocaleHandler.getString("Command.Answer.Error"));
		}
	}

}
