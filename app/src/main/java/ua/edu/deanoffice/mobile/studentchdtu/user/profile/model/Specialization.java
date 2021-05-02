package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import lombok.Data;

@Data
public class Specialization extends ValidModel {
    private String name;
    private Speciality speciality;
    private Degree degree;
}
