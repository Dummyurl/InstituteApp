package com.mksmcqapplicationtest.beans;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.mksmcqapplicationtest.MyBounceInterpolator;
import com.mksmcqapplicationtest.R;

/**
 * Created by Samsung on 18/01/2018.
 */

public class Bounce_Button_Class {

    Context context;
    Button btn;

    public Bounce_Button_Class(Context con, Button button) {
        this.context = con;
        this.btn = button;
    }




    public void BounceMethod() {
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);

    }
}
