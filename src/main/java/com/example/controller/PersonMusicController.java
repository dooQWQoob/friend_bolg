package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.PersonImage;
import com.example.entity.PersonMusic;
import com.example.mapper.PersonImageMapper;
import com.example.mapper.PersonMusicMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author taozi
 * @since 2023-11-08
 */
@RestController
@CrossOrigin
@RequestMapping("/personMusic")
public class PersonMusicController {
    @Autowired
    private PersonMusicMapper musicMapper;

    public String getImagePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\UserMp3\\";

    }

    @ApiOperation("根据用户id添加音乐")
    @PostMapping("/addMusicByUserid")
    public R addMusicByUserid(PersonMusic personMusic, @RequestParam("file") MultipartFile file) {
        String filepath = new Date().getTime() + file.getOriginalFilename();
        File targetFile = new File(getImagePath(), filepath);
        //防止路径穿越
        if (filepath.contains("../") || filepath.contains("..\\")) {
            return R.error().data("errmsg", "非法传输路径");
        }
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
            throw new RuntimeException(e);
        }
        //音乐路径
        personMusic.setMusicPath(filepath);
        //音乐名称,开始到有 . 结束
        String mp3Name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));
        personMusic.setMusicName(mp3Name);

        int insert = musicMapper.insert(personMusic);
        if (insert > 0) {
            return R.ok().data("msg", "添加音乐成功!");
        } else {
            return R.error().data("errmsg", "网络问题,请检查网络!");
        }
    }

    @ApiOperation("播放音乐")
    @GetMapping("/playMusic/{musicId}")
    public void playMusic(@PathVariable Integer musicId, HttpServletResponse response) {
        QueryWrapper<PersonMusic> wrapper = new QueryWrapper<>();
        wrapper.eq("music_id", musicId);
        PersonMusic personMusic = musicMapper.selectOne(wrapper);
        try {
            FileInputStream inputStream = new FileInputStream(getImagePath() + personMusic.getMusicPath());
            int i = inputStream.available(); //得到文件大小
            byte[] bytes = new byte[i];
            inputStream.read(bytes);
            inputStream.close();
            response.setContentType("audio/mp3"); //设置放回文件类型
            ServletOutputStream outputStream = response.getOutputStream();  //输出二进制数据对象
            outputStream.write(bytes); //输出对象
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("查看我的音乐列表")
    @GetMapping("/selectAllMusicByUserId/{userid}")
    public R selectAllMusicByUserId(@PathVariable Integer userid) {
        QueryWrapper<PersonMusic> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        List<PersonMusic> musicList = musicMapper.selectList(wrapper);
        return R.ok().data("musicList", musicList);
    }

    @ApiOperation("删除我的音乐")
    @GetMapping("/deleteByMusicId/{MusicId}")
    public R deleteByMusicId(@PathVariable Integer MusicId) {
        PersonMusic personMusic = musicMapper.selectById(MusicId);
        File file = new File(getImagePath() + personMusic.getMusicPath());
        if (file.isFile() && file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                musicMapper.deleteById(MusicId);
                return R.ok().data("msg", "删除成功");
            } else {
                return R.error().data("errmsg", "删除失败");
            }
        }
        return R.error().data("wlmsg", "当前网络问题,请稍等...");
    }
}
