package com.example.topquiz.model;

import java.util.List;

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        this.mQuestionList = questionList;
    }

    public Question getNextQuestion() {
        Question myQuestion = mQuestionList.get(mNextQuestionIndex);
        mNextQuestionIndex++;
        return myQuestion;
    }
    public Question getQuestion() {
        Question myQuestion = mQuestionList.get(mNextQuestionIndex);
        return myQuestion;
    }
}
