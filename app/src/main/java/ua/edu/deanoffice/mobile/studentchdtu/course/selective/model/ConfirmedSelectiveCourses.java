package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.List;

import lombok.Data;

@Data
public class ConfirmedSelectiveCourses {
    private List<Integer> selectiveCourses;
    private ExistingId studentDegree;
}
