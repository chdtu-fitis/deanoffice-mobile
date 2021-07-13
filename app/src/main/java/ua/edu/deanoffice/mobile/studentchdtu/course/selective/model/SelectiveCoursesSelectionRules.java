package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.TypeCycle;

@Data
@Keep
public class SelectiveCoursesSelectionRules {
    private TypeCycle cycleType;
    private int[] selectiveCoursesNumber;
}
