package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import lombok.Data;

@Data
@Keep
public class Department {
    private int id;
    private String name;
    private Faculty faculty;
    private boolean active;
    private String abbr;
}
