package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class SelectiveCourse extends ModelBase {

    private boolean available;
    private Course course;
    private Teacher teacher;
    private Degree degree;
    private Department department;
    private String trainingCycle;
    private String description;
    private int studyYear;
    private int studentsCount;

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentCount) {
        this.studentsCount = studentCount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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

    public static Comparator<SelectiveCourse> ByFacultyName = new Comparator<SelectiveCourse>() {
        @Override
        public int compare(SelectiveCourse one, SelectiveCourse two) {
            String facultyName1 = one.getDepartment().getFaculty().getAbbr();
            String facultyName2 = two.getDepartment().getFaculty().getAbbr();
            Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
            return uaCollator.compare(facultyName1, facultyName2);
        }
    };

    public static Comparator<SelectiveCourse> ByCourseName = new Comparator<SelectiveCourse>() {
        @Override
        public int compare(SelectiveCourse one, SelectiveCourse two) {
            String courseName1 = one.getCourse().getCourseName().getName();
            String courseName2 = two.getCourse().getCourseName().getName();
            Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
            return uaCollator.compare(courseName1, courseName2);
        }
    };

    public static Comparator<SelectiveCourse> ByStudentCount = new Comparator<SelectiveCourse>() {
        @Override
        public int compare(SelectiveCourse one, SelectiveCourse two) {
            return two.getStudentsCount() - one.getStudentsCount();
        }
    };
}
