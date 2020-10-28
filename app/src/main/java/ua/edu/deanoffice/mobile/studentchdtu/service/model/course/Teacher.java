package ua.edu.deanoffice.mobile.studentchdtu.service.model.course;

public class Teacher extends ModelBase {

    private String name;
    private String surname;
    private String patronimic;
    private String sex;
    private boolean active;
    //academicTitle = null;
    private DepartmentTeacher departmentTeacher;
    private Position position;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public DepartmentTeacher getDepartmentTeacher() {
        return departmentTeacher;
    }

    public void setDepartmentTeacher(DepartmentTeacher departmentTeacher) {
        this.departmentTeacher = departmentTeacher;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
