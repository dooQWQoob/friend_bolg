package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_types")
public class Types implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "sort_id", type = IdType.AUTO)
    private Long sortId;

    /**
     * 分类名称
     */
    private String sortName;

    /**
     * 分类描述
     */
    private String sortDescription;

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    public String getSortDescription() {
        return sortDescription;
    }

    public void setSortDescription(String sortDescription) {
        this.sortDescription = sortDescription;
    }

    @Override
    public String toString() {
        return "Types{" +
            "sortId=" + sortId +
            ", sortName=" + sortName +
            ", sortDescription=" + sortDescription +
        "}";
    }
}
