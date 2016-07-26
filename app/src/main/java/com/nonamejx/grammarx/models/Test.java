package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Test extends RealmObject {

	@PrimaryKey
	@Getter
	private String testId;

	@Getter
	@Setter
	private String testTitle;

	@Getter
	@Setter
	private String testDescription;

	@Getter
	@Setter
	private long created;

	@Getter
	@Setter
	private RealmList<Question> questions;

	@Getter
	@Setter
	private Result result;

	public Test() {
		this.testId = UUID.randomUUID().toString();
	}

}
