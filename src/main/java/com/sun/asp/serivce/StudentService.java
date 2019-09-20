package com.sun.asp.serivce;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    public String getStr(){
        System.out.println("service");
        return "succwwwwwess";
    }

}
