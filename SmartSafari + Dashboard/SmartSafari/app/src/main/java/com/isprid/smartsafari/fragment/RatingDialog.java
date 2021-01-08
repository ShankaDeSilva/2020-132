package com.isprid.smartsafari.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.isprid.smartsafari.R;


public class RatingDialog extends AppCompatDialog implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private Context context;
    private Builder builder;
    private TextView tvTitle, tvError, tvSubmit, tvCancel;
    private RatingBar ratingBar;

    public RatingDialog(Context context, Builder builder) {
        super(context);
        this.context = context;
        this.builder = builder;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_rating);

        tvTitle = findViewById(R.id.dialog_rating_title);
        tvError = findViewById(R.id.dialog_rating_error);
        tvSubmit = findViewById(R.id.dialog_rating_button_feedback_submit);
        tvCancel = findViewById(R.id.dialog_rating_button_feedback_cancel);
        ratingBar = findViewById(R.id.dialog_rating_rating_bar);

        init();
    }

    private void init() {

        tvTitle.setText(builder.title);
        tvSubmit.setText(builder.submitText);
        tvCancel.setText(builder.cancelText);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        int color = typedValue.data;

        tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));

        tvSubmit.setTextColor(color);
        tvCancel.setTextColor(ContextCompat.getColor(context, R.color.grey_500));


        if (builder.ratingBarColor != 0) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(0).setColorFilter(ContextCompat.getColor(context, R.color.grey_200), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(ContextCompat.getColor(context, builder.ratingBarColor), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(2).setColorFilter(ContextCompat.getColor(context, builder.ratingBarColor), PorterDuff.Mode.SRC_ATOP);

            } else {
                Drawable stars = ratingBar.getProgressDrawable();
                DrawableCompat.setTint(stars, ContextCompat.getColor(context, builder.ratingBarColor));
            }
        }


        ratingBar.setOnRatingBarChangeListener(this);
        tvSubmit.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.dialog_rating_button_feedback_submit) {

            if (ratingBar.getRating() <= 0) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(context.getString(R.string.rating_dialog_error));
            } else {
                tvError.setVisibility(View.GONE);
                dismiss();

                if (builder.ratingDialogFormListener != null) {
                    builder.ratingDialogFormListener.onRatingSubmitted(ratingBar.getRating());
                }
            }
        } else if (view.getId() == R.id.dialog_rating_button_feedback_cancel) {

            dismiss();

        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        if (ratingBar.getRating() <= 0) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(context.getString(R.string.rating_dialog_error));
        } else {
            tvError.setVisibility(View.GONE);
        }
    }


    public static class Builder {

        private final Context context;
        private String title, submitText, cancelText;
        private int ratingBarColor;
        private RatingDialogFormListener ratingDialogFormListener;

        public Builder(Context context) {
            this.context = context;
            initText();
        }

        private void initText() {
            title = context.getString(R.string.rating_dialog_experience);
            submitText = context.getString(R.string.rating_dialog_submit);
            cancelText = context.getString(R.string.rating_dialog_cancel);
        }

        public Builder onRatingBarSubmit(RatingDialogFormListener ratingDialogFormListener) {
            this.ratingDialogFormListener = ratingDialogFormListener;
            return this;
        }

        public Builder ratingBarColor(int ratingBarColor) {
            this.ratingBarColor = ratingBarColor;
            return this;
        }

        public RatingDialog build() {
            return new RatingDialog(context, this);
        }

        public interface RatingDialogFormListener {
            void onRatingSubmitted(float rating);
        }
    }
}