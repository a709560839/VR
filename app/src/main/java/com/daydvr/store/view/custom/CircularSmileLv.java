package com.daydvr.store.view.custom;

import static com.daydvr.store.base.BaseConstant.LOADING_PROGRESS_REPEAT_COUNT;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import com.daydvr.store.R;

/**
 * Created by lumingmin on 16/6/20.
 */

/**
 * setViewColor(int color)
 * startAnim(int time)
 * stopAnim()
 */
public class CircularSmileLv extends BaseLv {

    private Paint mPaint;

    private float mWidth = 0f;
    private float mEyeWidth = 0f;

    private float mPadding = 0f;
    private float startAngle = 0f;
    private boolean isSmile = false;
    RectF rectF = new RectF();

    public CircularSmileLv(Context context) {
        super(context);
    }

    public CircularSmileLv(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularSmileLv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() > getHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }

        mPadding = dip2px(10);
        mEyeWidth = dip2px(3);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        mPaint.setStyle(Paint.Style.STROKE);

        //第四个参数是否显示半径
        canvas.drawArc(rectF, startAngle, 180
                , false, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        if (isSmile) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth,
                    mPaint);
        }

    }

    @Override
    protected void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(getContext(),R.color.color6));
        mPaint.setStrokeWidth(dip2px(2));

    }

    public void setViewColor(int color) {
        mPaint.setColor(color);
        postInvalidate();
    }


    float mAnimatedValue = 0f;


    @Override
    protected void onAnimationRepeat(Animator animation) {

    }

    @Override
    protected void onAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimatedValue = (float) valueAnimator.getAnimatedValue();
        if (mAnimatedValue < 0.5) {
            isSmile = false;
            startAngle = 720 * mAnimatedValue;
        } else {
            startAngle = 720;
            isSmile = true;
        }

        invalidate();
    }

    @Override
    protected int onStopAnim() {
        isSmile = false;
        mAnimatedValue = 0f;
        startAngle = 0f;
        return 0;
    }


    @Override
    protected int setAnimRepeatMode() {
        return ValueAnimator.RESTART;
    }

    @Override
    protected void ainmIsRunning() {

    }

    @Override
    protected int setAnimRepeatCount() {
        return LOADING_PROGRESS_REPEAT_COUNT;
    }

    public void setAnimatorListenerAdapter(AnimatorListenerAdapter listenerAdapter) {

    }
}
