package com.mksmcqapplicationtest.beans;

import java.util.List;

public class Question {

    String id,questionString,selectedAnswer,AfterImagePath,BeforImagePath,AnswerDescription,Category,
            TestTotalMarks,PassingCriteria,SelectedAnsOption,CorrectOption,SelectedRadioButtonIndex;
    List<Object> options,optionImages;
    Object correctAnswer;
    int marks,SelectedOption=-1;
    private String testCode,AnsAImagePath,AnsBImagePath,AnsCImagePath,AnsDImagePath,AnsEImagePath,AnsFImagePath;

    public void setSelectedRadioButtonIndex(String selectedRadioButtonIndex) {
        SelectedRadioButtonIndex = selectedRadioButtonIndex;
    }

    public String getSelectedRadioButtonIndex() {
        return SelectedRadioButtonIndex;
    }

    public void setCorrectOption(String correctOption) {
        CorrectOption = correctOption;
    }

    public String getCorrectOption() {
        return CorrectOption;
    }

    public void setSelectedAnsOption(String selectedAnsOption) {
        SelectedAnsOption = selectedAnsOption;
    }

    public String getSelectedAnsOption() {
        return SelectedAnsOption;
    }

    public void setAnsAImagePath(String ansAImagePath) {
        AnsAImagePath = ansAImagePath;
    }

    public String getAnsAImagePath() {
        return AnsAImagePath;
    }

    public void setAnsBImagePath(String ansBImagePath) {
        AnsBImagePath = ansBImagePath;
    }

    public String getAnsBImagePath() {
        return AnsBImagePath;
    }

    public void setAnsCImagePath(String ansCImagePath) {
        AnsCImagePath = ansCImagePath;
    }

    public String getAnsCImagePath() {
        return AnsCImagePath;
    }

    public void setAnsDImagePath(String ansDImagePath) {
        AnsDImagePath = ansDImagePath;
    }

    public String getAnsDImagePath() {
        return AnsDImagePath;
    }

    public void setAnsEImagePath(String ansEImagePath) {
        AnsEImagePath = ansEImagePath;
    }

    public String getAnsEImagePath() {
        return AnsEImagePath;
    }

    public void setAnsFImagePath(String ansFImagePath) {
        AnsFImagePath = ansFImagePath;
    }

    public String getAnsFImagePath() {
        return AnsFImagePath;
    }

    public void setPassingCriteria(String passingCriteria) {
        PassingCriteria = passingCriteria;
    }

    public String getPassingCriteria() {
        return PassingCriteria;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getBeforImagePath() {
        return BeforImagePath;
    }

    public void setBeforImagePath(String BeforImagePath) {
        this.BeforImagePath = BeforImagePath;
    }

    public String getAfterImagePath() {
        return AfterImagePath;
    }

    public void setAfterImagePath(String AfterImagePath) {
        this.AfterImagePath = AfterImagePath;
    }
    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public void setCorrectOption(int  SelectedOption) {
        this.SelectedOption = SelectedOption;
    }

    public int  getSelectedOption() {
        return SelectedOption;
    }


    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

    public void setOptionImages(List<Object> optionImages) {
        this.optionImages = optionImages;
    }

    public List<Object> getOptionImages() {
        return optionImages;
    }

    public Object getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Object correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public void setAnswerDescription(String answerDescription) {
        AnswerDescription = answerDescription;
    }

    public String getAnswerDescription() {
        return AnswerDescription;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategory() {
        return Category;
    }

    public void setTestTotalMarks(String testTotalMarks) {
        TestTotalMarks = testTotalMarks;
    }

    public String getTestTotalMarks() {
        return TestTotalMarks;
    }
}
