package com.example.cyclingclub.events;
public class Comment{
    private int rating;
    private String comment;
    private int clubID;

    public Comment(int rating, String comment, int clubID) {
        this.rating = rating;
        this.comment = comment;
        this.clubID = clubID;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public int getClubID() {
        return clubID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public String getReviewText() {
        return "\nRating:" + getRating() + "\n" + getComment() + "";
    }
}
