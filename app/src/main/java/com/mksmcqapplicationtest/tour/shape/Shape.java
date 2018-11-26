package com.mksmcqapplicationtest.tour.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mksmcqapplicationtest.tour.target.Target;


public interface Shape {

    void draw(Canvas canvas, Paint paint, int x, int y, int padding);

    int getWidth();
    int getHeight();

    void updateTarget(Target target);

}
