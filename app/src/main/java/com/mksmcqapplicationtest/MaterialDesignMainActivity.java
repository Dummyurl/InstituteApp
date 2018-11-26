package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mksmcqapplicationtest.CarouselView.CarouselView;
import com.mksmcqapplicationtest.CarouselView.ImageListener;
import com.mksmcqapplicationtest.TabStructure.ActiveDeactive.TabEnableExtraTheoryTest;
import com.mksmcqapplicationtest.TabStructure.ActiveDeactive.TabEnableMcqTest;
import com.mksmcqapplicationtest.TabStructure.ActiveDeactive.TabEnableStudent;
import com.mksmcqapplicationtest.TabStructure.ActiveDeactive.TabEnableTheoryTest;
import com.mksmcqapplicationtest.TabStructure.Attendance.TabDayWiseReport;
import com.mksmcqapplicationtest.TabStructure.Attendance.TabMarkAttendance;
import com.mksmcqapplicationtest.TabStructure.Attendance.TabShowAttendance;
import com.mksmcqapplicationtest.TabStructure.Communication.TabChangePassword;
import com.mksmcqapplicationtest.TabStructure.Communication.TabChangePasswordForAll;
import com.mksmcqapplicationtest.TabStructure.Communication.TabQueries;
import com.mksmcqapplicationtest.TabStructure.Communication.TabQueriesGuestStud;
import com.mksmcqapplicationtest.TabStructure.Exam.TabExtraTheoryTest;
import com.mksmcqapplicationtest.TabStructure.Exam.TabGivenTest;
import com.mksmcqapplicationtest.TabStructure.Exam.TabMcqTest;
import com.mksmcqapplicationtest.TabStructure.Exam.TabSendMarks;
import com.mksmcqapplicationtest.TabStructure.Exam.TabShowMarks;
import com.mksmcqapplicationtest.TabStructure.Exam.TabStudentGivenTest;
import com.mksmcqapplicationtest.TabStructure.Exam.TabTheoryTest;
import com.mksmcqapplicationtest.TabStructure.Fees.TabAddFees;
import com.mksmcqapplicationtest.TabStructure.Fees.TabShowFees;
import com.mksmcqapplicationtest.TabStructure.Guest.TabGuestEnableExtraTheoryTest;
import com.mksmcqapplicationtest.TabStructure.Guest.TabGuestEnableTheoryTest;
import com.mksmcqapplicationtest.TabStructure.Guest.TabGuestProfile;
import com.mksmcqapplicationtest.TabStructure.Masters.TabAddQuestionMaster;
import com.mksmcqapplicationtest.TabStructure.Masters.TabPrepareTestMaster;
import com.mksmcqapplicationtest.TabStructure.Masters.TabTestMaster;
import com.mksmcqapplicationtest.TabStructure.Other.TabVideoManual;
import com.mksmcqapplicationtest.TabStructure.TabNotification.TabClearNotification;
import com.mksmcqapplicationtest.TabStructure.TabNotification.TabSendNotification;
import com.mksmcqapplicationtest.TabStructure.TabNotification.TabShowNotification;
import com.mksmcqapplicationtest.TabStructure.Utility.TabMoveGuest;
import com.mksmcqapplicationtest.TabStructure.Utility.TabMoveStudent;
import com.mksmcqapplicationtest.TabStructure.Utility.TabMoveTheoryTest;
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.util.AppUtility;

public class MaterialDesignMainActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    BottomNavigationView bottomNavigation;
    ExpandableListView ex_nav_list;
    ActionBarDrawerToggle mDrawerToggle;
    String title = "";
    Typeface font;
    View parentLayout;
    Boolean ActiveHome = false;
    LinearLayout imgButtonData, imgButtonShowAtten, imgButtonMcqTest, imgButtonSpokenEng;
    TextView txtmovingText;
    ViewPager view_pager;
    LinearLayout LLViewPager;
    ImageView imgShareApp;
    CarouselView carouselView;
    ImageButton imagviewbackpress, imageadd, imageButtonLogout;
    int[] sampleImages = {R.drawable.login, R.drawable.ic_launcher};
    LinearLayout layoutMoreOption1, layoutMoreOption2;
    LinearLayout linearhsExam1, linearhsExam2, linearhsExam3, linearhsExam4, linearhsExam5, linearhsExam6, linearhsExam7;
    LinearLayout linearhsACDC1, linearhsACDC2, linearhsACDC3, linearhsACDC4, linearhsACDC5, linearhsACDC6, linearhsACDC7;
    LinearLayout linearhsUtility1, linearhsUtility2, linearhsUtility3, linearhsUtility4, linearhsUtility5, linearhsUtility6, linearhsUtility7;
    LinearLayout linearhsGuest1, linearhsGuest2, linearhsGuest3, linearhsGuest4, linearhsGuest5, linearhsGuest6, linearhsGuest7;
    TextView edtxthsExam1, edtxthsExam2, edtxthsExam3, edtxthsExam4, edtxthsExam5, edtxthsExam6, edtxthsExam7;
    CustomTextView txthsfontExam1, txthsfontExam2, txthsfontExam3, txthsfontExam4, txthsfontExam5, txthsfontExam6, txthsfontExam7;
    TextView edtxthsACDC1, edtxthsACDC2, edtxthsACDC3, edtxthsACDC4, edtxthsACDC5, edtxthsACDC6, edtxthsACDC7;
    CustomTextView txthsfontACDC1, txthsfontACDC2, txthsfontACDC3, txthsfontACDC4, txthsfontACDC5, txthsfontACDC6, txthsfontACDC7;
    TextView edtxthsUtility1, edtxthsUtility2, edtxthsUtility3, edtxthsUtility4, edtxthsUtility5, edtxthsUtility6, edtxthsUtility7;
    CustomTextView txthsfontUtility1, txthsfontUtility2, txthsfontUtility3, txthsfontUtility4, txthsfontUtility5, txthsfontUtility6, txthsfontUtility7;
    TextView edtxthsGuest1, edtxthsGuest2, edtxthsGuest3;
    CustomTextView txthsfontGuest1, txthsfontGuest2, txthsfontGuest3;
    CustomTextViewBold txtactionbartitle;
    public String BACKPRESS_TAG;
    CustomTextView layout1MoreOptiontxt1, layout1MoreOptiontxt2, layout1MoreOptiontxt3, layout1MoreOptiontxt4,
            layout2MoreOptiontxt1, layout2MoreOptiontxt2, layout2MoreOptiontxt3, layout2MoreOptiontxt4;
    TextView layout1MoreOptionedtxt1, layout1MoreOptionedtxt2, layout1MoreOptionedtxt3, layout1MoreOptionedtxt4,
            layout2MoreOptionedtxt1, layout2MoreOptionedtxt2, layout2MoreOptionedtxt3, layout2MoreOptionedtxt4;
    LinearLayout horizonatal_scroll_exam, horizonatal_scroll_active_deactive, horizonatal_scroll_utility, horizonatal_scroll_guest;
    RelativeLayout relativeLayoutbottomNavigation;
    CustomTextView txtActiveDeactive, txtUtility, txtGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        try {
            title = AppUtility.KEY_TITLE;
            this.context = getApplicationContext();
            font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
            setUI();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MaterialDesignMainActivity.this, parentLayout, "MaterialDesignMainActivity", "OnCreate", e);
            pc.showCatchException();
        }
    }

    private void setUI() {
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        horizonatal_scroll_exam = (LinearLayout) findViewById(R.id.horizonatal_scroll_exam);
        horizonatal_scroll_active_deactive = (LinearLayout) findViewById(R.id.horizonatal_scroll_active_deactive);
        horizonatal_scroll_guest = (LinearLayout) findViewById(R.id.horizonatal_scroll_guest);
        horizonatal_scroll_utility = (LinearLayout) findViewById(R.id.horizonatal_scroll_utility);
        horizonatal_scroll_examId();
        layoutMoreOption1 = (LinearLayout) findViewById(R.id.layout1);
        layoutMoreOption2 = (LinearLayout) findViewById(R.id.layout2);
        MoreOptionId();
        txtActiveDeactive = (CustomTextView) findViewById(R.id.txtActiveDeactive);
        txtUtility = (CustomTextView) findViewById(R.id.txtUtility);
        txtGuest = (CustomTextView) findViewById(R.id.txtGuest);
        switch (AppUtility.IsTeacher) {
            case "T":
                txtActiveDeactive.setVisibility(View.VISIBLE);
                txtUtility.setVisibility(View.VISIBLE);
                txtGuest.setVisibility(View.VISIBLE);

                linearhsExam7.setVisibility(View.VISIBLE);
                txthsfontExam7.setVisibility(View.VISIBLE);
                edtxthsExam7.setVisibility(View.VISIBLE);

                layoutMoreOption1.setVisibility(View.VISIBLE);
                layoutMoreOption2.setVisibility(View.VISIBLE);

                horizonatal_scroll_active_deactiveId();
                horizonatal_scroll_UtilityId();
                horizonatal_scroll_guestId();
                visibleInvisibleMoreOptionTextView();
                visibleHorizontalScrollTextViewExam();
                visibleHorizontalScrollTextViewACDC();
                visibleHorizontalScrollTextViewUtility();
                visibleHorizontalScrollTextViewGuest();
                setTextToMoreOptionTextView();
                break;
            case "S":
                txtActiveDeactive.setVisibility(View.GONE);
                txtUtility.setVisibility(View.GONE);
                txtGuest.setVisibility(View.GONE);
                linearhsExam6.setVisibility(View.GONE);
                txthsfontExam6.setVisibility(View.GONE);
                edtxthsExam6.setVisibility(View.GONE);

                visibleHorizontalScrollTextViewExam();
                visibleInvisibleMoreOptionLayoutForStudent();
                visibleInvisibleTextViewForStudent();
                break;
            case "G":
                visibleInvisibleMoreOptionLayoutForStudent();
                visibleInvisibleTextViewForGuest();
                setTextToTextViewForGuest();
                break;
        }
        txtactionbartitle = (CustomTextViewBold) findViewById(R.id.txtactionbartitle);
        imagviewbackpress = (ImageButton) findViewById(R.id.imagviewbackpress);
        imageadd = (ImageButton) findViewById(R.id.imageadd);
        imageButtonLogout = (ImageButton) findViewById(R.id.imageButtonLogout);
        imageButtonLogout.setVisibility(View.GONE);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListner);
        relativeLayoutbottomNavigation = (RelativeLayout) findViewById(R.id.relativeLayoutbottomNavigation);
        bottomNavigation = relativeLayoutbottomNavigation.findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHome:
                        break;
                    case R.id.bottomNavigationSpokenEnglish:
                        break;
                    case R.id.bottomNavigationProfile:
                        Intent myProfileIntent = new Intent(MaterialDesignMainActivity.this, MyProfileForTeacher.class);
                        startActivity(myProfileIntent);
                        break;
                }
                return true;
            }

        });
        linearhsExam1.setOnClickListener(this);
        linearhsExam2.setOnClickListener(this);
        linearhsExam3.setOnClickListener(this);
        linearhsExam4.setOnClickListener(this);
        linearhsExam5.setOnClickListener(this);
        linearhsExam6.setOnClickListener(this);
        linearhsExam7.setOnClickListener(this);
    }

    private void MoreOptionId() {
        layout1MoreOptiontxt1 = layoutMoreOption1.findViewById(R.id.txt1);
        layout1MoreOptiontxt2 = layoutMoreOption1.findViewById(R.id.txt2);
        layout1MoreOptiontxt3 = layoutMoreOption1.findViewById(R.id.txt3);
        layout1MoreOptiontxt4 = layoutMoreOption1.findViewById(R.id.txt4);
        layout2MoreOptiontxt1 = layoutMoreOption2.findViewById(R.id.txt1);
        layout2MoreOptiontxt2 = layoutMoreOption2.findViewById(R.id.txt2);
        layout2MoreOptiontxt3 = layoutMoreOption2.findViewById(R.id.txt3);
        layout2MoreOptiontxt4 = layoutMoreOption2.findViewById(R.id.txt4);
        layout1MoreOptionedtxt1 = layoutMoreOption1.findViewById(R.id.edtext1);
        layout1MoreOptionedtxt2 = layoutMoreOption1.findViewById(R.id.edtext2);
        layout1MoreOptionedtxt3 = layoutMoreOption1.findViewById(R.id.edtext3);
        layout1MoreOptionedtxt4 = layoutMoreOption1.findViewById(R.id.edtext4);
        layout2MoreOptionedtxt1 = layoutMoreOption2.findViewById(R.id.edtext1);
        layout2MoreOptionedtxt2 = layoutMoreOption2.findViewById(R.id.edtext2);
        layout2MoreOptionedtxt3 = layoutMoreOption2.findViewById(R.id.edtext3);
        layout2MoreOptionedtxt4 = layoutMoreOption2.findViewById(R.id.edtext4);
        layout1MoreOptiontxt1.setTypeface(font);
        layout1MoreOptiontxt2.setTypeface(font);
        layout1MoreOptiontxt3.setTypeface(font);
        layout1MoreOptiontxt4.setTypeface(font);
        layout2MoreOptiontxt1.setTypeface(font);
        layout2MoreOptiontxt2.setTypeface(font);
        layout2MoreOptiontxt3.setTypeface(font);
        layout2MoreOptiontxt4.setTypeface(font);
        layoutMoreOption1.findViewById(R.id.txt4).setOnClickListener(this);
    }

    private void horizonatal_scroll_examId() {
        linearhsExam1 = horizonatal_scroll_exam.findViewById(R.id.linarhs1);
        linearhsExam2 = horizonatal_scroll_exam.findViewById(R.id.linarhs2);
        linearhsExam3 = horizonatal_scroll_exam.findViewById(R.id.linarhs3);
        linearhsExam4 = horizonatal_scroll_exam.findViewById(R.id.linarhs4);
        linearhsExam5 = horizonatal_scroll_exam.findViewById(R.id.linarhs5);
        linearhsExam6 = horizonatal_scroll_exam.findViewById(R.id.linarhs6);
        linearhsExam7 = horizonatal_scroll_exam.findViewById(R.id.linarhs7);
        edtxthsExam1 = horizonatal_scroll_exam.findViewById(R.id.edtxths1);
        edtxthsExam2 = horizonatal_scroll_exam.findViewById(R.id.edtxths2);
        edtxthsExam3 = horizonatal_scroll_exam.findViewById(R.id.edtxths3);
        edtxthsExam4 = horizonatal_scroll_exam.findViewById(R.id.edtxths4);
        edtxthsExam5 = horizonatal_scroll_exam.findViewById(R.id.edtxths5);
        edtxthsExam6 = horizonatal_scroll_exam.findViewById(R.id.edtxths6);
        edtxthsExam7 = horizonatal_scroll_exam.findViewById(R.id.edtxths7);
        txthsfontExam1 = horizonatal_scroll_exam.findViewById(R.id.txthsfont1);
        txthsfontExam2 = horizonatal_scroll_exam.findViewById(R.id.txthsfont2);
        txthsfontExam3 = horizonatal_scroll_exam.findViewById(R.id.txthsfont3);
        txthsfontExam4 = horizonatal_scroll_exam.findViewById(R.id.txthsfont4);
        txthsfontExam5 = horizonatal_scroll_exam.findViewById(R.id.txthsfont5);
        txthsfontExam6 = horizonatal_scroll_exam.findViewById(R.id.txthsfont6);
        txthsfontExam7 = horizonatal_scroll_exam.findViewById(R.id.txthsfont7);
    }

    private void horizonatal_scroll_active_deactiveId() {
        linearhsACDC1 = horizonatal_scroll_active_deactive.findViewById(R.id.linarhs1);
        linearhsACDC2 = horizonatal_scroll_active_deactive.findViewById(R.id.linarhs2);
        linearhsACDC3 = horizonatal_scroll_active_deactive.findViewById(R.id.linarhs3);
        linearhsACDC4 = horizonatal_scroll_active_deactive.findViewById(R.id.linarhs4);
        linearhsACDC5 = horizonatal_scroll_active_deactive.findViewById(R.id.linarhs5);
        linearhsACDC6 = horizonatal_scroll_active_deactive.findViewById(R.id.linarhs6);
        linearhsACDC7 = horizonatal_scroll_active_deactive.findViewById(R.id.linarhs7);
        linearhsACDC5.setVisibility(View.GONE);
        linearhsACDC6.setVisibility(View.GONE);
        linearhsACDC7.setVisibility(View.GONE);

        edtxthsACDC1 = horizonatal_scroll_active_deactive.findViewById(R.id.edtxths1);
        edtxthsACDC2 = horizonatal_scroll_active_deactive.findViewById(R.id.edtxths2);
        edtxthsACDC3 = horizonatal_scroll_active_deactive.findViewById(R.id.edtxths3);
        edtxthsACDC4 = horizonatal_scroll_active_deactive.findViewById(R.id.edtxths4);
        edtxthsACDC5 = horizonatal_scroll_active_deactive.findViewById(R.id.edtxths5);
        edtxthsACDC6 = horizonatal_scroll_active_deactive.findViewById(R.id.edtxths6);
        edtxthsACDC7 = horizonatal_scroll_active_deactive.findViewById(R.id.edtxths7);
        edtxthsACDC5.setVisibility(View.GONE);
        edtxthsACDC6.setVisibility(View.GONE);
        edtxthsACDC7.setVisibility(View.GONE);

        txthsfontACDC1 = horizonatal_scroll_active_deactive.findViewById(R.id.txthsfont1);
        txthsfontACDC2 = horizonatal_scroll_active_deactive.findViewById(R.id.txthsfont2);
        txthsfontACDC3 = horizonatal_scroll_active_deactive.findViewById(R.id.txthsfont3);
        txthsfontACDC4 = horizonatal_scroll_active_deactive.findViewById(R.id.txthsfont4);
        txthsfontACDC5 = horizonatal_scroll_active_deactive.findViewById(R.id.txthsfont5);
        txthsfontACDC6 = horizonatal_scroll_active_deactive.findViewById(R.id.txthsfont6);
        txthsfontACDC7 = horizonatal_scroll_active_deactive.findViewById(R.id.txthsfont7);
        txthsfontACDC5.setVisibility(View.GONE);
        txthsfontACDC6.setVisibility(View.GONE);
        txthsfontACDC7.setVisibility(View.GONE);

    }

    private void horizonatal_scroll_UtilityId() {
        linearhsUtility1 = horizonatal_scroll_utility.findViewById(R.id.linarhs1);
        linearhsUtility2 = horizonatal_scroll_utility.findViewById(R.id.linarhs2);
        linearhsUtility3 = horizonatal_scroll_utility.findViewById(R.id.linarhs3);
        linearhsUtility4 = horizonatal_scroll_utility.findViewById(R.id.linarhs4);
        linearhsUtility5 = horizonatal_scroll_utility.findViewById(R.id.linarhs5);
        linearhsUtility6 = horizonatal_scroll_utility.findViewById(R.id.linarhs6);
        linearhsUtility7 = horizonatal_scroll_utility.findViewById(R.id.linarhs7);
        linearhsUtility6.setVisibility(View.GONE);
        linearhsUtility7.setVisibility(View.GONE);
        edtxthsUtility1 = horizonatal_scroll_utility.findViewById(R.id.edtxths1);
        edtxthsUtility2 = horizonatal_scroll_utility.findViewById(R.id.edtxths2);
        edtxthsUtility3 = horizonatal_scroll_utility.findViewById(R.id.edtxths3);
        edtxthsUtility4 = horizonatal_scroll_utility.findViewById(R.id.edtxths4);
        edtxthsUtility5 = horizonatal_scroll_utility.findViewById(R.id.edtxths5);
        edtxthsUtility6 = horizonatal_scroll_utility.findViewById(R.id.edtxths6);
        edtxthsUtility7 = horizonatal_scroll_utility.findViewById(R.id.edtxths7);
        edtxthsUtility6.setVisibility(View.GONE);
        edtxthsUtility7.setVisibility(View.GONE);
        txthsfontUtility1 = horizonatal_scroll_utility.findViewById(R.id.txthsfont1);
        txthsfontUtility2 = horizonatal_scroll_utility.findViewById(R.id.txthsfont2);
        txthsfontUtility3 = horizonatal_scroll_utility.findViewById(R.id.txthsfont3);
        txthsfontUtility4 = horizonatal_scroll_utility.findViewById(R.id.txthsfont4);
        txthsfontUtility5 = horizonatal_scroll_utility.findViewById(R.id.txthsfont5);
        txthsfontUtility6 = horizonatal_scroll_utility.findViewById(R.id.txthsfont6);
        txthsfontUtility7 = horizonatal_scroll_utility.findViewById(R.id.txthsfont7);
        txthsfontUtility6.setVisibility(View.GONE);
        edtxthsUtility7.setVisibility(View.GONE);
    }

    private void horizonatal_scroll_guestId() {
        linearhsGuest1 = horizonatal_scroll_guest.findViewById(R.id.linarhs1);
        linearhsGuest2 = horizonatal_scroll_guest.findViewById(R.id.linarhs2);
        linearhsGuest3 = horizonatal_scroll_guest.findViewById(R.id.linarhs3);
        linearhsGuest4 = horizonatal_scroll_guest.findViewById(R.id.linarhs4);
        linearhsGuest5 = horizonatal_scroll_guest.findViewById(R.id.linarhs5);
        linearhsGuest6 = horizonatal_scroll_guest.findViewById(R.id.linarhs6);
        linearhsGuest7 = horizonatal_scroll_guest.findViewById(R.id.linarhs7);
        linearhsGuest4.setVisibility(View.GONE);
        linearhsGuest5.setVisibility(View.GONE);
        linearhsGuest6.setVisibility(View.GONE);
        linearhsGuest7.setVisibility(View.GONE);
        edtxthsGuest1 = horizonatal_scroll_guest.findViewById(R.id.edtxths1);
        edtxthsGuest2 = horizonatal_scroll_guest.findViewById(R.id.edtxths2);
        edtxthsGuest3 = horizonatal_scroll_guest.findViewById(R.id.edtxths3);
        txthsfontGuest1 = horizonatal_scroll_guest.findViewById(R.id.txthsfont1);
        txthsfontGuest2 = horizonatal_scroll_guest.findViewById(R.id.txthsfont2);
        txthsfontGuest3 = horizonatal_scroll_guest.findViewById(R.id.txthsfont3);
    }

    private void visibleHorizontalScrollTextViewExam() {
        txthsfontExam1.setTypeface(font);
        txthsfontExam2.setTypeface(font);
        txthsfontExam3.setTypeface(font);
        txthsfontExam4.setTypeface(font);
        txthsfontExam5.setTypeface(font);
        txthsfontExam6.setTypeface(font);
        txthsfontExam7.setTypeface(font);
        txthsfontExam1.setText(R.string.fa_exam);
        txthsfontExam2.setText(R.string.fa_giventestlist);
        txthsfontExam3.setText(R.string.fa_giventestlist);
        txthsfontExam4.setText(R.string.fa_data);
        txthsfontExam5.setText(R.string.fa_data);
        txthsfontExam6.setText(R.string.fa_attendance);
        txthsfontExam7.setText(R.string.fa_share);

        edtxthsExam1.setText(getResources().getString(R.string.MCQ_Exam));
        edtxthsExam2.setText(getResources().getString(R.string.Given_MCQ_Exam));
        edtxthsExam3.setText(getResources().getString(R.string.Student_Given_MCQ_Exam));
        edtxthsExam4.setText(getResources().getString(R.string.Theory_Data_Exam));
        edtxthsExam5.setText(getResources().getString(R.string.Extra_Theory_Data_Exam));
        edtxthsExam6.setText(getResources().getString(R.string.Send_Marks));
        edtxthsExam7.setText(getResources().getString(R.string.Show_Marks));
    }

    private void visibleHorizontalScrollTextViewACDC() {
        txthsfontACDC1.setTypeface(font);
        txthsfontACDC2.setTypeface(font);
        txthsfontACDC3.setTypeface(font);
        txthsfontACDC4.setTypeface(font);
        txthsfontACDC1.setText(R.string.fa_exam);
        txthsfontACDC2.setText(R.string.fa_data);
        txthsfontACDC3.setText(R.string.fa_data);
        txthsfontACDC4.setText(R.string.fa_User);
        edtxthsACDC1.setText(getResources().getString(R.string.MCQ_Exam));
        edtxthsACDC2.setText(getResources().getString(R.string.Theory_Data_Exam));
        edtxthsACDC3.setText(getResources().getString(R.string.Extra_Theory_Data_Exam));
        edtxthsACDC4.setText(getResources().getString(R.string.Student));
    }

    private void visibleHorizontalScrollTextViewUtility() {
        txthsfontUtility1.setTypeface(font);
        txthsfontUtility2.setTypeface(font);
        txthsfontUtility3.setTypeface(font);
        txthsfontUtility4.setTypeface(font);
        txthsfontUtility5.setTypeface(font);
        txthsfontUtility1.setText(R.string.fa_next);
        txthsfontUtility2.setText(R.string.fa_User);
        txthsfontUtility3.setText(R.string.fa_User);
        txthsfontUtility4.setText(R.string.fa_exam);
        txthsfontUtility5.setText(R.string.fa_data);
        edtxthsUtility1.setText(getResources().getString(R.string.Move_Theory_Test));
        edtxthsUtility2.setText(getResources().getString(R.string.Move_Student));
        edtxthsUtility3.setText(getResources().getString(R.string.Move_Guest));
        edtxthsUtility4.setText(getResources().getString(R.string.Add_Test));
        edtxthsUtility5.setText(getResources().getString(R.string.Add_Question));
    }

    private void visibleHorizontalScrollTextViewGuest() {
        txthsfontGuest1.setTypeface(font);
        txthsfontGuest2.setTypeface(font);
        txthsfontGuest3.setTypeface(font);
        txthsfontGuest1.setText(R.string.fa_exam);
        txthsfontGuest2.setText(R.string.fa_data);
        txthsfontGuest3.setText(R.string.fa_data);
        edtxthsGuest1.setText(getResources().getString(R.string.Theory_Test_For_Guest));
        edtxthsGuest2.setText(getResources().getString(R.string.Extra_Theory_Test_For_Guest));
        edtxthsGuest3.setText(getResources().getString(R.string.Guest_Profile));
    }

    private void visibleInvisibleMoreOptionLayoutForStudent() {
        layoutMoreOption1.setVisibility(View.VISIBLE);
    }

    private void visibleInvisibleMoreOptionTextView() {
        layout1MoreOptiontxt1.setVisibility(View.VISIBLE);
        layout1MoreOptiontxt2.setVisibility(View.VISIBLE);
        layout1MoreOptiontxt3.setVisibility(View.VISIBLE);
        layout1MoreOptiontxt4.setVisibility(View.VISIBLE);
        layout2MoreOptiontxt1.setVisibility(View.VISIBLE);
        layout2MoreOptiontxt2.setVisibility(View.VISIBLE);
        layout2MoreOptiontxt3.setVisibility(View.VISIBLE);
        layout2MoreOptiontxt4.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt1.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt2.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt3.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt4.setVisibility(View.VISIBLE);
        layout2MoreOptionedtxt1.setVisibility(View.VISIBLE);
        layout2MoreOptionedtxt2.setVisibility(View.VISIBLE);
        layout2MoreOptionedtxt3.setVisibility(View.VISIBLE);
        layout2MoreOptionedtxt4.setVisibility(View.VISIBLE);
    }


    private void setTextToMoreOptionTextView() {
        layout1MoreOptionedtxt1.setText(getResources().getString(R.string.Show_Attendance));
        layout1MoreOptionedtxt2.setText(getResources().getString(R.string.Mark_Attendance));
        layout1MoreOptionedtxt3.setText(getResources().getString(R.string.Day_Wise_Report));
        layout1MoreOptionedtxt4.setText(getResources().getString(R.string.Add_Fees));
        layout2MoreOptionedtxt1.setText(getResources().getString(R.string.Show_Fees));
        layout2MoreOptionedtxt2.setText(getResources().getString(R.string.Send_Notification));
        layout2MoreOptionedtxt3.setText(getResources().getString(R.string.Show_Attendance));
        layout2MoreOptionedtxt4.setText(getResources().getString(R.string.Clear_Notification));
    }

    private void visibleInvisibleTextViewForStudent() {
        layout1MoreOptiontxt1.setVisibility(View.VISIBLE);
        layout1MoreOptiontxt2.setVisibility(View.VISIBLE);
        layout1MoreOptiontxt3.setVisibility(View.VISIBLE);
        layout1MoreOptiontxt4.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt1.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt2.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt3.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt4.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt1.setText(getResources().getString(R.string.Show_Attendance));
        layout1MoreOptionedtxt2.setText(getResources().getString(R.string.Show_Fees));
        layout1MoreOptionedtxt3.setText(getResources().getString(R.string.Spoken_English));
        layout1MoreOptionedtxt4.setText(getResources().getString(R.string.Show_Notification));
    }

    private void visibleInvisibleTextViewForGuest() {
        layout1MoreOptiontxt1.setVisibility(View.VISIBLE);
        layout1MoreOptiontxt2.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt1.setVisibility(View.VISIBLE);
        layout1MoreOptionedtxt2.setVisibility(View.VISIBLE);
    }

    private void setTextToTextViewForGuest() {
        layout1MoreOptionedtxt1.setText(getResources().getString(R.string.Spoken_English));
        layout1MoreOptionedtxt2.setText(getResources().getString(R.string.Show_Notification));
    }

    ImageListener imageListner = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public void BackpressFromHome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher_logo_c);
        builder.setTitle(getResources().getString(R.string.Titile_Exit_pop_up));
        builder.setMessage(getResources().getString(R.string.messagetext_exit));
        builder.setPositiveButton(getResources().getString(R.string.okbtntext_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.canclebtntext_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @Override
    public void onBackPressed() {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                txtactionbartitle.setText(getResources().getString(R.string.home_title));
                imagviewbackpress.setVisibility(View.GONE);
                imageadd.setVisibility(View.GONE);
            } else {
                BackpressFromHome();
            }
        } catch (Exception e) {
            Log.d("Exception", "" + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pdf_menu, menu);
        menu.findItem(R.id.pdf_menu).setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            ActiveHome = true;
            BACKPRESS_TAG = getResources().getString(R.string.home_title);
            txtactionbartitle.setText(getResources().getString(R.string.home_title));
            imagviewbackpress.setVisibility(View.GONE);
            imageadd.setVisibility(View.GONE);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MaterialDesignMainActivity.this, parentLayout, "MaterialDesignMainActivity", "onStart", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ActiveHome = false;
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.linarhs1:
//                    BACKPRESS_TAG = getResources().getString(R.string.mcq_exam_fragment_title);
//                    TabMcqTest tabMcqTest = new TabMcqTest();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.mcq_exam_fragment_title);
//                    TabEnableMcqTest tabMcqTest = new TabEnableMcqTest();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.theory_test_fragment_title);
//                    TabEnableTheoryTest tabMcqTest = new TabEnableTheoryTest();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Student);
//                    TabEnableStudent tabMcqTest = new TabEnableStudent();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Show_Fees);
//                    TabShowFees tabMcqTest = new TabShowFees();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Show_Attendance);
//                    TabShowAttendance tabMcqTest = new TabShowAttendance();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Mark_Attendance);
//                    TabMarkAttendance tabMcqTest = new TabMarkAttendance();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Extra_Theory_Data_Exam);
//                    TabGuestEnableExtraTheoryTest tabMcqTest = new TabGuestEnableExtraTheoryTest();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Guest_Profile);
//                    TabGuestProfile tabMcqTest = new TabGuestProfile();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Move_Theory_Test);
//                    TabMoveTheoryTest tabMcqTest = new TabMoveTheoryTest();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Move_Guest);
//                    TabMoveGuest tabMcqTest = new TabMoveGuest();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Move_Guest);
//                    TabChangePasswordForAll tabMcqTest = new TabChangePasswordForAll();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Video_Manual_hint);
//                    TabVideoManual tabMcqTest = new TabVideoManual();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();
//
//                    BACKPRESS_TAG = getResources().getString(R.string.Video_Manual_hint);
//                    TabVideoManual tabMcqTest = new TabVideoManual();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Queries_hint);
//                    TabQueriesGuestStud tabMcqTest = new TabQueriesGuestStud();
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.homeContainer, tabMcqTest);
//                    transaction.addToBackStack(BACKPRESS_TAG);
//                    transaction.commit();
                    BACKPRESS_TAG = getResources().getString(R.string.Add_Question);
                    TabAddQuestionMaster tabMcqTest = new TabAddQuestionMaster();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.homeContainer, tabMcqTest);
                    transaction.addToBackStack(BACKPRESS_TAG);
                    transaction.commit();

                    break;

                case R.id.linarhs2:
//                    BACKPRESS_TAG = getResources().getString(R.string.given_mcq_exam_fragment_title);
//                    TabGivenTest tabGivenMCQTest = new TabGivenTest();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.add(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Day_Wise_Report);
//                    TabDayWiseReport tabGivenMCQTest = new TabDayWiseReport();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();
//                    break;

//                    BACKPRESS_TAG = getResources().getString(R.string.Send_Notification);
//                    TabSendNotification tabGivenMCQTest = new TabSendNotification();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Show_Notification);
//                    TabShowNotification tabGivenMCQTest = new TabShowNotification();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Clear_Notification);
//                    TabClearNotification tabGivenMCQTest = new TabClearNotification();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Theory_Data_Exam);
//                    TabGuestEnableTheoryTest tabGivenMCQTest = new TabGuestEnableTheoryTest();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();

//                    BACKPRESS_TAG = getResources().getString(R.string.Theory_Data_Exam);
//                    TabChangePassword tabGivenMCQTest = new TabChangePassword();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();
//                    break;

//                    BACKPRESS_TAG = getResources().getString(R.string.Queries_hint);
//                    TabQueries tabGivenMCQTest = new TabQueries();
//                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
//                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
//                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionGivenMCQTest.commit();
//                    break;

                    BACKPRESS_TAG = getResources().getString(R.string.Add_Test);
                    TabTestMaster tabGivenMCQTest = new TabTestMaster();
                    FragmentManager managerGivenMCQTest = getSupportFragmentManager();
                    FragmentTransaction transactionGivenMCQTest = managerGivenMCQTest.beginTransaction();
                    transactionGivenMCQTest.replace(R.id.homeContainer, tabGivenMCQTest);
                    transactionGivenMCQTest.addToBackStack(BACKPRESS_TAG);
                    transactionGivenMCQTest.commit();
                    break;

                case R.id.linarhs3:
//                    BACKPRESS_TAG = getResources().getString(R.string.student_given_mcq_exam_fragment_title);
//                    TabStudentGivenTest tabStudentGivenMCQTest = new TabStudentGivenTest();
//                    FragmentManager managerStudentGivenMCQTest = getSupportFragmentManager();
//                    FragmentTransaction transactionStudentGivenMCQTest = managerStudentGivenMCQTest.beginTransaction();
//                    transactionStudentGivenMCQTest.add(R.id.homeContainer, tabStudentGivenMCQTest);
//                    transactionStudentGivenMCQTest.addToBackStack(BACKPRESS_TAG);
//                    transactionStudentGivenMCQTest.commit();
//                    break;
                    BACKPRESS_TAG = getResources().getString(R.string.preparetest);
                    TabPrepareTestMaster tabStudentGivenMCQTest = new TabPrepareTestMaster();
                    FragmentManager managerStudentGivenMCQTest = getSupportFragmentManager();
                    FragmentTransaction transactionStudentGivenMCQTest = managerStudentGivenMCQTest.beginTransaction();
                    transactionStudentGivenMCQTest.add(R.id.homeContainer, tabStudentGivenMCQTest);
                    transactionStudentGivenMCQTest.addToBackStack(BACKPRESS_TAG);
                    transactionStudentGivenMCQTest.commit();
                    break;
                case R.id.linarhs4:
                    BACKPRESS_TAG = getResources().getString(R.string.theory_test_fragment_title);
                    TabTheoryTest tabTheoryTest = new TabTheoryTest();
                    FragmentManager managerTheoryTest = getSupportFragmentManager();
                    FragmentTransaction transactionTheoryTest = managerTheoryTest.beginTransaction();
                    transactionTheoryTest.add(R.id.homeContainer, tabTheoryTest);
                    transactionTheoryTest.addToBackStack(BACKPRESS_TAG);
                    transactionTheoryTest.commit();
                    break;
                case R.id.linarhs5:
                    BACKPRESS_TAG = getResources().getString(R.string.extra_theory_test_fragment_title);
                    TabExtraTheoryTest tabExtraTheoryTest = new TabExtraTheoryTest();
                    FragmentManager managerExtraTheoryTest = getSupportFragmentManager();
                    FragmentTransaction transactionExtraTheoryTest = managerExtraTheoryTest.beginTransaction();
                    transactionExtraTheoryTest.add(R.id.homeContainer, tabExtraTheoryTest);
                    transactionExtraTheoryTest.addToBackStack(BACKPRESS_TAG);
                    transactionExtraTheoryTest.commit();
                    break;
                case R.id.linarhs6:
                    BACKPRESS_TAG = getResources().getString(R.string.send_marks_fragment_title);
                    TabSendMarks tabSendMark = new TabSendMarks();
                    FragmentManager managerSendMark = getSupportFragmentManager();
                    FragmentTransaction transactionSendMark = managerSendMark.beginTransaction();
                    transactionSendMark.add(R.id.homeContainer, tabSendMark);
                    transactionSendMark.addToBackStack(BACKPRESS_TAG);
                    transactionSendMark.commit();
                    break;
                case R.id.linarhs7:
                    BACKPRESS_TAG = getResources().getString(R.string.Show_Marks);
                    TabShowMarks tabShowMark = new TabShowMarks();
                    FragmentManager managerShowMark = getSupportFragmentManager();
                    FragmentTransaction transactionShowMark = managerShowMark.beginTransaction();
                    transactionShowMark.add(R.id.homeContainer, tabShowMark);
                    transactionShowMark.addToBackStack(BACKPRESS_TAG);
                    transactionShowMark.commit();
                    break;
                case R.id.txt4:
                    BACKPRESS_TAG = getResources().getString(R.string.add_fees_fragment_title);
                    TabAddFees tabAddFees = new TabAddFees();
                    FragmentManager managerAddFees = getSupportFragmentManager();
                    FragmentTransaction transactionAddFees = managerAddFees.beginTransaction();
                    transactionAddFees.add(R.id.homeContainer, tabAddFees);
                    transactionAddFees.addToBackStack(BACKPRESS_TAG);
                    transactionAddFees.commit();
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MaterialDesignMainActivity.this, parentLayout, "MaterialDesignMainActivity", "onClick", e);
            pc.showCatchException();
        }
    }
}


