package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import androidx.annotation.Keep;

import lombok.Data;

@Data
@Keep
public class Student extends ValidModel {
    protected String name;
    protected String surname;
    protected String patronimic;
    protected int year;
    protected StudentDegree[] degrees;
}
