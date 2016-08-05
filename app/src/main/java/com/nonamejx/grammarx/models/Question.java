package com.nonamejx.grammarx.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.UUID;

import io.realm.QuestionRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Parcel(implementations = { QuestionRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { Question.class })
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

	@SerializedName("answers")
	private RealmList<Answer> answers;

	public RealmList<Answer> getAnswers() {
		return answers;
	}

	@ParcelPropertyConverter(AnswerListParcelConverter.class)
	public void setAnswers(RealmList<Answer> answers) {
		this.answers = answers;
	}

	public Question() {
		this.questionId = UUID.randomUUID().toString();
		this.answers = new RealmList<>();
	}

	
}
