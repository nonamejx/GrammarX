package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Test extends RealmObject {

	private UUID testId;
	private String testTitle;
	private String testDescription;
	private long created;

	private RealmList<Question> questions;
	private Result result;

	public Test() {
		this.testId = UUID.randomUUID();
	}
	
	public UUID getTestId() {
		return testId;
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public RealmList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(RealmList<Question> questions) {
		this.questions = questions;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
