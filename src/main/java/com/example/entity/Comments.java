package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author taozi
 * @since 2023-11-08
 */
@TableName("t_comments")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    /**
     * 发表用户ID
     */
    private Long userId;

    /**
     * 评论博文ID
     */
    private Long articleId;

    /**
     * 点赞数
     */
    private Long articleThumbs;

    /**
     * 评论日期
     */
    private LocalDateTime commentDate;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 父评论ID
     */
    private Long parentCommentId;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getArticleThumbs() {
        return articleThumbs;
    }

    public void setArticleThumbs(Long articleThumbs) {
        this.articleThumbs = articleThumbs;
    }
    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public String toString() {
        return "Comments{" +
            "commentId=" + commentId +
            ", userId=" + userId +
            ", articleId=" + articleId +
            ", articleThumbs=" + articleThumbs +
            ", commentDate=" + commentDate +
            ", commentContent=" + commentContent +
            ", parentCommentId=" + parentCommentId +
        "}";
    }
}
