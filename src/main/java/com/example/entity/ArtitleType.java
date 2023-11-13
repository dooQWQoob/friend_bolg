package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author taozi
 * @since 2023-11-08
 */
@TableName("t_artitle_type")
public class ArtitleType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableId
    private Long articleId;

    /**
     * 分类ID
     */
    private Long sortId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "ArtitleType{" +
            "articleId=" + articleId +
            ", sortId=" + sortId +
        "}";
    }
}
