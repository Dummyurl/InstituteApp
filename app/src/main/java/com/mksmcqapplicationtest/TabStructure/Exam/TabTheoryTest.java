package com.mksmcqapplicationtest.TabStructure.Exam;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.DataListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class TabTheoryTest extends Fragment implements ResponseListener, SearchView.OnQueryTextListener {

    View view;
    RecyclerView recyclerViewAllData;
    String ClassDataJSONString;
    List<Data> datas;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Class> classResponse;
    Spinner spinnerForClass;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString, ClassCodeForNetworkcall;
    int count1 = 0;
    int j1 = 0;
    DataListAdapter adapter;
    TextView txtCount;
    Toast toast;
    android.support.v7.widget.SearchView searchViewAndroidActionBar;
    TextView txtmovingText;
    CustomTextViewBold txtactionbartitle;
    LinearLayout layoutspinnerofclass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_theory_test, container, false);

        try {
            setUI();

            recyclerViewAllData.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewAllData.setLayoutManager(layoutManager);
            recyclerViewAllData.setItemAnimator(new DefaultItemAnimator());
            txtCount.setVisibility(View.GONE);
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                CheckForUserType();
            }
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    txtCount.setVisibility(View.GONE);
                    if (count1 != 0) {
                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                        recyclerViewAllData.setAdapter(null);


                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerViewAllData.setAdapter(null);
                    }
                }
            });

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {

        layoutspinnerofclass = view.findViewById(R.id.layoutspinnerofclass);
        txtmovingText = (TextView) view.findViewById(R.id.txtmovingText);
        txtCount = (TextView) view.findViewById(R.id.txtCount);
        spinnerForClass = (Spinner) view.findViewById(R.id.spinnerForClass);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewAllData = (RecyclerView) view.findViewById(R.id.recyclerViewAllData);
        txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.theory_test_fragment_title));

        ImageButton imageadd = (ImageButton) getActivity().findViewById(R.id.imageadd);
        imageadd.setVisibility(View.GONE);

        ImageButton imageButtonBackPress = (ImageButton) getActivity().findViewById(R.id.imagviewbackpress);
        imageButtonBackPress.setVisibility(View.VISIBLE);
        imageButtonBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void CheckForUserType() {
        try {
            NetWorkCallForClass();
            if (AppUtility.IsTeacher.equals("G")) {
                //For Moving Text
                txtmovingText.setVisibility(View.VISIBLE);
                Animation animationToLeft = new TranslateAnimation(1000, 0, 0, 0);
                animationToLeft.setDuration(12000);
                animationToLeft.setRepeatMode(Animation.RESTART);
                animationToLeft.setRepeatCount(Animation.INFINITE);
                txtmovingText.setAnimation(animationToLeft);
                txtmovingText.setText(R.string.MoveTextGuestSide);


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
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "CheckForUserType", e);
            pc.showCatchException();
        }
    }

    private void ShowAdvertisement() {
        try {
//            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//            interstitialAdvertiesClass.showInterstitialAdver();

            AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), view, R.id.adView);
            advertiesClass.ShowAdver();

            if (AppUtility.AboveIsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass1 = new AdvertiesClassForFragment(getContext(), view, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }


    private void NetWorkCallForClass() {
        try {
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                ClassNameJSONString = new CreateJsonFunction().CreateJSONForClass();
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getClassNameList(ClassNameJSONString, AppUtility.GET_CLASS_NAME_LIST);
            } else {
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getListClass();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }

    private void NetWorkCall() {
        if (AppUtility.IsTeacher.equals("G")) {
            NeteorkCallForGuest();
        } else {
            NeteorkCallForTeacherStud();
        }


    }

    private void NeteorkCallForGuest() {
        try {
            CreateJSON();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getClassData(ClassDataJSONString, AppUtility.GET_CLASS_EXTRA_DATA);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "NeteorkCallForGuest", e);
            pc.showCatchException();
        }
    }

    private void CreateJSON() {
        try {
            Gson gson = new Gson();
            Data dataresponse = new Data();
            dataresponse.setClassCode(ClassCodeForNetworkcall);
            dataresponse.setUserName(AppUtility.KEY_USERNAME);
            dataresponse.setExtra("0");
            String jsonData = gson.toJson(dataresponse);
            ClassDataJSONString = "[" + jsonData + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "CreateJSON", e);
            pc.showCatchException();
        }
    }

    private void NeteorkCallForTeacherStud() {
        try {
            ClassDataJSONString = new CreateJsonFunction().CreateJSONForGetData(ClassCodeForNetworkcall, "active", "0");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getClassData(ClassDataJSONString, AppUtility.GET_DATA_OF_CLASS);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "NeteorkCallForTeacherStud", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            switch (requestCode) {
                case 140:
                    recyclerViewAllData.setVisibility(View.VISIBLE);
                    datas = (List<Data>) data;
                    try {

                        if (datas.get(0).getResultCode().equals("1")) {
//                            SharedPreferences setting = getContext().getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
//                            SharedPreferences.Editor editor = setting.edit();
//                            editor.putString("ClassCodeForShowAnswersheet", ClassCodeForNetworkcall);
//                            editor.commit();
                            adapter = new DataListAdapter(getContext(), datas);
                            txtCount.setVisibility(View.GONE);
                            txtCount.setText("Data Count :-" + String.valueOf(datas.size()));
                            recyclerViewAllData.setAdapter(adapter);
                            toast = Toast.makeText(getContext(), "Total: " + String.valueOf(datas.size()), Toast.LENGTH_LONG);
                            toast.show();

                        } else if (datas.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", datas.get(0).getResult().toString());

                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();
                    }
                    break;
                case 170:
                    classResponse = (List<Class>) data;
                    if (classResponse != null) {
                        if (classResponse.get(0).getResult().equals("Suessfull")) {
                            AppUtility.CLASS_RESPONSE = classResponse;

                            UserNameDropDownForClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                            j1 = 0;
                            UserNameDropDownForClass[j1] = "Select Class Name...";
                            for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
                                j1 = j1 + 1;
                                UserNameDropDownForClass[j1] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
                            }

                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForClass);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForClass.setAdapter(Select_Category_Adapter);

                            spinnerForClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    txtCount.setVisibility(View.GONE);
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {
                                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                                        recyclerViewAllData.setAdapter(null);
                                        NetWorkCall();
                                        if (toast != null) {
                                            toast.cancel();
                                        }


                                    } else {
                                        recyclerViewAllData.setAdapter(null);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });


                        } else if (classResponse.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", classResponse.get(0).getResult().toString());
                        } else if (classResponse.get(0).getResult().equals("Something went wrong")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", classResponse.get(0).getResult().toString());
                        }
                    } else {
                        Snackbar.make(view, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCall();
                                    }
                                }).show();
                    }
                    break;
            }
        } catch (Exception ex) {
            Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();
        }


    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(view, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TheoryTestActivity", "noResponse", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem item = menu.findItem(R.id.search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public boolean onQueryTextSubmit(String newText) {
        try {
            if (datas != null) {
                newText = newText.toLowerCase();
                List<Data> newTestList = new ArrayList<>();
                for (Data data : datas) {
                    if (data.getDataName() != null && data.getDataName() != null) {
                        String name = data.getDataName().toLowerCase();
                        String name1 = data.getDataName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getDataName() == null) {
                        String name1 = data.getDataName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getDataName() == null) {
                        String name = data.getDataName().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                adapter.setFilter(newTestList);
                return false;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTheoryTest", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (datas != null) {
                newText = newText.toLowerCase();
                List<Data> newTestList = new ArrayList<>();
                for (Data data : datas) {
                    if (data.getDataName() != null && data.getDataName() != null) {
                        String name = data.getDataName().toLowerCase();
                        String name1 = data.getDataName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getDataName() == null) {
                        String name1 = data.getDataName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getDataName() == null) {
                        String name = data.getDataName().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                adapter.setFilter(newTestList);
                return true;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTheoryTest", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

