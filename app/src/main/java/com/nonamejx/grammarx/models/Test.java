package com.nonamejx.grammarx.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Test extends RealmObject {

	public static final int TEST_TYPE_BY_TOPIC = 1;

	@PrimaryKey
	@Getter
	private String testId;

	@Getter
	@Setter
	@SerializedName("testTitle")
	private String testTitle;

	@Getter
	@Setter
	private String testDescription = "No Description";

	@Getter
	@Setter
	@SerializedName("testType")
	private int testType;

	@Getter
	@Setter
	@SerializedName("questions")
	private RealmList<Question> questions;

	@Getter
	@Setter
	private Result result;

	public Test() {
		this.testId = UUID.randomUUID().toString();
	}

}
