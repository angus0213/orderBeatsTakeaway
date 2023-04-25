package com.angus.orderBeats.controller;

import com.angus.orderBeats.common.R;
import com.angus.orderBeats.dto.SetmealDto;
import com.angus.orderBeats.entity.Setmeal;
import com.angus.orderBeats.service.SetmealDishService;
import com.angus.orderBeats.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("new setmeal added!");
    }

    @GetMapping("/page")
    public R<Page> page (int page, int pageSize, String name) {
        Page<Setmeal> pageInfo =new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete (@RequestParam List<Long> ids) {
        setmealService.removeWithDish(ids);
        return R.success("setmeal deleted");
    }

    @PostMapping("/status/0")
    public R<String> close (Long ids) {
        setmealService.stopSale(ids);
        return R.success("setmeal closed");
    }

    @PostMapping("/status/1")
    public R<String> start (Long ids) {
        setmealService.startSale(ids);
        return R.success("setmeal opened");
    }

}
