package com.drevelopment.amplechatbot.api.question;

import java.util.HashMap;
import java.util.List;

public interface QuestionHandler {

	public boolean addQuestionToDatabase(Question question);

	public boolean updateQuestion(Question question);

	public HashMap<Integer, HashMap<String, String>> listQuestions();

	public void deleteQuestion(Question question);

	public boolean questionExists(int id);

	public Question getQuestion(int id);

	public Question getQuestion(String question);

	public List<Question> getQuestions();

}
