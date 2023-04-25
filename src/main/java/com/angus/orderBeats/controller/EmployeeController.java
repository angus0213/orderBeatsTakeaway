package com.angus.orderBeats.controller;

import com.angus.orderBeats.common.R;
import com.angus.orderBeats.entity.Employee;
import com.angus.orderBeats.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // employee login
    @PostMapping("/login")
    public  R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        try{
        String password=employee.getPassword();
        LambdaQueryWrapper<Employee> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp=employeeService.getOne((queryWrapper));

        //check username
        if (emp==null){
            return R.error("no such user");
        }

        //check password
        if (!emp.getPassword().equals(password)) {
            return  R.error("wrong password");
        }

        //check user status (stop to use the system or normal)
        if (emp.getStatus()==0) { //0 means can not use the system
            return  R.error("account can not be used");
        }

        //login successful, set id to session
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    catch (Exception err) {
            return R.error("error");
    }
    }

    //user logout
    @PostMapping("/logout")
    public R<String> logout (HttpServletRequest request){
        //clear session
        request.getSession().removeAttribute("employee");
        return R.success("logout successful");
    }

    //add staff
    @PostMapping
    public  R<String> saveStaff (HttpServletRequest request, @RequestBody Employee employee) {
        try {
        employee.setPassword("123456");
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

//       Long empId=(Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);

        return R.success("staff added successful");
    }
    catch (Exception err) {
            return R.error("error");
    }
    }

    @GetMapping("/page")
    public R<Page> page (int page, int pageSize, String name) {
        //pagination
        Page pageInfo= new Page(page, pageSize);
        //condition
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //find name in mysql
        queryWrapper.like(StringUtils.isNotBlank(name),Employee::getName, name);
        //sort
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        try {
//            Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
            employeeService.updateById(employee);

            return R.success("update successful");
        } catch (Exception err) {
            return R.error("error");
        }
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        try {
            Employee emp = employeeService.getById(id);

            return R.success(emp);
        }
        catch (Exception err) {
            return R.error("error");
        }
    }
        }


