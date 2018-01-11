package com.daydvr.store.view.custom;


import static com.daydvr.store.base.GameConstant.DOWNLOADABLE;
import static com.daydvr.store.base.GameConstant.DOWNLOADING;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.INSTALLED;
import static com.daydvr.store.base.GameConstant.NOT_ADAPTION;
import static com.daydvr.store.base.GameConstant.PAUSED;
import static com.daydvr.store.base.GameConstant.UPDATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;
import com.daydvr.store.R;
import com.daydvr.store.util.ThreadPoolUtil;
import java.util.concurrent.ExecutorService;


/**
 * Created by chenliu on 2016/8/26.<br/>
 * 描述：添加圆角支持 on 2016/11/11
 * </br>
 */
public class FlikerProgressBar extends ProgressBar {

    private byte mState;

    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    private final int DEFAULT_HEIGHT_DP = 40;

    private int borderWidth;

    private float maxProgress = Float.MAX_VALUE;

    private Paint textPaint;

    private Paint bgPaint;

    private Paint pgPaint;

    private String progressText;

    private Rect textRect;

    private RectF bgRectf;

    /**
     * 进度条 bitmap ，包含滑块
     */
    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    /**
     * 当前进度
     */
    private float progress = 0f;

    /**
     * 下载中颜色
     */
    private int progressColor;

    /**
     * 默认颜色
     */
    private int backgroundColor;

    /**
     * 进度文本、边框、进度条颜色
     */
    private int color;

    private int textSize;

    private int radius;

    private static ExecutorService SingleThreadPool;

    BitmapShader bitmapShader;

    public FlikerProgressBar(Context context) {
        this(context, null, 0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FlikerProgressBar);
        try {
            textSize = (int) ta.getDimension(R.styleable.FlikerProgressBar_textSize, 12);
            progressColor = ta.getColor(R.styleable.FlikerProgressBar_progressColor,
                    Color.parseColor("#0ca4ea"));
            backgroundColor = ta.getColor(R.styleable.FlikerProgressBar_backgroundColor,
                    Color.parseColor("#0ca4ea"));
            radius = (int) ta.getDimension(R.styleable.FlikerProgressBar_pbRadius, 0);
            borderWidth = (int) ta.getDimension(R.styleable.FlikerProgressBar_borderWidth, 1);
        } finally {
            ta.recycle();
        }
    }

    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(borderWidth);
        // bgPaint.setColor(Color.parseColor("#e0c3fc"));

        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);

        textRect = new Rect();
        bgRectf = new RectF(borderWidth, borderWidth, getMeasuredWidth() - borderWidth,
                getMeasuredHeight() - borderWidth);

        if (mState == DOWNLOADING) {
            color = progressColor;
        } else {
            color = backgroundColor;
        }

        initPgBimap();
    }

    private void initPgBimap() {
        pgBitmap = Bitmap
                .createBitmap(getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth,
                        Bitmap.Config.ARGB_8888);
        pgCanvas = new Canvas(pgBitmap);
        SingleThreadPool = ThreadPoolUtil.getSingleThreadPool(SingleThreadPool);
        SingleThreadPool.execute(mDownloadAnima);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
                height = dpToPx(DEFAULT_HEIGHT_DP);
                break;
            case MeasureSpec.EXACTLY:
                height = dpToPx(DEFAULT_HEIGHT_DP);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = dpToPx(DEFAULT_HEIGHT_DP);
                break;
            default:
                break;
        }
        setMeasuredDimension(widthSpecSize, height);

        if (pgBitmap == null) {
            init();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mState != NOT_ADAPTION) {
            //背景
            drawBackGround(canvas);

            if (mState == DOWNLOADING || mState == PAUSED) {
                //进度
                float right = progress / maxProgress;
                drawProgress(canvas, right);
                if (mState == DOWNLOADING) {
                    SingleThreadPool.execute(mDownloadAnima);
                }
            }
            //文本text
            drawProgressText(canvas);

            //变色处理
            drawColorProgressText(canvas);
        } else {
            //文本text
            drawProgressText(canvas);
        }
    }

    /**
     * 边框
     */
    private void drawBackGround(Canvas canvas) {
        bgPaint.setColor(color);
        if (mState != DOWNLOADING && mState != PAUSED) {
            bgPaint.setStyle(Paint.Style.FILL);
        } else {
            bgPaint.setStyle(Paint.Style.STROKE);
        }
        //left、top、right、bottom不要贴着控件边，否则border只有一半绘制在控件内,导致圆角处线条显粗
        canvas.drawRoundRect(bgRectf, radius, radius, bgPaint);
    }

    /**
     * 进度
     */
    public void drawProgress(Canvas canvas, float progress) {
        pgPaint.setColor(color);

        pgCanvas.save();
        pgCanvas.clipRect(0, 0, progress * getMeasuredWidth(), getMeasuredHeight());
        pgCanvas.drawColor(color);
        pgCanvas.restore();

        if (mState == DOWNLOADING) {
            pgPaint.setXfermode(xfermode);
            pgPaint.setXfermode(null);
        }

        //控制显示区域
        bitmapShader = new BitmapShader(pgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        pgPaint.setShader(bitmapShader);
        canvas.drawRoundRect(bgRectf, radius, radius, pgPaint);
    }

    /**
     * 进度提示文本
     */
    private void drawProgressText(Canvas canvas) {
        if(mState== DOWNLOADABLE){
            textPaint.setColor(Color.WHITE);
        }else {
            textPaint.setColor(ContextCompat.getColor(getContext(),R.color.color6));
        }
        progressText = getProgressText();
        textPaint.getTextBounds(progressText, 0, progressText.length(), textRect);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline =
                (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(progressText, xCoordinate, baseline, textPaint);
    }

    @Override
    public synchronized void setMax(int max) {
        this.maxProgress = max;
        super.setMax(max);
    }

    /**
     * 变色处理
     */
    private void drawColorProgressText(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline =
                (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        float progressWidth = (progress / maxProgress) * getMeasuredWidth();
        if (progressWidth > xCoordinate) {
            canvas.save();
            float right = Math.min(progressWidth, xCoordinate + tWidth * 1.1f);
            canvas.clipRect(xCoordinate, 0, right, getMeasuredHeight());
            canvas.drawText(progressText, xCoordinate, baseline, textPaint);
            canvas.restore();
        }
    }

    public void setDownloadProgress(float progress) {

        if (mState == DOWNLOADING) {
            color = progressColor;
            if (progress == maxProgress) {
                color = backgroundColor;
            }
            this.progress = progress;
            invalidate();
        }
    }

    public float getDownloadProgress() {
        return progress;
    }

    /**
     * 重置
     */
    public void reset() {
        progress = 0;
        mState = DOWNLOADABLE;
        color = progressColor;
        progressText = "";
        initPgBimap();
    }

    @SuppressLint("DefaultLocale")
    private String getProgressText() {
        String text = null;
        switch (mState) {
            case DOWNLOADABLE:
                text = "下载";
                break;

            case UPDATE:
                text = "更新";
                break;

            case NOT_ADAPTION:
                text = "不适配";
                break;

            case INSTALLED:
                text = "打开";
                break;

            case INSTALLABLE:
                text = "安装";
                break;

            case DOWNLOADING:
                text = String.format("%.1f", progress / maxProgress * 100) + "%";
                break;

            case PAUSED:
                text = "继续";
                break;

            default:
                text = "";
                break;
        }
        return text;
    }

    /*private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density+0.5f);
    }*/
    public int dpToPx(float dpVal) {
        return (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getContext().getResources().getDisplayMetrics());
    }

    public byte getState() {
        return mState;
    }

    public void setState(byte state) {
        this.mState = state;
        invalidate();
    }

    public void onDestory() {
        SingleThreadPool.shutdownNow();
    }

    Runnable mDownloadAnima = new Runnable() {
        @Override
        public void run() {
            try {
                while (mState != PAUSED && !SingleThreadPool.isShutdown()) {
                    float progressWidth = (progress / maxProgress) * getMeasuredWidth();
                    postInvalidate();
                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        invalidate();
    }
}
