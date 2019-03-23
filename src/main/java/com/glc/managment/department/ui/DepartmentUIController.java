package com.glc.managment.department.ui;

import com.glc.managment.department.Department;
import com.glc.managment.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by mberhe on 1/1/19.
 */
@Controller
@RequestMapping("/departments")
public class DepartmentUIController {
    private DepartmentService departmentService;

    @Autowired
    public DepartmentUIController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/addDepartment")
    public String addDepartment(@Valid Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-department";
        }

        departmentService.addDepartment(department);
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "department/department_list";
    }

    @PostMapping("/updateDepartment")
    public String updateDepartment(@Valid Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-department";
        }

        departmentService.updateDepartment(department);
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "department/department_list";
    }

    @DeleteMapping("/deleteDepartment")
    public String deleteDepartment(@Valid String departmentUuid, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "add-department";
        }

        departmentService.deleteDepartmentByUuid(departmentUuid);
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "department/department_list";
    }
}
