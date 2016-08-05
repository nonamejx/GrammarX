package com.nonamejx.grammarx.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by noname on 29/07/2016.
 */
public class Level extends RealmObject {

    @PrimaryKey
    @Getter
    private String levelId;

    @Getter
    @Setter
    @SerializedName("levelTitle")
    private String levelTitle;

    @Getter
    @Setter
    private String levelDescription = "No Description";

    @Getter
    @Setter
    @SerializedName("topics")
    public RealmList<Topic> topics;

    public Level() {
        this.levelId = UUID.randomUUID().toString();
        this.topics = new RealmList<>();
    }

}
