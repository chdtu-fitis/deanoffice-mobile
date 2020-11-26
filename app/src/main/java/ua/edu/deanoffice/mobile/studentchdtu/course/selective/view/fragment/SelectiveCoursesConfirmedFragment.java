package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;

public class SelectiveCoursesConfirmedFragment extends Fragment {

    private SelectiveCourses selectiveCourses;

    public SelectiveCoursesConfirmedFragment(SelectiveCourses selectiveCourses) {
        this.selectiveCourses = selectiveCourses;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selective_courses_confirmed, container, false);
    }


    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.listview1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getFragmentManager(), null, true);
        recyclerView.setAdapter(adapter);
        adapter.disableCheckBoxes();
    }
}
