package com.drevelopment.amplechatbot.core.commands;

import com.drevelopment.amplechatbot.api.command.CommandException;
import com.drevelopment.amplechatbot.api.command.CommandHandler;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.core.commands.runnable.AnswerCommand;
import com.drevelopment.amplechatbot.core.commands.runnable.DelQuestionCommand;
import com.drevelopment.amplechatbot.core.commands.runnable.QListCommand;
import com.drevelopment.amplechatbot.core.commands.runnable.QuestionCommand;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;

public class SimpleCommandHandler implements CommandHandler {

	@Override
	public boolean handleCommand(String command, String[] args, CommandSender sender) throws CommandException {
		if (command.equalsIgnoreCase("ample")) {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Wildcards"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Question"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Answer"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Qlist"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Delquestion"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Amplesay"));
			return true;
		} else if (command.equalsIgnoreCase("question")) {
			new QuestionCommand(sender, args).run();
			return true;
		} else if (command.equalsIgnoreCase("answer")) {
			new AnswerCommand(sender, args).run();
			return true;
		} else if (command.equalsIgnoreCase("qlist")) {
			new QListCommand(sender, args).run();
			return true;
		} else if (command.equalsIgnoreCase("delquestion")) {
			new DelQuestionCommand(sender, args).run();
			return true;
		} else if (command.equalsIgnoreCase("amplesay")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean handleCommand(String command, CommandSender sender) throws CommandException {
		if (command.equalsIgnoreCase("ample")) {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Wildcards"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Question"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Answer"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Qlist"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Delquestion"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Ampleupdate"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Amplesay"));
			return true;
		} else if (command.equalsIgnoreCase("question")) {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Wildcards"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Question"));
			return true;
		} else if (command.equalsIgnoreCase("answer")) {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Wildcards"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Answer"));
			return true;
		} else if (command.equalsIgnoreCase("qlist")) {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Wildcards"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Qlist"));
			return true;
		} else if (command.equalsIgnoreCase("delquestion")) {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Wildcards"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Delquestion"));
			return true;
		} else if (command.equalsIgnoreCase("amplesay")) {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Wildcards"));
			sender.sendMessage(LocaleHandler.getString("Command.Help.Amplesay"));
			return true;
		}
		return false;
	}

}
