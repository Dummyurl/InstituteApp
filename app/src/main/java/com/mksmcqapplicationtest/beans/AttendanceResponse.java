package com.mksmcqapplicationtest.beans;


public class AttendanceResponse {
    String Response;
    String UserName, Id, UserID, EmailId, MobileNumber, ReturnResponce, Password, IsAdmin, NewPassword, OldPassword, IsActive, AttendanceFlag,
            Date, Check, Status;
    String Attendance;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.Id = UserID;
    }


    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String EmailId) {
        this.EmailId = EmailId;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

    public String getReturnResponce() {
        return ReturnResponce;
    }

    public void setReturnResponce(String ReturnResponce) {
        this.ReturnResponce = ReturnResponce;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String OldPassword) {
        this.OldPassword = OldPassword;
    }


    public String getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(String IsAdmin) {
        this.IsAdmin = IsAdmin;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String IsActive) {
        this.IsActive = IsActive;
    }


    public String getMarkPresenty() {
        return AttendanceFlag;
    }

    public void setMarkPresenty(String AttendanceFlag) {
        this.AttendanceFlag = AttendanceFlag;
    }

    public String getDate() {
        return Date;
    }


    public void setDate(String Date) {
        this.Date = Date;
    }

//    public String getCheck() {
//        return Check;
//    }


    public void setCheck(String Check) {
        this.Check = Check;
    }

    public String getAttendance() {
        return Attendance;
    }


    public void setAttendance(String Attendance) {
        this.Attendance = Attendance;
    }
}

