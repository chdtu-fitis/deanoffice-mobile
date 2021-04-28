package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCourseFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SemesterFragment;

public class SelectiveCoursesAdapter extends RecyclerView.Adapter<SelectiveCoursesAdapter.ViewHolder> implements View.OnClickListener {

    // 1 - bak
    // 3 - magistr

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
        }
    }

    private SelectListener selectListener = null;
    private SelectedStates selectedStates;

    private TextView selectiveCoursesCounter;

    private SelectiveCourses selectiveCourses;
    private FragmentManager fragmentManager;

    private List<SelectiveCourseFragment> selectiveCourseFragmentsFirstSemester;
    private List<SelectiveCourseFragment> selectiveCourseFragmentsSecondSemester;

    private List<SelectiveCourse> selectedCourseFirstSemester;
    private List<SelectiveCourse> selectedCourseSecondSemester;

    private boolean interactive;
    private boolean showTrainingCycle;
    private StudentDegree studentDegree;

    public int getMaxCoursesFirstSemester() {
        return studentDegree.getMaxCoursesFirstSemester();
    }

    public int getMaxCoursesSecondSemester() {
        return studentDegree.getMaxCoursesSecondSemester();
    }

    public SelectiveCoursesAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager, TextView selectiveCoursesCounter, boolean disableCheckBoxes) {
        this(selectiveCourses, supportFragmentManager, selectiveCoursesCounter, disableCheckBoxes, false);
    }

    public SelectiveCoursesAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager, TextView selectiveCoursesCounter, boolean disableCheckBoxes, boolean forMagister) {
        initAdapter(selectiveCourses, supportFragmentManager, selectiveCoursesCounter, disableCheckBoxes);
        initMaxCourses(forMagister);
    }

    public boolean hasGeneralAndProfessional() {
        boolean hasGeneral = false;
        boolean hasProfessional = false;
        for (SelectiveCourse selectiveCourse : selectedCourseFirstSemester) {
            if (selectiveCourse.getTrainingCycle().equals("GENERAL")) {
                hasGeneral = true;
            } else {
                hasProfessional = true;
            }
        }
        boolean hasFirstSemester = hasGeneral && hasProfessional;

        hasGeneral = false;
        hasProfessional = false;
        for (SelectiveCourse selectiveCourse : selectedCourseSecondSemester) {
            if (selectiveCourse.getTrainingCycle().equals("GENERAL")) {
                hasGeneral = true;
            } else {
                hasProfessional = true;
            }
        }
        return hasFirstSemester && (hasGeneral && hasProfessional);
    }

    public void initAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager, TextView selectiveCoursesCounter, boolean disableCheckBoxes) {
        this.selectiveCourses = selectiveCourses;
        this.fragmentManager = supportFragmentManager;
        this.selectedStates = new SelectedStates();

        selectiveCourseFragmentsFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectiveCourseFragmentsSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());

        selectedCourseFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectedCourseSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());

        for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesFirstSemester()) {
            if (course.selected && course.isAvailable()) {
                selectedCourseFirstSemester.add(course);
            }
        }

        for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesSecondSemester()) {
            if (course.selected && course.isAvailable()) {
                selectedCourseSecondSemester.add(course);
            }
        }

        this.selectiveCoursesCounter = selectiveCoursesCounter;
        interactive = disableCheckBoxes;
        showTrainingCycle = hasGeneralAndProfessional();
    }

    public void initMaxCourses(boolean master) {
        if (master) {
            studentDegree = StudentDegree.Master;
        } else {
            studentDegree = StudentDegree.Bachelor;
        }

        if (selectiveCoursesCounter != null) {
            selectiveCoursesCounter.setText(studentDegree.getMaxCoursesFirstSemester() +
                    " в 1 семестрі (" + selectedCourseFirstSemester.size() + "/" + studentDegree.getMaxCoursesFirstSemester() + ")" + ", " + studentDegree.getMaxCoursesSecondSemester() +
                    " в 2 семестрі(" + selectedCourseSecondSemester.size() + "/" + studentDegree.getMaxCoursesSecondSemester() + ")");
        }
    }

    @NonNull
    @Override
    public SelectiveCoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        if (position == 0) {
            SemesterFragment fragment1 = new SemesterFragment(1, R.layout.fragment_semester);
            fragmentManager.beginTransaction().add(R.id.containterCourses, fragment1).commit();

            for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesFirstSemester()) {
                SelectiveCourseFragment fragment = new SelectiveCourseFragment(course, R.layout.fragment_selectivecourse, this, interactive, showTrainingCycle);
                selectiveCourseFragmentsFirstSemester.add(fragment);
                fragmentManager.beginTransaction().add(R.id.containterCourses, fragment).commit();
            }

            SemesterFragment fragment2 = new SemesterFragment(2, R.layout.fragment_semester);
            fragmentManager.beginTransaction().add(R.id.containterCourses, fragment2).commit();

            for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesSecondSemester()) {
                SelectiveCourseFragment fragment = new SelectiveCourseFragment(course, R.layout.fragment_selectivecourse, this, interactive, showTrainingCycle);
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

            selectedCourseFirstSemester = checkSelectiveCourses(selectiveCourses.getSelectiveCoursesFirstSemester(), selectiveCourseFragmentsFirstSemester,
                    studentDegree.getMaxProfCoursesFirstSemester(), studentDegree.getMaxGenCoursesFirstSemester(),
                    studentDegree.getMaxCoursesFirstSemester());

            selectedCourseSecondSemester = checkSelectiveCourses(selectiveCourses.getSelectiveCoursesSecondSemester(), selectiveCourseFragmentsSecondSemester,
                    studentDegree.getMaxProfCoursesSecondSemester(), studentDegree.getMaxGenCoursesSecondSemester(),
                    studentDegree.getMaxCoursesSecondSemester());

            if (selectiveCoursesCounter != null) {
                selectiveCoursesCounter.setText(studentDegree.getMaxCoursesFirstSemester() +
                        " в 1 семестрі (" + selectedCourseFirstSemester.size() + "/" + studentDegree.getMaxCoursesFirstSemester() + ")" + ", " + studentDegree.getMaxCoursesSecondSemester() +
                        " в 2 семестрі(" + selectedCourseSecondSemester.size() + "/" + studentDegree.getMaxCoursesSecondSemester() + ")");
            }
            onSelectedCoursesCountChanged();
        }
    }

    private void onSelectedCoursesCountChanged(){
        if (selectListener != null) {
            if (selectedCourseFirstSemester.size() > 0 || selectedCourseSecondSemester.size() > 0) {
                if (!selectedStates.hasOneSelected) {
                    selectedStates.hasOneSelected = true;
                    selectListener.onOneSelected();
                }
                if (!selectedStates.hasAllSelected) {
                    if (hasAllSelected()) {
                        selectedStates.hasAllSelected = true;
                        selectListener.onAllSelected();
                    }
                } else {
                    if (!hasAllSelected()) {
                        selectedStates.hasAllSelected = false;
                        selectListener.onNotAllSelected();
                    }
                }
            } else {
                if (selectedStates.hasOneSelected) {
                    selectedStates.hasOneSelected = false;
                    selectListener.onLastDeselected();
                }
                if (selectedStates.hasAllSelected) {
                    selectedStates.hasAllSelected = false;
                    selectListener.onNotAllSelected();
                }
            }
        }
    }

    private boolean hasAllSelected() {
        return selectedCourseFirstSemester.size() >= studentDegree.getMaxCoursesFirstSemester() &&
                selectedCourseSecondSemester.size() >= studentDegree.getMaxCoursesSecondSemester();
    }

    public boolean isProfCourse(SelectiveCourse course) {
        return !course.getTrainingCycle().equals("GENERAL");
    }

    public List<SelectiveCourse> getSelectedCourseFirstSemester() {
        return selectedCourseFirstSemester;
    }

    public List<SelectiveCourse> getSelectedCourseSecondSemester() {
        return selectedCourseSecondSemester;
    }

    public List<SelectiveCourse> checkSelectiveCourses(List<SelectiveCourse> selectiveCoursesSemester, List<SelectiveCourseFragment> selectiveCoursesFragment,
                                                       int maxProfCourses, int maxGenCourse, int maxSemCourses) {

        List<SelectiveCourse> selectedCourses = new ArrayList<>();
        int profCount = 0;
        int genCount = 0;
        for (int i = 0; i < selectiveCoursesSemester.size(); i++) {
            SelectiveCourse course = selectiveCoursesSemester.get(i);
            if (course.selected && course.isAvailable()) {
                if (isProfCourse(course)) {
                    profCount++;
                } else {
                    genCount++;
                }

                selectedCourses.add(course);
            }
            if (profCount >= maxProfCourses) {
                for (SelectiveCourseFragment frag : selectiveCoursesFragment) {
                    if (isProfCourse(frag.getSelectiveCourse())) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }
            if (genCount >= maxGenCourse) {
                for (SelectiveCourseFragment frag : selectiveCoursesFragment) {
                    if (!isProfCourse(frag.getSelectiveCourse())) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }
            if (selectedCourses.size() >= maxSemCourses) {
                for (SelectiveCourseFragment frag : selectiveCoursesFragment) {
                    if (!frag.isChecked()) {
                        frag.setCheckBoxInteractive(false);
                    }
                }
            }
        }
        return selectedCourses;
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

    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
        onSelectedCoursesCountChanged();
    }

    public interface SelectListener {
        void onOneSelected();

        void onAllSelected();

        void onLastDeselected();

        void onNotAllSelected();
    }

    private static class SelectedStates {
        public boolean hasOneSelected = false;
        public boolean hasAllSelected = false;
    }
}
