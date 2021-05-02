package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import lombok.Data;

@Data
public class Student extends ValidModel {
    protected String name;
    protected String surname;
    protected String patronimic;
    protected int year;
    protected StudentDegree[] degrees;
}
