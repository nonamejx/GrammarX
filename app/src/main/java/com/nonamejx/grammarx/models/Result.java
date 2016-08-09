package com.nonamejx.grammarx.models;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.ResultRealmProxy;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Parcel(implementations = { ResultRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { Result.class })
public class Result extends RealmObject {

	@PrimaryKey
	@Getter
	private String resultId;

	@Getter
	@Setter
	private long created = System.currentTimeMillis();

	private RealmList<UserAnswerItem> userAnswerItems;

	public RealmList<UserAnswerItem> getUserAnswerItems() {
		return userAnswerItems;
	}

	@ParcelPropertyConverter(UserAnswerItemListParcelConverter.class)
	public void setUserAnswerItems(RealmList<UserAnswerItem> userAnswerItems) {
		this.userAnswerItems = userAnswerItems;
	}

	public Result() {
		this.resultId = UUID.randomUUID().toString();
		userAnswerItems = new RealmList<>();
	}

	public int countCorrectAnswer() {
		int count = 0;
		for (UserAnswerItem u : userAnswerItems) {
			if (u.getUserChoice().isCorrect()) {
				count++;
			}
		}
		return count;
	}
	
}
