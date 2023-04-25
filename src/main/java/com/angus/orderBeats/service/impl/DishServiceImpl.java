package com.angus.orderBeats.service.impl;
import com.angus.orderBeats.common.customException;
import com.angus.orderBeats.entity.Dish;
import com.angus.orderBeats.entity.Setmeal;
import com.angus.orderBeats.entity.SetmealDish;
import com.angus.orderBeats.mapper.DishMapper;
import com.angus.orderBeats.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Override
    public void delete(List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        queryWrapper.eq(Dish::getStatus, 1);
        int count = this.count(queryWrapper);
        if (count > 0) {
            throw new customException("Plese close sale first");
        }
        this.removeByIds(ids);
    }

    @Override
    public void stopSale(Long ids) {
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Dish::getId, ids);

        updateWrapper.set(Dish::getStatus, 0);
        this.update(updateWrapper);
    }

    @Override
    public void startSale(Long ids) {
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Dish::getId, ids);

        updateWrapper.set(Dish::getStatus, 1);
        this.update(updateWrapper);

    }
}
