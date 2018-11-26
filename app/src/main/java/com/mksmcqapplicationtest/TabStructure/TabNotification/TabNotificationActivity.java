package com.mksmcqapplicationtest.TabStructure.TabNotification;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;

import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.TabStructure.OtherOld.CreateArrayList;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.util.AppUtility;

public class TabNotificationActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    String[] arrayList;
    Bundle bundle;
    int SelectedTabPosition;
    View view;
    String GroupCode;
    String[] arrayListCode;
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
            getSupportActionBar().setTitle(" Notification");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);

            bundle = getIntent().getExtras();
            if (bundle != null) {
                SelectedTabPosition = bundle.getInt("SelectedTabPosition");
                GroupCode=bundle.getString("GroupCode");
            }
            arrayList = new CreateArrayList().getNotificationArrayList(GroupCode);
            arrayListCode = new CreateArrayList().getExamArrayListCode(GroupCode);

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

            //Code For hide Tab
//        ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
            TabLayout.Tab tab = tabLayout.getTabAt(SelectedTabPosition);
            tab.select();

            Advertise();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(TabNotificationActivity.this,view, "TabNotificationActivity", "OnCreate", e);
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
                String name= getPositionName(position);
                String code=getChildCode(position);
                switch (code) {
                    case "18":
                        TabSendNotification tab1 = new TabSendNotification();
                        return tab1;
                    case "19":
                        TabShowNotification tab2 = new TabShowNotification();
                        return tab2;
                    case "20":
                        TabClearNotification tab3 = new TabClearNotification();
                        return tab3;
                    default:
                        TabSendNotification tab11 = new TabSendNotification();
                        return tab11;
                }
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabNotificationActivity.this,view, "TabNotificationActivity", "getItem", e);
                pc.showCatchException();
                return null;
            }
        }

        private String getPositionName(int position) {
            try {
                String name = arrayList[position];
                return name;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabNotificationActivity.this,view, "TabNotificationActivity", "getPositionName", e);
                pc.showCatchException();
                return "";
            }
        }

        private String getChildCode(int position) {
            try {
                String code = arrayListCode[position];
                return code;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabNotificationActivity.this,view, "TabExamActivity", "getPositionName", e);
                pc.showCatchException();
                return "";
            }
        }

        @Override
        public int getCount() {
            try {
                return arrayList.length;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabNotificationActivity.this,view, "TabNotificationActivity", "getCount", e);
                pc.showCatchException();
                return 0;
            }
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
            PrintCatchException pc = new PrintCatchException(TabNotificationActivity.this,view, "TabNotificationActivity", "Advertise", e);
            pc.showCatchException();
        }

    }

    private void ShowAdvertisement() {
        try {
            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
            interstitialAdvertiesClass.showInterstitialAdver();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(TabNotificationActivity.this,view, "TabNotificationActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }
}
