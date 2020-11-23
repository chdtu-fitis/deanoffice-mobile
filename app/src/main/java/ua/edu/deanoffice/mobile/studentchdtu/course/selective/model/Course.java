package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.math.BigDecimal;

public class Course extends ModelBase {

    private CourseName courseName;
    private int semester;
    private KnowledgeControl knowledgeControl;
    private int hours;
    private BigDecimal credits;
    private int hoursPerCredit;

    public CourseName getCourseName() {
        return courseName;
    }

    public void setCourseName(CourseName courseName) {
        this.courseName = courseName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public KnowledgeControl getKnowledgeControl() {
        return knowledgeControl;
    }

    public void setKnowledgeControl(KnowledgeControl knowledgeControl) {
        this.knowledgeControl = knowledgeControl;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public int getHoursPerCredit() {
        return hoursPerCredit;
    }

    public void setHoursPerCredit(int hoursPerCredit) {
        this.hoursPerCredit = hoursPerCredit;
    }
}
