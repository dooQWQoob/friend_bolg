package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.PersonImage;
import com.example.mapper.PersonImageMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author taozi
 * @since 2023-11-08
 */
@RestController
@CrossOrigin
@RequestMapping("/personImage")
public class PersonImageController {
    @Autowired
    private PersonImageMapper imageMapper;

    public String getImagePath(){
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\UserImage\\";

    }

    @ApiOperation("根据用户id添加图片")
    @PostMapping("/insertByUserId")
    public R selectById(PersonImage personImage, @RequestParam("file") MultipartFile file){
        String rootpath = "http://localhost:8090/UserImage/";

        String newFileName = new Date().getTime() + file.getOriginalFilename();
        File targetFile = new File(getImagePath(),newFileName);
        //防止路径穿越
        if (newFileName.contains("../") || newFileName.contains("..\\")){
            return R.error().data("errmsg","非法传输路径");
        }
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        personImage.setPersonImgPath(rootpath+newFileName);
        int insert = imageMapper.insert(personImage);
        if (insert>0){
            return R.ok().data("msg","添加相册成功!");
        }else {
            return R.error().data("errmsg","网络问题,请检查网络!");
        }
    }

    @ApiOperation("根据图片地址删除图片")
    @GetMapping("/deleteByUrl/{imgid}")
    public R deleteByUrl(@PathVariable Integer imgid){
        QueryWrapper<PersonImage> wrapper = new QueryWrapper<>();
        wrapper.eq("person_img_id",imgid);
        PersonImage personImage = imageMapper.selectOne(wrapper);
        String imgpath = personImage.getPersonImgPath().substring(31);
        File file = new File(getImagePath()+imgpath);
        if (file.isFile()&&file.exists()){
            boolean delete = file.delete();
            if (delete){
                imageMapper.deleteById(imgid);
                return R.ok().data("msg","删除成功");
            }else {
                return R.error().data("errmsg","删除失败");
            }
        }
        return R.error().data("wlmsg","当前网络问题,请稍等...");
    }

    @ApiOperation("查看我的相册")
    @GetMapping("/selectImgByUserId/{userid}")
    public R selectImgByUserId(@PathVariable Integer userid){
        QueryWrapper<PersonImage> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        List<PersonImage> imageList = imageMapper.selectList(wrapper);
        return R.ok().data("imageList",imageList);
    }
}
