package com.nonamejx.grammarx.models;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Created by noname on 05/08/2016.
 */
public class UserAnswerItemListParcelConverter extends RealmListParcelConverter<UserAnswerItem> {
    @Override
    public void itemToParcel(UserAnswerItem input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public UserAnswerItem itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(UserAnswerItem.class.getClassLoader()));
    }
}
