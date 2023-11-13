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
@TableName("t_person_image")
public class PersonImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户相册图片ID
     */
    @TableId
    private Long personImgId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户相册图片路径
     */
    private String personImgPath;

    public Long getPersonImgId() {
        return personImgId;
    }

    public void setPersonImgId(Long personImgId) {
        this.personImgId = personImgId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getPersonImgPath() {
        return personImgPath;
    }

    public void setPersonImgPath(String personImgPath) {
        this.personImgPath = personImgPath;
    }

    @Override
    public String toString() {
        return "PersonImage{" +
            "personImgId=" + personImgId +
            ", userId=" + userId +
            ", personImgPath=" + personImgPath +
        "}";
    }
}
