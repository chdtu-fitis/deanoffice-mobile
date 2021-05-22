package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.TypeCycle;
import ua.edu.deanoffice.mobile.studentchdtu.shared.util.UAComparator;

@Data
public class SelectiveCourse extends ModelBase {
    private boolean available;
    private Course course;
    private Teacher teacher;
    private Degree degree;
    private Department department;
    private TypeCycle trainingCycle;
    private String description;
    private int studyYear;
    private int studentsCount;
    private boolean selected;
    private boolean selectedFromFirstRound;

    public static Comparator<SelectiveCourse> ByFacultyName = (one, two) -> {
        String facultyName1 = one.getDepartment().getFaculty().getAbbr();
        String facultyName2 = two.getDepartment().getFaculty().getAbbr();
        Comparator uaComparator = new UAComparator();
        return uaComparator.compare(facultyName1, facultyName2);
    };

    public static Comparator<SelectiveCourse> ByTrainingCycle = (one, two) -> {
        Collator engCollator = Collator.getInstance(Locale.ENGLISH);
        return engCollator.compare(one.getTrainingCycle(), two.getTrainingCycle());
    };

    public static Comparator<SelectiveCourse> ByCourseName = (one, two) -> {
        String courseName1 = one.getCourse().getCourseName().getName();
        String courseName2 = two.getCourse().getCourseName().getName();
        Comparator uaComparator = new UAComparator();
        return uaComparator.compare(courseName1, courseName2);
    };

    public static Comparator<SelectiveCourse> ByStudentCount = (one, two) -> two.getStudentsCount() - one.getStudentsCount();
}
