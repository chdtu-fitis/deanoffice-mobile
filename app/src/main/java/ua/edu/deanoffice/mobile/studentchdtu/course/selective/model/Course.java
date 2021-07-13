package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import java.math.BigDecimal;

import lombok.Data;

@Data
@Keep
public class Course extends ModelBase {
    private CourseName courseName;
    private int semester;
    private KnowledgeControl knowledgeControl;
    private int hours;
    private BigDecimal credits;
    private int hoursPerCredit;
}
