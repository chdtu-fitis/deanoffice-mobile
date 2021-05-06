package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import lombok.Data;

@Data
public class Department {
    private int id;
    private String name;
    private Faculty faculty;
    private boolean active;
    private String abbr;
}
