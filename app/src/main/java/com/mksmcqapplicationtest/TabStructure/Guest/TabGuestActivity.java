package com.mksmcqapplicationtest.TabStructure.Guest;

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

public class TabGuestActivity extends AppCompatActivity {

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
            getSupportActionBar().setTitle(" Guest");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);

            bundle = getIntent().getExtras();
            if (bundle != null) {
                SelectedTabPosition = bundle.getInt("SelectedTabPosition");
                GroupCode=bundle.getString("GroupCode");
            }
            arrayList = new CreateArrayList().getGuestArrayList(GroupCode);
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

            TabLayout.Tab tab = tabLayout.getTabAt(SelectedTabPosition);
            tab.select();


        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(TabGuestActivity.this,view, "TabGuestActivity", "OnCreate", e);
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
                String code=getChildCode(position);
                switch (code) {
                    case "21":
                        TabGuestEnableTheoryTest tab1 = new TabGuestEnableTheoryTest();
                        return tab1;
                    case "22":
                        TabGuestEnableExtraTheoryTest tab2 = new TabGuestEnableExtraTheoryTest();
                        return tab2;
                    case "23":
                        TabGuestProfile tab3 = new TabGuestProfile();
                        return tab3;
                    default:
                        TabGuestEnableTheoryTest tab11 = new TabGuestEnableTheoryTest();
                        return tab11;
                }
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabGuestActivity.this,view, "TabGuestActivity", "getItem", e);
                pc.showCatchException();
                return null;
            }
        }

        private String getPositionName(int position) {
            try {
                String name = arrayList[position];
                return name;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabGuestActivity.this,view, "TabGuestActivity", "getPositionName", e);
                pc.showCatchException();
                return "";
            }
        }

        private String getChildCode(int position) {
            try {
                String code = arrayListCode[position];
                return code;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabGuestActivity.this,view, "TabExamActivity", "getPositionName", e);
                pc.showCatchException();
                return "";
            }
        }

        @Override
        public int getCount() {
            try {
                return arrayList.length;
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(TabGuestActivity.this,view, "TabGuestActivity", "getCount", e);
                pc.showCatchException();
                return 0;
            }
        }

    }
}
