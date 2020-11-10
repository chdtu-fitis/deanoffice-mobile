package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SemesterFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCourseFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;

public class ChdtuAdapter extends RecyclerView.Adapter<ChdtuAdapter.ViewHolder> implements View.OnClickListener {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
        }
    }

    private TextView selectiveCoursesCounter;

    private SelectiveCourses selectiveCourses;
    private FragmentManager fragmentManager;

    private List<SelectiveCourseFragment> selectiveCourseFragmentsFirstSemester;
    private List<SelectiveCourseFragment> selectiveCourseFragmentsSecondSemester;

    private List<SelectiveCourse> selectedCourseFirstSemester;
    private List<SelectiveCourse> selectiveCourseSecondSemester;

    private boolean interactive;

    public ChdtuAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager, TextView selectiveCoursesCounter, boolean disableCheckBoxes) {
        this.selectiveCourses = selectiveCourses;
        this.fragmentManager = supportFragmentManager;

        selectiveCourseFragmentsFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectiveCourseFragmentsSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());

        selectedCourseFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectiveCourseSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());
        this.selectiveCoursesCounter = selectiveCoursesCounter;
        interactive = disableCheckBoxes;
    }

    @NonNull
    @Override
    public ChdtuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_chdtu_list_selectivecourse, null);

        // create ViewHolder
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        itemLayoutView.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final int pos = position;

        if (pos == 0) {
            SemesterFragment fragment1 = new SemesterFragment(1, R.layout.fragment_semester);
            fragmentManager.beginTransaction().add(R.id.containterCourses, fragment1).commit();

            for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesFirstSemester()) {
                SelectiveCourseFragment fragment = new SelectiveCourseFragment(course, R.layout.fragment_selectivecourse, this, interactive);
                selectiveCourseFragmentsFirstSemester.add(fragment);
                fragmentManager.beginTransaction().add(R.id.containterCourses, fragment).commit();
            }

            SemesterFragment fragment2 = new SemesterFragment(2, R.layout.fragment_semester);
            fragmentManager.beginTransaction().add(R.id.containterCourses, fragment2).commit();

            for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesSecondSemester()) {
                SelectiveCourseFragment fragment = new SelectiveCourseFragment(course, R.layout.fragment_selectivecourse, this, interactive);
                selectiveCourseFragmentsSecondSemester.add(fragment);
                fragmentManager.beginTransaction().add(R.id.containterCourses, fragment).commit();
            }
        }
    }

    @Override
    public int getItemCount() {
        return selectiveCourses.getSelectiveCoursesFirstSemester().size() + selectiveCourses.getSelectiveCoursesSecondSemester().size();
    }

    public void disableCheckBoxes() {
        for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
            frag.setCheckBoxInteractive(false);
        }
        for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
            frag.setCheckBoxInteractive(false);
        }
        interactive = false;
    }

    @Override
    public void onClick(View v) {
        if (interactive) {
            for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
                frag.setCheckBoxInteractive(true);
            }
            for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
                frag.setCheckBoxInteractive(true);
            }


            int count = 0;
            selectedCourseFirstSemester.clear();
            for (int i = 0; i < selectiveCourses.getSelectiveCoursesFirstSemester().size(); i++) {
                if (selectiveCourses.getSelectiveCoursesFirstSemester().get(i).selected) {
                    selectedCourseFirstSemester.add(selectiveCourses.getSelectiveCoursesFirstSemester().get(i));
                    count++;
                }
                if (count >= 3) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }

            count = 0;
            selectiveCourseSecondSemester.clear();
            for (int i = 0; i < selectiveCourses.getSelectiveCoursesSecondSemester().size(); i++) {
                if (selectiveCourses.getSelectiveCoursesSecondSemester().get(i).selected) {
                    selectiveCourseSecondSemester.add(selectiveCourses.getSelectiveCoursesSecondSemester().get(i));
                    count++;
                }
                if (count >= 2) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }

            selectiveCoursesCounter.setText("3 в 1 семестрі (" + getSelectedCourseFirstSemester().size() + "/3)" +
                    ", 2 в 2 семестрі(" + getSelectiveCourseSecondSemester().size() + "/2)");
        }
    }

    public List<SelectiveCourse> getSelectedCourseFirstSemester() {
        return selectedCourseFirstSemester;
    }

    public List<SelectiveCourse> getSelectiveCourseSecondSemester() {
        return selectiveCourseSecondSemester;
    }

    public void clearSelected() {
        for (SelectiveCourseFragment courseFragment : selectiveCourseFragmentsFirstSemester) {
            courseFragment.setChecked(false);
        }
        for (SelectiveCourseFragment courseFragment : selectiveCourseFragmentsSecondSemester) {
            courseFragment.setChecked(false);
        }
        onClick(null);
    }
}
