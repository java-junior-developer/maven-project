package ru.ifmo.lesson;

import java.io.IOException;

/**
 * Класс ru.ifmo.lesson.Pojo для ...
 * @author Ifmo Java
 * */
public class Pojo {
    /** Поле id - уникальный идентификатор */
    private final int id;
    /** Поле name ... */
    private String name;

    /**
     * Конструктор принимает на вход значение
     * поля {@link Pojo#id}, которое не мб
     * изменено после инициализации
     * */
    public Pojo(int id) {
        this.id = id;
    }

    /**
     * Описание метода
     * @return значение уникального идентификатора
     * */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // @throws тип выбрасываемого исключения, описание в свободной форме
    /**
     * @throws IOException метод выбрасывает исключение
     * если id меньше 100
     * */
    public void someVoid() throws IOException{
        if (id > 100) {
            throw new IOException();
        }
        System.out.println(id);
    }

    // @param имя перемнной, описание в свободной форме
    // количество аннотаций @param определяется количеством принимаеных
    // аргументов
    /**
     * Описание метода
     * @param name значение свойссва name
     * */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
