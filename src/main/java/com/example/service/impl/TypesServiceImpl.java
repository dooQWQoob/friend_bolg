package com.example.service.impl;

import com.example.entity.Types;
import com.example.mapper.TypesMapper;
import com.example.service.ITypesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author taozi
 * @since 2023-11-08
 */
@Service
public class TypesServiceImpl extends ServiceImpl<TypesMapper, Types> implements ITypesService {

}
