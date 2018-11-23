package com.yenimobile.weblibprject.model;

public class TumPost {

    private String tumPostId;
    private String tumPostImageUrl;
    private String tumPostType;
    private String tumPostUserName;

    public TumPost(){}


    public TumPost(String tumPostId, String tumPostImageUrl, String tumPostType, String tumPostUserName) {
        this.tumPostId = tumPostId;
        this.tumPostImageUrl = tumPostImageUrl;
        this.tumPostType = tumPostType;
        this.tumPostUserName = tumPostUserName;
    }


    public String getTumPostId() {
        return tumPostId;
    }

    public void setTumPostId(String tumPostId) {
        this.tumPostId = tumPostId;
    }

    public String getTumPostImageUrl() {
        return tumPostImageUrl;
    }

    public void setTumPostImageUrl(String tumPostImageUrl) {
        this.tumPostImageUrl = tumPostImageUrl;
    }

    public String getTumPostType() {
        return tumPostType;
    }

    public void setTumPostType(String tumPostType) {
        this.tumPostType = tumPostType;
    }

    public String getTumPostUserName() {
        return tumPostUserName;
    }

    public void setTumPostUserName(String tumPostUserName) {
        this.tumPostUserName = tumPostUserName;
    }
}
