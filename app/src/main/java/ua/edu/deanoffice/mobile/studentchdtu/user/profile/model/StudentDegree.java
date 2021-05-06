package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import lombok.Data;

@Data
public class StudentDegree extends ValidModel {
    protected int id;
    private StudentGroup studentGroup;
    private Specialization specialization;
    private String payment;
    private String tuitionForm;
    private String tuitionTerm;
    private boolean active;
}
