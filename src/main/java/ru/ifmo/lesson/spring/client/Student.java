package ru.ifmo.lesson.spring.client;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    @Getter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @Getter
    @Setter
    private Course course;
}