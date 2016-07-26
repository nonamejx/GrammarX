package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Question extends RealmObject{
	private UUID questionId;
	private String questionTitle;
	private int questionType;
	private long created;
	
	private RealmList<Answer> answers;

	public Question() {
		this.questionId = UUID.randomUUID();
	}
	
	public UUID getQuestionId() {
		return questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public RealmList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(RealmList<Answer> answers) {
		this.answers = answers;
	}
	
}
