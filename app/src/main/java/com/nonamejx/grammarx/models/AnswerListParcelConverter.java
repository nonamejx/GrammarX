package com.nonamejx.grammarx.models;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Created by noname on 04/08/2016.
 */
public class AnswerListParcelConverter extends RealmListParcelConverter<Answer> {
    @Override
    public void itemToParcel(Answer input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Answer itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Answer.class.getClassLoader()));
    }
}
