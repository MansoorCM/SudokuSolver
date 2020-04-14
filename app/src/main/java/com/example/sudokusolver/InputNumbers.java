package com.example.sudokusolver;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

public class InputNumbers extends androidx.appcompat.widget.AppCompatImageView {
    Drawable numbers;
    int clickedNumber;

    public InputNumbers(Context context) {
        super(context);
        init();
    }

    public InputNumbers(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputNumbers(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init()
    {
        numbers= ResourcesCompat.getDrawable(getResources(),R.drawable.ic_numbers,null);
        setImageDrawable(numbers);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int numWidth=getWidth()/9;
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    int touchPos = (int) motionEvent.getX();
                    if (touchPos <= numWidth) {
                        clickedNumber = 1;
                    } else if (touchPos <= 2 * numWidth) {
                        clickedNumber = 2;
                    } else if (touchPos <= 3 * numWidth) {
                        clickedNumber = 3;
                    } else if (touchPos <= 4 * numWidth) {
                        clickedNumber = 4;
                    } else if (touchPos <= 5 * numWidth) {
                        clickedNumber = 5;
                    } else if (touchPos <= 6 * numWidth) {
                        clickedNumber = 6;
                    } else if (touchPos <= 7 * numWidth) {
                        clickedNumber = 7;
                    } else if (touchPos <= 8 * numWidth) {
                        clickedNumber = 8;
                    } else {
                        clickedNumber = 9;
                    }
                    if(sudokuboardAdapter.selected!=-1)
                    {
                        MainActivity.inputNum(clickedNumber);
                    }

                }
                return true;
            }
        });
    }

}
