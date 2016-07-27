package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Topic extends RealmObject {

	@PrimaryKey
	@Getter
	private String topicId;

	@Getter
	@Setter
	private String topicTitle;

	@Getter
	@Setter
	private String topicDescription;

	@Getter
	@Setter
	private long created;

	@Getter
	@Setter
	private RealmList<Test> tests;

	public Topic() {
		this.topicId = UUID.randomUUID().toString();
	}

}
