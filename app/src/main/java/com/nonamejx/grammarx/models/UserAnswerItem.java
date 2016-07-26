package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmObject;

public class UserAnswerItem extends RealmObject {

	private UUID itemId;
	private Question question;
	private Answer userChoice;
	private long created;

	public UserAnswerItem() {
		this.itemId = UUID.randomUUID();
	}
	
	public UUID getItemId() {
		return itemId;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getUserChoice() {
		return userChoice;
	}

	public void setUserChoice(Answer userChoice) {
		this.userChoice = userChoice;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

}
