package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Types;
import com.example.mapper.TypesMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/types")
public class TypesController {
    @Autowired
    private TypesMapper typesMapper;

    @ApiOperation("添加类别")
    @PostMapping(value = "/addtypes",produces = {"application/json;charset=UTF-8;"})
    public R addtypes(@RequestBody Types types){
        QueryWrapper<Types> wrapper = new QueryWrapper<>();
        wrapper.eq("sort_name",types.getSortName());
        Types selectOne = typesMapper.selectOne(wrapper);
        if (selectOne==null){
            typesMapper.insert(types);
            return R.ok().data("msg","添加成功");
        }else{
            return R.error().data("errmsg","您已添加该类别,请不要重复添加");
        }
    }

    @ApiOperation("删除类别")
    @GetMapping("/deleteById/{typeid}")
    public R deleteById(@PathVariable Integer typeid){
        int i = typesMapper.deleteById(typeid);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }

    @ApiOperation("查询所有类别")
    @GetMapping("/selectAll")
    public R selectAll(){
        List<Types> types = typesMapper.selectList(null);
        return R.ok().data("types",types);
    }

    @ApiOperation("修改类别")
    @PostMapping(value = "/upTypes",produces = {"application/json;charset=UTF-8;"})
    public R upTypes(@RequestBody Types types){
        UpdateWrapper<Types> wrapper = new UpdateWrapper<>();
        wrapper.set("sort_name",types.getSortName()).set("sort_description",types.getSortDescription()).eq("sort_id",types.getSortId());
        int update = typesMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("errmsg","修改失败");
        }
    }
}
