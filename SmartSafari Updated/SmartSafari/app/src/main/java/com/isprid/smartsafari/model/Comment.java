package com.isprid.smartsafari.model;

import com.isprid.smartsafari.util.Constant;

import java.util.concurrent.TimeUnit;

public class Comment {
    String cid;
    String userName;
    String userId;
    String comment;
    String type;
    float userRate;
    String predictedRate;
    String image;
    long timestamp;

    public Comment() {
    }


    public Comment(String cid, String userName, String userId, String comment, String type, float userRate, String predictedRate) {
        this.cid = cid;
        this.userName = userName;
        this.userId = userId;
        this.comment = comment;
        this.type = type;
        this.userRate = userRate;
        this.predictedRate = predictedRate;
        this.image = Constant.DEFAULT_USER;
        this.timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }


    public Comment(String userName, String userId, String comment, String type, float userRate, String predictedRate) {
        this.userName = userName;
        this.userId = userId;
        this.comment = comment;
        this.type = type;
        this.userRate = userRate;
        this.predictedRate = predictedRate;
        this.image = Constant.DEFAULT_USER;
        this.timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }


    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUserRate(float userRate) {
        this.userRate = userRate;
    }

    public String getCid() {
        return cid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public float getUserRate() {
        return userRate;
    }

    public String getPredictedRate() {
        return predictedRate;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
