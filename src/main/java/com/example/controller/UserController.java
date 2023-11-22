package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @ApiOperation("验证用户手机号是否满足注册")
    @GetMapping("/enrollByPhone/{userPhone}")
    public R enrollByPhone(@PathVariable("userPhone") String userPhone){
        //查询手机号是否被注册
        User user = userMapper.selectByUserPhone(userPhone);
        if (user==null){
            return R.ok().data("msg","手机号可用");
        }else {
            return R.error().data("errmsg","手机号已被注册");
        }
    }

    @ApiOperation("用户注册")
    @PostMapping(value = "/enrollForm",produces = {"application/json;charset=UTF-8;"})
    public R userEnroll(@RequestBody User user){
            int insert = userMapper.insert(user);
            if (insert>0){
                return R.ok().data("okmsg","注册成功");
            }else {
                return R.ok().data("errmsg","请注意格式是否正确");
            }
    }

    @ApiOperation("用户修改头像")
    @PutMapping("/upUserPhoto")
    public R upUserPhoto(@RequestParam(value = "file",required = true) MultipartFile file, String phone) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(file.getBytes());
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_photo",encode).eq("user_phone",phone);
        int update = userMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("errmsg","修改失败");
        }
    }

    @ApiOperation("用户修改个人信息")
    @PostMapping(value = "/upPerson",produces = {"application/json;charset=UTF-8;"})
    public R upPerson(@RequestBody User user){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_name",user.getUserName())
                .set("user_phone",user.getUserPhone())
                .set("user_password",user.getUserPassword())
                .set("user_birthday",user.getUserBirthday())
                .set("user_email",user.getUserEmail())
                .set("user_age",user.getUserAge())
                .eq("user_id",user.getUserId());
        int update = userMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功,请重新登录");
        }else {
            return R.error().data("errmsg","修改失败,请注意您的格式是否正确");
        }
    }

    @ApiOperation("用户注销")
    @GetMapping("/deleteUser")
    public R deleteUser(@RequestParam("userphone")String userphone,@RequestParam("userpwd") String userpwd,@RequestParam("useremail") String useremail){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",userphone).eq("user_password",userpwd).eq("user_email",useremail);
        int delete = userMapper.delete(wrapper);
        if (delete>0){
            return R.ok().data("msg","销户成功,您可在一个月内恢复使用");
        }else {
            return R.error().data("errmsg","删除失败,请核对您的信息");
        }
    }

    @ApiOperation("用户恢复")
    @GetMapping("/userRecover")
    public R userRecover(@RequestParam("userphone") String userphone,@RequestParam("userpwd") String userpwd){
        int i = userMapper.updateUserPhoneAndUserPassword(userphone, userpwd);
        if (i>0){
            return R.ok().data("msg","恢复成功,请前往登录");
        }else {
            return R.error().data("msg","手机号或密码不正确");
        }
    }

    @ApiOperation("手机号密码登录")
    @GetMapping("/loginByPhone")
    public R loginByPhone(@RequestParam("userPhone") String userPhone,@RequestParam("userpassword") String userpassword){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",userPhone).eq("user_password",userpassword);
        User user = userMapper.selectOne(wrapper);
        if (user!=null){

            if (user.getUserPhoto()==null){
                return R.ok().data("user",user);
            }else {
                Base64.Decoder decoder = Base64.getDecoder();
                user.setUserPhoto(decoder.decode(user.getUserPhoto()));
            }
            return R.ok().data("user",user);
        } else {
            return R.error().data("msg","手机号或密码错误");
        }
    }

    @ApiOperation("邮箱密码登录")
    @GetMapping("/loginByEmail")
    public R loginByEmail(@RequestParam("userEmail") String userEmail,@RequestParam("userpassword") String userpassword){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_email",userEmail).eq("user_password",userpassword);
        User user = userMapper.selectOne(wrapper);
        if (user!=null){
            if (user.getUserPhoto()==null){
                return R.ok().data("user",user);
            }else {
                Base64.Decoder decoder = Base64.getDecoder();
                user.setUserPhoto(decoder.decode(user.getUserPhoto()));
            }
            return R.ok().data("user",user);
        } else {
            return R.error().data("msg","邮箱或密码错误");
        }
    }

    @ApiOperation("返回所有博主")
    @GetMapping("/allUser")
    public R allUser(){
        List<User> users = userMapper.selectList(null);
        users.forEach((u)->{
            if (u.getUserPhoto()!=null){
                Base64.Decoder decoder = Base64.getDecoder();
                u.setUserPhoto(decoder.decode(u.getUserPhoto()));
            }
        });
        return R.ok().data("userlist",users);
    }
}