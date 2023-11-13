package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.ArtitleType;
import com.example.mapper.ArtitleTypeMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/articleClass")
public class ArtitleTypeController {
    @Autowired
    private ArtitleTypeMapper artitleTypeMapper;

    @ApiOperation("用户将博客文分类")
    @PostMapping(value = "/articleById",produces = {"application/json;charset=UTF-8;"})
    public R articleById(@RequestBody ArtitleType artitleType){
        int insert = artitleTypeMapper.insert(artitleType);
        if (insert>0){
            return R.ok().data("msg","添加分类成功");
        }else {
            return R.error().data("errmsg","添加失败");
        }
    }

    @ApiOperation("修改博客文分类")
    @PostMapping(value = "/UpArticleById",produces = {"application/json;charset=UTF-8;"})
    public R UpArticleById(@RequestBody ArtitleType artitleType){
        UpdateWrapper<ArtitleType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("sort_id",artitleType.getSortId()).eq("article_id",artitleType.getArticleId());
        int update = artitleTypeMapper.update(artitleType, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改分类成功");
        }else {
            return R.error().data("errmsg","修改失败");
        }
    }
}
