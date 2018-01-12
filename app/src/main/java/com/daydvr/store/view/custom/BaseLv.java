package com.daydvr.store.view.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by lumingmin on 2016/12/2.
 */

public abstract class BaseLv extends View {

    public BaseLv(Context context) {
        this(context, null);
    }

    public BaseLv(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 500);
    }

    public void startAnim(int time) {
        stopAnim();
        startViewAnim(0f, 1f, time);
    }


    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();

            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
            if (onStopAnim() == 0) {
                valueAnimator.setRepeatCount(0);
                valueAnimator.cancel();
                valueAnimator.end();
            }

        }
    }

   public ValueAnimator valueAnimator;

    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());


        valueAnimator.setRepeatCount(setAnimRepeatCount());



        if (ValueAnimator.RESTART == setAnimRepeatMode()) {
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        } else if (ValueAnimator.REVERSE == setAnimRepeatMode()) {
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        }

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BaseLv.this.onAnimationUpdate(valueAnimator);

            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(loadingDialog!=null){
                    loadingDialog.dismiss();
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }

            @Override
            public void onAnimationStart(Animator animation) {

                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                BaseLv.this.onAnimationRepeat(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            ainmIsRunning();
            valueAnimator.start();

        }

        return valueAnimator;
    }


    protected abstract void initPaint();

    protected abstract void onAnimationUpdate(ValueAnimator valueAnimator);

    protected abstract void onAnimationRepeat(Animator animation);

    protected abstract int onStopAnim();

    protected abstract int setAnimRepeatMode();

    protected abstract int setAnimRepeatCount();

    protected abstract void ainmIsRunning();

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();

    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    private LoadingDialog loadingDialog;

    public void setDialogDismiss(LoadingDialog dialog){
        loadingDialog = dialog;
    }
}
