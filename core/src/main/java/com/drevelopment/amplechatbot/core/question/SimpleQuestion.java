package com.drevelopment.amplechatbot.core.question;

import com.drevelopment.amplechatbot.api.question.Question;

public class SimpleQuestion implements Question {

	private int id;
	private String question;
	private String answer;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getQuestion() {
		return question;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	@Override
	public Question setId(int id) {
		this.id = id;
		return this;
	}

	@Override
	public Question setQuestion(String question) {
		this.question = question;
		return this;
	}

	@Override
	public Question setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

}
