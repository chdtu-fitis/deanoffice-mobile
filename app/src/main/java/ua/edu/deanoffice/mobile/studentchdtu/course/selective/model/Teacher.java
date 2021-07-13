package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import lombok.Data;

@Data
@Keep
public class Teacher extends ModelBase {
    private String name;
    private String surname;
    private String patronimic;
    private String sex;
    private boolean active;
    private DepartmentTeacher departmentTeacher;
    private Position position;
}
