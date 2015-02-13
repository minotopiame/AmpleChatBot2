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
			if (args.length < 1) {
				if (Util.isInteger(args[1])) {
					Question question = Ample.getQuestionHandler().getQuestion(Integer.parseInt(args[1]));
					if ( question != null) {
						Ample.getQuestionHandler().deleteQuestion(question);
						sender.sendMessage(LocaleHandler.getString("Command.DelQuestion.Deleted", args[1]));
					} else sender.sendMessage(LocaleHandler.getString("Command.DelQuestion.NotFound", args[1]));
				} else sender.sendMessage(LocaleHandler.getString("Command.DelQuestion.InvalidID", args[1]));
			} else sender.sendMessage(LocaleHandler.getString("Command.DelQuestion.NoID"));
		} else sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
	}

}
