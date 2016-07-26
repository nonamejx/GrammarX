package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Topic extends RealmObject {

	private UUID topicId;
	private String topicTitle;
	private String topicDescription;
	private long created;
	
	private RealmList<Test> tests;

	public Topic() {
		this.topicId = UUID.randomUUID();
	}
	
	public UUID getTopicId() {
		return topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getTopicDescription() {
		return topicDescription;
	}

	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public RealmList<Test> getTests() {
		return tests;
	}

	public void setTests(RealmList<Test> tests) {
		this.tests = tests;
	}

}
