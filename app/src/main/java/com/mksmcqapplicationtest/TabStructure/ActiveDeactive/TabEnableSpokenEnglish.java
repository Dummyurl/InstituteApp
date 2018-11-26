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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.AudioPlay;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.AudioFilesEnableAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabEnableSpokenEnglish extends Fragment implements  View.OnClickListener, SearchView.OnQueryTextListener {

    RecyclerView recyclerViewOfEnableData;
    Button btnMakeDataEnableDisable;
    List<Data> datas;
    List<AudioPlay> audioPlays;
    View view;
    List<Class> classResponse;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString;
    int count1 = 0;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    AudioFilesEnableAdapter activeUserListAdapter;
    Toast toast;
    String AudioListJSONString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_enable_spoken_english, container, false);
        try {
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            recyclerViewOfEnableData = (RecyclerView) view.findViewById(R.id.recyclerViewOfEnable);
            btnMakeDataEnableDisable = (Button) view.findViewById(R.id.btnMakeEnable);
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
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableAudioFiles", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnMakeEnable:
                    btnMakeDataEnableDisable.setEnabled(false);
                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog);
                    TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                    txtTitle.setText("Confirmation!!");
                    TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                    txtMessage.setText("Are you sure? You want to active/deactive audio files");
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
                    break;

            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableAudioFiles", "onClick", e);
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
            if (audioPlays != null) {
                newText = newText.toLowerCase();
                List<AudioPlay> newTestList = new ArrayList<>();
                for (AudioPlay data : audioPlays) {
                    if (data.getAudioName() != null && data.getAudioName() != null) {
                        String name = data.getAudioName().toLowerCase();
                        String name1 = data.getAudioName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getAudioName() == null) {
                        String name1 = data.getAudioName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getAudioName() == null) {
                        String name = data.getAudioName().toLowerCase();

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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabEnableSpokenEnglish", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (audioPlays != null) {
                newText = newText.toLowerCase();
                List<AudioPlay> newTestList = new ArrayList<>();
                for (AudioPlay data : audioPlays) {
                    if (data.getAudioName() != null && data.getAudioName() != null) {
                        String name = data.getAudioName().toLowerCase();
                        String name1 = data.getAudioName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getAudioName() == null) {
                        String name1 = data.getAudioName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getAudioName() == null) {
                        String name = data.getAudioName().toLowerCase();

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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabEnableSpokenEnglish", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

