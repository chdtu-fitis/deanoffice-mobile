package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.Semester;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter;

public class SelectiveCoursesConfirmedFragment extends BaseSelectiveCoursesFragment {
    private TextView textSelectedSemester;

    public SelectiveCoursesConfirmedFragment(SelectiveCourses selectiveCourses) {
        super(selectiveCourses,null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selective_courses_confirmed, container, false);
    }

    @Override
    public void onViewCreated(final @NotNull View view, final Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.listview1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        textSelectedSemester = view.findViewById(R.id.textSelectedSemester);

        View buttonToMainMenu = view.findViewById(R.id.buttonToMenu);
        buttonToMainMenu.setOnClickListener((v) -> {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.onBackPressed();
            }
        });

        initAdapters();

        if (recyclerView != null) {
            SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.disableCheckBoxes(true);
            }
        }
    }

    @Override
    protected void showFirstSemester() {
        super.showFirstSemester();
        textSelectedSemester.setText(getRString(R.string.label_selected_courses_s1));
    }

    @Override
    protected void showSecondSemester() {
        super.showSecondSemester();
        textSelectedSemester.setText(getRString(R.string.label_selected_courses_s2));
    }
}
