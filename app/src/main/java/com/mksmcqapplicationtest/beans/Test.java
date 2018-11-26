package com.mksmcqapplicationtest.beans;

import java.util.List;


public class Test {

    private String testCode ,testName,submitTestTime,UserName,TestTotalMarks,IsTeacher,ClassName,id,
            selectedAnswer,ClassCode,AcFlag,Result,ResultCode,AcDate,AcFlagForGuest,Checked,NegativeMarks,
            currentMarks,PassingCriteria,PassOrFail,Stage,AudioCode,Instruction;

    private int currentAttempt,time,totalAttempt,correctAttempt,SelectedOption,wrongAttempt;

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setAudioCode(String audioCode) {
        AudioCode = audioCode;
    }

    public String getAudioCode() {
        return AudioCode;
    }

    public void setStage(String stage) {
        Stage = stage;
    }

    public String getStage() {
        return Stage;
    }

    public void setPassOrFail(String passOrFail) {
        PassOrFail = passOrFail;
    }

    public String getPassOrFail() {
        return PassOrFail;
    }

    public void setPassingCriteria(String passingCriteria) {
        PassingCriteria = passingCriteria;
    }

    public String getPassingCriteria() {
        return PassingCriteria;
    }

    public String getNegativeMarks() {
        return NegativeMarks;
    }


    public String getCurrentMarks() {
        return currentMarks;
    }

    public void setCurrentMarks(String currentMarks) {
        this.currentMarks = currentMarks;
    }

    public void setNegativeMarks(String negativeMarks) {
        NegativeMarks = negativeMarks;
    }

    public String getChecked() {
        return Checked;
    }

    public void setChecked(String checked) {
        Checked = checked;
    }



    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResult() {
        return Result;
    }

    public String getAcFlag() {
        return AcFlag;
    }

    public void setAcFlag(String acFlag) {
        AcFlag = acFlag;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedOption(int selectedOption) {
        SelectedOption = selectedOption;
    }

    public int getSelectedOption() {
        return SelectedOption;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public void setCurrentAttempt(int currentAttempt) {
        this.currentAttempt = currentAttempt;
    }


    private List<Question> questions;



    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public Test(){}

    public Test(String testCode,String testName,int time,String TestTotalMarks,String ClassName){
        this.testCode = testCode;
        this.testName = testName;
        this.time=time;
        this.ClassName=ClassName;
        this.TestTotalMarks=TestTotalMarks;

    }

    public void setTestTotalMarks(String testTotalMarks) {
        TestTotalMarks = testTotalMarks;
    }

    public String getTestTotalMarks() {
        return TestTotalMarks;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public String getTestTime() {
        return submitTestTime;
    }

    public void setTestTime(String submitTestTime) {
        this.submitTestTime = submitTestTime;
    }
    public String getUsername() {
        return UserName;
    }

    public void setUsername(String UserName) {
        this.UserName = UserName;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setAttemptQuestion(int totalAttempt){
        this.totalAttempt=totalAttempt;
    }
    public int getAttemptQuestion()
    {   return totalAttempt;
    }
    public void setCorrectAttemptQuestion(int correctAttempt){
        this.correctAttempt=correctAttempt;
    }
    public int getCorrectAttemptQuestion()
    {   return correctAttempt;
    }
    public void setWrongAttemptQuestion(int wrongAttempt){
        this.wrongAttempt=wrongAttempt;
    }
    public int getWrongAttemptQuestion()
    {   return wrongAttempt;
    }

    public void setIsTeacher(String isTeacher) {
            IsTeacher = isTeacher;
    }

    public String getIsTeacher() {
            return IsTeacher;
    }

    public void setClassName(String className) {
        this.ClassName = className;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setAcDate(String acDate) {
        AcDate = acDate;
    }

    public String getAcDate() {
        return AcDate;
    }

    public void setAcFlagForGuest(String acFlagForGuest) {
        AcFlagForGuest = acFlagForGuest;
    }

    public String getAcFlagForGuest() {
        return AcFlagForGuest;
    }
}


