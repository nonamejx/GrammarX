package com.nonamejx.grammarx.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class UserAnswerItem extends RealmObject {

	@PrimaryKey
	@Getter
	private String itemId;

	@Getter
	@Setter
	private Question question;

	@Getter
	@Setter
	private Answer userChoice;

	@Getter
	@Setter
	private long created = System.currentTimeMillis();

	public UserAnswerItem() {
		this.itemId = UUID.randomUUID().toString();
	}

}
