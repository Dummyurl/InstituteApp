package com.mksmcqapplicationtest.dataaccess;

import android.content.Context;

import com.android.volley.Request;
import com.mksmcqapplicationtest.ProgressDialog.ProgressDialogShow;
import com.mksmcqapplicationtest.beans.Advertise;
import com.mksmcqapplicationtest.beans.AttendanceResponse;
import com.mksmcqapplicationtest.beans.AudioPlay;
import com.mksmcqapplicationtest.beans.Child;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.Data_Master_Upload;
import com.mksmcqapplicationtest.beans.Fees;
import com.mksmcqapplicationtest.beans.GivenTestListResponse;
import com.mksmcqapplicationtest.beans.Group;
import com.mksmcqapplicationtest.beans.IMEI;
import com.mksmcqapplicationtest.beans.Master;
import com.mksmcqapplicationtest.beans.Notice;
import com.mksmcqapplicationtest.beans.Notification;
import com.mksmcqapplicationtest.beans.OTP;
import com.mksmcqapplicationtest.beans.Question;
import com.mksmcqapplicationtest.beans.References;
import com.mksmcqapplicationtest.beans.Response;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.SendQuery;
import com.mksmcqapplicationtest.beans.SignUpGuest;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.beans.Synchronize;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.beans.TestList;
import com.mksmcqapplicationtest.beans.TimeTable;
import com.mksmcqapplicationtest.beans.User;
import com.mksmcqapplicationtest.beans.VideoManual;
import com.mksmcqapplicationtest.disk.DatabaseHandler;
import com.mksmcqapplicationtest.network.NetworkManager;
import com.mksmcqapplicationtest.network.NetworkResponseListener;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataAccess implements NetworkResponseListener {

    private static final int REQUEST_TESTS = 110, REQUEST_QUESTION = 120, REQUEST_SYNCHRO = 130, REQUEST_CLASS_DATA = 140,
            REQUEST_GET_TEST_LIST = 150, REQUEST_GET_DATA = 160, REQUEST_CLASS_LIST = 170, REQUEST_GET_STUDENT_LIST = 180, REQUEST_MARK_ATTENDANCE = 190,
            REQUEST_GET_ATTENDANCE_REPORT = 200, REQUEST_SYNCHRO_ATTENDANCE = 210, REQUEST_GET_FEES_STRUCTURE = 220, REQUEST_GET_UNINSTALL_APP = 230,
            REQUEST_LOGIN = 240, REQUEST_SUBMIT_TEST_TO_SERVER = 250, REQUEST_GIVEN_TEST_LIST = 260, REQUEST_FOR_ADD_FEES = 270, REQUEST_GIVEN_TEST_QUESTION = 280,
            REQUEST_GET_CHANGE_TEACHER = 290, REQUEST_GET_FEES_DETAIL_STRUCTURE = 300, REQUEST_TIMETABLE_LIST = 310, REQUEST_SHOW_TIMETABLE_LIST = 320,
            REQUEST_FLAG_SETTING_LIST = 330, REQUEST_GET_STUDENT_NAME_ON_DATA = 340, REQUEST_ENABLE_DATA = 360, REQUEST_ENABLE_TEST = 370,
            REQUEST_NOTIFICATION_LIST = 380, REQUEST_GET_ALL_TEST = 390, REQUEST_GET_ALL_STUDENT = 400, REQUEST_ENABLE_STUDENT = 410, REQUEST_CLASS_LIST_ON_LOGIN = 420,
            REQUEST_STUDENT_LIST_ON_LOGIN = 430, REQUEST_GET_INDIVUAL_STATUS_REPORT = 440, REQUEST_SEND_NOTIFICATION = 450, REQUEST_SHOW_NOTIFICATION = 460,
            REQUEST_UPDATE_NOTIFICATION = 470, REQUEST_PROFILE = 480, REQUEST_UPDATE_PROFILE = 490, REQUEST_UPLOAD_MASTER_DATA = 500, REQUEST_AUDIO_LIST_DATA = 510, REQUEST_ENABLE_AUDIO = 520,
            REQUEST_GUEST_AUDIO_LIST_DATA = 530, REQUEST_All_GET_Guest_DATA = 540, REQUEST_GUEST_DATA = 550, REQUEST_SIGN_UP_FOR_GUEST = 560, REQUEST_GET_ALL_GUEST = 570,
            REQUEST_VIDEO_MANUAL_LIST_DATA = 580, REQUEST_MOVE_DATA = 590, REQUEST_MOVE_STUDENT = 600, REQUEST_SHOW_ANWSERSHEET = 610, REQUEST_UPLOAD_ANWSERSHEET = 620,
            REQUEST_CLEAR_NOTIFICATION = 630, REQUEST_SEND_QUERY = 640, REQUEST_UPDATE_QUERY = 650, REQUEST_GET_QUERY = 660, REQUEST_ALL_TEST_LIST_FOR_ENABLE = 670,
            REQUEST_OTP_VERIFICATION = 680, REQUEST_RESEND_EMAIL = 690, REQUEST_SETTING_DATA = 700, REQUEST_THEORY_TEST_MARKS = 710,
            REQUEST_FOR_NOTICE = 720, REQUEST_SEARCH_EMAIL_MOBILE = 730, REQUEST_ADD_THEORY_TEST = 740, REQUEST_GROUP_MENU = 750, REQUEST_CHILD_MENU = 760,
            REQUEST_GET_LEVEL = 770, REQUEST_GET_STAGE = 780, REQUEST_TEST_LIST_ENGLISH_SPEAKING = 790, REQUEST_GIVEN_TEST_LIST_ENGLISH_SPEAKING = 800,
            REQUEST_QUESTION_ENGLISH_SPEAKING = 810, REQUEST_SUBMIT_TEST_TO_SERVER_ENGLISH_SPEAKING = 820, REQUEST_GIVEN_TEST_QUESTION_ENGLISH_SPEAKING = 830,
            REQUEST_ADVERTISE_VISIBLE = 840, REQUEST_REFERENCES_APP = 850, REQUEST_UNIVERSITY_LIST = 860, REQUEST_IMEI_DATA = 870,
            REQUEST_MASTER_ADD_TEST=880,REQUEST_MASTER_ADD_QUESTION=890,REQUEST_MASTER_GET_CATEGORY=900,REQUEST_MASTER_GET_QUESTION=910,
            REQUEST_MASTER_GET_TESTNAME=920,REQUEST_MASTER_GET_PREPARE_TEST=930;

    private Context context;
    List<OTP> otps = null;
    private List<Test> tests = null;
    private List<Fees> fees = null;
    List<User> userResponse;
    private List<Question> questions;
    public List<TestList> testLists;
    public List<GivenTestListResponse> givenTestListResponses;
    public List<AttendanceResponse> response;
    public List<Synchronize> synchronizes;
    private List<TimeTable> TimeTableResponse;
    String result;
    private ResponseListener responseListener;
    List<Group> groupResponse;
    List<Child> childResponse;
    private List<Data> dataResponse;
    List<SignUpGuest> signUpGuests;
    private List<Test> testResponse;
    private List<Class> ClassResponse;
    private List<Response> responses;
    private List<Student> StudentResponse;
    private DatabaseHandler databaseHandler;
    private List<Fees> fessResponse;
    private List<Notification> notificationResponse;
    private List<User> users;
    private List<SendNotification> sendNotifications;
    private List<Data_Master_Upload> dataMasterUploads;
    List<AudioPlay> audioPlaysListResponse;
    List<VideoManual> videoManuals;
    ProgressDialogShow progressDialogClickClass;
    List<SendQuery> sendQueries;
    private List<Notice> NoticeResponse;
    public List<Advertise> advertises;
    private List<References> ReferencesResponse;
    private List<IMEI> imeiResponse;
    private List<Master> masterResponse;

    public DataAccess(Context context, ResponseListener responseListener) {
        this.context = context;
        this.responseListener = responseListener;
        databaseHandler = new DatabaseHandler(context);
    }

    public void Login(String Username, String Password, String ActivityName) {
        NetworkManager.getInstance(context).loginUser(
                REQUEST_LOGIN,
                Request.Method.POST,
                AppUtility.LOGIN_URL,
                this,
                Username,
                Password,
                ActivityName);
    }

    public void AddTheoryTestName(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_ADD_THEORY_TEST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getMasterAddTest(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MASTER_ADD_TEST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }
    public void getMasterQuestion(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MASTER_GET_QUESTION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }
    public void getMasterPrepareTest(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MASTER_GET_PREPARE_TEST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }
    public void getMasterGetTestName(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MASTER_GET_TESTNAME,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }
    public void getMasterCategory(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MASTER_GET_CATEGORY,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }
    public void getMasterAddQuestion(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MASTER_ADD_QUESTION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void getVideoManualList(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_VIDEO_MANUAL_LIST_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void MoveStudent(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MOVE_STUDENT,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void SendQuery(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SEND_QUERY,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void UpdateQuery(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_UPDATE_QUERY,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void ResendEmail(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_RESEND_EMAIL,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void GetQuery(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_QUERY,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void GetSettingData(String ArrayString, String url) {

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SETTING_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void GetIMEIData(String ArrayString, String url) {

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_IMEI_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void GetAdvertiseParameter(String ArrayString, String url) {

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_ADVERTISE_VISIBLE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void verifyOTP(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_OTP_VERIFICATION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void getIndividualStatusReport(String SignUpArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_INDIVUAL_STATUS_REPORT,
                Request.Method.POST,
                url,
                null,
                SignUpArrayString,
                this
        );
    }

    public void getTestList(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_ALL_TEST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getAllTestList(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_ALL_TEST_LIST_FOR_ENABLE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

//    public void getAllStudentList(String url, String ArrayString) {
//        progressDialogClickClass = new ProgressDialogShow
//                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
//        progressDialogClickClass.show();
//
//        NetworkManager.getInstance(context).makeNetworkRequestForData(
//                REQUEST_GET_ALL_STUDENT,
//                Request.Method.POST,
//                url,
//                null,
//                ArrayString,
//                this
//        );
//    }

    public void MoveData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MOVE_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void getAllGuestList(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_ALL_GUEST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getChnagePassworTeacher(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_CHANGE_TEACHER,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getUninstallApp(String ArrayString, String url) {


        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_UNINSTALL_APP,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void GetGivenTestListFromServer(String url, String JSONArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GIVEN_TEST_LIST,
                Request.Method.POST,
                url,
                null,
                JSONArrayString,
                this
        );
    }

//    public void getListOfExams() {
//        loadExamListFromNetwork();
//    }

    public void getListClass() {

        ClassResponse = getClassListFromDisk();
        if (ClassResponse != null) {
            ClassNameListReady(170);
        }
    }

    public void getListStudent(String ClassCode) {

        StudentResponse = getStudentListFromDisk(ClassCode);
        if (StudentResponse != null) {
            StudentNameListReady(500);
        }
//       else
//       {
//
//       }

    }

    public void getListOfFees(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_FEES_STRUCTURE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getListOfDetailFees(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_FEES_DETAIL_STRUCTURE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getQuestionOfGivenTestEnglishSpeaking(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GIVEN_TEST_QUESTION_ENGLISH_SPEAKING,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void getQuestionOfGivenTest(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GIVEN_TEST_QUESTION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getAddFees(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_FOR_ADD_FEES,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getNotice(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_FOR_NOTICE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getClassData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_CLASS_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void SignUpForGuest(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SIGN_UP_FOR_GUEST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getGuestData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GUEST_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getClassNameList(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_CLASS_LIST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void GetReferencesData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_REFERENCES_APP,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getUniversityNameList(String ArrayString, String url) {

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_UNIVERSITY_LIST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void getClassNameListOnLogin(String ArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_CLASS_LIST_ON_LOGIN,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void getGroupMenu(String ArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GROUP_MENU,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getChildMenu(String ArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_CHILD_MENU,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getSearchEmailOrMobile(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SEARCH_EMAIL_MOBILE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getStudentNameListOnLogin(String ArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_STUDENT_LIST_ON_LOGIN,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getStudentNameList(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_STUDENT_LIST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getStudentNameListonDataCode(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_STUDENT_NAME_ON_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getUploadAnwserSheet(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_UPLOAD_ANWSERSHEET,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getShowAnwserSheet(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SHOW_ANWSERSHEET,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void MarkAttendance(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_MARK_ATTENDANCE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getAttendaneReport(String SignUpArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_ATTENDANCE_REPORT,
                Request.Method.POST,
                url,
                null,
                SignUpArrayString,
                this
        );
    }

    public void getListOfTest(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_TEST_LIST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getLevelListData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_LEVEL,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getAudioListStage(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GET_STAGE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getTestListEnglishSpeking(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_TEST_LIST_ENGLISH_SPEAKING,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void GetGivenTestListFromServerEnglishSpeaking(String url, String JSONArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GIVEN_TEST_LIST_ENGLISH_SPEAKING,
                Request.Method.POST,
                url,
                null,
                JSONArrayString,
                this
        );
    }

    public void getQuestionsOfEnglishSpeaking(String testID) {
        questions = getQuestionListFromDiskEnglishSpeaking(testID);

        if (questions == null || questions.size() == 0) {
            loadQuestionsFromNetworkEnglishSpeaking(testID);
        } else {
            questionListReady();
        }
    }

    public List<Question> getQuestionListFromDiskEnglishSpeaking(String testID) {
        AppUtility.KEY_TEST_ID = testID;
        DatabaseHandler handler = new DatabaseHandler(context);
        String data = handler.getData(AppUtility.QUESTION_URL + "," + testID);
        if (data == null)
            return null;
        return parseDataToQuestionsEnglishSpeaking(data);
    }

    private void loadQuestionsFromNetworkEnglishSpeaking(String testID) {
        AppUtility.KEY_TEST_ID = testID;
        NetworkManager.getInstance(context).makeNetworkRequestForString(
                REQUEST_QUESTION_ENGLISH_SPEAKING,
                Request.Method.POST,
                AppUtility.QUESTION_URL_ENGLISH_SPEAKING + "," + testID,
                null,
                this
        );
    }

    public void SubmiteTestToServerEnglishSpeaking(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SUBMIT_TEST_TO_SERVER_ENGLISH_SPEAKING,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getTimeTableList(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_TIMETABLE_LIST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getShowTimeTableList(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SHOW_TIMETABLE_LIST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public List<Test> getSubmittedExams() {//TODO Submited Exam list from database
        return getSubmittedExamListFromDisk();
    }

    public String getDataForSynchronization() {
        String[] Data = databaseHandler.getDataForSyncghronization();
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        if (Data != null) {
            for (int i = 0; i < Data.length; i++) {
                NetworkManager.getInstance(context).Synchronization(
                        REQUEST_SYNCHRO,
                        Request.Method.POST,
                        AppUtility.SYNCHRONIZATION_URL,
                        this,
                        Data[i]
                );
            }
        } else {
            progressDialogClickClass.dismiss();
            return "All Data Synchronnize..No New Data Found";
        }
        progressDialogClickClass.dismiss();
        return "Data Synchronize SuccessFully";
    }

    public void SubmiteTestToServer(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();


        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SUBMIT_TEST_TO_SERVER,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public String getAttendanceForSynchronization() {
        String[] Data = databaseHandler.getAttendanceForSyncghronization();
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        if (Data != null) {
            for (int i = 0; i < Data.length; i++) {
                NetworkManager.getInstance(context).AttendanceSynchronization(
                        REQUEST_SYNCHRO_ATTENDANCE,
                        Request.Method.POST,
                        AppUtility.MARK_ATTENDANCE,
                        this,
                        Data[i]
                );
            }
        } else {
            progressDialogClickClass.dismiss();
            return "All Attendance Synchronnize..No New Data Found";
        }
        progressDialogClickClass.dismiss();
        return "Attendance Synchronize SuccessFully";
    }

    public void getQuestionsOf(String testID) {
        questions = getQuestionListFromDisk(testID);

        if (questions == null || questions.size() == 0) {
            loadQuestionsFromNetwork(testID);
        } else {
            questionListReady();
        }
    }

    private void loadQuestionsFromNetwork(String testID) {
        AppUtility.KEY_TEST_ID = testID;
        NetworkManager.getInstance(context).makeNetworkRequestForString(
                REQUEST_QUESTION,
                Request.Method.POST,
                AppUtility.QUESTION_URL + "," + testID,
                null,
                this
        );
    }

    public void loadExamListFromNetwork(String jsonString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_TESTS,
                Request.Method.POST,
                url,
                null,
                jsonString,
                this
        );
    }

    public void getFlagSettingList(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_FLAG_SETTING_LIST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

//    public void GetAllData(String url, String ArrayString) {
//        progressDialogClickClass = new ProgressDialogShow
//                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
//        progressDialogClickClass.show();
//
//        NetworkManager.getInstance(context).makeNetworkRequestForData(
//                REQUEST_All_GET_DATA,
//                Request.Method.POST,
//                url,
//                null,
//                ArrayString,
//                this
//        );
//    }

    public void GetAllGuestData(String url, String ArrayString) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_All_GET_Guest_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }



    public void EnableDisableData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_ENABLE_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void SendTheoryTestMarks(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_THEORY_TEST_MARKS,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void ClearNotification(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_CLEAR_NOTIFICATION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void EnableDisableAudio(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_ENABLE_AUDIO,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void EnableDisableTest(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_ENABLE_TEST,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void EnableDisableStudent(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_ENABLE_STUDENT,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void sendNotification(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SEND_NOTIFICATION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void showNotification(String ArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_SHOW_NOTIFICATION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void updateNotification(String ArrayString, String url) {

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_UPDATE_NOTIFICATION,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }


    public void getProfile(String ArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_PROFILE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void UpdateProfile(String ArrayString, String url) {
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_UPDATE_PROFILE,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getUploadMasterData(String ArrayString, String url) {

        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_UPLOAD_MASTER_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getAudioListData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_AUDIO_LIST_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    public void getGuestAudioListData(String ArrayString, String url) {
        progressDialogClickClass = new ProgressDialogShow
                (context, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
        progressDialogClickClass.show();
        NetworkManager.getInstance(context).makeNetworkRequestForData(
                REQUEST_GUEST_AUDIO_LIST_DATA,
                Request.Method.POST,
                url,
                null,
                ArrayString,
                this
        );
    }

    private List<Student> parseUpdateProfile(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setResult(jsonObject.getString("Result"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<Test> parseAllTestListEnglishSpeaking(Object data) {
        String response = (String) data;

        try {
            tests = new ArrayList<Test>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Test data1 = new Test();
                data1.setPassingCriteria(jsonObject.getString("PassingCriteria"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setTestCode(jsonObject.getString("TestCode"));
                data1.setTestName(jsonObject.getString("TestName"));
                data1.setTestTotalMarks(jsonObject.getString("TestTotalMarks"));
                data1.setTestTime(jsonObject.getString("Time"));
                data1.setResult(jsonObject.getString("returnValue"));
                data1.setNegativeMarks(jsonObject.getString("NegativeMarks"));

                tests.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tests;
    }

    private List<Test> parseAllTestListForEnable(Object data) {
        String response = (String) data;

        try {
            tests = new ArrayList<Test>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Test data1 = new Test();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setTestName(jsonObject.getString("TestName"));
                data1.setTestCode(jsonObject.getString("TestCode"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setTestTime(jsonObject.getString("TestDuration"));
                data1.setTestTotalMarks(jsonObject.getString("TestTotalMarks"));
                data1.setNegativeMarks(jsonObject.getString("NegativeMarks"));
                data1.setInstruction(jsonObject.getString("Instruction"));

                tests.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tests;
    }

    private List<Student> parseUpdateStudentAnswerSheet(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setResult(jsonObject.getString("Result"));
                data1.setMessage(jsonObject.getString("Message"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<Data> parseMoveDataToClassData(Object data) {
        String response = (String) data;

        try {
            dataResponse = new ArrayList<Data>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data1 = new Data();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                dataResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<Student> parseShowStudentAnswerSheet(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setResult(jsonObject.getString("Result"));
                data1.setAnswerSheet(jsonObject.getString("AnswerSheet"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<Student> parseProfile(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setResult(jsonObject.getString("Result"));
                data1.setStudentName(jsonObject.getString("Name"));
                data1.setMobileNumber(jsonObject.getString("MobileNo"));
                data1.setNewPassword(jsonObject.getString("Password"));
                data1.setEmailAddress(jsonObject.getString("EmailAddress"));
                data1.setProfile(jsonObject.getString("Profile"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<SendNotification> parseSendNotificationFromTeacher(Object data) {
        String response = (String) data;

        try {
            sendNotifications = new ArrayList<SendNotification>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SendNotification data1 = new SendNotification();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                sendNotifications.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendNotifications;
    }

    private List<SendNotification> parseShowNotification(Object data) {
        String response = (String) data;

        try {
            sendNotifications = new ArrayList<SendNotification>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SendNotification data1 = new SendNotification();
                data1.setResult(jsonObject.getString("Result"));
                data1.setNotificationId(jsonObject.getString("NotificationId"));
                data1.setNotificationTitle(jsonObject.getString("NotificationTitle"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                sendNotifications.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendNotifications;
    }

    private List<SendNotification> parseUpdateNotification(Object data) {
        String response = (String) data;

        try {
            sendNotifications = new ArrayList<SendNotification>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SendNotification data1 = new SendNotification();
                data1.setResult(jsonObject.getString("Result"));
                sendNotifications.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendNotifications;
    }


    public List<Class> getClassListFromDisk() {
        DatabaseHandler handler = new DatabaseHandler(context);
        String data = handler.getData(AppUtility.GET_CLASS_NAME_LIST);
        if (data != null)
            return parseClassNameListToData(data);
        return null;
    }

    public List<Student> getStudentListFromDisk(String ClassCode) {
        DatabaseHandler handler = new DatabaseHandler(context);
        String data = handler.getStudentData(AppUtility.GET_ALL_STUDENT_NAME_LIST);
        if (data != null)
            return parseAllStudentNameListOnLoginToData(data);
        return null;
    }

    public List<Question> getQuestionListFromDisk(String testID) {
        AppUtility.KEY_TEST_ID = testID;
        DatabaseHandler handler = new DatabaseHandler(context);
        String data = handler.getData(AppUtility.QUESTION_URL + "," + testID);
        if (data == null)
            return null;
        return parseDataToQuestions(data);
    }


    private List<User> parseDataToLogin(Object data) {
        String response = (String) data;

        try {
            userResponse = new ArrayList<User>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = new User();
                user.setStudentName(jsonObject.getString("StudentName"));
                user.setClassCode(jsonObject.getString("ClassCode"));
                user.setClassName(jsonObject.getString("ClassName"));
                user.setIsTeacher(jsonObject.getString("IsTeacher"));
                user.setStudentCode(jsonObject.getString("StudentCode"));
                user.setResult(jsonObject.getString("returnValue"));
                user.setEmailAddress(jsonObject.getString("EmailAddress"));
                user.setMobileNumber(jsonObject.getString("MobileNumber"));
                user.setResultCode(jsonObject.getString("ResultCode"));
                user.setProfile(jsonObject.getString("Profile"));
                user.setOTP(jsonObject.getString("OTP"));
                userResponse.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userResponse;
    }

    private List<Test> parseDataToTestList(Object data) {
        String response = (String) data;
        try {
            tests = new ArrayList<Test>();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Test test = new Test();
                test.setTestCode(jsonObject.getString("TestCode"));
                test.setTestName(jsonObject.getString("TestName"));
                test.setTestTime(jsonObject.getString("Time"));
                test.setTestTotalMarks(jsonObject.getString("TestTotalMarks"));
                test.setClassName(jsonObject.getString("ClassName"));
                test.setResult(jsonObject.getString("returnValue"));
                test.setResultCode(jsonObject.getString("ResultCode"));
                tests.add(test);
            }
            return tests;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tests;
    }

    private List<Question> parseDataToQuestionsEnglishSpeaking(Object data) {
        String response = (String) data;

        try {
            questions = new ArrayList<Question>();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Question question = new Question();
                question.setId(jsonObject.getString("QuestionCode"));
                question.setQuestionString(jsonObject.getString("Question"));
                question.setBeforImagePath(jsonObject.getString("BeforeQuestionImagePath"));
                question.setAfterImagePath(jsonObject.getString("AfterQuestionImagePath"));
                List<Object> options = new ArrayList<Object>();
                options.add(jsonObject.getString("AnsA"));
                options.add(jsonObject.getString("AnsB"));
                options.add(jsonObject.getString("AnsC"));
                options.add(jsonObject.getString("AnsD"));
                options.add(jsonObject.getString("AnsE"));
                options.add(jsonObject.getString("AnsF"));
                question.setOptions(options);
                question.setCorrectAnswer(jsonObject.getString("CorrectAns"));
                question.setMarks(jsonObject.getInt("Marks"));
                question.setAnswerDescription(jsonObject.getString("AnswerDescription"));
                question.setTestTotalMarks(jsonObject.getString("TestTotalMarks"));
                question.setCategory(jsonObject.getString("Category"));
                question.setPassingCriteria(jsonObject.getString("PassingCriteria"));
                questions.add(question);
//                submitedExamQuestions.add(submitedExamQuestion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    private List<Question> parseDataToQuestions(Object data) {
        String response = (String) data;

        try {
            questions = new ArrayList<Question>();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Question question = new Question();
                question.setId(jsonObject.getString("QuestionCode"));
                question.setQuestionString(jsonObject.getString("Question"));
                question.setBeforImagePath(jsonObject.getString("BeforeQuestionImagePath"));
                question.setAfterImagePath(jsonObject.getString("AfterQuestionImagePath"));
                List<Object> options = new ArrayList<Object>();
                options.add(jsonObject.getString("AnsA"));
                options.add(jsonObject.getString("AnsB"));
                options.add(jsonObject.getString("AnsC"));
                options.add(jsonObject.getString("AnsD"));
                options.add(jsonObject.getString("AnsE"));
                options.add(jsonObject.getString("AnsF"));
                question.setOptions(options);
                question.setCorrectAnswer(jsonObject.getString("CorrectAns"));
                question.setMarks(jsonObject.getInt("Marks"));
                question.setAnswerDescription(jsonObject.getString("AnswerDescription"));
                question.setTestTotalMarks(jsonObject.getString("TestTotalMarks"));
                question.setCategory(jsonObject.getString("Category"));
                question.setAnsAImagePath(jsonObject.getString("AnsAImagePath"));
                question.setAnsBImagePath(jsonObject.getString("AnsBImagePath"));
                question.setAnsCImagePath(jsonObject.getString("AnsCImagePath"));
                question.setAnsDImagePath(jsonObject.getString("AnsDImagePath"));
                question.setAnsEImagePath(jsonObject.getString("AnsEImagePath"));
                question.setAnsFImagePath(jsonObject.getString("AnsFImagePath"));
                List<Object> optionsImages = new ArrayList<Object>();
                optionsImages.add(jsonObject.getString("AnsAImagePath"));
                optionsImages.add(jsonObject.getString("AnsBImagePath"));
                optionsImages.add(jsonObject.getString("AnsCImagePath"));
                optionsImages.add(jsonObject.getString("AnsDImagePath"));
                optionsImages.add(jsonObject.getString("AnsEImagePath"));
                optionsImages.add(jsonObject.getString("AnsFImagePath"));
                question.setOptionImages(optionsImages);
                question.setCorrectOption(jsonObject.getString("CorrectOption"));
                questions.add(question);
//                submitedExamQuestions.add(submitedExamQuestion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    private List<Question> parseGivenTestQuestionsEnglishSpeaking(Object data) {
        String response = (String) data;

        try {
            questions = new ArrayList<Question>();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Question question = new Question();
                question.setId(jsonObject.getString("QuestionCode"));
                question.setQuestionString(jsonObject.getString("Question"));
                question.setBeforImagePath(jsonObject.getString("BeforeQuestionImagePath"));
                question.setAfterImagePath(jsonObject.getString("AfterQuestionImagePath"));
                List<Object> options = new ArrayList<Object>();
                options.add(jsonObject.getString("AnsA"));
                options.add(jsonObject.getString("AnsB"));
                options.add(jsonObject.getString("AnsC"));
                options.add(jsonObject.getString("AnsD"));
                options.add(jsonObject.getString("AnsE"));
                options.add(jsonObject.getString("AnsF"));
                question.setOptions(options);
                question.setSelectedAnswer(jsonObject.getString("SelectedOption"));
                question.setCorrectAnswer(jsonObject.getString("CorrectAns"));
                question.setMarks(jsonObject.getInt("Marks"));
                question.setAnswerDescription(jsonObject.getString("AnswerDescription"));
                question.setTestTotalMarks(jsonObject.getString("TestTotalMarks"));
                question.setCategory(jsonObject.getString("Category"));
                questions.add(question);
//                submitedExamQuestions.add(submitedExamQuestion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    private List<Question> parseGivenTestQuestions(Object data) {
        String response = (String) data;

        try {
            questions = new ArrayList<Question>();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Question question = new Question();
                question.setId(jsonObject.getString("QuestionCode"));
                question.setQuestionString(jsonObject.getString("Question"));
                question.setBeforImagePath(jsonObject.getString("BeforeQuestionImagePath"));
                question.setAfterImagePath(jsonObject.getString("AfterQuestionImagePath"));
                List<Object> options = new ArrayList<Object>();
                options.add(jsonObject.getString("AnsA"));
                options.add(jsonObject.getString("AnsB"));
                options.add(jsonObject.getString("AnsC"));
                options.add(jsonObject.getString("AnsD"));
                options.add(jsonObject.getString("AnsE"));
                options.add(jsonObject.getString("AnsF"));
                question.setOptions(options);
                question.setSelectedAnswer(jsonObject.getString("SelectedOption"));
                question.setCorrectAnswer(jsonObject.getString("CorrectAns"));
                question.setMarks(jsonObject.getInt("Marks"));
                question.setAnswerDescription(jsonObject.getString("AnswerDescription"));
                question.setTestTotalMarks(jsonObject.getString("TestTotalMarks"));
                question.setCategory(jsonObject.getString("Category"));
                question.setAnsAImagePath(jsonObject.getString("AnsAImagePath"));
                question.setAnsBImagePath(jsonObject.getString("AnsBImagePath"));
                question.setAnsCImagePath(jsonObject.getString("AnsCImagePath"));
                question.setAnsDImagePath(jsonObject.getString("AnsDImagePath"));
                question.setAnsEImagePath(jsonObject.getString("AnsEImagePath"));
                question.setAnsFImagePath(jsonObject.getString("AnsFImagePath"));
                List<Object> optionsImages = new ArrayList<Object>();
                optionsImages.add(jsonObject.getString("AnsAImagePath"));
                optionsImages.add(jsonObject.getString("AnsBImagePath"));
                optionsImages.add(jsonObject.getString("AnsCImagePath"));
                optionsImages.add(jsonObject.getString("AnsDImagePath"));
                optionsImages.add(jsonObject.getString("AnsEImagePath"));
                optionsImages.add(jsonObject.getString("AnsFImagePath"));
                question.setOptionImages(optionsImages);
                question.setCorrectOption(jsonObject.getString("CorrectOption"));
                questions.add(question);
//                submitedExamQuestions.add(submitedExamQuestion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    private List<Data> parseDataToClassData(Object data) {
        String response = (String) data;

        try {
            dataResponse = new ArrayList<Data>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data1 = new Data();
                data1.setDataCode(jsonObject.getString("DataCode"));
                data1.setDataName(jsonObject.getString("DataName"));
                data1.setDataText(jsonObject.getString("DataText"));
                data1.setImagePath(jsonObject.getString("DataImagePath"));
                data1.setVideoPath(jsonObject.getString("DataVideoPath"));
                data1.setPDFPath(jsonObject.getString("DataPDFPath"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                dataResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<Data> parseDataGuestData(Object data) {
        String response = (String) data;

        try {
            dataResponse = new ArrayList<Data>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data1 = new Data();
                data1.setDataCode(jsonObject.getString("DataCode"));
                data1.setDataName(jsonObject.getString("DataName"));
                data1.setDataText(jsonObject.getString("DataText"));
                data1.setImagePath(jsonObject.getString("DataImagePath"));
                data1.setVideoPath(jsonObject.getString("DataVideoPath"));
                data1.setPDFPath(jsonObject.getString("DataPDFPath"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setAcFlagGuest(jsonObject.getString("AcFlagGuest"));
                data1.setResult(jsonObject.getString("Result"));
                dataResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<Data> parseGuestDataToClassData(Object data) {
        String response = (String) data;

        try {
            dataResponse = new ArrayList<Data>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data1 = new Data();
                data1.setDataCode(jsonObject.getString("DataCode"));
                data1.setDataName(jsonObject.getString("DataName"));
                data1.setDataText(jsonObject.getString("DataText"));
                data1.setImagePath(jsonObject.getString("DataImagePath"));
                data1.setVideoPath(jsonObject.getString("DataVideoPath"));
                data1.setPDFPath(jsonObject.getString("DataPDFPath"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setAcFlagGuest(jsonObject.getString("AcFlagGuest"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                dataResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<Data> parseEnableDataToClassData(Object data) {
        String response = (String) data;

        try {
            dataResponse = new ArrayList<Data>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data1 = new Data();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                dataResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<Student> parseSendTheoryTestMarks(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<SendNotification> parseClearNotification(Object data) {
        String response = (String) data;

        try {
            sendNotifications = new ArrayList<SendNotification>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SendNotification data1 = new SendNotification();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                sendNotifications.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendNotifications;
    }

    private List<SendQuery> parseSendQuery(Object data) {
        String response = (String) data;

        try {
            sendQueries = new ArrayList<SendQuery>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SendQuery data1 = new SendQuery();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                sendQueries.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendQueries;
    }

    private List<SendQuery> parseGetQuery(Object data) {
        String response = (String) data;

        try {
            sendQueries = new ArrayList<SendQuery>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SendQuery data1 = new SendQuery();
                data1.setQueryCode(jsonObject.getString("QueryCode"));
                data1.setQueryTitle(jsonObject.getString("QueryTitle"));
                data1.setQueryMessage(jsonObject.getString("QueryMessage"));
                data1.setStudentCode(jsonObject.getString("StudentCode"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setTeacherReplayMessage(jsonObject.getString("TeacherReplyMessage"));
                data1.setTeacherReplay(jsonObject.getString("TeacherReplay"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setStudentName(jsonObject.getString("StudentName"));
                data1.setClassName(jsonObject.getString("ClassName"));
                sendQueries.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendQueries;
    }

    private List<AudioPlay> parseEnableAudio(Object data) {
        String response = (String) data;

        try {
            audioPlaysListResponse = new ArrayList<AudioPlay>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AudioPlay data1 = new AudioPlay();
                data1.setResult(jsonObject.getString("Result"));
                audioPlaysListResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioPlaysListResponse;
    }

    private List<Test> parseEnableDataToTestData(Object data) {
        String response = (String) data;

        try {
            testResponse = new ArrayList<Test>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Test data1 = new Test();
                data1.setResult(jsonObject.getString("Result"));
                testResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return testResponse;
    }

    private List<SignUpGuest> parseDataSignUpForGuest(Object data) {
        String response = (String) data;

        try {
            signUpGuests = new ArrayList<SignUpGuest>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SignUpGuest data1 = new SignUpGuest();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setOTP(jsonObject.getString("OTP"));
                signUpGuests.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signUpGuests;
    }

    private List<Student> parseEnableStudent(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setResult(jsonObject.getString("Result"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<TestList> parseListOfTest(Object data) {
        String response = (String) data;

        try {
            testLists = new ArrayList<TestList>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TestList data1 = new TestList();
                data1.setTestCode(jsonObject.getString("TestCode"));
                data1.setTestName(jsonObject.getString("TestName"));
                data1.setTestDuration(jsonObject.getString("TestDuration"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setResult(jsonObject.getString("Result"));
                testLists.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return testLists;
    }

    private List<Data> parseDataToData(Object data) {
        String response = (String) data;

        try {
            dataResponse = new ArrayList<Data>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data1 = new Data();
                data1.setDataText(jsonObject.getString("DataText"));
                data1.setImagePath(jsonObject.getString("DataImagePath"));
                data1.setVideoPath(jsonObject.getString("DataVideoPath"));
                data1.setPDFPath(jsonObject.getString("DataPDFPath"));
                data1.setResult(jsonObject.getString("Result"));
                dataResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<Fees> parseDataToFees(Object data) {
        String response = (String) data;

        try {
            fessResponse = new ArrayList<Fees>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Fees data1 = new Fees();
                data1.setResult(jsonObject.getString("Result"));
                data1.setStudentCode(jsonObject.getString("StudentCode"));
                data1.setStudentName(jsonObject.getString("StudentName"));
                data1.setTotalPaid(jsonObject.getString("TotalPaid"));
                fessResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fessResponse;
    }

    private List<Fees> parseDataToDetailFees(Object data) {
        String response = (String) data;

        try {
            fessResponse = new ArrayList<Fees>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Fees data1 = new Fees();
                data1.setResult(jsonObject.getString("Result"));
                data1.setPaidFees(jsonObject.getString("PaidFees"));
                data1.setPaidDate(jsonObject.getString("PaidDate"));
                fessResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fessResponse;
    }

    private List<Response> parseAddFees(Object data) {
        String response = (String) data;

        try {
            responses = new ArrayList<Response>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Response data1 = new Response();
                data1.setResult(jsonObject.getString("Result"));
                responses.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }

    private List<Class> parseClassNameListToData(Object data) {
        String response = (String) data;

        try {
            ClassResponse = new ArrayList<Class>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Class data1 = new Class();
                data1.setClassCode(jsonObject.getString("ClassCode"));
                data1.setClassName(jsonObject.getString("ClassName"));
                data1.setTotalFees(jsonObject.getString("TotalFees"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                ClassResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ClassResponse;
    }

    private List<Student> parseAllStudentNameListOnLoginToData(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setClassCode(jsonObject.getString("ClassCode"));
                data1.setStudentName(jsonObject.getString("StudentName"));
                data1.setStudentCode(jsonObject.getString("StudentCode"));
                data1.setResult(jsonObject.getString("Result"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<AttendanceResponse> ParseAttendanceReport(Object data) {
        String Getresponse = (String) data;
        try {
            response = new ArrayList<AttendanceResponse>();
            JSONArray jsonArray = new JSONArray(Getresponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AttendanceResponse result = new AttendanceResponse();
                result.setId(jsonObject.getString("UserID"));
                result.setAttendance(jsonObject.getString("Attendance"));
                result.setReturnResponce(jsonObject.getString("Result"));
                result.setDate(jsonObject.getString("Date"));
                response.add(result);
            }
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    private List<Response> parseMarkAttendanceToData(Object data) {
        String response = (String) data;

        try {
            responses = new ArrayList<Response>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Response data1 = new Response();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                responses.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }

    private List<Student> parseStudentNameListToData(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setStudentName(jsonObject.getString("StudentName"));
                data1.setStudentCode(jsonObject.getString("StudentCode"));
                data1.setMobileNumber(jsonObject.getString("MobileNumber"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setNotificationFlag(jsonObject.getString("NotificationFlag"));
                data1.setProfile(jsonObject.getString("Profile"));
                data1.setNewPassword(jsonObject.getString("NewPassword"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<Student> parseStudentListWithMarks(Object data) {
        String response = (String) data;

        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student data1 = new Student();
                data1.setStudentName(jsonObject.getString("StudentName"));
                data1.setStudentCode(jsonObject.getString("StudentCode"));
                data1.setMobileNumber(jsonObject.getString("MobileNumber"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setMarks(jsonObject.getString("Marks"));
                data1.setProfile(jsonObject.getString("Profile"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                StudentResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    private List<TimeTable> parseDataToTimeTable(Object data) {
        String response = (String) data;

        try {
            TimeTableResponse = new ArrayList<TimeTable>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TimeTable data1 = new TimeTable();
                data1.setResult(jsonObject.getString("Result"));
                TimeTableResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return TimeTableResponse;
    }

    private List<TimeTable> parseDataToShowTimeTable(Object data) {
        String response = (String) data;

        try {
            TimeTableResponse = new ArrayList<TimeTable>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TimeTable data1 = new TimeTable();
                data1.setStartTime(jsonObject.getString("StartTime"));
                data1.setEndTime(jsonObject.getString("EndTime"));
                data1.setSubject(jsonObject.getString("Subject"));
                data1.setResult(jsonObject.getString("Result"));
                TimeTableResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return TimeTableResponse;
    }

    private List<Test> parseTestList(Object data) {
        String response = (String) data;

        try {
            tests = new ArrayList<Test>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Test data1 = new Test();
                data1.setTestName(jsonObject.getString("TestName"));
                data1.setTestTime(jsonObject.getString("TestTime"));
                data1.setTestCode(jsonObject.getString("TestCode"));
                data1.setClassName(jsonObject.getString("ClassName"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setResult(jsonObject.getString("Result"));

                tests.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tests;
    }

    private List<Data_Master_Upload> parseDataMasterUpload(Object data) {
        String response = (String) data;

        try {
            dataMasterUploads = new ArrayList<Data_Master_Upload>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data_Master_Upload data1 = new Data_Master_Upload();
                data1.setResult(jsonObject.getString("Result"));

                dataMasterUploads.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMasterUploads;
    }

    private List<AudioPlay> parseAudioListWithStage(Object data) {
        String response = (String) data;

        try {
            audioPlaysListResponse = new ArrayList<AudioPlay>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AudioPlay data1 = new AudioPlay();
                data1.setAudioCode(jsonObject.getString("AudioCode"));
                data1.setAudioName(jsonObject.getString("AudioName"));
                data1.setAudioPath(jsonObject.getString("AudioPath"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setAudioFormula(jsonObject.getString("AudioFormula"));
                data1.setAudioDescription(jsonObject.getString("AudioDescription"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setDirectoryPath(jsonObject.getString("DirectoryPath"));
                data1.setStage(jsonObject.getString("Stage"));
                audioPlaysListResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioPlaysListResponse;
    }

    private List<AudioPlay> parseAudioList(Object data) {
        String response = (String) data;

        try {
            audioPlaysListResponse = new ArrayList<AudioPlay>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AudioPlay data1 = new AudioPlay();
                data1.setAudioCode(jsonObject.getString("AudioCode"));
                data1.setAudioName(jsonObject.getString("AudioName"));
                data1.setAudioPath(jsonObject.getString("AudioPath"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setAudioFormula(jsonObject.getString("AudioFormula"));
                data1.setAudioDescription(jsonObject.getString("AudioDescription"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setDirectoryPath(jsonObject.getString("DirectoryPath"));
                audioPlaysListResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioPlaysListResponse;
    }

    private List<AudioPlay> parseGuestAudioList(Object data) {
        String response = (String) data;

        try {
            audioPlaysListResponse = new ArrayList<AudioPlay>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AudioPlay data1 = new AudioPlay();
                data1.setAudioCode(jsonObject.getString("AudioCode"));
                data1.setAudioName(jsonObject.getString("AudioName"));
                data1.setAudioPath(jsonObject.getString("AudioPath"));
                data1.setAcFlagGuest(jsonObject.getString("AcFlagGuest"));
                data1.setAudioFormula(jsonObject.getString("AudioFormula"));
                data1.setAudioDescription(jsonObject.getString("AudioDescription"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setDirectoryPath(jsonObject.getString("DirectoryPath"));
                audioPlaysListResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioPlaysListResponse;
    }

    private List<Synchronize> parseResultOfSubmitedTestEnglishSpeaking(Object data) {
        String response = (String) data;

        try {
            synchronizes = new ArrayList<Synchronize>();

            JSONObject jsonObject = new JSONObject(response);
            Synchronize data1 = new Synchronize();
            data1.setResult(jsonObject.getString("Result"));
            data1.setResultCode(jsonObject.getString("ResultCode"));
            synchronizes.add(data1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return synchronizes;
    }

    private List<Synchronize> parseResultOfSubmitedTest(Object data) {
        String response = (String) data;

        try {
            synchronizes = new ArrayList<Synchronize>();

            JSONObject jsonObject = new JSONObject(response);
            Synchronize data1 = new Synchronize();
            data1.setResult(jsonObject.getString("Result"));
            synchronizes.add(data1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return synchronizes;
    }

    private List<GivenTestListResponse> parseDataToGivenTestListEnglishSpeaking(Object data) {
        String response = (String) data;

        try {
            givenTestListResponses = new ArrayList<GivenTestListResponse>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GivenTestListResponse data1 = new GivenTestListResponse();
                data1.setAttempt(jsonObject.getString("Attempt"));
                data1.setNegativeMarks(jsonObject.getString("NegativeMarks"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setScore(jsonObject.getString("Score"));
                data1.setSubmittedTestTime(jsonObject.getString("SubmittedTestTime"));
                data1.setTestCode(jsonObject.getString("TestCode"));
                data1.setTestName(jsonObject.getString("TestName"));
                data1.setTime(jsonObject.getString("Time"));
                data1.setReturnValue(jsonObject.getString("returnValue"));

                givenTestListResponses.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return givenTestListResponses;
    }

    private List<GivenTestListResponse> parseDataToGivenTestList(Object data) {
        String response = (String) data;

        try {
            givenTestListResponses = new ArrayList<GivenTestListResponse>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GivenTestListResponse data1 = new GivenTestListResponse();
                data1.setAttempt(jsonObject.getString("Attempt"));
                data1.setClassCode(jsonObject.getString("ClassCode"));
                data1.setClassName(jsonObject.getString("ClassName"));
                data1.setScore(jsonObject.getString("Score"));
                data1.setTestCode(jsonObject.getString("TestCode"));
                data1.setTestName(jsonObject.getString("TestName"));
                data1.setTime(jsonObject.getString("Time"));
                data1.setReturnValue(jsonObject.getString("returnValue"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setSubmittedTestTime(jsonObject.getString("SubmittedTestTime"));
                data1.setNegativeMarks(jsonObject.getString("NegativeMarks"));
                givenTestListResponses.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return givenTestListResponses;
    }

    private List<User> parseChangeTeacherForTeacher(Object data) {
        String response = (String) data;

        try {
            users = new ArrayList<User>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User data1 = new User();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                users.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private List<Notification> parseNotificationList(Object data) {
        String response = (String) data;

        try {
            notificationResponse = new ArrayList<Notification>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Notification data1 = new Notification();
                data1.setNotificationFlag(jsonObject.getString("NotificationFlag"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setShowNotificationFlag(jsonObject.getString("ShowNotificationFlag"));

                notificationResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return notificationResponse;
    }

    private List<TimeTable> parseFlagSetting(Object data) {
        String response = (String) data;

        try {
            TimeTableResponse = new ArrayList<TimeTable>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TimeTable data1 = new TimeTable();
                data1.setResult(jsonObject.getString("Result"));
                TimeTableResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return TimeTableResponse;
    }

//    private List<Student> parseAllStudentNameListToData(Object data) {
//        String response = (String) data;
//
//        try {
//            StudentResponse = new ArrayList<Student>();
//            JSONArray jsonArray = new JSONArray(response);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Student data1 = new Student();
//                data1.setStudentCode(jsonObject.getString("StudentCode"));
//                data1.setStudentName(jsonObject.getString("StudentName"));
//                data1.setNewPassword(jsonObject.getString("NewPassword"));
//                data1.setAcFlag(jsonObject.getString("AcFlag"));
//                data1.setMobileNumber(jsonObject.getString("MobileNumber"));
//                data1.setProfile(jsonObject.getString("Profile"));
//                data1.setResult(jsonObject.getString("Result"));
//                StudentResponse.add(data1);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return StudentResponse;
//    }

    private List<VideoManual> parseVideoManualList(Object data) {
        String response = (String) data;

        try {
            videoManuals = new ArrayList<VideoManual>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                VideoManual data1 = new VideoManual();
                data1.setVideoCode(jsonObject.getString("VideoCode"));
                data1.setVideoName(jsonObject.getString("VideoName"));
                data1.setVideoID(jsonObject.getString("VideoID"));
                data1.setResult(jsonObject.getString("Result"));

                videoManuals.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoManuals;
    }

    private List<SignUpGuest> parseAllGuestListToData(Object data) {
        String response = (String) data;

        try {
            signUpGuests = new ArrayList<SignUpGuest>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SignUpGuest data1 = new SignUpGuest();
                data1.setStudentCode(jsonObject.getString("StudentCode"));
                data1.setStudentName(jsonObject.getString("StudentName"));
                data1.setAcFlag(jsonObject.getString("AcFlag"));
                data1.setMobileNumber(jsonObject.getString("MobileNumber"));
                data1.setPassword(jsonObject.getString("Password"));
                data1.setProfile(jsonObject.getString("Profile"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setEmailAddress(jsonObject.getString("EmailAddress"));
                data1.setAcDate(jsonObject.getString("AcDate"));
                data1.setOTPVerified(jsonObject.getString("OTPVerified"));
                data1.setTotlaCount(jsonObject.getString("TotalCount"));
                data1.setUniversityName(jsonObject.getString("UniversityName"));
//                data1.setEndIndex(jsonObject.getString("EndIndex"));
                signUpGuests.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signUpGuests;
    }

    private List<Data> parseAddDataToClassData(Object data) {
        String response = (String) data;

        try {
            dataResponse = new ArrayList<Data>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data1 = new Data();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                dataResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<Master> parseMasterAddTest(Object data) {
        String response = (String) data;

        try {
            masterResponse = new ArrayList<Master>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Master data1 = new Master();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                masterResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterResponse;
    }

    private List<Master> parseMasterGetCategory(Object data) {
        String response = (String) data;

        try {
            masterResponse = new ArrayList<Master>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Master data1 = new Master();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setCategoryCode(jsonObject.getString("CategoryCode"));
                data1.setCategoryName(jsonObject.getString("CategoryName"));
                masterResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterResponse;
    }
    private List<Master> parseMasterGetTestName(Object data) {
        String response = (String) data;

        try {
            masterResponse = new ArrayList<Master>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Master data1 = new Master();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setTest(jsonObject.getString("Test"));
                data1.setTestCode(jsonObject.getString("TestCode"));
                masterResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterResponse;
    }
    private List<Master> parseMasterGetQuestion(Object data) {
        String response = (String) data;

        try {
            masterResponse = new ArrayList<Master>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Master data1 = new Master();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setQuestion(jsonObject.getString("Question"));
                data1.setQuestionCode(jsonObject.getString("QuestionCode"));
                masterResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterResponse;
    }

    private List<Group> parseGroupData(Object data) {
        String response = (String) data;

        try {
            groupResponse = new ArrayList<Group>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Group data1 = new Group();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setGroupMenuCode(jsonObject.getString("GroupMenuCode"));
                data1.setGroupMenuName(jsonObject.getString("GroupMenuName"));
                data1.setFontAwsome(jsonObject.getString("GroupFontAwesome"));
                groupResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupResponse;
    }

    private List<Child> parseChildData(Object data) {
        String response = (String) data;

        try {
            childResponse = new ArrayList<Child>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Child data1 = new Child();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setGroupMenuCode(jsonObject.getString("GroupMenuCode"));
                data1.setChildMenuName(jsonObject.getString("ChildMenuName"));
                data1.setChildMenuCode(jsonObject.getString("ChildMenuCode"));
                childResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return childResponse;
    }

    private List<Student> ParseIndivualStatusReport(Object data) {
        String Getresponse = (String) data;
        try {
            StudentResponse = new ArrayList<Student>();
            JSONArray jsonArray = new JSONArray(Getresponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Student result = new Student();
                result.setStudentName(jsonObject.getString("StudentName"));
                result.setResult(jsonObject.getString("Result"));
                result.setMobileNumber(jsonObject.getString("MobileNo"));
                result.setStudentCode(jsonObject.getString("StudentCode"));
                StudentResponse.add(result);
            }
            return StudentResponse;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return StudentResponse;
    }

    List<OTP> parseOTPResponse(Object data) {
        String getResponse = (String) data;
        try {
            otps = new ArrayList<OTP>();
            JSONArray jsonArray = new JSONArray(getResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                OTP otp = new OTP();
                otp.setResult(jsonObject.getString("Result"));
                otp.setResultCode(jsonObject.getString("ResultCode"));
                otps.add(otp);
            }
            return otps;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return otps;
    }

    List<OTP> parseSettingDataResponse(Object data) {
        String getResponse = (String) data;
        try {
            otps = new ArrayList<OTP>();
            JSONArray jsonArray = new JSONArray(getResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                OTP otp = new OTP();
                otp.setResult(jsonObject.getString("Result"));
                otp.setResultCode(jsonObject.getString("ResultCode"));
                otp.setExtra(jsonObject.getString("Extra"));
                otp.setParamValue(jsonObject.getString("ParamValue"));
                otp.setParamCode(jsonObject.getString("ParamCode"));
                otp.setSettingCode(jsonObject.getString("SettingCode"));
                otps.add(otp);
            }
            return otps;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return otps;
    }

    List<OTP> parseUninstallDataResponse(Object data) {
        String getResponse = (String) data;
        try {
            otps = new ArrayList<OTP>();
            JSONArray jsonArray = new JSONArray(getResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                OTP otp = new OTP();
                otp.setResult(jsonObject.getString("Result"));
                otp.setResultCode(jsonObject.getString("ResultCode"));
                otp.setUninstall(jsonObject.getString("Uninstall"));
                otp.setNewPackageName(jsonObject.getString("NewPackageName"));

                otps.add(otp);
            }
            return otps;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return otps;
    }

    List<Notice> parseNoticeDataResponse(Object data) {
        String getResponse = (String) data;
        try {
            NoticeResponse = new ArrayList<Notice>();
            JSONArray jsonArray = new JSONArray(getResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Notice notice = new Notice();
                notice.setNoticeCode(jsonObject.getString("NoticeCode"));
                notice.setNoticeImage(jsonObject.getString("NoticeImage"));
                notice.setNoticeSequence(jsonObject.getString("NoticeSequence"));
                notice.setNoticeText(jsonObject.getString("NoticeText"));
                notice.setNoticeTitle(jsonObject.getString("NoticeTitle"));
                notice.setResult(jsonObject.getString("Result"));
                notice.setResultCode(jsonObject.getString("ResultCode"));
                NoticeResponse.add(notice);
            }
            return NoticeResponse;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return NoticeResponse;
    }

    List<AudioPlay> parseLevelesponse(Object data) {
        String getResponse = (String) data;
        try {
            audioPlaysListResponse = new ArrayList<AudioPlay>();
            JSONArray jsonArray = new JSONArray(getResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AudioPlay audioPlay = new AudioPlay();
                audioPlay.setResultCode(jsonObject.getString("ResultCode"));
                audioPlay.setLevelCount(jsonObject.getString("LevelCount"));
                audioPlaysListResponse.add(audioPlay);
            }
            return audioPlaysListResponse;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return audioPlaysListResponse;
    }

    List<Advertise> parseAdveriseResponse(Object data) {
        String getResponse = (String) data;
        try {
            advertises = new ArrayList<Advertise>();
            JSONArray jsonArray = new JSONArray(getResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Advertise advertise = new Advertise();
                advertise.setResult(jsonObject.getString("Result"));
                advertise.setResultCode(jsonObject.getString("ResultCode"));
                advertise.setIsAdvertiseVisible(jsonObject.getString("IsAdvertiseVisible"));
                advertise.setAboveAdvertiesIsVisible(jsonObject.getString("AboveAdvertiesIsVisible"));
                advertise.setAdvertiseVisibleForGuest(jsonObject.getString("AdvertiseVisibleForGuest"));
                advertises.add(advertise);
            }
            return advertises;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return advertises;
    }

    private List<References> parseReferences(Object data) {
        String response = (String) data;
        try {
            ReferencesResponse = new ArrayList<References>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                References data1 = new References();
                data1.setAppCode(jsonObject.getString("AppCode"));
                data1.setAppLogo(jsonObject.getString("AppLogo"));
                data1.setAppName(jsonObject.getString("AppName"));
                data1.setAppPackageName(jsonObject.getString("AppPackageName"));
                data1.setAppShortDetail(jsonObject.getString("AppShortDetail"));
                data1.setAppSequence(jsonObject.getString("AppSequence"));
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                ReferencesResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReferencesResponse;
    }

    private List<SignUpGuest> parseGetUniversityName(Object data) {
        String response = (String) data;

        try {
            signUpGuests = new ArrayList<SignUpGuest>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SignUpGuest data1 = new SignUpGuest();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                data1.setStateCode(jsonObject.getString("StateCode"));
                data1.setUniversityCode(jsonObject.getString("UniversityCode"));
                data1.setUniversityName(jsonObject.getString("UniversityName"));
                signUpGuests.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signUpGuests;
    }

    private List<IMEI> parseGetImeiData(Object data) {
        String response = (String) data;

        try {
            imeiResponse = new ArrayList<IMEI>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                IMEI data1 = new IMEI();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                imeiResponse.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiResponse;
    }


    private void OTPIsReady(int requestcode) {
        responseListener.onResponseWithRequestCode(otps, requestcode);
    }

    private void NoticeIsReady(int requestcode) {
        responseListener.onResponseWithRequestCode(NoticeResponse, requestcode);
    }

    private void testListReady() {
        responseListener.onResponse(tests);
    }

    private void LoginListReady(int requestCode) {
        responseListener.onResponseWithRequestCode(userResponse, requestCode);
    }

    private void GivenTestListReady(int requestCode) {
        responseListener.onResponseWithRequestCode(givenTestListResponses, requestCode);
    }

    private void questionListReady() {
        responseListener.onResponse(questions);
    }

    private void SynchronizationListReady() {
        responseListener.onResponse(result);
    }

    private void SubmitTestToServerReady(int requestCode) {
        responseListener.onResponseWithRequestCode(synchronizes, requestCode);
    }

    public void DataForClassIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(dataResponse, requestCode);
    }
    public void MasterAddTestIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(masterResponse, requestCode);
    }

    public void GroupIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(groupResponse, requestCode);
    }

    public void ChildIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(childResponse, requestCode);
    }

    public void DataOfClass() {
        responseListener.onResponse(dataResponse);
    }

    public void SignUpForGuestIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(signUpGuests, requestCode);
    }

    public void ImeiDataIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(imeiResponse, requestCode);
    }

    public void DataForClassIsReadyWithRequestCode(int requestcode) {
        responseListener.onResponseWithRequestCode(dataResponse, requestcode);
    }

    public void DataForUpdateClassIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(dataResponse, requestCode);
    }

    public void ClearNotificationReady(int requestCode) {
        responseListener.onResponseWithRequestCode(sendNotifications, requestCode);
    }

    public void SendQueryReady(int request) {
        responseListener.onResponseWithRequestCode(sendQueries, request);
    }

    public void AllTestListReady(int request) {
        responseListener.onResponseWithRequestCode(tests, request);
    }

    public void AudioUpdateIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(audioPlaysListResponse, requestCode);
    }

    public void DataMoveClassIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(dataResponse, requestCode);
    }

    public void StudentForUpdateReady(int requestCode) {
        responseListener.onResponseWithRequestCode(StudentResponse, requestCode);
    }

    public void DataForUpdateTestIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(testResponse, requestCode);
    }

    public void DataForFeesIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(fessResponse, requestCode);
    }

    public void DataForAddFeesIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(responses, requestCode);
    }

    public void ClassNameListReady(int requestCode) {
        responseListener.onResponseWithRequestCode(ClassResponse, requestCode);
    }

    public void StudentNameListReady(int requestCode) {
        responseListener.onResponseWithRequestCode(StudentResponse, requestCode);
    }

    public void GuestListReady(int requestCode) {
        responseListener.onResponseWithRequestCode(signUpGuests, requestCode);
    }

    public void VideoManualList(int requestCode) {
        responseListener.onResponseWithRequestCode(videoManuals, requestCode);
    }

    public void StudentAnwserSheetReady(int requestCode) {
        responseListener.onResponseWithRequestCode(StudentResponse, requestCode);
    }

    public void DataMasterUploadReady(int requestCode) {
        responseListener.onResponseWithRequestCode(dataMasterUploads, requestCode);
    }

    public void DataAudioListReady(int requestCode) {
        responseListener.onResponseWithRequestCode(audioPlaysListResponse, requestCode);
    }

    public void ResponseReady(int requestCode) {
        responseListener.onResponseWithRequestCode(responses, requestCode);
    }

    public void ListOfTestReady() {
        responseListener.onResponse(testLists);
    }

    private void AttendanceReportReady() {
        responseListener.onResponse(response);
    }

    private void DayWiseReportReady(int requestCode) {
        responseListener.onResponseWithRequestCode(StudentResponse, requestCode);
    }

    public void DataForChangeTeacherIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(users, requestCode);
    }

    public void DataForAddDetailFeesIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(fessResponse, requestCode);
    }

    public void DataForTimeTableIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(TimeTableResponse, requestCode);
    }

    public void DataForShowTimeTableIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(TimeTableResponse, requestCode);
    }

    public void DataForFlagSettingIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(TimeTableResponse, requestCode);
    }

    public void saveSubmittedTestToDisk(String testid, int attempt, String time, String test) {
        databaseHandler.insertWithExtra(AppUtility.SUBMIT_TEST_URL + "*" + testid + "*" + "/" + attempt + "/" + time, test, 0, AppUtility.KEY_TEST_SUBMIT);
        responses = parseSubmittedTestToDataFromDisk();
        ResponseReady(2500);
    }

    private List<Response> parseSubmittedTestToDataFromDisk() {
        String result = "\"Result\"";
        String colon = ":";
        String msg = "\"Test Result Save SuccessFully\"";
        String resultCode = "\"ResultCode\"";
        String resultCount = "\"1\"";
        String response = "[" + "{" + result + colon + msg + "," + resultCode + colon + resultCount + "}" + "]";
        try {
            responses = new ArrayList<Response>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Response data1 = new Response();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                responses.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }


    public void DataForNotificationListIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(notificationResponse, requestCode);
    }

    public void ResultOfSenfNotificationIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(sendNotifications, requestCode);
    }

    public void DataForTestIsReady(int requestCode) {
        responseListener.onResponseWithRequestCode(tests, requestCode);
    }

    public void advertiseParameterReady(int requestCode) {
        responseListener.onResponseWithRequestCode(advertises, requestCode);
    }

    public void ReferenceParameterReady(int requestCode) {
        responseListener.onResponseWithRequestCode(ReferencesResponse, requestCode);
    }

    @Override
    public void onDataReceived(int requestCode, Object data, String url) {

        if (requestCode == REQUEST_TESTS) {
            progressDialogClickClass.dismiss();
            tests = parseDataToTestList(data);
            testListReady();
        } else if (requestCode == REQUEST_LOGIN) {
            userResponse = parseDataToLogin(data);
            LoginListReady(requestCode);
        } else if (requestCode == REQUEST_QUESTION) {
            questions = parseDataToQuestions(data);
            questionListReady();
        } else if (requestCode == REQUEST_SYNCHRO) {
            //dialog.dismiss();
            SynchronizationListReady();
        } else if (requestCode == REQUEST_CLASS_DATA) {
            progressDialogClickClass.dismiss();
            dataResponse = parseDataToClassData(data);
            DataForClassIsReady(requestCode);
        } else if (requestCode == REQUEST_GET_TEST_LIST) {
            progressDialogClickClass.dismiss();
            testLists = parseListOfTest(data);
            ListOfTestReady();
        } else if (requestCode == REQUEST_GET_DATA) {
            progressDialogClickClass.dismiss();
            dataResponse = parseDataToData(data);
            DataOfClass();
        } else if (requestCode == REQUEST_CLASS_LIST) {
            progressDialogClickClass.dismiss();
            ClassResponse = parseClassNameListToData(data);
            ClassNameListReady(requestCode);
        } else if (requestCode == REQUEST_GET_STUDENT_LIST) {
            progressDialogClickClass.dismiss();
            StudentResponse = parseStudentNameListToData(data);
            StudentNameListReady(requestCode);
        } else if (requestCode == REQUEST_GET_STUDENT_NAME_ON_DATA) {
            progressDialogClickClass.dismiss();
            StudentResponse = parseStudentListWithMarks(data);
            StudentNameListReady(requestCode);
        } else if (requestCode == REQUEST_MARK_ATTENDANCE) {
            progressDialogClickClass.dismiss();
            responses = parseMarkAttendanceToData(data);
            ResponseReady(requestCode);
        } else if (requestCode == REQUEST_GET_ATTENDANCE_REPORT) {

            response = ParseAttendanceReport(data);
            AttendanceReportReady();
        } else if (requestCode == REQUEST_SYNCHRO_ATTENDANCE) {
            SynchronizationListReady();
        } else if (requestCode == REQUEST_GET_FEES_STRUCTURE) {
            progressDialogClickClass.dismiss();
            fessResponse = parseDataToFees(data);
            DataForFeesIsReady(requestCode);
        } else if (requestCode == REQUEST_SUBMIT_TEST_TO_SERVER) {
            progressDialogClickClass.dismiss();
            synchronizes = parseResultOfSubmitedTest(data);
            SubmitTestToServerReady(requestCode);

        } else if (requestCode == REQUEST_FOR_ADD_FEES) {
            progressDialogClickClass.dismiss();
            responses = parseAddFees(data);
            DataForAddFeesIsReady(requestCode);
        } else if (requestCode == REQUEST_GIVEN_TEST_LIST) {
            progressDialogClickClass.dismiss();
            givenTestListResponses = parseDataToGivenTestList(data);
            GivenTestListReady(requestCode);
        } else if (requestCode == REQUEST_GET_CHANGE_TEACHER) {
            progressDialogClickClass.dismiss();
            users = parseChangeTeacherForTeacher(data);
            DataForChangeTeacherIsReady(requestCode);
        } else if (requestCode == REQUEST_GET_FEES_DETAIL_STRUCTURE) {
            progressDialogClickClass.dismiss();
            fessResponse = parseDataToDetailFees(data);
            DataForAddDetailFeesIsReady(requestCode);
        } else if (requestCode == REQUEST_TIMETABLE_LIST) {
            progressDialogClickClass.dismiss();
            TimeTableResponse = parseDataToTimeTable(data);
            DataForTimeTableIsReady(requestCode);
        } else if (requestCode == REQUEST_SHOW_TIMETABLE_LIST) {
            progressDialogClickClass.dismiss();
            TimeTableResponse = parseDataToShowTimeTable(data);
            DataForShowTimeTableIsReady(requestCode);
        } else if (requestCode == REQUEST_FLAG_SETTING_LIST) {
            progressDialogClickClass.dismiss();
            TimeTableResponse = parseFlagSetting(data);
            DataForFlagSettingIsReady(requestCode);
        } else if (requestCode == REQUEST_GIVEN_TEST_QUESTION) {
            progressDialogClickClass.dismiss();
            questions = parseGivenTestQuestions(data);
            questionListReady();

        } else if (requestCode == REQUEST_ENABLE_DATA) {
            progressDialogClickClass.dismiss();
            dataResponse = parseEnableDataToClassData(data);
            DataForUpdateClassIsReady(requestCode);
        } else if (requestCode == REQUEST_THEORY_TEST_MARKS) {
            progressDialogClickClass.dismiss();
            StudentResponse = parseSendTheoryTestMarks(data);
            StudentForUpdateReady(requestCode);
        } else if (requestCode == REQUEST_ENABLE_TEST) {
            progressDialogClickClass.dismiss();
            testResponse = parseEnableDataToTestData(data);
            DataForUpdateTestIsReady(requestCode);
        } else if (requestCode == REQUEST_NOTIFICATION_LIST) {
            notificationResponse = parseNotificationList(data);
            DataForNotificationListIsReady(requestCode);
        } else if (requestCode == REQUEST_GET_ALL_TEST) {
            progressDialogClickClass.dismiss();
            tests = parseTestList(data);
            DataForTestIsReady(requestCode);
        } else if (requestCode == REQUEST_ENABLE_STUDENT) {
            progressDialogClickClass.dismiss();
            StudentResponse = parseEnableStudent(data);
            StudentForUpdateReady(requestCode);
        } else if (requestCode == REQUEST_CLASS_LIST_ON_LOGIN) {
            databaseHandler.insert(url, (String) data, 1);
            ClassResponse = parseClassNameListToData(data);
            ClassNameListReady(requestCode);
        } else if (requestCode == REQUEST_STUDENT_LIST_ON_LOGIN) {
            databaseHandler.insert(url, (String) data, 1);
            StudentResponse = parseAllStudentNameListOnLoginToData(data);
            StudentNameListReady(requestCode);
        } else if (requestCode == REQUEST_GET_INDIVUAL_STATUS_REPORT) {
            progressDialogClickClass.dismiss();
            StudentResponse = ParseIndivualStatusReport(data);
            DayWiseReportReady(requestCode);
        } else if (requestCode == REQUEST_SEND_NOTIFICATION) {
            progressDialogClickClass.dismiss();
            sendNotifications = parseSendNotificationFromTeacher(data);
            ResultOfSenfNotificationIsReady(requestCode);
        } else if (requestCode == REQUEST_SHOW_NOTIFICATION) {
            sendNotifications = parseShowNotification(data);
            ResultOfSenfNotificationIsReady(requestCode);
        } else if (requestCode == REQUEST_UPDATE_NOTIFICATION) {

            sendNotifications = parseUpdateNotification(data);
            ResultOfSenfNotificationIsReady(requestCode);
        } else if (requestCode == REQUEST_PROFILE) {
            StudentResponse = parseProfile(data);
            StudentNameListReady(requestCode);

        } else if (requestCode == REQUEST_UPDATE_PROFILE) {
            StudentResponse = parseUpdateProfile(data);
            StudentNameListReady(requestCode);

        } else if (requestCode == REQUEST_UPLOAD_MASTER_DATA) {
            dataMasterUploads = parseDataMasterUpload(data);
            DataMasterUploadReady(requestCode);

        } else if (requestCode == REQUEST_AUDIO_LIST_DATA) {
            progressDialogClickClass.dismiss();
            audioPlaysListResponse = parseAudioList(data);
            DataAudioListReady(requestCode);

        } else if (requestCode == REQUEST_GUEST_AUDIO_LIST_DATA) {
            progressDialogClickClass.dismiss();
            audioPlaysListResponse = parseGuestAudioList(data);
            DataAudioListReady(requestCode);

        } else if (requestCode == REQUEST_ENABLE_AUDIO) {
            progressDialogClickClass.dismiss();
            audioPlaysListResponse = parseEnableAudio(data);
            DataAudioListReady(requestCode);
        } else if (requestCode == REQUEST_All_GET_Guest_DATA) {
            progressDialogClickClass.dismiss();
            dataResponse = parseGuestDataToClassData(data);
            DataForClassIsReadyWithRequestCode(requestCode);
        } else if (requestCode == REQUEST_GUEST_DATA) {
            progressDialogClickClass.dismiss();
            dataResponse = parseDataGuestData(data);
            DataForClassIsReadyWithRequestCode(requestCode);
        } else if (requestCode == REQUEST_SIGN_UP_FOR_GUEST) {
            progressDialogClickClass.dismiss();
            signUpGuests = parseDataSignUpForGuest(data);
            SignUpForGuestIsReady(requestCode);
        } else if (requestCode == REQUEST_GET_ALL_GUEST) {
            progressDialogClickClass.dismiss();
            signUpGuests = parseAllGuestListToData(data);
            GuestListReady(requestCode);
        } else if (requestCode == REQUEST_VIDEO_MANUAL_LIST_DATA) {
            progressDialogClickClass.dismiss();
            videoManuals = parseVideoManualList(data);
            VideoManualList(requestCode);

        } else if (requestCode == REQUEST_UPLOAD_ANWSERSHEET) {
            progressDialogClickClass.dismiss();
            StudentResponse = parseUpdateStudentAnswerSheet(data);
            StudentAnwserSheetReady(requestCode);
        } else if (requestCode == REQUEST_SHOW_ANWSERSHEET) {
            progressDialogClickClass.dismiss();
            StudentResponse = parseShowStudentAnswerSheet(data);
            StudentAnwserSheetReady(requestCode);
        } else if (requestCode == REQUEST_MOVE_DATA) {
            progressDialogClickClass.dismiss();
            dataResponse = parseMoveDataToClassData(data);
            DataMoveClassIsReady(requestCode);
        } else if (requestCode == REQUEST_MOVE_STUDENT) {
            progressDialogClickClass.dismiss();
            StudentResponse = parseEnableStudent(data);
            StudentForUpdateReady(requestCode);
        } else if (requestCode == REQUEST_CLEAR_NOTIFICATION) {
            progressDialogClickClass.dismiss();
            sendNotifications = parseClearNotification(data);
            ClearNotificationReady(requestCode);
        } else if (requestCode == REQUEST_SEND_QUERY) {
            progressDialogClickClass.dismiss();
            sendQueries = parseSendQuery(data);
            SendQueryReady(requestCode);
        } else if (requestCode == REQUEST_UPDATE_QUERY) {
            progressDialogClickClass.dismiss();
            sendQueries = parseSendQuery(data);
            SendQueryReady(requestCode);
        } else if (requestCode == REQUEST_GET_QUERY) {
            progressDialogClickClass.dismiss();
            sendQueries = parseGetQuery(data);
            SendQueryReady(requestCode);
        } else if (requestCode == REQUEST_ALL_TEST_LIST_FOR_ENABLE) {
            progressDialogClickClass.dismiss();
            tests = parseAllTestListForEnable(data);
            AllTestListReady(requestCode);
        } else if (requestCode == REQUEST_OTP_VERIFICATION) {
            progressDialogClickClass.dismiss();
            otps = parseOTPResponse(data);
            OTPIsReady(requestCode);
        } else if (requestCode == REQUEST_RESEND_EMAIL) {
            progressDialogClickClass.dismiss();
            otps = parseOTPResponse(data);
            OTPIsReady(requestCode);
        } else if (requestCode == REQUEST_SETTING_DATA) {
            otps = parseSettingDataResponse(data);
            OTPIsReady(requestCode);
        } else if (requestCode == REQUEST_FOR_NOTICE) {
            progressDialogClickClass.dismiss();
            NoticeResponse = parseNoticeDataResponse(data);
            NoticeIsReady(requestCode);
        } else if (requestCode == REQUEST_SEARCH_EMAIL_MOBILE) {
            progressDialogClickClass.dismiss();
            otps = parseOTPResponse(data);
            OTPIsReady(requestCode);
        } else if (requestCode == REQUEST_GET_UNINSTALL_APP) {
            otps = parseUninstallDataResponse(data);
            OTPIsReady(requestCode);
        } else if (requestCode == REQUEST_ADD_THEORY_TEST) {
            progressDialogClickClass.dismiss();
            dataResponse = parseAddDataToClassData(data);
            DataForClassIsReady(requestCode);
        } else if (requestCode == REQUEST_GROUP_MENU) {
            groupResponse = parseGroupData(data);
            GroupIsReady(requestCode);
        } else if (requestCode == REQUEST_CHILD_MENU) {
            childResponse = parseChildData(data);
            ChildIsReady(requestCode);
        } else if (requestCode == REQUEST_GET_LEVEL) {
            progressDialogClickClass.dismiss();
            audioPlaysListResponse = parseLevelesponse(data);
            DataAudioListReady(requestCode);
        } else if (requestCode == REQUEST_GET_STAGE) {
            progressDialogClickClass.dismiss();
            audioPlaysListResponse = parseAudioListWithStage(data);
            DataAudioListReady(requestCode);
        } else if (requestCode == REQUEST_TEST_LIST_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            tests = parseAllTestListEnglishSpeaking(data);
            AllTestListReady(requestCode);
        } else if (requestCode == REQUEST_GIVEN_TEST_LIST_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            givenTestListResponses = parseDataToGivenTestListEnglishSpeaking(data);
            GivenTestListReady(requestCode);
        } else if (requestCode == REQUEST_QUESTION_ENGLISH_SPEAKING) {
            questions = parseDataToQuestionsEnglishSpeaking(data);
            questionListReady();
        } else if (requestCode == REQUEST_SUBMIT_TEST_TO_SERVER_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            synchronizes = parseResultOfSubmitedTestEnglishSpeaking(data);
            SubmitTestToServerReady(requestCode);

        } else if (requestCode == REQUEST_GIVEN_TEST_QUESTION_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            questions = parseGivenTestQuestionsEnglishSpeaking(data);
            questionListReady();
        } else if (requestCode == REQUEST_ADVERTISE_VISIBLE) {
            advertises = parseAdveriseResponse(data);
            advertiseParameterReady(requestCode);
        } else if (requestCode == REQUEST_REFERENCES_APP) {
            progressDialogClickClass.dismiss();
            ReferencesResponse = parseReferences(data);
            ReferenceParameterReady(requestCode);
        } else if (requestCode == REQUEST_UNIVERSITY_LIST) {
            signUpGuests = parseGetUniversityName(data);
            SignUpForGuestIsReady(requestCode);
        } else if (requestCode == REQUEST_IMEI_DATA) {
            imeiResponse = parseGetImeiData(data);
            ImeiDataIsReady(requestCode);
        } else if (requestCode == REQUEST_MASTER_ADD_TEST) {
            progressDialogClickClass.dismiss();
            masterResponse = parseMasterAddTest(data);
            MasterAddTestIsReady(requestCode);
        } else if (requestCode == REQUEST_MASTER_ADD_QUESTION) {
            progressDialogClickClass.dismiss();
            masterResponse = parseMasterAddTest(data);
            MasterAddTestIsReady(requestCode);
        }else if (requestCode == REQUEST_MASTER_GET_CATEGORY) {
            progressDialogClickClass.dismiss();
            masterResponse = parseMasterGetCategory(data);
            MasterAddTestIsReady(requestCode);
        }else if (requestCode == REQUEST_MASTER_GET_QUESTION) {
            progressDialogClickClass.dismiss();
            masterResponse = parseMasterGetQuestion(data);
            MasterAddTestIsReady(requestCode);
        }else if (requestCode == REQUEST_MASTER_GET_TESTNAME) {
            progressDialogClickClass.dismiss();
            masterResponse = parseMasterGetTestName(data);
            MasterAddTestIsReady(requestCode);
        } else if (requestCode == REQUEST_MASTER_GET_PREPARE_TEST) {
            progressDialogClickClass.dismiss();
            masterResponse = parseMasterAddTest(data);
            MasterAddTestIsReady(requestCode);
        }
//        insert into db
//        databaseHandler.insert(url, (String) data, 1);
    }


    @Override
    public void onDataFailed(int requestCode, Object error) {
        String Error = String.valueOf(error);
        if (requestCode == REQUEST_TESTS) {
            progressDialogClickClass.dismiss();
            responseListener.noResponse(Error);
        } else if (requestCode == REQUEST_QUESTION) {
            responseListener.noResponse(Error);
        } else if (requestCode == REQUEST_CLASS_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_TEST_LIST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_CLASS_LIST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_STUDENT_LIST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_STUDENT_NAME_ON_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_MARK_ATTENDANCE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_ATTENDANCE_REPORT) {
            OnError(Error);
        } else if (requestCode == REQUEST_GET_FEES_STRUCTURE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SUBMIT_TEST_TO_SERVER) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_FOR_ADD_FEES) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GIVEN_TEST_LIST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_CHANGE_TEACHER) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_FEES_DETAIL_STRUCTURE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_LOGIN) {
            OnError(Error);
        } else if (requestCode == REQUEST_TIMETABLE_LIST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SHOW_TIMETABLE_LIST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_FLAG_SETTING_LIST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GIVEN_TEST_QUESTION) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_ENABLE_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_THEORY_TEST_MARKS) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_ENABLE_TEST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_NOTIFICATION_LIST) {
            OnError(Error);
        } else if (requestCode == REQUEST_GET_ALL_TEST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_CLASS_LIST_ON_LOGIN) {
            OnError(Error);
        } else if (requestCode == REQUEST_STUDENT_LIST_ON_LOGIN) {
            OnError(Error);
        } else if (requestCode == REQUEST_SYNCHRO_ATTENDANCE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_INDIVUAL_STATUS_REPORT) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SEND_NOTIFICATION) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SHOW_NOTIFICATION) {
            OnError(Error);
        } else if (requestCode == REQUEST_UPDATE_NOTIFICATION) {
            OnError(Error);
        } else if (requestCode == REQUEST_PROFILE) {
            OnError(Error);
        } else if (requestCode == REQUEST_UPDATE_PROFILE) {
            OnError(Error);
        } else if (requestCode == REQUEST_UPLOAD_MASTER_DATA) {
            OnError(Error);
        } else if (requestCode == REQUEST_AUDIO_LIST_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GUEST_AUDIO_LIST_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_ENABLE_AUDIO) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_All_GET_Guest_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GUEST_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SIGN_UP_FOR_GUEST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_ALL_GUEST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_VIDEO_MANUAL_LIST_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_UPLOAD_ANWSERSHEET) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SHOW_ANWSERSHEET) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_MOVE_DATA) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_MOVE_STUDENT) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_CLEAR_NOTIFICATION) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SEND_QUERY) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_UPDATE_QUERY) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_QUERY) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_ALL_TEST_LIST_FOR_ENABLE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_OTP_VERIFICATION) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_RESEND_EMAIL) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SETTING_DATA) {
            OnError(Error);
        } else if (requestCode == REQUEST_ENABLE_STUDENT) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_FOR_NOTICE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_SEARCH_EMAIL_MOBILE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_ADD_THEORY_TEST) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_UNINSTALL_APP) {
            OnError(Error);
        } else if (requestCode == REQUEST_GROUP_MENU) {
            OnError(Error);
        } else if (requestCode == REQUEST_CHILD_MENU) {
            OnError(Error);
        } else if (requestCode == REQUEST_GET_LEVEL) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GET_STAGE) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_TEST_LIST_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GIVEN_TEST_LIST_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_QUESTION_ENGLISH_SPEAKING) {
            responseListener.noResponse(Error);
        } else if (requestCode == REQUEST_SUBMIT_TEST_TO_SERVER_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_GIVEN_TEST_QUESTION_ENGLISH_SPEAKING) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_ADVERTISE_VISIBLE) {
            OnError(Error);
        } else if (requestCode == REQUEST_REFERENCES_APP) {
            progressDialogClickClass.dismiss();
            OnError(Error);
        } else if (requestCode == REQUEST_UNIVERSITY_LIST) {
            OnError(Error);
        } else if (requestCode == REQUEST_IMEI_DATA) {
            OnError(Error);
        } else if (requestCode == REQUEST_MASTER_ADD_TEST) {
            OnError(Error);
        } else if (requestCode == REQUEST_MASTER_ADD_QUESTION) {
            OnError(Error);
        } else if (requestCode == REQUEST_MASTER_GET_CATEGORY) {
            OnError(Error);
        } else if (requestCode == REQUEST_MASTER_GET_QUESTION) {
            OnError(Error);
        }else if (requestCode == REQUEST_MASTER_GET_TESTNAME) {
            OnError(Error);
        }else if (requestCode == REQUEST_MASTER_GET_PREPARE_TEST) {
            OnError(Error);
        }

    }

    public void OnError(String error) {
        responseListener.noResponse(error);
    }

    public List<Test> getSubmittedExamListFromDisk() {//TODO Submitted exam list from database
        return databaseHandler.getSubmittedTests();
    }


    public int getAttemptCount(String testId) {
        return databaseHandler.getCountFor(testId);
    }

    public void saveAttendanceToDisk(String classId, JSONArray jsonArray) {
        databaseHandler.insertAttendance(AppUtility.MARK_ATTENDANCE + "*" + classId, String.valueOf(jsonArray), "1");
        responses = parseMarkAttendanceToDataFromDisk();
        ResponseReady(1900);
    }

    private List<Response> parseMarkAttendanceToDataFromDisk() {
        String result = "\"Result\"";
        String colon = ":";
        String msg = "\"Attendance Save Sucessfully\"";
        String resultCode = "\"ResultCode\"";
        String resultCount = "\"1\"";
        String response = "[" + "{" + result + colon + msg + "," + resultCode + colon + resultCount + "}" + "]";
        try {
            responses = new ArrayList<Response>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Response data1 = new Response();
                data1.setResult(jsonObject.getString("Result"));
                data1.setResultCode(jsonObject.getString("ResultCode"));
                responses.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }


}

