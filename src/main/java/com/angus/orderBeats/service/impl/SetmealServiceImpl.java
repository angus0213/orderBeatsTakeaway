package com.angus.orderBeats.service.impl;

import com.angus.orderBeats.common.customException;
import com.angus.orderBeats.dto.SetmealDto;
import com.angus.orderBeats.entity.Setmeal;
import com.angus.orderBeats.entity.SetmealDish;
import com.angus.orderBeats.mapper.SetmealMapper;
import com.angus.orderBeats.service.SetmealDishService;
import com.angus.orderBeats.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    SetmealDishService setmealDishService;

    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //save basic dish info
        this.save(setmealDto);
        //gather full info
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId((setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());

        //operate setmeal_dish table
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);
        if (count > 0) {
            throw new customException("Plese close sale first");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);

    }

    @Override
    public void stopSale(Long ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Setmeal::getId, ids);

        updateWrapper.set(Setmeal::getStatus, 0);
        this.update(updateWrapper);
    }

    @Override
    public void startSale(Long ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Setmeal::getId, ids);

        updateWrapper.set(Setmeal::getStatus, 1);

        this.update(updateWrapper);


    }
}
