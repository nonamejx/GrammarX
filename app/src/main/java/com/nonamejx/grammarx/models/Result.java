package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Result extends RealmObject {
	private UUID resultId;
	private long created;
	
	private RealmList<UserAnswerItem> userAnswerItems;

	public Result() {
		this.resultId = UUID.randomUUID();
	}
	
	public UUID getResultId() {
		return resultId;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public RealmList<UserAnswerItem> getUserAnswerItems() {
		return userAnswerItems;
	}

	public void setUserAnswerItems(RealmList<UserAnswerItem> userAnswerItems) {
		this.userAnswerItems = userAnswerItems;
	}
	
	
}
