package com.angus.orderBeats.service.impl;

import com.angus.orderBeats.common.customException;
import com.angus.orderBeats.entity.Category;
import com.angus.orderBeats.entity.Dish;
import com.angus.orderBeats.entity.Setmeal;
import com.angus.orderBeats.mapper.CategoryMapper;
import com.angus.orderBeats.service.CategoryService;
import com.angus.orderBeats.service.DishService;
import com.angus.orderBeats.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    //judge before delete whether category contains dish or setmeal, which cannot delete category in that case

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count= dishService.count(dishLambdaQueryWrapper);
        if (count>0) {
            throw new customException("has sub-dish, can not delete");

        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2=setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0) {
            throw new customException("has sub-setmeal, can not delete");

        }

        super.removeById(id);

    }
}
