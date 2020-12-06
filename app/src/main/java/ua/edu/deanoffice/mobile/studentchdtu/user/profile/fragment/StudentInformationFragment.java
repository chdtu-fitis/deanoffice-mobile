package ua.edu.deanoffice.mobile.studentchdtu.user.profile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;

public class StudentInformationFragment extends Fragment {

    private Map<String, TextView> studentInformationViews = new HashMap<>();
    private Student student;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_studentinfo, container, false);
    }

    public StudentInformationFragment(Student student) {
        this.student = student;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Інформація про студента");

        studentInformationViews.put("Name", (TextView) view.findViewById(R.id.studentNameTextView));
        studentInformationViews.put("Facult", (TextView) view.findViewById(R.id.facultyNameTextView));
        studentInformationViews.put("Degree", (TextView) view.findViewById(R.id.degreeNameTextView));
        studentInformationViews.put("Speciality", (TextView) view.findViewById(R.id.specialityNameTextView));
        studentInformationViews.put("Specialization", (TextView) view.findViewById(R.id.specializationNameTextView));
        studentInformationViews.put("GroupAndYear", (TextView) view.findViewById(R.id.groupNameTextView));
        studentInformationViews.put("Termin", (TextView) view.findViewById(R.id.tuitionFormNameTextView));

        studentInformationViews.get("Name").setText(student.getSurname() + " " + student.getName() + " " + student.getPatronimic());
        studentInformationViews.get("Facult").setText("Факультет інформаційних технологій і систем");
        studentInformationViews.get("Degree").setText(student.getDegrees()[0].getSpecialization().getDegree().getName());
        studentInformationViews.get("Speciality").setText(student.getDegrees()[0].getSpecialization().getSpeciality().getCode() + " " + student.getDegrees()[0].getSpecialization().getSpeciality().getName());
        studentInformationViews.get("Specialization").setText(student.getDegrees()[0].getSpecialization().getName());
        studentInformationViews.get("GroupAndYear").setText(student.getDegrees()[0].getStudentGroup().getName());
        studentInformationViews.get("Termin").setText((student.getDegrees()[0].getTuitionForm().equals("FULL_TIME") ? "Денна" : "Заочна")
                + (student.getDegrees()[0].getTuitionTerm().equals("REGULAR") ? "" : "Скорочена"));

    }
}