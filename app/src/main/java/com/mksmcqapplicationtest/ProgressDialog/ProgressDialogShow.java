package com.mksmcqapplicationtest.ProgressDialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;
import com.mksmcqapplicationtest.R;

public class ProgressDialogShow {

    private Wave mWaveDrawable;
    private RotatingPlane mRotatingPlaneDrawable;
    private DoubleBounce mDoubleBounceDrawable;
    private WanderingCubes mWanderingCubesDrawable;
    private Pulse mPulseDrawable;
    private ChasingDots mChasingDotsDrawable;
    private ThreeBounce mThreeBounceDrawable;
    private Circle mCircleDrawable;
    private CubeGrid mCubeGridDrawable;
    private FadingCircle mFadingCircleDrawable;
    private FoldingCube mFoldingCubeDrawable;
    private RotatingCircle mRotatingCircleDrawable;

    ProgressDialog dialog;
    Context context;
    String Type, Massage;
    int Color;

    public ProgressDialogShow(Context tryProgressDialog, String type, int colorAccent, String Msg) {
        this.context = tryProgressDialog;
        this.Type = type;
        this.Color = colorAccent;
        this.Massage = Msg;
    }

    public void show() {
        try {
            dialog = new ProgressDialog(context, R.style.ProgressDialogTheme);
            dialog.setMessage(Massage);
            dialog.setCancelable(false);
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.show();
            if (Type.equals("Wave")) {
                mWaveDrawable = new Wave();
                mWaveDrawable.setBounds(0, 0, 100, 100);
                mWaveDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mWaveDrawable);

            } else if (Type.equals("RotatingPlane")) {

                mRotatingPlaneDrawable = new RotatingPlane();
                mRotatingPlaneDrawable.setBounds(0, 0, 100, 100);
                mRotatingPlaneDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mRotatingPlaneDrawable);
            } else if (Type.equals("DoubleBounce")) {

                mDoubleBounceDrawable = new DoubleBounce();
                mDoubleBounceDrawable.setBounds(0, 0, 100, 100);
                mDoubleBounceDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mDoubleBounceDrawable);
            } else if (Type.equals("WanderingCubes")) {

                mWanderingCubesDrawable = new WanderingCubes();
                mWanderingCubesDrawable.setBounds(0, 0, 100, 100);
                mWanderingCubesDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mWanderingCubesDrawable);
            } else if (Type.equals("Pulse")) {

                mPulseDrawable = new Pulse();
                mPulseDrawable.setBounds(0, 0, 100, 100);
                mPulseDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mPulseDrawable);
            } else if (Type.equals("ChasingDots")) {

                mChasingDotsDrawable = new ChasingDots();
                mChasingDotsDrawable.setBounds(0, 0, 100, 100);
                mChasingDotsDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mChasingDotsDrawable);
            } else if (Type.equals("ThreeBounce")) {

                mThreeBounceDrawable = new ThreeBounce();
                mThreeBounceDrawable.setBounds(0, 0, 100, 100);
                mThreeBounceDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mThreeBounceDrawable);
            } else if (Type.equals("Circle")) {

                mCircleDrawable = new Circle();
                mCircleDrawable.setBounds(0, 0, 100, 100);
                mCircleDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mCircleDrawable);
            } else if (Type.equals("CubeGrid")) {

                mCubeGridDrawable = new CubeGrid();
                mCubeGridDrawable.setBounds(0, 0, 100, 100);
                mCubeGridDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mCubeGridDrawable);
            } else if (Type.equals("FadingCircle")) {

                mFadingCircleDrawable = new FadingCircle();
                mFadingCircleDrawable.setBounds(0, 0, 100, 100);
                mFadingCircleDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mFadingCircleDrawable);
            } else if (Type.equals("FoldingCube")) {

                mFoldingCubeDrawable = new FoldingCube();
                mFoldingCubeDrawable.setBounds(0, 0, 100, 100);
                mFoldingCubeDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mFoldingCubeDrawable);
            } else if (Type.equals("RotatingCircle")) {

                mRotatingCircleDrawable = new RotatingCircle();
                mRotatingCircleDrawable.setBounds(0, 0, 100, 100);
                mRotatingCircleDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mRotatingCircleDrawable);
            } else {
                mWaveDrawable = new Wave();
                mWaveDrawable.setBounds(0, 0, 100, 100);
                mWaveDrawable.setColor(context.getResources().getColor(Color));
                dialog.setIndeterminateDrawable(mWaveDrawable);

            }

//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                public void run() {
//                    dialog.dismiss();
//                }
//            }, 6000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        try {
            if (dialog != null) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        } catch (Exception e) {

        }
    }
}
