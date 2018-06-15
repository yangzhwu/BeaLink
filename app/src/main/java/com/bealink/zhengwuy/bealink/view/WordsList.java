package com.bealink.zhengwuy.bealink.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.utils.DimenUtils;

/**
 * Created by zhengwuy on 2018/6/15.
 * email: 13802885114@139.com
 * des:
 */
public class WordsList extends View {
    private static final char[] sWords = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#'};

    private Paint mWordsPaint;
    private Paint mBgPaint;
    private int mWordItemWidth, mWordItemHeight;
    private int mCurrentIndex = 0;
    private Rect mRect;
    private OnIndexChangeListener mOnIndexChangeListener;

    public WordsList(Context context) {
        this(context, null);
    }

    public WordsList(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WordsList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mWordsPaint = new Paint();
        mWordsPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mWordsPaint.setTextSize(DimenUtils.dp2px(context, 14));

        mBgPaint = new Paint();
        mBgPaint.setColor(getResources().getColor(R.color.colorAccent));

        mRect = new Rect();
    }

    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        mOnIndexChangeListener = onIndexChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWordItemWidth = getMeasuredWidth();
        mWordItemHeight = getMeasuredHeight() / sWords.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < sWords.length; i++) {
            if (i == mCurrentIndex) {
                int radius = Math.min(mWordItemWidth / 2, mWordItemHeight / 2);
                canvas.drawCircle(mWordItemWidth / 2, mWordItemHeight * i + mWordItemHeight / 2, radius, mBgPaint);
                mWordsPaint.setColor(Color.WHITE);
            } else {
                mWordsPaint.setColor(Color.GRAY);
            }
            mWordsPaint.getTextBounds(sWords, i, 1, mRect);
            int wordWidth = mRect.width();
            int wordHeight = mRect.height();
            float wordX = mWordItemWidth / 2 - wordWidth / 2;
            float wordY = mWordItemHeight / 2 + mWordItemHeight * i + wordHeight / 2 ;
            canvas.drawText(sWords, i, 1, wordX, wordY, mWordsPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / mWordItemHeight);
                if (mCurrentIndex != index && index >= 0 && index < sWords.length) {
                    mCurrentIndex = index;
                    if (mOnIndexChangeListener != null) {
                        mOnIndexChangeListener.onIndexChangeListener(sWords[index]);
                    }
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;

    }

    public static interface OnIndexChangeListener {
        void onIndexChangeListener(char c);
    }
}
