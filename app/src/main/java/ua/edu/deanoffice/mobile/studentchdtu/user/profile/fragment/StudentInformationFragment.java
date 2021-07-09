package ua.edu.deanoffice.mobile.studentchdtu.user.profile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.StudentDegree;

public class StudentInformationFragment extends Fragment {

    private final Map<String, TextView> studentInformationViews = new HashMap<>();
    private final Student student;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_studentinfo, container, false);
    }

    public StudentInformationFragment(Student student) {
        this.student = student;
    }

    @Override
    public void onViewCreated(final @NotNull View view, final Bundle savedInstanceState) {
        if (App.getInstance().getJwt().getUserRole() == JWToken.UserRole.ROLE_STUD_IN_ACADEMIC_VACATION) {
            view.findViewById(R.id.inAcademicVacationTag).setVisibility(View.VISIBLE);
            view.findViewById(R.id.tableRowCourse).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.tableRowGroup).setVisibility(View.INVISIBLE);
        }

        studentInformationViews.put("Name", view.findViewById(R.id.studentNameTextView));
        studentInformationViews.put("Facult", view.findViewById(R.id.facultyNameTextView));
        studentInformationViews.put("Degree", view.findViewById(R.id.degreeNameTextView));
        studentInformationViews.put("Speciality", view.findViewById(R.id.specialityNameTextView));
        studentInformationViews.put("Specialization", view.findViewById(R.id.specializationNameTextView));
        studentInformationViews.put("GroupAndYear", view.findViewById(R.id.groupNameTextView));
        studentInformationViews.put("Termin", view.findViewById(R.id.tuitionFormNameTextView));
        studentInformationViews.put("Course", view.findViewById(R.id.courseNameTextView));

        StudentDegree studentDegree = App.getInstance().getSelectedStudentDegree();

        String fullName = student.getSurname() + " " + student.getName() + " " + student.getPatronimic();
        studentInformationViews.get("Name").setText(fullName);
        studentInformationViews.get("Facult").setText(studentDegree.getSpecialization().getFaculty().getName());
        studentInformationViews.get("Degree").setText(studentDegree.getSpecialization().getDegree().getName());
        String speciality = studentDegree.getSpecialization().getSpeciality().getCode();
        speciality += " " + studentDegree.getSpecialization().getSpeciality().getName();
        studentInformationViews.get("Speciality").setText(speciality);
        studentInformationViews.get("Specialization").setText(studentDegree.getSpecialization().getName());
        studentInformationViews.get("GroupAndYear").setText(studentDegree.getStudentGroup().getName());
        String tuitionForm = studentDegree.getTuitionForm().equals("FULL_TIME") ? "Денна" : "Заочна";
        tuitionForm += studentDegree.getTuitionTerm().equals("REGULAR") ? "" : " Скорочена";
        studentInformationViews.get("Termin").setText(tuitionForm);

        int realCourse = studentDegree.getRealYear();
        int formalCourse = studentDegree.getYear();
        String courseString = String.valueOf(formalCourse);
        if (realCourse != formalCourse) {
            courseString += "(" + realCourse + ")";
        }
        studentInformationViews.get("Course").setText(courseString);
    }
}