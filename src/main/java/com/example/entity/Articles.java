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
@TableName("t_articles")
public class Articles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博文ID
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    /**
     * 发表用户ID
     */
    private Long userId;

    /**
     * 博文标题
     */
    private String articleTitle;

    /**
     * 博文内容
     */
    private String articleContent;

    /**
     * 博文图片
     */
    private byte[] artcleImage;

    /**
     * 浏览量
     */
    private Long articleViews;

    /**
     * 发表时间
     */
    private String articleDate;

    /**
     * 点赞数
     */
    private Long articleThumbs;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
    public byte[] getArtcleImage() {
        return artcleImage;
    }

    public void setArtcleImage(byte[] artcleImage) {
        this.artcleImage = artcleImage;
    }
    public Long getArticleViews() {
        return articleViews;
    }

    public void setArticleViews(Long articleViews) {
        this.articleViews = articleViews;
    }
    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }
    public Long getArticleThumbs() {
        return articleThumbs;
    }

    public void setArticleThumbs(Long articleThumbs) {
        this.articleThumbs = articleThumbs;
    }

    @Override
    public String toString() {
        return "Articles{" +
            "articleId=" + articleId +
            ", userId=" + userId +
            ", articleTitle=" + articleTitle +
            ", articleContent=" + articleContent +
            ", artcleImage=" + artcleImage +
            ", articleViews=" + articleViews +
            ", articleDate=" + articleDate +
            ", articleThumbs=" + articleThumbs +
        "}";
    }
}
