package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmObject;

public class Answer extends RealmObject {

	private UUID answerId;
	private String answerContent;
	private boolean correctAnswer;
	
	public Answer() {
		this.answerId = UUID.randomUUID();
	}

	public UUID getAnswerId() {
		return answerId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

}
