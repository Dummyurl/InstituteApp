package com.mksmcqapplicationtest.TabStructure.Exam;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.mksmcqapplicationtest.NotAuthorizedActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;

import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.TabStructure.OtherOld.CreateArrayList;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.util.AppUtility;

public class TabExamActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    String[] arrayList;
    String[] arrayListCode;
    Bundle bundle;
    int SelectedTabPosition;
    String GroupCode;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_tab);
        try {
            view = findViewById(android.R.id.content);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Exam");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);
            bundle = getIntent().getExtras();
            if (bundle != null) {
                SelectedTabPosition = bundle.getInt("SelectedTabPosition");
                GroupCode = bundle.getString("GroupCode");
            }
            arrayList = new CreateArrayList().getExamArrayList(GroupCode);
            arrayListCode = new CreateArrayList().getExamArrayListCode(GroupCode);
            if (arrayList == null) {
                NotAuthorizedActivity notAuthorizedActivity = new NotAuthorizedActivity();
                notAuthorizedActivity.ShowDailog(TabExamActivity.this);
            }
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            for (int i = 0; i < arrayList.length; i++) {
                tabLayout.addTab(tabLayout.newTab().setText(arrayList[i]), i);
            }
            if (arrayList.length < 3) {
                tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
            }

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            TabLayout.Tab tab = tabLayout.getTabAt(SelectedTabPosition);
            tab.select();

            Advertise();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(TabExamActivity.this,view, "TabExamActivity", "OnCreate", e);
            pc.showCatchException();
        }
    }

    private void Advertise() {
        try {
            if (AppUtility.IsTeacher.equals("G")) {
                //For Advertise
                if (AppUtility.IsAdvertiesVisibleForGuest) {
                    ShowAdvertisement();
                } else if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            } else {
                if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(TabExamActivity.this,view, "TabExamActivity", "Advertise", e);
            pc.showCatchException();
        }

    }

    private void ShowAdvertisement() {
        try {
            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
            interstitialAdvertiesClass.showInterstitialAdver();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(TabExamActivity.this,view, "TabExamActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            try {
                String name = getPositionName(position);
                String code = getChildCode(position);
                switch (code) {
                    case "1":
                        TabMcqTest tab1 = new TabMcqTest();
                        return tab1;
                    case "2":
                        TabGivenTest tab2 = new TabGivenTest();
                        return tab2;
                    case "3":
                        TabStudentGivenTest tab3 = new TabStudentGivenTest();
                        return tab3;
                    case "4":
                        TabTheoryTest tab4 = new TabTheoryTest();
                        return tab4;
                    case "5":
                        TabExtraTheoryTest tab5 = new TabExtraTheoryTest();
                        return tab5;
                    case "6":
                        TabSendMarks tab6 = new TabSendMarks();
                        return tab6;
                    case "7":
                        TabShowMarks tab7 = new TabShowMarks();
                        return tab7;
                    default:
                        TabMcqTest tab11 = new TabMcqTest();
                        return tab11;
                }
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabExamActivity.this,view, "TabExamActivity", "getItem", e);
                pc.showCatchException();
                return null;
            }
        }

        private String getPositionName(int position) {
            try {
                String name = arrayList[position];
                return name;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabExamActivity.this,view, "TabExamActivity", "getPositionName", e);
                pc.showCatchException();
                return "";
            }
        }

        private String getChildCode(int position) {
            try {
                String code = arrayListCode[position];
                return code;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabExamActivity.this,view, "TabExamActivity", "getPositionName", e);
                pc.showCatchException();
                return "";
            }
        }

        @Override
        public int getCount() {
            try {
                return arrayList.length;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabExamActivity.this,view, "TabExamActivity", "getCount", e);
                pc.showCatchException();
                return 0;
            }
        }

    }
}
