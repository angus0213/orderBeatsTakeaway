package com.angus.orderBeats.service.impl;

import com.angus.orderBeats.entity.Employee;
import com.angus.orderBeats.mapper.EmployeeMapper;
import com.angus.orderBeats.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
