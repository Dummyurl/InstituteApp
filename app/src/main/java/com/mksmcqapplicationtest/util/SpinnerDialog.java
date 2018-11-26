package com.mksmcqapplicationtest.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.View.CustomTextViewBold;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpinnerDialog {
    ArrayList<Class> items;
    Activity context;
    String dTitle;
    String closeTitle = "Close";
    OnSpinerItemClick onSpinerItemClick;
    AlertDialog alertDialog;
    int clickpos;
    int style;
    Fragment fragment;

    private int limit = -1;
    private int selected = 0;
    private LimitExceedListener limitListener;
    int selectedIndex = -1;
    ListView listView;
    int pos;

    String name = "", image = "";

    public SpinnerDialog(Activity activity, ArrayList<Class> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<Class> items, String dialogTitle, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle = closeTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<Class> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }

    public SpinnerDialog(Activity activity, ArrayList<Class> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle = closeTitle;
    }

    public SpinnerDialog(Activity activity, String name, String image, int style) {
        this.context = activity;
        this.name = name;
        this.image = image;
        this.style = style;
    }


    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }




    public interface LimitExceedListener {
        void onLimitListener(Class data);
    }


}
