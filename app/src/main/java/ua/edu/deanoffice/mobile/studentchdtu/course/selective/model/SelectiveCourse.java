package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

public class SelectiveCourse extends ModelBase {

    private boolean available;
    private Course course;
    private Teacher teacher;
    private Degree degree;
    private Department department;
    private String fieldsOfKnowledge;
    private String trainingCycle;
    private String description;
    private int studyYear;

    public boolean selected;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getFieldsOfKnowledge() {
        return fieldsOfKnowledge;
    }

    public void setFieldsOfKnowledge(String fieldsOfKnowledge) {
        this.fieldsOfKnowledge = fieldsOfKnowledge;
    }

    public String getTrainingCycle() {
        return trainingCycle;
    }

    public void setTrainingCycle(String trainingCycle) {
        this.trainingCycle = trainingCycle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
