package com.mksmcqapplicationtest.TabStructure.Guest;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.Guest.GuestEnableAdapter;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.beans.SignUpGuest;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.beans.User;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabGuestProfile extends Fragment implements  View.OnClickListener,ResponseListener {

    RecyclerView recyclerViewOfEnableTest;
    CustomButton btnMakeClassEnableDisable;
    String ClassNameJSONString, ClassCodeForNetworkcall, TestListJSONString;
    String[] UserNameDropDownForClass = new String[1];
    List<Class> classResponse;
    Spinner spinnerForClass;
    List<Student> student;
    View view;
    int count1;
    private List<SignUpGuest> signUpGuests;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    Toast toast;
    CheckBox chkSelectAll;
    android.support.v7.widget.SearchView searchViewAndroidActionBar;

    LinearLayoutManager manager;
    private List<SignUpGuest> AllsignUpGuests = new ArrayList<>();
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    GuestEnableAdapter adapter;
    SpinKitView progress;
    int StartIndex = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_enable_guest, container, false);
        try {
            StartIndex = 1;
setUI();

            if (AppUtility.IsDemoApplication) {
                btnMakeClassEnableDisable.setClickable(false);
                btnMakeClassEnableDisable.setEnabled(false);
            } else {
                btnMakeClassEnableDisable.setClickable(true);
                btnMakeClassEnableDisable.setEnabled(true);
            }


            manager = new LinearLayoutManager(getContext());
            recyclerViewOfEnableTest.setLayoutManager(manager);
            adapter = new GuestEnableAdapter(getContext(), recyclerViewOfEnableTest, AllsignUpGuests);
            recyclerViewOfEnableTest.setAdapter(adapter);
            recyclerViewOfEnableTest.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true;
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    currentItems = manager.getChildCount();
                    totalItems = manager.getItemCount();
                    scrollOutItems = manager.findFirstVisibleItemPosition();

                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        isScrolling = false;


                    }

                }
            });


            spinnerForClass = (Spinner) view.findViewById(R.id.spinnerForClass);
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetworkCallForAllClass();
            }

            btnMakeClassEnableDisable.setOnClickListener(this);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        AppUtility.KEY_CLASSCODE = classResponse.get(count1 - 1).getClassCode();
                        ClassCodeForNetworkcall = "1";
                        AllsignUpGuests.clear();
                        StartIndex = 1;
                        adapter.notifyDataSetChanged();

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        progress =  view.findViewById(R.id.progressSpinkit);
        recyclerViewOfEnableTest =  view.findViewById(R.id.recyclerViewOfEnable);
        chkSelectAll =  view.findViewById(R.id.chkSelectAll);
        swipeRefreshLayout =  view.findViewById(R.id.swipeRefreshLayout);
        btnMakeClassEnableDisable =  view.findViewById(R.id.btnMakeEnable);
        CustomTextViewBold txtactionbartitle =  getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Guest_Profile));

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
    private void NetworkCallForAllClass() {
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "NetworkCallForAllClass", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            switch (requestCode) {
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
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {
                                        AppUtility.KEY_CLASSCODE = classResponse.get(count1 - 1).getClassCode();
                                        AllsignUpGuests.clear();
                                        adapter.notifyDataSetChanged();
                                        ClassCodeForNetworkcall = "1";
                                        StartIndex=1;
                                        NetworkCallForStudentList();
                                    } else {
                                        AllsignUpGuests.clear();
                                        adapter.notifyDataSetChanged();
                                        ClassCodeForNetworkcall = "0";
                                    }
                                    if (toast != null)
                                        toast.cancel();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });


                        } else if (classResponse.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",classResponse.get(0).getResult().toString());
                        } else if (classResponse.get(0).getResult().equals("Something went wrong")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",classResponse.get(0).getResult().toString());
                        }

                    } else {
                        Snackbar.make(view, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetworkCallForAllClass();
                                    }
                                }).show();
                    }
                    break;

                case 570:
                    try {
                        progress.setVisibility(View.GONE);
                        signUpGuests = (List<SignUpGuest>) data;

                        if (signUpGuests.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",signUpGuests.get(0).getResult().toString());
                        } else if (signUpGuests.get(0).getResultCode().equals("1")) {
                            if (StartIndex == 1) {
                                toast = Toast.makeText(getContext(), "Total: " + String.valueOf(signUpGuests.get(0).getTotlaCount()), Toast.LENGTH_LONG);
                                toast.show();
                            }
                            AllsignUpGuests.addAll(signUpGuests);
                            StartIndex = AllsignUpGuests.size() + 1;
                            adapter.notifyDataSetChanged();
                        } else {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error","No students are available..");
//
//                            Snackbar snackbar = Snackbar.make(view, "No students are available..", Snackbar.LENGTH_LONG)
//                                    .setAction("Retry", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            NetworkCallForStudentList();
//                                        }
//                                    });
//
//                            snackbar.show();
                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();
                    }

                    break;
                case 410:
                    student = (List<Student>) data;
                    try {

                        if (student.get(0).getResult().equals("Suessfull")) {
                            Snackbar.make(view, "Active/Deactive Operation Sucessfull", Snackbar.LENGTH_LONG).show();
                        } else if (student.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",student.get(0).getResult().toString());
                        } else if (student.get(0).getResult().equals("Something Went Wrong Please Try Again")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",student.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "Error GCD3!! No Data Found", Snackbar.LENGTH_LONG).show();

                    }
                    if (AppUtility.IsAdvertiesVisible) {
                        InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
                        interstitialAdvertiesClass.showInterstitialAdver();
                    }
                    break;

            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallForStudentList() {
        try {
            if (!(AppUtility.KEY_CLASSCODE.equals("0") || AppUtility.KEY_CLASSCODE == null)) {
                CreateJSonForTestList();
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getAllGuestList(AppUtility.GET_ALL_GUEST_FOR_ENABLE, TestListJSONString);

            } else {
                Snackbar.make(view, "Select Class Name ", Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "NetworkCallForStudentList", e);
            pc.showCatchException();
        }
    }

    private void CreateJSonForTestList() {
        try {
            User user = new User();
            Gson gson = new Gson();
            user.setClassCode(AppUtility.KEY_CLASSCODE);
            user.setWhichList("All");
            user.setStartIndex(String.valueOf(StartIndex));
            String jso = gson.toJson(user);
            TestListJSONString = "[" + jso + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "CreateJSonForTestList", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(view,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "noResponse", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnMakeEnable:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnMakeClassEnableDisable);
                    bounce_button_class1.BounceMethod();
                    if (ClassCodeForNetworkcall == "0") {
                        Snackbar.make(view, "Please Select Class Name", Snackbar.LENGTH_LONG).show();
                    } else {
                        btnMakeClassEnableDisable.setEnabled(false);
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Confirmation!!");
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText("Are you sure? You want to active/deactive guest");
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Yes");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                bounce_button_class1.BounceMethod();
                                btnMakeClassEnableDisable.setEnabled(true);

                                dialog.dismiss();
                            }
                        });
                        final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                        CancledialogButton.setText("No");
                        CancledialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), CancledialogButton);
                                bounce_button_class1.BounceMethod();
                                btnMakeClassEnableDisable.setEnabled(true);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "onClick", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForEnableDisableTest() {
        try {
            if (signUpGuests.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < signUpGuests.size(); i++) {
                    String abc = signUpGuests.get(i).getAcFlag();
                    if (abc != null) {
                        JSONObject student1 = new JSONObject();
                        try {
                            student1.put("StudentCode", signUpGuests.get(i).getStudentCode());
                            student1.put("AcFlag", signUpGuests.get(i).getAcFlag());

                        } catch (JSONException e) {
                            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableGuestForScrollMore", "CreateJSONForEnableDisableTest", e);
                            pc.showCatchException();
                        }
                        jsonArray.put(student1);
                    }

                }

                DataAccess dataAcess = new DataAccess(getContext(), this);
                dataAcess.EnableDisableStudent(String.valueOf(jsonArray), AppUtility.ENABLE_DISABLE_STUDENT);
            } else {
                Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
        }
    }
}

