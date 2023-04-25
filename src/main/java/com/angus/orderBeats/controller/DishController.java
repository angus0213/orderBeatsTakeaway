package com.angus.orderBeats.controller;

import com.angus.orderBeats.common.R;
import com.angus.orderBeats.entity.Category;
import com.angus.orderBeats.entity.Dish;
import com.angus.orderBeats.service.CategoryService;
import com.angus.orderBeats.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public R<String> save(@RequestBody Dish dish) {
        try {
            dishService.save(dish);
            return R.success("dish added");
        }
        catch (Exception err) {
            return R.error("error");
        }
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        try {
            Page<Dish> pageInfo = new Page<>(page, pageSize);
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(name != null, Dish::getName, name);
            queryWrapper.orderByDesc(Dish::getUpdateTime);
            dishService.page(pageInfo, queryWrapper);
            return R.success(pageInfo);
        }
        catch (Exception err) {
            return R.error("error");
        }
    }

    @GetMapping("/{id}")
    public R<Dish> get(@PathVariable Long id) {
        try {
        Dish dish = dishService.getById(id);
        return R.success(dish);
    }
        catch (Exception err) {
            return R.error("error");
        }
    }


    @PutMapping
    public R<String> update(@RequestBody Dish dish) {
        try{
        dishService.updateById(dish);
        return R.success("Edit successfully");
    }
        catch (Exception err) {
            return R.error("error");
        }
    }

    //get dish data
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        try{
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list=dishService.list(queryWrapper);

        return R.success(list);
    }
        catch (Exception err) {
            return R.error("error");
        }
    }

    @DeleteMapping
    public R<String> removebyId (@RequestParam List<Long> ids) {
        dishService.delete(ids);
        return R.success("dish deleted");
    }

    @PostMapping("/status/0")
    public R<String> close (Long ids) {
        dishService.stopSale(ids);
        return R.success("dish closed");
    }

    @PostMapping("/status/1")
    public R<String> start (Long ids) {
        dishService.startSale(ids);
        return R.success("dish opened");
    }

}


