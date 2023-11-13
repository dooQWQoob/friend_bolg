package com.example.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author taozi
 * @since 2023-11-08
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    User selectByUserPhone(@Param("userPhone") String userPhone);
    int updateUserPhoneAndUserPassword(@Param("userPhone") String userPhone, @Param("userPassword") String userPassword);
}
