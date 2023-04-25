package com.angus.orderBeats.service;

import com.angus.orderBeats.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void delete(List<Long> ids);
    public void stopSale(Long ids);
    public void startSale(Long ids);
}
