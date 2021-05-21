package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.Faculty;

@Data
public class Specialization extends ValidModel {
    private String name;
    private Speciality speciality;
    private Degree degree;
    private Faculty faculty;
}
