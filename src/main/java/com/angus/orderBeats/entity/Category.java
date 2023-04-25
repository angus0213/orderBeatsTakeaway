package com.angus.orderBeats.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //Type 1 dish category 2 setmeal category
    private Integer type;


    //category name
    private String name;


    //sort
    private Integer sort;


    //creat time
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //update time
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //people who creat
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    //people who update
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;



}
