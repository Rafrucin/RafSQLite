package com.example.rafsqlite;

public class CustomerModel {
    private int id;
    private String name;
    private int age;
    private boolean isAactive;

    public CustomerModel(int id, String name, int age, boolean isAactive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isAactive = isAactive;
    }

    public CustomerModel() {
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isAactive=" + isAactive +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAactive() {
        return isAactive;
    }

    public void setAactive(boolean aactive) {
        isAactive = aactive;
    }
}
