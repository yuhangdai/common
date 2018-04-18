package com.aotain.mytest.usefultool.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/18
 */
@Getter
@Setter
public class Person {


    private String name;
    private Long id;

    private List<Integer> integerList;

    @Override
    public String toString() {
        return "id="+id+",name="+name+",integerList="+integerList.toString();
    }
}
