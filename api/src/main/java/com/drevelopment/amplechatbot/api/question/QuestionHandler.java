package com.drevelopment.amplechatbot.api.question;

public interface QuestionHandler {

	/**
	 * Adds a question to the database
	 * @param question The question to add
	 * @return The questions question id. -1 if the question was not added.
	 */
	public int addQuestionToDatabase(String question);

	public boolean addAnswerToDatabase(String answer, int qid);

}
