package com.nonamejx.grammarx.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Question extends RealmObject{

	public static final int QUESTION_TYPE_SINGLE_CHOICE = 1;
	public static final int QUESTION_TYPE_MULTIPLE_CHOICE = 2;
	public static final int QUESTION_TYPE_PUT_IN_ORDER = 3;

	@PrimaryKey
	@Getter
	private String questionId;

	@Getter
	@Setter
	@SerializedName("questionTitle")
	private String questionTitle;

	@Getter
	@Setter
	private int questionType = QUESTION_TYPE_SINGLE_CHOICE;

	@Getter
	@Setter
	@SerializedName("answers")
	private RealmList<Answer> answers;

	public Question() {
		this.questionId = UUID.randomUUID().toString();
	}

	
}
