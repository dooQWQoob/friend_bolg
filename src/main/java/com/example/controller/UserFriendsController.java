package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.User;
import com.example.entity.UserFriends;
import com.example.mapper.UserFriendsMapper;
import com.example.mapper.UserMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/friends")
public class UserFriendsController {
    @Autowired
    private UserFriendsMapper userFriendsMapper;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation("发起好友请求")
    @PostMapping(value = "/addfriend",produces = {"application/json;charset=UTF-8;"})
    public R addfriend(@RequestBody UserFriends userFriends){
        QueryWrapper<UserFriends> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userFriends.getUserId()).eq("user_friends_id",userFriends.getUserFriendsId());
        UserFriends friends = userFriendsMapper.selectOne(wrapper);
        if (friends!=null){
            if ("0".equals(friends.getUserStatus())){
                return R.error().data("errmsg","您已发送好友请求,请等待好友确认");
            }else if ("1".equals(friends.getUserStatus())){
                return R.error().data("errmsg","你们已是好友关系");
            }else {
                return R.error().data("errmsg","您已发送好友请求");
            }

        }else {
            userFriendsMapper.insert(userFriends);
            return R.ok().data("msg","发起添加好友成功");
        }
    }

    @ApiOperation("好友通过申请")
    @GetMapping("/upStatus1")
    public R upStatus1(@RequestParam("friend_id") Integer friend_id,@RequestParam("userId") Integer userId){
        UpdateWrapper<UserFriends> wrapper = new UpdateWrapper<>();
        wrapper.set("user_status","1").eq("user_friends_id",friend_id).eq("user_id",userId);
        userFriendsMapper.update(null,wrapper);
        return R.ok().data("msg","通过成功");
    }

    @ApiOperation("好友未通过")
    @GetMapping("/upStatus2")
    public R upupStatus2(@RequestParam("friend_id") Integer friend_id,@RequestParam("userId") Integer userId){
        UpdateWrapper<UserFriends> wrapper = new UpdateWrapper<>();
        wrapper.set("user_status","2").eq("user_friends_id",friend_id).eq("user_id",userId);
        userFriendsMapper.update(null,wrapper);
        return R.ok().data("msg","未通过好友申请");
    }

    @ApiOperation("再次发送好友请求")
    @GetMapping("upSetStatus0")
    public R upSetStatus1(@RequestParam("friend_id") Integer friend_id,@RequestParam("userId") Integer userId,@RequestParam("user_note") String user_note){
        UpdateWrapper<UserFriends> wrapper = new UpdateWrapper<>();
        wrapper.set("user_status","0")
                .set("user_note",user_note)
                .eq("user_friends_id",friend_id)
                .eq("user_id",userId)
                .eq("user_status","2");
        userFriendsMapper.update(null,wrapper);
        return R.ok().data("msg","再次发送好友申请成功");
    }

    @ApiOperation("查看新朋友")
    @GetMapping("/selectMyFriends/{userid}")
    public R selectMyFriends(@PathVariable Integer userid){
        QueryWrapper<UserFriends> wrapper = new QueryWrapper<>();
        wrapper.eq("user_friends_id",userid).eq("user_status",0);
        List<UserFriends> userFriends = userFriendsMapper.selectList(wrapper);
        ArrayList<User> users = new ArrayList<>();
        userFriends.forEach((u)->{
            //根据用户id查询信息
            User user = userMapper.selectById(u.getUserId());
            if (user.getUserPhoto()!=null){
                Base64.Decoder decoder = Base64.getDecoder();
                user.setUserPhoto(decoder.decode(user.getUserPhoto()));
            }
            users.add(user);
        });
        return R.ok().data("users",users).data("userFriends",userFriends);
    }

    @ApiOperation("查看我发起的好友请求")
    @GetMapping("/selectMyFriendsByOK/{userid}")
    public R selectMyFriendsByOK(@PathVariable Integer userid){
        QueryWrapper<UserFriends> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        List<UserFriends> userFriends = userFriendsMapper.selectList(wrapper);
        ArrayList<User> users = new ArrayList<>();
        userFriends.forEach((u)->{
            //根据好友id查询
            User user = userMapper.selectById(u.getUserFriendsId());
            if (user.getUserPhoto()!=null){
                Base64.Decoder decoder = Base64.getDecoder();
                user.setUserPhoto(decoder.decode(user.getUserPhoto()));
            }
            users.add(user);
        });
        return R.ok().data("users",users).data("userFriends",userFriends);
    }

    @ApiOperation("好友列表")
    @GetMapping("/selectByList/{uid}")
    public R selectByList(@PathVariable Integer uid){
        QueryWrapper<UserFriends> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",uid).eq("user_status","1");
        List<UserFriends> userFriends = userFriendsMapper.selectList(wrapper);
        //好友集合
        ArrayList<User> users = new ArrayList<>();
        userFriends.forEach((u)->{
            //根据好友id查询
            User user = userMapper.selectById(u.getUserFriendsId());
            if (user.getUserPhoto()!=null){
                Base64.Decoder decoder = Base64.getDecoder();
                user.setUserPhoto(decoder.decode(user.getUserPhoto()));
            }
            users.add(user);
        });
        return R.ok().data("userFriends",userFriends).data("Friendslist",users);
    }

}
