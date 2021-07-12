package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import lombok.Data;

@Data
@Keep
public class CourseName extends ModelBase {
    private String name;
    private String nameEng;
}
