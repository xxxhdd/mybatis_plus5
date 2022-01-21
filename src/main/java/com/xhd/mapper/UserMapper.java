package com.xhd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhd.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xhd
 * @create 2022-01-14 14:27
 */
//在对应的接口上面继承一个基本的接口 BaseMapper
@Mapper//代表持久层
public interface UserMapper extends BaseMapper<User> {
    //所有CRUD操作都编写完成了，不用像以前一样配置一大堆文件
}