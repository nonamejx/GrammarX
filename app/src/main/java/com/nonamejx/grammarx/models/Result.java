package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Result extends RealmObject {

	@PrimaryKey
	@Getter
	private String resultId;

	@Getter
	@Setter
	private long created = System.currentTimeMillis();

	@Getter
	@Setter
	private RealmList<UserAnswerItem> userAnswerItems;

	public Result() {
		this.resultId = UUID.randomUUID().toString();
	}
	
	
}
