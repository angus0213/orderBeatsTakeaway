package com.angus.orderBeats.controller;

import com.angus.orderBeats.common.R;
import com.angus.orderBeats.entity.Category;
import com.angus.orderBeats.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        try{
        log.info("category::: {}", category);
        categoryService.save(category);
        return R.success("new category added");
    }
    catch (Exception err) {
        return R.error("error");
    }
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        try{
        Page<Category> pageInfo=new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
        catch (Exception err) {
            return R.error("error");
        }
    }

    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("id: {}", id );
        try {
            categoryService.remove(id);
            return R.success("Category deleted successful");
    }
        catch (Exception err) {
            return R.error("error");
        }

    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        try{
        categoryService.updateById(category);
        return R.success("Edit successfully");
    }
        catch (Exception err) {
            return R.error("error");
        }
    }

    @GetMapping("/list")
    public R<List<Category>>list(Category category){
        try {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list=categoryService.list(queryWrapper);
        return R.success(list);
    }
        catch (Exception err) {
            return R.error("error");
        }
    }

}
