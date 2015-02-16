package com.drevelopment.amplechatbot.core.commands.runnable;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.api.question.Question;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;
import com.drevelopment.amplechatbot.core.util.Util;

public class DelQuestionCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public DelQuestionCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (sender.hasPermission("ample.delete")) {
				if (Util.isInteger(args[0])) {
					Question question = Ample.getQuestionHandler().getQuestion(Integer.parseInt(args[0]));
					if ( question != null) {
						Ample.getQuestionHandler().deleteQuestion(question);
						sender.sendMessage(LocaleHandler.getString("Command.DelQuestion.Deleted", args[0]));
					} else sender.sendMessage(LocaleHandler.getString("Command.DelQuestion.NotFound", args[0]));
				} else sender.sendMessage(LocaleHandler.getString("Command.DelQuestion.InvalidID", args[0]));
		} else sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
	}

}
