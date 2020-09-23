package ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData;


///Temp
public class User {
    private String name;
    private String surname;
    private String patronymic;
    private String group;
    private int course;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getGroup() {
        return group;
    }

    public int getCourse() {
        return course;
    }

    public User(String name, String surname, String patronimic, String group, int course) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronimic;
        this.group = group;
        this.course = course;
    }
}
