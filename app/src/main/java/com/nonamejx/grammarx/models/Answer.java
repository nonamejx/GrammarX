package com.nonamejx.grammarx.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Answer extends RealmObject {

	@PrimaryKey
	@Getter
	private String answerId;

	@SerializedName("answerTitle")
	@Setter
	@Getter
	private String answerTitle;

	@SerializedName("isCorrect")
	@Setter
	@Getter
	private boolean isCorrect;
	
	public Answer() {
		this.answerId = UUID.randomUUID().toString();
	}
}
