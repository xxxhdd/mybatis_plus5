package com.xhd.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @Version
    private Integer version;//乐观锁version注解

    @TableLogic
    private Integer deleted;

    //字段添加 填充内容
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//驼峰命名
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}