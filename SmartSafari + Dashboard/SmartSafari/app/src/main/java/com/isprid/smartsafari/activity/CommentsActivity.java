package com.isprid.smartsafari.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.isprid.smartsafari.R;
import com.isprid.smartsafari.adapter.CommentsAdapter;
import com.isprid.smartsafari.adapter.SliderAdapter;
import com.isprid.smartsafari.fragment.EditCommentDialog;
import com.isprid.smartsafari.fragment.RatingDialog;
import com.isprid.smartsafari.helper.AnalyzeListener;
import com.isprid.smartsafari.model.Comment;
import com.isprid.smartsafari.model.Report;
import com.isprid.smartsafari.model.SliderItem;
import com.isprid.smartsafari.util.Constant;
import com.isprid.smartsafari.util.PrefUtils;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class CommentsActivity extends AppCompatActivity {

    private final String TAG = CommentsActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private ArrayList<Comment> comments = new ArrayList<>();
    private DatabaseReference databaseComments;
    private PrefUtils pref;
    private CommentsAdapter mAdapter;
    private ProgressBar pbComment;
    private TextView txtCommentCount;
    private EditText edtComment;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = new PrefUtils(this);

        //add firebase references
        databaseComments = FirebaseDatabase.getInstance().getReference("Comments");
        databaseReports = FirebaseDatabase.getInstance().getReference("Reports");

        recyclerView = findViewById(R.id.mainRecViewCm);
        pbComment = findViewById(R.id.pbComment);
        final ImageView ivFilter = findViewById(R.id.ivFilter);
        txtCommentCount = findViewById(R.id.txtCommentCount);
        edtComment = findViewById(R.id.edt_comment);
        ImageView ivAddComment = findViewById(R.id.ivAddComment);


        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(CommentsActivity.this, ivFilter);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.sort_popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(Constant.ALL)) {
                            fetchComments();
                        } else if (item.getTitle().equals(Constant.POSITIVE)) {
                            fetchCommentsByFilter(Constant.POSITIVE);
                        } else if (item.getTitle().equals(Constant.NEGATIVE)) {
                            fetchCommentsByFilter(Constant.NEGATIVE);
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu

            }
        }); //closing the setOnClickListener method


        ivAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String commentText = edtComment.getText().toString().trim();

                //validate comment field
                if (TextUtils.isEmpty(commentText)) {
                    edtComment.setError("Please type comment");
                    return;
                }


                final RatingDialog ratingDialog = new RatingDialog.Builder(CommentsActivity.this)
                        .ratingBarColor(R.color.yellow)
                        .onRatingBarSubmit(new RatingDialog.Builder.RatingDialogFormListener() {
                            @Override
                            public void onRatingSubmitted(float rating) {
                                Log.i(TAG, "Rating:" + rating);
                                submitUserComment(commentText, rating);

                            }
                        })
                        .build();


                ratingDialog.show();
            }
        });


        initAdapter();
        initSlider();
        fetchComments();
    }

    /**
     * initialize the main slider
     */
    private void initSlider() {
        ArrayList<SliderItem> images = new ArrayList<>();
        images.add(new SliderItem(R.drawable.safari_image, ""));
        images.add(new SliderItem(R.drawable.masai_mara_shutter, ""));
        images.add(new SliderItem(R.drawable.serengeti_pioneer_camp, ""));
        images.add(new SliderItem(R.drawable.safari_getty_image, ""));

        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(this, images);

        sliderView.setSliderAdapter(adapter);

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }


    /**
     * initialize the comment adapter
     */
    private void initAdapter() {
        mAdapter = new CommentsAdapter(CommentsActivity.this, comments, new CommentsAdapter.CommentListener() {
            @Override
            public void onEdit(Comment comment) {

                final EditCommentDialog editCommentDialog = new EditCommentDialog.Builder(CommentsActivity.this)
                        .ratingBarColor(R.color.yellow)
                        .setComment(comment)
                        .onCommentSubmit(new EditCommentDialog.Builder.CommentDialogListener() {
                            @Override
                            public void onCommentUpdated(Comment comment) {
                                Log.i(TAG, "Comment:" + comment);
                                updateComment(comment);
                            }

                        })
                        .build();


                editCommentDialog.show();
            }

            @Override
            public void onRemove(Comment comment) {
                removeComment(comment);
            }

            @Override
            public void onReport(Comment comment) {
                reportComment(comment);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void submitUserComment(final String commentText, final float userRate) {
        final String userName = pref.getPrefsValue(Constant.NAME);
        final String userId = pref.getPrefsValue(Constant.UUID);

        progressDialog = new ProgressDialog(CommentsActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        //call to analyse comment end point to get comment type
        analyseComment(commentText, new AnalyzeListener() {
            @Override
            public void onSuccess(String type, String predictedRate) {
                Comment comment = new Comment(userName, userId, commentText, type, userRate, predictedRate);
                saveComment(comment);

                if (!CommentsActivity.this.isFinishing()) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onError(final String message) {
                if (!CommentsActivity.this.isFinishing()) {
                    Toast.makeText(CommentsActivity.this, message, Toast.LENGTH_LONG).show();

                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }


    /**
     * Report comment
     *
     * @param comment
     */
    private void reportComment(final Comment comment) {
        //get report count
        databaseReports.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild(comment.getCid())) {
                    DataSnapshot existComment = snapshot.child(comment.getCid());
                    Report report = existComment.getValue(Report.class);

                    int reportCount = report.getReportCount() + 1;

                    if (reportCount >= Constant.MAX_REPORT_COUNT) {
                        String type = (comment.getType().equals("Negative") ? "Positive" : "Negative");
                        comment.setType(type);
                        databaseComments.child(comment.getCid()).setValue(comment);

                        DatabaseReference deleteNode = databaseReports.child(comment.getCid());
                        deleteNode.removeValue();
                    } else {
                        report.setReportCount(reportCount);
                        databaseReports.child(comment.getCid()).setValue(report);
                    }

                } else {
                    Report report = new Report(comment.getCid(), 1);
                    databaseReports.child(comment.getCid()).setValue(report);
                }
                Toast.makeText(CommentsActivity.this, "Report submitted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * remove comment from database
     *
     * @param comment
     */
    private void removeComment(final Comment comment) {
        AlertDialog.Builder alert = new AlertDialog.Builder(CommentsActivity.this);
        alert.setTitle("Remove comment");
        alert.setMessage("Are you sure you want to remove?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference deleteNode = databaseComments.child(comment.getCid());
                deleteNode.removeValue();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


    /**
     * fetch all user comment
     */
    private void fetchComments() {
        showLoading(true);

        //attaching value event listener
        Query commentsQuery = databaseComments.orderByChild("timestamp");

        commentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showLoading(false);

                //clearing the previous data list
                comments.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Comment
                    Comment comment = postSnapshot.getValue(Comment.class);
                    //adding data to the list
                    comments.add(comment);
                }

                //get latest comment to the top of the list
                Collections.reverse(comments);

                //notify comment adapter about list changes
                mAdapter.notifyDataSetChanged();

                //update the comment count
                txtCommentCount.setText(String.valueOf(comments.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showLoading(false);
            }
        });
    }


    /**
     * Fetch comment according to the comment type from the firebase
     *
     * @param type
     */
    private void fetchCommentsByFilter(String type) {
        Query query = databaseComments.orderByChild("type").equalTo(type);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showLoading(false);

                //clearing the previous data list
                comments.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Comment
                    Comment comment = postSnapshot.getValue(Comment.class);
                    //adding data to the list
                    comments.add(comment);
                }

                //get latest comment to the top of the list
                Collections.reverse(comments);

                //notify comment adapter about list changes
                mAdapter.notifyDataSetChanged();

                //update the comment count
                txtCommentCount.setText(String.valueOf(comments.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showLoading(false);
            }
        });

    }


    /**
     * Analyze comment from the server and get comment details
     *
     * @param comment
     * @param listener
     */
    public void analyseComment(String comment, final AnalyzeListener listener) {

        //create object parameters
        JSONObject paramJson = new JSONObject();

        try {
            paramJson.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //create new volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constant.COMMENT_ANALYSE_END_POINT, paramJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject messageObj = response.getJSONObject("message");
                            Boolean status = response.getBoolean("status");

                            String rate = messageObj.getString("rate");
                            String type = messageObj.getString("type");

                            if (status) {
                                type = (type.equals("neg")) ? "Negative" : "Positive";

                                Log.e("Analyse Comment", "Rate : " + rate + " | Type : " + type);


                                listener.onSuccess(type, rate);

                            } else {
                                listener.onError("Something went wrong");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * Save the comment in firebase
     *
     * @param comment
     */
    private void saveComment(Comment comment) {
        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for Comment
        String id = databaseComments.push().getKey();
        comment.setCid(id);
        //Saving the Comment
        databaseComments.child(id).setValue(comment);

        //setting edittext to blank again
        edtComment.setText("");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //displaying a success toast
                Toast.makeText(CommentsActivity.this, "Comment submitted", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * Update the comment in firebase
     *
     * @param comment
     */
    private void updateComment(Comment comment) {
        //Saving the Comment
        databaseComments.child(comment.getCid()).setValue(comment);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //displaying a success toast
                Toast.makeText(CommentsActivity.this, "Comment Updated", Toast.LENGTH_LONG).show();
            }
        });

    }


    /**
     * Display the progressing bar when loading comments
     *
     * @param show
     */
    private void showLoading(boolean show) {
        pbComment.setVisibility((show) ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility((show) ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}