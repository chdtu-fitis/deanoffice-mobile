package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesFragment;

public class MainMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mainmenu, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Button btn_selectiveCourses = view.findViewById(R.id.btn_selecrivecourses);
        btn_selectiveCourses.setOnClickListener((viewbtn)->{
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectiveCoursesFragment()).commit();
        });
    }
}
