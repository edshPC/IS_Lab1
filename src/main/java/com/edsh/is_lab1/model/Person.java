package com.edsh.is_lab1.model;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле не может быть null
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    private float weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 10, Поле не может быть null
    private Country nationality; //Поле может быть null
}
