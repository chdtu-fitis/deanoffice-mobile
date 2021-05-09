package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter;

public class SelectiveCoursesConfirmedFragment extends BaseSelectiveCoursesFragment {
    public SelectiveCoursesConfirmedFragment(SelectiveCourses selectiveCourses) {
        super(null);
        this.showingSelectiveCourses = selectiveCourses;
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

        View buttonToMainMenu = view.findViewById(R.id.buttonToMenu);
        buttonToMainMenu.setOnClickListener((v) -> {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.onBackPressed();
            }
        });

        SelectiveCoursesAdapter adapter = new SelectiveCoursesAdapter(showingSelectiveCourses, getFragmentManager(), null);
        recyclerView.setAdapter(adapter);
        adapter.disableCheckBoxes(true);
    }
}
