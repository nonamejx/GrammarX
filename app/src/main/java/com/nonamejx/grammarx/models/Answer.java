package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Answer extends RealmObject {

	@PrimaryKey
	@Getter
	private String answerId;

	@Getter
	@Setter
	private String answerContent;

	@Getter
	@Setter
	private boolean correctAnswer;
	
	public Answer() {
		this.answerId = UUID.randomUUID().toString();
	}
}
