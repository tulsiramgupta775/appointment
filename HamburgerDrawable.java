package com.example.anuj.appointmentrequest;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

/**
 * Created by Anuj on 27-02-2018.
 */

public class HamburgerDrawable extends DrawerArrowDrawable {

    public HamburgerDrawable(Context context){
        super(context);
        setColor(context.getResources().getColor(R.color.white));
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        setBarLength(70.0f);
        setBarThickness(7.0f);
        setGapSize(11.0f);

    }
}