package com.example.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuestionBank implements Parcelable {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex=-1;

    public QuestionBank(List<Question> questionList) {
        this.mQuestionList = questionList;
    }

    protected QuestionBank(Parcel in) {
        mNextQuestionIndex = in.readInt();
    }

    public Question getNextQuestion() {
        mNextQuestionIndex++;
        Question myQuestion = mQuestionList.get(mNextQuestionIndex);
        return myQuestion;
    }
    public Question getQuestion() {
        Question myQuestion = mQuestionList.get(mNextQuestionIndex);
        return myQuestion;
    }

    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }

        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mNextQuestionIndex);
    }
}
