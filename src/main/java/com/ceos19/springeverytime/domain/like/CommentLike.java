package com.ceos19.springeverytime.domain.like;

import com.ceos19.springeverytime.domain.Comment;
import com.ceos19.springeverytime.domain.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Entity
@DiscriminatorValue("C")
@NoArgsConstructor
public class CommentLike extends Like {
    @ManyToOne
    Comment comment;

    public CommentLike(@NonNull User user, @NonNull Date createDate, Comment comment) {
        super(user, createDate);
        this.comment = comment;
    }
}