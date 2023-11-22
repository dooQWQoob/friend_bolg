package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Articles;
import com.example.entity.Comments;
import com.example.entity.User;
import com.example.mapper.ArticlesMapper;
import com.example.mapper.CommentsMapper;
import com.example.mapper.UserMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @ApiOperation("添加新博客文")
    @PostMapping("/addArticle")
    public R addArticle(Articles articles, @RequestParam("file") MultipartFile file) throws IOException {
        //判断该博文是否已添加
        QueryWrapper<Articles> wrapper = new QueryWrapper<>();
        wrapper.eq("article_title", articles.getArticleTitle());
        Articles selectOne = articlesMapper.selectOne(wrapper);
//        空，表示未添加，可以添加
        if (selectOne == null) {
            //将图片加码
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encode = encoder.encode(file.getBytes());
            articles.setArtcleImage(encode);
            articlesMapper.insert(articles);
            return R.ok().data("msg", "添加成功");
        } else {
            return R.error().data("errmsg", "该博客标题您已添加");
        }
    }

    @ApiOperation("修改博客文")
    @PostMapping("/upArticle")
    public R upArticle(Articles articles, @RequestParam("file") MultipartFile file) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(file.getBytes());
        articles.setArtcleImage(encode);
        UpdateWrapper<Articles> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("article_title", articles.getArticleTitle())
                .set("article_content", articles.getArticleContent())
                .set("artcle_image", articles.getArtcleImage())
                .eq("article_id", articles.getArticleId());
        int update = articlesMapper.update(null, updateWrapper);
        if (update > 0) {
            return R.ok().data("msg", "修改成功");
        } else {
            return R.error().data("errmsg", "修改失败,请注意是否有空值");
        }
    }

    @ApiOperation("查看所有博客文")
    @GetMapping("/allArticles/{current}")
    public R allArticles(@PathVariable Integer current) {
        //设置分页参数 1：第几页 2：每页几条数据
        Page<Articles> page = new Page<>(current, 10);
        QueryWrapper<Articles> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("article_date");
        //进行查询
        articlesMapper.selectPage(page, wrapper);
        //将查询数据用实体类包装
        List<Articles> articles = page.getRecords();
        //获取博文总数
        int pages = articlesMapper.countArticle();

        //将博文的图片解码
        articles.forEach((a) -> {
            Base64.Decoder decoder = Base64.getDecoder();
            a.setArtcleImage(decoder.decode(a.getArtcleImage()));
        });
        return R.ok().data("articles", articles).data("pages",pages);
    }

    @ApiOperation("根据id查看博文")
    @GetMapping("/selectByArticleId/{ArticleId}")
    public R selectByArticleId(@PathVariable Long ArticleId){
        //查询博文
        Articles articles = articlesMapper.selectById(ArticleId);
        Base64.Decoder decoder = Base64.getDecoder();
        articles.setArtcleImage(decoder.decode(articles.getArtcleImage()));
        //浏览量+1
        UpdateWrapper<Articles> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("article_views",articles.getArticleViews()+1).eq("article_id",ArticleId);
        articlesMapper.update(null,updateWrapper);
        //博文的博主
        User user = userMapper.selectById(articles.getUserId());
        if (user.getUserPhoto()!=null){
            user.setUserPhoto(decoder.decode(user.getUserPhoto()));
        }
        //博文评论数量
        int countArticle = commentsMapper.countByArticleId(ArticleId);
        //博文评论
        QueryWrapper<Comments> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",ArticleId).orderByDesc("comment_date");
        List<Comments> comments = commentsMapper.selectList(wrapper);
        //统计博主发布数量
        int countArticleSum = articlesMapper.countByUserId(articles.getUserId());
        //博主前三条博文
        List<Articles> articlesList = articlesMapper.selectAllUser(articles.getUserId());
        return R.ok().data("articles",articles)
                .data("user",user)
                .data("countArticle",countArticle)
                .data("comments",comments)
                .data("countArticleSum",countArticleSum)
                .data("articlesList",articlesList);
    }

    @ApiOperation("查看今日博客")
    @GetMapping("/selectByDay")
    public R selectByDay(){
        List<Articles> articles = articlesMapper.selectByDay();
        return R.ok().data("articles",articles);
    }

    @ApiOperation("查看个人博客")
    @GetMapping("/selectByUserId/{userId}")
    public R selectByUserId(@PathVariable Integer userId) {
        QueryWrapper<Articles> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Articles> articles = articlesMapper.selectList(wrapper);
        //将博文的图片解码
        articles.forEach((a) -> {
            Base64.Decoder decoder = Base64.getDecoder();
            a.setArtcleImage(decoder.decode(a.getArtcleImage()));
        });
        return R.ok().data("articles", articles);
    }

    @ApiOperation("删除博客文")
    @GetMapping("/deleteById/{articleId}")
    public R deleteById(@PathVariable Integer articleId) {
        int i = articlesMapper.deleteById(articleId);
        if (i > 0) {
            return R.ok().data("msg", "删除成功");
        } else {
            return R.error().data("errmsg", "删除失败");
        }
    }
}
