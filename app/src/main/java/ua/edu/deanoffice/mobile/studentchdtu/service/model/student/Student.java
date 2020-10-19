package ua.edu.deanoffice.mobile.studentchdtu.service.model.student;

public class Student {

    private String name;
    private String surname;
    private String patronimic;
    private StudentDegree[] degrees;

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

    public StudentDegree[] getDegrees() {
        return degrees;
    }

    public void setDegrees(StudentDegree[] degrees) {
        this.degrees = degrees;
    }
}
