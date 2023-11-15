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
@TableName("t_person_music")
public class PersonMusic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户音乐id
     */
    @TableId(value = "music_id", type = IdType.AUTO)
    private Long musicId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 音乐名称
     */
    private String musicName;
    /**
     * 用户音乐路径
     */
    private String musicPath;

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    @Override
    public String toString() {
        return "PersonMusic{" +
                "musicId=" + musicId +
                ", musicName='" + musicName + '\'' +
                ", musicPath='" + musicPath + '\'' +
                ", userId=" + userId +
                '}';
    }
}
