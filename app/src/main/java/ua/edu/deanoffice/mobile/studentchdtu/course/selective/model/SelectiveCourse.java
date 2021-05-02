package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import lombok.Data;

@Data
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
    private boolean selected;

    public static Comparator<SelectiveCourse> ByFacultyName = (one, two) -> {
        String facultyName1 = one.getDepartment().getFaculty().getAbbr();
        String facultyName2 = two.getDepartment().getFaculty().getAbbr();
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        return uaCollator.compare(facultyName1, facultyName2);
    };

    public static Comparator<SelectiveCourse> ByCourseName = (one, two) -> {
        String courseName1 = one.getCourse().getCourseName().getName();
        String courseName2 = two.getCourse().getCourseName().getName();
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        return uaCollator.compare(courseName1, courseName2);
    };

    public static Comparator<SelectiveCourse> ByStudentCount = (one, two) -> two.getStudentsCount() - one.getStudentsCount();
}
