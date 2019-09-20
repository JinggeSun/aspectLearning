package com.sun.asp.controller;

import com.sun.asp.serivce.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/index")
    public String getData(){
        return studentService.getStr();
    }
}
