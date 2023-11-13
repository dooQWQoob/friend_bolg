package com.example.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Articles;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author taozi
 * @since 2023-11-08
 */
@Mapper
public interface ArticlesMapper extends BaseMapper<Articles> {
    List<Articles> selectAllArticles();
}
