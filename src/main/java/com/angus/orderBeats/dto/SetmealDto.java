package com.angus.orderBeats.dto;



import com.angus.orderBeats.entity.Setmeal;
import com.angus.orderBeats.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
