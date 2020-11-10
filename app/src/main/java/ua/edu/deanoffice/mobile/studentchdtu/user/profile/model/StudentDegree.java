package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

public class StudentDegree extends ValidModel {

    protected int id;
    private StudentGroup studentGroup;
    private Specialization specialization;
    private String payment;
    private String tuitionForm;
    private String tuitionTerm;

    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTuitionForm() {
        return tuitionForm;
    }

    public void setTuitionForm(String tuitionForm) {
        this.tuitionForm = tuitionForm;
    }

    public String getTuitionTerm() {
        return tuitionTerm;
    }

    public void setTuitionTerm(String tuitionTerm) {
        this.tuitionTerm = tuitionTerm;
    }
}
