package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.TypeCycle;

@Data
public class SelectiveCoursesSelectionRules {
    private TypeCycle cycleType;
    private Integer[] selectiveCoursesNumber;
}
