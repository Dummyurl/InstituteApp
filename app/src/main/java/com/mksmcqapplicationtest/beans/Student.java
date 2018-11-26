package com.mksmcqapplicationtest.beans;


public class Student {
    String StudentName,StudentCode,MasterCode,Result,NotificationFlag,Status,LName,Check,SelectedDate,MarkPresenty,ClassCode,
            Amount,NewPassword,MobileNumber,AcFlag,NotificationId,WhichProfile,AnswerSheet,EducationalYear,UserName,
    NotificationStatus,EmailAddress,Password,IsNotificationForGuest;
    private boolean selected;
    String StudentProfile,whoseImage,Profile,Message,isSelected,Checked,Resultcode,Marks,ResultCode;
int position;

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }

    public String getMarks() {
        return Marks;
    }

//    public void setResultcode(String resultcode) {
//        Resultcode = resultcode;
//    }
//
//    public String getResultcode() {
//        return Resultcode;
//    }

    public String getChecked() {
        return Checked;
    }

    public void setChecked(String checked) {
        Checked = checked;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getAnswerSheet() {
        return AnswerSheet;
    }

    public void setAnswerSheet(String answerSheet) {
        AnswerSheet = answerSheet;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setNotificationId(String notificationId) {
        NotificationId = notificationId;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public void setAcFlag(String acFlag) {
        AcFlag = acFlag;
    }

    public String getAcFlag() {
        return AcFlag;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAmount() {
        return Amount;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResult() {
        return Result;
    }

    public void setMasterCode(String masterCode) {
        MasterCode = masterCode;
    }

    public String getMasterCode() {
        return MasterCode;
    }

    public void setStudentCode(String studentCode) {
        StudentCode = studentCode;
    }

    public String getStudentCode() {
        return StudentCode;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setNotificationFlag(String NotificationFlag) {
        this.NotificationFlag = NotificationFlag;
    }

    public String getNotificationFlag() {
        return NotificationFlag;
    }


    public void setCheck(String check) {
        Check = check;
    }

    public String getCheck() {
        return Check;
    }

    public void setSelectedDate(String selectedDate) {
        SelectedDate = selectedDate;
    }

    public void setMarkPresenty(String markPresenty) {
        MarkPresenty = markPresenty;
    }

    public String getMarkPresenty() {
        return MarkPresenty;
    }

    public String getSelectedDate() {
        return SelectedDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setWhichProfile(String whichProfile) {
        WhichProfile = whichProfile;
    }

    public String getWhichProfile() {
        return WhichProfile;
    }

    public String getNotificationStatus() {
        return NotificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        NotificationStatus = notificationStatus;
    }

    public String getStudentProfile() {
        return StudentProfile;
    }

    public void setStudentProfile(String studentProfile) {
        StudentProfile = studentProfile;
    }

    public String getWhoseImage() {
        return whoseImage;
    }

    public void setWhoseImage(String whoseImage) {
        this.whoseImage = whoseImage;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public void setEducationalYear(String educationYear) {
        EducationalYear = educationYear;
    }

    public String getEducationalYear() {
        return EducationalYear;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setIsNotificationForGuest(String isNotificationForGuest) {
        IsNotificationForGuest = isNotificationForGuest;
    }

    public String getIsNotificationForGuest() {
        return IsNotificationForGuest;
    }
}
