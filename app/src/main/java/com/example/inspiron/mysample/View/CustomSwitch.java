package com.example.inspiron.mysample.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inspiron.mysample.R;
import com.example.inspiron.mysample.Utility.Utility;

public class CustomSwitch extends CardView {

    public interface OnCheckedChangeListener {
        void onCheckedChange(boolean checkState);
    }

    private TextView leftView, rightView;
    private String leftTextString, rightTextString;
    private @ColorInt
    int selectedTextColor, selectedViewColor, unselectedTextColor, unselectedViewColor;
    private boolean checkState = true;
    private int parentWidth;
    private OnCheckedChangeListener onCheckedChangeListener;

    public CustomSwitch(Context context) {
        super(context);
    }

    public CustomSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCustomAttr(attrs);
        initView();
    }

    public CustomSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttr(attrs);
        initView();
    }

    private void initCustomAttr(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSwitch);
        leftTextString = a.getString(R.styleable.CustomSwitch_leftText);
        rightTextString = a.getString(R.styleable.CustomSwitch_rightText);
        selectedTextColor = a.getColor(R.styleable.CustomSwitch_selectedTextColor, 0);
        selectedViewColor = a.getColor(R.styleable.CustomSwitch_selectedViewColor, 0);
        unselectedTextColor = a.getColor(R.styleable.CustomSwitch_unselectedTextColor, 0);
        unselectedViewColor = a.getColor(R.styleable.CustomSwitch_unselectedViewColor, 0);
        a.recycle();
    }

    private void initView() {

        // init parent layout
        setCardElevation(0);
        setCardBackgroundColor(selectedViewColor);
        setRadius(Utility.dpToPx(10));
        int padding = (int) Utility.dpToPx(3);
        setContentPadding(padding, padding, padding, padding);
        this.setOnClickListener(clickListener);

        // init second layout
        CardView secondLayer = new CardView(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        secondLayer.setLayoutParams(layoutParams);
        secondLayer.setCardElevation(0);
        secondLayer.setRadius(Utility.dpToPx(7));
        this.addView(secondLayer);

        // init content layout
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        leftView = new TextView(getContext());
        rightView = new TextView(getContext());
        leftView.setText(leftTextString);
        rightView.setText(rightTextString);
        leftView.setLayoutParams(layoutParams);
        rightView.setLayoutParams(layoutParams);
        leftView.setGravity(Gravity.CENTER);
        rightView.setGravity(Gravity.CENTER);
        linearLayout.addView(leftView);
        linearLayout.addView(rightView);
        secondLayer.addView(linearLayout);

        updateView();
    }

    public void setSelected(boolean selected) {
        checkState = selected;
        if (onCheckedChangeListener != null) onCheckedChangeListener.onCheckedChange(checkState);
        updateView();
    }

    private void updateView() {
        if (checkState) {
            leftView.setTextColor(selectedTextColor);
            leftView.setBackgroundColor(selectedViewColor);
            rightView.setTextColor(unselectedTextColor);
            rightView.setBackgroundColor(unselectedViewColor);
        } else {
            leftView.setTextColor(unselectedTextColor);
            leftView.setBackgroundColor(unselectedViewColor);
            rightView.setTextColor(selectedTextColor);
            rightView.setBackgroundColor(selectedViewColor);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup.LayoutParams layoutParams = leftView.getLayoutParams();
        layoutParams.width = parentWidth / 2;
        leftView.setLayoutParams(layoutParams);
        rightView.setLayoutParams(layoutParams);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    private View.OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            setSelected(!checkState);
        }
    };

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (!hasOnClickListeners())
            super.setOnClickListener(l);
        else
            throw new
                    IllegalStateException("Cannot override default OnClickListener!");
    }
}