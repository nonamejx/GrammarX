package com.nonamejx.grammarx.database;

import android.content.Context;

import com.nonamejx.grammarx.common.Constant;
import com.nonamejx.grammarx.models.Answer;
import com.nonamejx.grammarx.models.Level;
import com.nonamejx.grammarx.models.Question;
import com.nonamejx.grammarx.models.Result;
import com.nonamejx.grammarx.models.Test;
import com.nonamejx.grammarx.models.Topic;
import com.nonamejx.grammarx.models.UserAnswerItem;

import java.util.List;

import io.realm.Realm;

/**
 * Created by noname on 27/07/2016.
 */

public class RealmHelper {
    private Realm mRealm;
    private Context mContext;

    private static RealmHelper mInstance;

    private RealmHelper(Context context) {
        mContext = context;
        mRealm = Realm.getDefaultInstance();
    }

    public static RealmHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RealmHelper(context);
        }
        return mInstance;
    }

    public Realm getRealm() {
        return mRealm;
    }

    public List<Level> getLevels() {
        return mRealm.where(Level.class).findAll();
    }

    public Level getLevel(String levelId) {
        return mRealm.where(Level.class).equalTo(Constant.ATTR_LEVEL_ID, levelId).findFirst();
    }

    public List<Topic> getTopics(String levelId) {
        Level level = mRealm.where(Level.class).equalTo(Constant.ATTR_LEVEL_ID, levelId).findFirst();
        if (level == null) {
            return null;
        }
        return level.getTopics();
    }

    public int countTakenTests(String topicId) {
        Topic topic = mRealm.where(Topic.class).equalTo(Constant.ATTR_TOPIC_ID, topicId).findFirst();
        int count = 0;
        List<Test> tests = topic.getTests();
        for (Test t : tests) {
            if (t.getResult() != null) {
                count++;
            }
        }
        return count;
    }

    public Topic getTopic(String topicId) {
        return mRealm.where(Topic.class).equalTo(Constant.ATTR_TOPIC_ID, topicId).findFirst();
    }

    public List<Test> getTests(String topicId) {
        Topic topic = getTopic(topicId);
        if (topic == null) {
            return null;
        }
        return topic.getTests();
    }

    public Test getTest(String testId) {
        return mRealm.where(Test.class).equalTo(Constant.ATTR_TEST_ID, testId).findFirst();
    }

    public List<Question> getQuestions(String testId) {
        return null;
    }

    public Question getQuestion(String questionId) {
        return mRealm.where(Question.class).equalTo(Constant.ATTR_QUESTION_ID, questionId).findFirst();
    }

    public List<Answer> getAnswers(String questionId) {
        return null;
    }

    public Answer getAnswer(String answerId) {
        return null;
    }

    public Result addResult(Result mResult) {
        Result result;
        mRealm.beginTransaction();
        result = mRealm.copyToRealmOrUpdate(mResult);
        mRealm.commitTransaction();
        return result;
    }

    public void deleteResult(Result mResult) {
        mRealm.beginTransaction();
        mResult.removeFromRealm();
        mRealm.commitTransaction();
    }

    public UserAnswerItem getUserAnswerItem(String userAnswerItemId) {
        return mRealm.where(UserAnswerItem.class).equalTo(Constant.ATTR_USER_ANSWER_ITEM_ID, userAnswerItemId).findFirst();
    }

    public void addUserAnswerItem(UserAnswerItem userAnswerItem) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(userAnswerItem);
        mRealm.commitTransaction();
    }
}
