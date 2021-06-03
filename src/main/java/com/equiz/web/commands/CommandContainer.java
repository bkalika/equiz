package com.equiz.web.commands;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CommandContainer {
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	private static Map<String, Command> commands = new TreeMap<>();

	static {
		commands.put("goToLoginCommand", new GoToLoginCommand());
		commands.put("goToUserCommand", new GoToUserCommand());
		commands.put("goToUserRegisterCommand", new GoToUserRegisterCommand());
		commands.put("goToUpdateTestCommand", new GoToUpdateTestCommand());
		commands.put("goToUpdateAnswerCommand", new GoToUpdateAnswerCommand());
		commands.put("goToSolveTestCommand", new GoToSolveTestCommand());
		commands.put("addUsersToTheTestCommand", new AddUsersToTheTestCommand());
		commands.put("createSubjectCommand", new CreateSubjectCommand());
		commands.put("createTestCommand", new CreateTestCommand());
		commands.put("createQuestionCommand", new CreateQuestionCommand());
		commands.put("createAnswerCommand", new CreateAnswerCommand());
		commands.put("updateUserCommand", new UpdateUserCommand());
		commands.put("updateSubjectCommand", new UpdateSubjectCommand());
		commands.put("updateTestCommand", new UpdateTestCommand());
		commands.put("updateQuestionCommand", new UpdateQuestionCommand());
		commands.put("updateAnswerCommand", new UpdateAnswerCommand());
		commands.put("deleteSubjectCommand", new DeleteSubjectCommand());
		commands.put("deleteTestCommand", new DeleteTestCommand());
		commands.put("deleteQuestionCommand", new DeleteQuestionCommand());
		commands.put("deleteAnswerCommand", new DeleteAnswerCommand());
		commands.put("loginCommand", new LoginCommand());
		commands.put("registerCommand", new RegisterCommand());
		commands.put("logoutCommand", new LogoutCommand());
		commands.put("languageCommand", new LanguageCommand());
		commands.put("adminCommand", new AdminCommand());
		commands.put("subjectCommand", new SubjectCommand());
		commands.put("testCommand", new TestCommand());
		commands.put("solveCommand", new SolveCommand());
		commands.put("noCommand", new NoCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands: " + commands.size());
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name: " + commandName);
			return commands.get("noCommand");
		}
		return commands.get(commandName);
	}
}
