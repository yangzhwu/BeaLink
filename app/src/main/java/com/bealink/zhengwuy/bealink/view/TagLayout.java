package com.bealink.zhengwuy.bealink.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengwuy on 2018/6/20.
 * email: 13802885114@139.com
 * des: 标签布局layout
 */
public class TagLayout extends ViewGroup{
    private static final String TAG = "TabLayout";

    private Context mContext;
    //子标签的横向间隔
    private int mSubviewHorizontalSpace;
    //子标签的纵向间隔
    private int mSubviewVerticalSpace;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    //读取自定义属性
    private void init(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TagLayout);
        mSubviewHorizontalSpace = (int) typedArray.getDimension(R.styleable.TagLayout_sub_view_hor_space, 0);
        mSubviewVerticalSpace = (int) typedArray.getDimension(R.styleable.TagLayout_sub_view_ver_space, 0);
        typedArray.recycle();
    }

    private List<List<View>> mLists = new ArrayList<>();
    private List<Integer> mHeight = new ArrayList<>();
    private List<View> mHorviewList = new ArrayList<>();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int totalHeight = 0;
        int lineLength = 0;

        mLists.clear();
        mHeight.clear();
        mHorviewList.clear();
        int childrenCount = getChildCount();
        LogUtil.d(TAG, childrenCount + " ");
        //所有标签行中最大行高
        int lineMaxHeight = 0;
        //所有标签行中最大宽长
        int lineMaxWidth = 0;
        for (int i = 0; i < childrenCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childMeasuerWidth = child.getMeasuredWidth();
            int childMeasureHeight = child.getMeasuredHeight();
            //第一个标签
            if (lineLength == 0) {
                lineLength += childMeasuerWidth;
                lineMaxHeight = childMeasureHeight;
                lineMaxWidth = childMeasuerWidth;
                mHorviewList.add(child);
            } else {
                if (lineLength + childMeasuerWidth + mSubviewHorizontalSpace + getPaddingLeft() + getPaddingRight() > widthSize) {
                    mLists.add(mHorviewList);
                    mHorviewList = new ArrayList<>();
                    totalHeight += lineMaxHeight + mSubviewVerticalSpace;
                    mHeight.add(totalHeight);
                    if (lineLength > lineMaxWidth) {
                        lineMaxWidth = lineLength;
                    }

                    i--;
                    lineLength = 0;
                    lineMaxHeight = 0;
                } else {
                    mHorviewList.add(child);
                    lineLength += childMeasuerWidth + mSubviewHorizontalSpace;
                    if (childMeasureHeight > lineMaxHeight) {
                        lineMaxHeight = childMeasureHeight;
                    }
                    if (lineLength > lineMaxWidth) {
                        lineMaxWidth = lineLength;
                    }
                }
            }
            if (i == childrenCount - 1) {
                totalHeight += lineMaxHeight;
                mHeight.add(totalHeight);
                mLists.add(mHorviewList);
            }

        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : lineMaxWidth + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY? heightSize : totalHeight + getPaddingTop() + getPaddingBottom());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int rows = mLists.size();
        for (int i = 0; i < rows; i++) {
            int cols = mLists.get(i).size();
            for (int j = 0; j < cols; j++) {
                View child = mLists.get(i).get(j);
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                child.layout(left, top, left + width, top + height);
                left = left + mSubviewHorizontalSpace + width;
            }
            if (i != rows - 1) {
                top = mHeight.get(i) + getPaddingTop();
                left = getPaddingLeft();
            }
        }
    }
}
