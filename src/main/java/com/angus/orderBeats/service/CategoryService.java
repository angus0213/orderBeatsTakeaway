package com.angus.orderBeats.service;

import com.angus.orderBeats.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CategoryService extends IService<Category> {
    public void remove (Long id);
}
