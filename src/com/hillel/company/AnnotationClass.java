package com.hillel.company;


public class AnnotationClass {

    @SaveField
    private String name;

    @SaveField
    private int age;

    @TestAnnotation(a = 2, b = 5)
    public void test(int a, int b){
        System.out.println("First check!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



}
