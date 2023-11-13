package com.example.service.impl;

import com.example.entity.Comments;
import com.example.mapper.CommentsMapper;
import com.example.service.ICommentsService;
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
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

}
