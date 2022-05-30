package ru.ifmo.lesson.spring.client;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Course {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @JsonSerialize(using = LocalDateSerializer.class) // к строке
    @JsonDeserialize(using = LocalDateDeserializer.class) // из строки
    private LocalDate start;
    @Getter
    @Setter
    private String price;

    @Getter
    @Setter
    private String duration;

    @Getter
    @Setter
    private Set<Student> students = new HashSet<>();
}