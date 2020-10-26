package ua.edu.deanoffice.mobile.studentchdtu.service.model.student;

import java.lang.reflect.Field;

public class Student extends ValidModel{

    protected String name;
    protected String surname;
    protected String patronimic;
    protected int course;
    protected StudentDegree[] degrees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronimic() {
        return patronimic;
    }

    public void setPatronimic(String patronimic) {
        this.patronimic = patronimic;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public StudentDegree[] getDegrees() {
        return degrees;
    }

    public void setDegrees(StudentDegree[] degrees) {
        this.degrees = degrees;
    }
}
