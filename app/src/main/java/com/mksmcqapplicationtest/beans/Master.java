package com.mksmcqapplicationtest.beans;

public class Master {

    String TestName,Time,NegativeMarks,UserName,Result,ResultCode,
            QuestionHint,Question,AnsA,AnsB,AnsC,AnsD,AnsE,AnsF,CorrectOption,CorrectAns,Marks,AnswerDescription,
            CategoryCode,CategoryName,Test,TestCode,QuestionCode,AcFlag,ClassCode;
     boolean selected;

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public void setAcFlag(String acFlag) {
        AcFlag = acFlag;
    }

    public String getAcFlag() {
        return AcFlag;
    }

    public String getQuestionCode() {
        return QuestionCode;
    }

    public void setQuestionCode(String questionCode) {
        QuestionCode = questionCode;
    }

    public String getTest() {
        return Test;
    }

    public void setTest(String test) {
        Test = test;
    }

    public String getTestCode() {
        return TestCode;
    }

    public void setTestCode(String testCode) {
        TestCode = testCode;
    }

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getMarks() {
        return Marks;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }

    public String getAnswerDescription() {
        return AnswerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        AnswerDescription = answerDescription;
    }



    public String getCorrectOption() {
        return CorrectOption;
    }

    public void setCorrectOption(String correctOption) {
        CorrectOption = correctOption;
    }

    public String getCorrectAns() {
        return CorrectAns;
    }

    public void setCorrectAns(String correctAns) {
        CorrectAns = correctAns;
    }

    public void setQuestionHint(String questionHint) {
        QuestionHint = questionHint;
    }

    public String getQuestionHint() {
        return QuestionHint;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getQuestion() {
        return Question;
    }

    public void setAnsA(String ansA) {
        AnsA = ansA;
    }

    public String getAnsA() {
        return AnsA;
    }

    public void setAnsB(String ansB) {
        AnsB = ansB;
    }

    public String getAnsB() {
        return AnsB;
    }

    public void setAnsC(String ansC) {
        AnsC = ansC;
    }

    public String getAnsC() {
        return AnsC;
    }

    public void setAnsD(String ansD) {
        AnsD = ansD;
    }

    public String getAnsD() {
        return AnsD;
    }

    public void setAnsE(String ansE) {
        AnsE = ansE;
    }

    public String getAnsE() {
        return AnsE;
    }

    public void setAnsF(String ansF) {
        AnsF = ansF;
    }

    public String getAnsF() {
        return AnsF;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResult() {
        return Result;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setNegativeMarks(String negativeMarks) {
        NegativeMarks = negativeMarks;
    }

    public String getNegativeMarks() {
        return NegativeMarks;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }
}
