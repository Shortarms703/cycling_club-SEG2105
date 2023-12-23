package com.example.cyclingclub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.cyclingclub.accounts.Account;
import com.example.cyclingclub.accounts.CyclingClubAccount;
import com.example.cyclingclub.accounts.ParticipantAccount;
import com.example.cyclingclub.accounts.Role;
import com.example.cyclingclub.events.Comment;

import org.junit.Test;

public class CommentTest {


    @Test
    public void testCommentConstruction() {
        Comment comment = new Comment(5, "This is a comment", 1);
        assertEquals(5, comment.getRating());
        assertEquals("This is a comment", comment.getComment());
        assertEquals(1, comment.getClubID());
    }

    @Test
    public void testCommentNullConstruction() {
        Comment comment = new Comment(0, null, 0);
        assertEquals(0, comment.getRating());
        assertNull(comment.getComment());
        assertEquals(0, comment.getClubID());
    }

    @Test
    public void testCommentGetRating() {
        assertEquals(5, new Comment(5, "This is a comment", 1).getRating());
    }

    @Test
    public void testCommentGetComment() {
        assertEquals("This is a comment", new Comment(5, "This is a comment", 1).getComment());
    }

    @Test
    public void testCommentGetClubID() {
        assertEquals(1, new Comment(5, "This is a comment", 1).getClubID());
    }

    @Test
    public void testCommentSetRating() {
        Comment comment = new Comment(5, "This is a comment", 1);
        comment.setRating(4);
        assertEquals(4, comment.getRating());
    }

    @Test
    public void testCommentSetComment() {
        Comment comment = new Comment(5, "This is a comment", 1);
        comment.setComment("This is a new comment");
        assertEquals("This is a new comment", comment.getComment());
    }

    @Test
    public void testCommentSetClubID() {
        Comment comment = new Comment(5, "This is a comment", 1);
        comment.setClubID(2);
        assertEquals(2, comment.getClubID());
    }

    @Test
    public void testGetReviewText() {
        Comment comment = new Comment(5, "This is a comment", 1);
        assertEquals("\nRating:5\nThis is a comment", comment.getReviewText());
    }

    @Test
    public void testNullComment() {
        Comment comment = new Comment(5, null, 1);
        assertEquals("\nRating:5\nnull", comment.getReviewText());
    }
}
