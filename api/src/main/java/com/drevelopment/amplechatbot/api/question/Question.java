package com.drevelopment.amplechatbot.api.question;

public interface Question {

	public int getId();

	public String getQuestion();

	public String getAnswer();

	public Question setId(int id);

	public Question setQuestion(String question);

	public Question setAnswer(String answer);
}
