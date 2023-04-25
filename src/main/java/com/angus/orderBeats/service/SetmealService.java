package com.angus.orderBeats.service;

import com.angus.orderBeats.dto.SetmealDto;
import com.angus.orderBeats.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    //used for save setmeal and dish relationship
    public void saveWithDish(SetmealDto SetmealDto);

    //used for remove setmeal and dish relationship
    public void removeWithDish(List<Long> ids);
    public void stopSale(Long ids);
    public void startSale(Long ids);
}
