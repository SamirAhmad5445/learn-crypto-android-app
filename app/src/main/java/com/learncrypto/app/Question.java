package com.learncrypto.app;

public class Question {
    private final int questionId;
    private final String questionText;
    private final String choiceA;
    private final String choiceB;
    private final String choiceC;
    private final String correctChoice;
    private final int isCorrect; // boolean flag

    public Question(int questionId, String questionText, String choiceA, String choiceB, String choiceC, String correctChoice, int isCorrect) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.correctChoice = correctChoice;
        this.isCorrect = isCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return new String[]{
                choiceA,
                choiceB,
                choiceC
        };
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public int getIsCorrect() {
        return isCorrect;
    }
}
