package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import androidx.annotation.Keep;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.Faculty;

@Data
@Keep
public class Specialization extends ValidModel {
    private String name;
    private Speciality speciality;
    private Degree degree;
    private Faculty faculty;
}
