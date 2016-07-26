package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Question extends RealmObject{

	@PrimaryKey
	@Getter
	private String questionId;

	@Getter
	@Setter
	private String questionTitle;

	@Getter
	@Setter
	private int questionType;

	@Getter
	@Setter
	private long created;

	@Getter
	@Setter
	private RealmList<Answer> answers;

	public Question() {
		this.questionId = UUID.randomUUID().toString();
	}

	
}
