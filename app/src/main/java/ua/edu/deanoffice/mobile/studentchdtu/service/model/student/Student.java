package ua.edu.deanoffice.mobile.studentchdtu.service.model.student;

public class Student extends ValidModel{

    protected String name;
    protected String surname;
    protected String patronimic;
    protected int year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public StudentDegree[] getDegrees() {
        return degrees;
    }

    public void setDegrees(StudentDegree[] degrees) {
        this.degrees = degrees;
    }
}
