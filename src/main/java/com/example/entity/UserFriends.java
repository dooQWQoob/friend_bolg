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
@TableName("t_user_friends")
public class UserFriends implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户与好友关系ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 好友ID
     */
    private Long userFriendsId;

    /**
     * 好友备注
     */
    private String userNote;

    /**
     * 好友状态
     * 0、申请好友
     * 1、好友通过
     * 2、未通过好友申请
     */
    private String userStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserFriendsId() {
        return userFriendsId;
    }

    public void setUserFriendsId(Long userFriendsId) {
        this.userFriendsId = userFriendsId;
    }
    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "UserFriends{" +
            "id=" + id +
            ", userId=" + userId +
            ", userFriendsId=" + userFriendsId +
            ", userNote=" + userNote +
            ", userStatus=" + userStatus +
        "}";
    }
}
