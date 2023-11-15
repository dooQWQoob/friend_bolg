package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Comments;
import com.example.mapper.CommentsMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsMapper commentsMapper;

    @ApiOperation("新发表评论")
    @PostMapping(value = "/addComment",produces = {"application/json;charset=UTF-8;"})
    public R addComment(@RequestBody Comments comments){
        int insert = commentsMapper.insert(comments);
        if (insert>0){
            return R.ok().data("msg","评论成功");
        }else {
            return R.error().data("errmsg","评论失败,请注意您的言辞");
        }
    }

    @ApiOperation("修改评论")
    @PostMapping(value = "/upComments",produces = {"application/json;charset=UTF-8;"})
    public R upComments(@RequestBody Comments comments){
        UpdateWrapper<Comments> wrapper = new UpdateWrapper<>();
        wrapper.set("comment_content",comments.getCommentContent())
                .set("comment_date",comments.getCommentDate())
                .eq("comment_id",comments.getCommentId())
                .eq("user_id",comments.getUserId())
                .eq("article_id",comments.getArticleId())
                .eq("parent_comment_id",comments.getParentCommentId());
        int update = commentsMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改评论");
        }else {
            return R.error().data("errmsg","评论失败,请注意您的言辞");
        }
    }

    @ApiOperation("删除评论")
    @GetMapping("/deleteByCommentId")
    public R deleteByCommentId(@RequestParam Long commentId,@RequestParam Long userId){
        int i = commentsMapper.deleteByCommentIdAndUserId(commentId, userId);
        if (i>0){
            return R.ok().data("msg","删除评论成功");
        }else {
            return R.error().data("errmsg","删除评论失败");
        }
    }

    @ApiOperation("查看我的评论")
    @GetMapping("/selectCommentByUserId/{userid}")
    public R selectCommentByUserId(@PathVariable Integer userid){
        QueryWrapper<Comments> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        List<Comments> comments = commentsMapper.selectList(wrapper);
        return R.ok().data("comments",comments);
    }

    @ApiOperation("查询当前博客评论")
    @GetMapping("/selectCommentByArticleId/{ArticleId}")
    public R selectCommentByArticleId(@PathVariable Integer ArticleId){
        QueryWrapper<Comments> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",ArticleId);
        List<Comments> comments = commentsMapper.selectList(wrapper);
        return R.ok().data("comments",comments);
    }
}
