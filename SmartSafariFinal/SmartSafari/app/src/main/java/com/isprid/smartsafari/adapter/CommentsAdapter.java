package com.isprid.smartsafari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.isprid.smartsafari.R;
import com.isprid.smartsafari.model.Comment;
import com.isprid.smartsafari.util.Constant;
import com.isprid.smartsafari.util.PrefUtils;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private final ArrayList<Comment> comments;
    private final PrefUtils pref;
    private final CommentListener listener;
    private Context context;

    public CommentsAdapter(Context context, ArrayList<Comment> comments, CommentListener listener) {
        this.context = context;
        this.comments = comments;
        this.listener = listener;
        pref = new PrefUtils(context);
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.comment_row, parent, false);
        CommentsViewHolder item = new CommentsViewHolder(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentsViewHolder holder, final int position) {
        final Comment comment = comments.get(position);
        final String userId = pref.getPrefsValue(Constant.UUID);

        holder.txtCommentType.setText(comment.getType());
        holder.txtComment.setText(comment.getComment());
        holder.txtUserName.setText(comment.getUserName());
        holder.txtRate.setText("Predicted Rate : " + comment.getPredictedRate());
        holder.userRatingBar.setRating(comment.getUserRate());

        if (comment.getImage() != null) {
            Picasso.with(context).load(comment.getImage()).into(holder.ivUser);
        }

        //SETTING LISTENERS TO MORE
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, holder.ivMore);
                //Inflating the Popup using xml file

                popup.getMenuInflater().inflate(R.menu.context_popup_menu, popup.getMenu());

                popup.getMenu().findItem(R.id.menu_edit).setVisible(comment.getUserId().equals(userId));
                popup.getMenu().findItem(R.id.menu_remove).setVisible(comment.getUserId().equals(userId));

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(Constant.EDIT)) {
                            listener.onEdit(comment);
                        } else if (item.getTitle().equals(Constant.REMOVE)) {
                            listener.onRemove(comment);
                        } else if (item.getTitle().equals(Constant.REPORT)) {
                            listener.onReport(comment);
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public interface CommentListener {

        void onEdit(Comment comment);

        void onRemove(Comment comment);

        void onReport(Comment comment);
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        public View container;
        RatingBar userRatingBar;
        CircularImageView ivUser;
        ImageView ivMore;
        TextView txtCommentType, txtUserName, txtRate, txtComment;


        public CommentsViewHolder(final View itemView) {
            super(itemView);
            container = itemView;
            ivUser = itemView.findViewById(R.id.ivUser);
            txtCommentType = itemView.findViewById(R.id.txt_comment_type);
            txtUserName = itemView.findViewById(R.id.txt_user_name);
            txtRate = itemView.findViewById(R.id.txt_rate);
            txtComment = itemView.findViewById(R.id.txt_comment);
            ivMore = itemView.findViewById(R.id.iv_more);
            userRatingBar = itemView.findViewById(R.id.user_rating_bar);
        }
    }
}
