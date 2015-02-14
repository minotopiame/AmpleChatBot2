package com.drevelopment.amplechatbot.api.question;

import java.util.List;

public interface QuestionHandler {

	public boolean addQuestionToDatabase(Question question);

	public void updateQuestion(Question question);

	public void deleteQuestion(Question question);

	public boolean questionExists(int id);

	public boolean questionExists(String question);

	public Question getQuestion(int id);

	public Question getQuestion(String question);

	public List<Question> getQuestions();

}
