package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import java.util.List;

import lombok.Data;

@Data
@Keep
public class ConfirmedSelectiveCourses {
    private List<Integer> selectiveCourses;
    private ExistingId studentDegree;
}
