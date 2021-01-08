package com.isprid.smartsafari.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.isprid.smartsafari.R;
import com.isprid.smartsafari.model.Comment;

public class EditCommentDialog extends AppCompatDialog implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private Context context;
    private Builder builder;
    private TextView tvTitle, tvError, tvSubmit, tvCancel;
    private RatingBar ratingBar;
    private EditText edtComment;

    public EditCommentDialog(Context context, Builder builder) {
        super(context);
        this.context = context;
        this.builder = builder;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.edit_comment_dialog);

        tvTitle = findViewById(R.id.dialog_rating_title);
        tvError = findViewById(R.id.dialog_rating_error);
        tvSubmit = findViewById(R.id.dialog_rating_button_feedback_submit);
        tvCancel = findViewById(R.id.dialog_rating_button_feedback_cancel);
        ratingBar = findViewById(R.id.dialog_rating_rating_bar);
        edtComment = findViewById(R.id.dialog_rating_feedback);

        init();
    }

    /**
     * Initialize the Edit comment dialog with values
     */
    private void init() {

        tvTitle.setText(builder.title);
        tvSubmit.setText(builder.submitText);
        tvCancel.setText(builder.cancelText);
        edtComment.setText(builder.comment.getComment());
        ratingBar.setRating(builder.comment.getUserRate());

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

            //check user has set one rating or not
            if (ratingBar.getRating() <= 0) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(context.getString(R.string.rating_dialog_error));
            } else {
                tvError.setVisibility(View.GONE);

                //check text field is empty or not
                if (TextUtils.isEmpty(edtComment.getText().toString())) {
                    edtComment.setError(context.getString(R.string.please_type_your_comment));
                } else {
                    dismiss();

                    builder.comment.setComment(edtComment.getText().toString());
                    builder.comment.setUserRate(ratingBar.getRating());

                    if (builder.commentDialogListener != null) {
                        builder.commentDialogListener.onCommentUpdated(builder.comment);
                    }
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
        private CommentDialogListener commentDialogListener;
        private Comment comment;

        public Builder(Context context) {
            this.context = context;
            initText();
        }

        private void initText() {
            title = context.getString(R.string.edit_rating_dialog_experience);
            submitText = context.getString(R.string.rating_dialog_submit);
            cancelText = context.getString(R.string.rating_dialog_cancel);
        }

        public Builder setComment(Comment comment) {
            this.comment = comment;
            return this;
        }

        public Builder onCommentSubmit(CommentDialogListener commentDialogListener) {
            this.commentDialogListener = commentDialogListener;
            return this;
        }

        public Builder ratingBarColor(int ratingBarColor) {
            this.ratingBarColor = ratingBarColor;
            return this;
        }

        public EditCommentDialog build() {
            return new EditCommentDialog(context, this);
        }

        public interface CommentDialogListener {
            void onCommentUpdated(Comment comment);
        }
    }
}