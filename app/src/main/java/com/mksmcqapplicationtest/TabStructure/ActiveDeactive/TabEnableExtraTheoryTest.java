package com.mksmcqapplicationtest.TabStructure.ActiveDeactive;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.DataEnableAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabEnableExtraTheoryTest extends Fragment implements View.OnClickListener,  SearchView.OnQueryTextListener {

    RecyclerView recyclerViewOfEnableData;
    Button btnMakeDataEnableDisable;
    String GetDataJSONString;
    List<Data> datas, dataa;
    View view;
    List<Class> classResponse;
    Spinner spinnerForClass;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString, ClassCodeForNetworkcall, ClassNameForNetworkCall;
    int count1 = 0;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    DataEnableAdapter activeUserListAdapter;
    String sendNotificationJSONString;
    List<SendNotification> sendNotifications;
    Toast toast;
    CheckBox chkSelectAll;
    LinearLayout LLForSelectAll;
    CustomTextViewBold txtactionbartitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_enable_theory_test, container, false);
        try {
           setUI();

            if (AppUtility.IsDemoApplication) {
                btnMakeDataEnableDisable.setClickable(false);
                btnMakeDataEnableDisable.setEnabled(false);
            } else {
                btnMakeDataEnableDisable.setClickable(true);
                btnMakeDataEnableDisable.setEnabled(true);
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewOfEnableData.setLayoutManager(layoutManager);
            recyclerViewOfEnableData.setItemAnimator(new DefaultItemAnimator());
            btnMakeDataEnableDisable.setOnClickListener(this);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                        ClassNameForNetworkCall = classResponse.get(count1 - 1).getClassName();
                        recyclerViewOfEnableData.setAdapter(null);


                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });

            chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        if (datas.size() > 0) {
                            activeUserListAdapter.selectAll();
                        } else {
                            Snackbar.make(view, "No Extra Theory Test Available For Enable", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        if (datas.size() > 0) {
                            activeUserListAdapter.deselectAll();
                            activeUserListAdapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(view, "No Extra Theory Test Available For Disable", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraData", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        chkSelectAll = (CheckBox) view.findViewById(R.id.chkSelectAll);
        LLForSelectAll = (LinearLayout) view.findViewById(R.id.LLForSelectAll);
        LLForSelectAll.setVisibility(View.GONE);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        spinnerForClass = (Spinner) view.findViewById(R.id.spinnerForClass);
        recyclerViewOfEnableData = (RecyclerView) view.findViewById(R.id.recyclerViewOfEnable);
        btnMakeDataEnableDisable = (Button) view.findViewById(R.id.btnMakeEnable);
        txtactionbartitle =  getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Extra_Theory_Data_Exam));

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


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnMakeEnable:

                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnMakeDataEnableDisable);
                    bounce_button_class1.BounceMethod();
                    if (ClassCodeForNetworkcall.equals("0")) {
                        Snackbar.make(view, "Please Select Class Name", Snackbar.LENGTH_LONG).show();
                    } else {

                        btnMakeDataEnableDisable.setEnabled(false);
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Confirmation!!");
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText("Are you sure? You want to active/deactive extra theory test");
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Yes");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                bounce_button_class1.BounceMethod();
                                btnMakeDataEnableDisable.setEnabled(true);

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
                                btnMakeDataEnableDisable.setEnabled(true);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraData", "onClick", e);
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
                activeUserListAdapter.setFilter(newTestList);
                return false;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabEnableExtraTheoryTest", "onQueryTextSubmit", e);
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
                activeUserListAdapter.setFilter(newTestList);
                return true;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabEnableExtraTheoryTest", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

