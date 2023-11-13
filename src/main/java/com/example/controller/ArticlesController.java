package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Articles;
import com.example.mapper.ArticlesMapper;
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
 *  前端控制器
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

    @ApiOperation("添加新博客文")
    @PostMapping("/addArticle")
    public R addArticle(Articles articles, @RequestParam("file")MultipartFile file) throws IOException {
        //判断该博文是否已添加
        QueryWrapper<Articles> wrapper = new QueryWrapper<>();
        wrapper.eq("article_title",articles.getArticleTitle());
        Articles selectOne = articlesMapper.selectOne(wrapper);
        //空，表示未添加，可以添加
        if (selectOne==null){
            //将图片加码
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encode = encoder.encode(file.getBytes());
            articles.setArtcleImage(encode);
            articlesMapper.insert(articles);
            return R.ok().data("msg","添加成功");
        }else{
            return R.error().data("errmsg","该博客标题您已添加");
        }
    }

    @ApiOperation("修改博客文")
    @PostMapping("/upArticle")
    public R upArticle(Articles articles, @RequestParam("file")MultipartFile file) throws IOException {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encode = encoder.encode(file.getBytes());
            articles.setArtcleImage(encode);
        UpdateWrapper<Articles> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("article_title",articles.getArticleTitle())
                .set("article_content",articles.getArticleContent())
                .set("artcle_image",articles.getArtcleImage())
                .eq("article_id",articles.getArticleId());
        int update = articlesMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("errmsg","修改失败,请注意是否有空值");
        }
    }

    @ApiOperation("查看所有博客文")
    @GetMapping("/allArticles")
    public R allArticles(){
        List<Articles> articlesList = articlesMapper.selectAllArticles();
        //将博文的图片解码
        articlesList.forEach((a)->{
            Base64.Decoder decoder = Base64.getDecoder();
            a.setArtcleImage(decoder.decode(a.getArtcleImage()));
        });
        return R.ok().data("articles",articlesList);
    }

    @ApiOperation("查看个人博客")
    @GetMapping("/selectByUserId/{userId}")
    public R selectByUserId(@PathVariable Integer userId){
        QueryWrapper<Articles> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Articles> articles = articlesMapper.selectList(wrapper);
        //将博文的图片解码
        articles.forEach((a)->{
            Base64.Decoder decoder = Base64.getDecoder();
            a.setArtcleImage(decoder.decode(a.getArtcleImage()));
        });
        return R.ok().data("articles",articles);
    }

    @ApiOperation("删除博客文")
    @GetMapping("/deleteById/{articleId}")
    public R deleteById(@PathVariable Integer articleId){
        int i = articlesMapper.deleteById(articleId);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("errmsg","删除失败");
        }
    }
}
