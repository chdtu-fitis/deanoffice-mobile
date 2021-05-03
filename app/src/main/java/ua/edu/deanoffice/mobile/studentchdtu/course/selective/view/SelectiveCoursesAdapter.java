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
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCourseFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SemesterFragment;

public class SelectiveCoursesAdapter extends RecyclerView.Adapter<SelectiveCoursesAdapter.ViewHolder> implements SelectiveCourseFragment.OnClickListener {
    @Override
    public boolean onClick(Object obj) {
        SelectiveCourseFragment fragment = (SelectiveCourseFragment) obj;

        SelectiveCourse course = fragment.getSelectiveCourse();
        int courseSemester = course.getCourse().getSemester();
        boolean isSuccess;
        if (courseSemester % 2 != 0) {
            isSuccess = addOrRemoveToSelectedList(course, selectedCourseFirstSemester, selectedCoursesCounter.isFirstSemesterFull());
        } else {
            isSuccess = addOrRemoveToSelectedList(course, selectedCourseSecondSemester, selectedCoursesCounter.isSecondSemesterFull());
        }

        selectedCoursesCounter.setSelectedFirstSemester(selectedCourseFirstSemester.size());
        selectedCoursesCounter.setSelectedSecondSemester(selectedCourseSecondSemester.size());

        return isSuccess;
    }

    private boolean addOrRemoveToSelectedList(SelectiveCourse course, List<SelectiveCourse> list, boolean listIsFull) {
        if (!course.isSelected()) {
            if (!listIsFull) {
                list.add(course);
                return true;
            }
        } else {
            return list.remove(course);
        }
        return false;
    }

    // 1 - bak
    // 3 - magistr

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
        }
    }

    private final SelectedCoursesCounter selectedCoursesCounter;

    private final SelectiveCourses selectiveCourses;
    private final FragmentManager fragmentManager;

    private final List<SelectiveCourseFragment> selectiveCourseFragmentsFirstSemester;
    private final List<SelectiveCourseFragment> selectiveCourseFragmentsSecondSemester;

    private final List<SelectiveCourse> selectedCourseFirstSemester;
    private final List<SelectiveCourse> selectedCourseSecondSemester;

    private boolean interactive;
    private final boolean showTrainingCycle;
/*    private StudentDegree studentDegree;

    public int getMaxCoursesFirstSemester() {
        return studentDegree.getMaxCoursesFirstSemester();
    }

    public int getMaxCoursesSecondSemester() {
        return studentDegree.getMaxCoursesSecondSemester();
    }*/

    public SelectiveCoursesAdapter(SelectiveCourses selectiveCourses, FragmentManager fragmentManager) {
        this(selectiveCourses, fragmentManager, null, false);
    }

    public SelectiveCoursesAdapter(SelectiveCourses selectiveCourses, FragmentManager fragmentManager, SelectedCoursesCounter selectedCoursesCounter, boolean disableCheckboxes) {
        this.selectiveCourses = selectiveCourses;
        this.fragmentManager = fragmentManager;


        selectiveCourseFragmentsFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectiveCourseFragmentsSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());

        selectedCourseFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectedCourseSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());

        for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesFirstSemester()) {
            if (course.isSelected() && course.isAvailable()) {
                selectedCourseFirstSemester.add(course);
            }
        }

        for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesSecondSemester()) {
            if (course.isSelected() && course.isAvailable()) {
                selectedCourseSecondSemester.add(course);
            }
        }

        this.selectedCoursesCounter = selectedCoursesCounter;
        interactive = disableCheckboxes;
        showTrainingCycle = hasGeneralAndProfessional();
    }

    /*
        public SelectiveCoursesAdapter(SelectedCoursesCounter selectedCoursesCounter, SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager) {
            initAdapter(selectiveCourses, supportFragmentManager, selectedCoursesCounter, false);
            initMaxCourses(false);
        }


        public SelectiveCoursesAdapter(SelectedCoursesCounter selectedCoursesCounter, FragmentManager supportFragmentManager) {
            this(selectiveCourses, supportFragmentManager, selectedCoursesCounter, false);
            initMaxCourses(false);
        }

        public SelectiveCoursesAdapter(FragmentManager supportFragmentManager, SelectedCoursesCounter selectedCoursesCounter, boolean disableCheckBoxes) {
            initAdapter(selectiveCourses, supportFragmentManager, selectedCoursesCounter, disableCheckBoxes);
            initMaxCourses(forMagister);
        }
    */
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

/*
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
*/

    @NonNull
    @Override
    public SelectiveCoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_chdtu_list_selectivecourse, null);

        // create ViewHolder
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        //itemLayoutView.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (position == 0) {
            selectiveCourseFragmentsFirstSemester.clear();
            selectiveCourseFragmentsSecondSemester.clear();

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

  /*  @Override
    public void onClick(View v) {
        Log.e("s", "Sda");
        if (!interactive) return;

        for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
            frag.setCheckBoxInteractive(true);
        }
        for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
            frag.setCheckBoxInteractive(true);
        }

        if (selectedCoursesCounter != null) {
            StudentDegree studentDegree = selectedCoursesCounter.getStudentDegree();

            selectedCourseFirstSemester = checkSelectiveCourses(
                    selectiveCourses.getSelectiveCoursesFirstSemester(),
                    selectiveCourseFragmentsFirstSemester,
                    studentDegree.getMaxProfCoursesFirstSemester(),
                    studentDegree.getMaxGenCoursesFirstSemester(),
                    studentDegree.getMaxCoursesFirstSemester());

            selectedCourseSecondSemester = checkSelectiveCourses(
                    selectiveCourses.getSelectiveCoursesSecondSemester(),
                    selectiveCourseFragmentsSecondSemester,
                    studentDegree.getMaxProfCoursesSecondSemester(),
                    studentDegree.getMaxGenCoursesSecondSemester(),
                    studentDegree.getMaxCoursesSecondSemester());

            selectedCoursesCounter.setSelectedFirstSemester(selectedCourseFirstSemester.size());
            selectedCoursesCounter.setSelectedSecondSemester(selectedCourseSecondSemester.size());
        }
    }*/
/*
    @Override
    public void onClick(View v) {
        if (interactive) {
            for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
                frag.setCheckBoxInteractive(true);
            }
            for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
                frag.setCheckBoxInteractive(true);
            }

            if(selectedCoursesCounter != null) {
                StudentDegree studentDegree = selectedCoursesCounter.getStudentDegree();

                selectedCourseFirstSemester = checkSelectiveCourses(
                        selectiveCourses.getSelectiveCoursesFirstSemester(),
                        selectiveCourseFragmentsFirstSemester,
                        studentDegree.getMaxProfCoursesFirstSemester(),
                        studentDegree.getMaxGenCoursesFirstSemester(),
                        studentDegree.getMaxCoursesFirstSemester());

                selectedCourseSecondSemester = checkSelectiveCourses(
                        selectiveCourses.getSelectiveCoursesSecondSemester(),
                        selectiveCourseFragmentsSecondSemester,
                        studentDegree.getMaxProfCoursesSecondSemester(),
                        studentDegree.getMaxGenCoursesSecondSemester(),
                        studentDegree.getMaxCoursesSecondSemester());

                selectedCoursesCounter.setSelectedFirstSemester(selectedCourseFirstSemester.size());
                selectedCoursesCounter.setSelectedSecondSemester(selectedCourseSecondSemester.size());
            }
        }
    }*/
/*
    public List<SelectiveCourse> checkSelectiveCourses(List<SelectiveCourse> selectiveCourseList, List<SelectiveCourseFragment> selectiveCourseFragments,
                                                       int maxProfCourses, int maxGenCourse, int maxSemCourses) {
        List<SelectiveCourse> selectedCourses = new ArrayList<>();
        int profCount = 0;
        int genCount = 0;
        for (int i = 0; i < selectiveCourseList.size(); i++) {
            SelectiveCourse course = selectiveCourseList.get(i);
            if (course.isSelected() && course.isAvailable()) {
                if (isProfCourse(course)) {
                    profCount++;
                } else {
                    genCount++;
                }
                selectedCourses.add(course);
            }
            if (profCount >= maxProfCourses) {
                for (SelectiveCourseFragment frag : selectiveCourseFragments) {
                    if (isProfCourse(frag.getSelectiveCourse())) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }
            if (genCount >= maxGenCourse) {
                for (SelectiveCourseFragment frag : selectiveCourseFragments) {
                    if (!isProfCourse(frag.getSelectiveCourse())) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }
            if (selectedCourses.size() >= maxSemCourses) {
                for (SelectiveCourseFragment frag : selectiveCourseFragments) {
                    if (!frag.isChecked()) {
                        frag.setCheckBoxInteractive(false);
                    }
                }
            }
        }
        return selectedCourses;
    }*/
/*
    public boolean isProfCourse(SelectiveCourse course) {
        return !course.getTrainingCycle().equals("GENERAL");
    }
*/
    public List<SelectiveCourse> getSelectedCourseFirstSemester() {
        return selectedCourseFirstSemester;
    }

    public List<SelectiveCourse> getSelectedCourseSecondSemester() {
        return selectedCourseSecondSemester;
    }

    public void clearSelected() {

        for (SelectiveCourseFragment courseFragment : selectiveCourseFragmentsFirstSemester) {
            courseFragment.setChecked(false);
        }
        for (SelectiveCourseFragment courseFragment : selectiveCourseFragmentsSecondSemester) {
            courseFragment.setChecked(false);
        }

        selectedCourseFirstSemester.clear();
        selectedCourseSecondSemester.clear();

        selectedCoursesCounter.setSelectedFirstSemester(selectedCourseFirstSemester.size());
        selectedCoursesCounter.setSelectedSecondSemester(selectedCourseSecondSemester.size());
        //onClick(null);
    }

    public void setExtendedView() {
        for (SelectiveCourseFragment fragment : selectiveCourseFragmentsFirstSemester) {
            fragment.setExtendedView();
        }
        for (SelectiveCourseFragment fragment : selectiveCourseFragmentsSecondSemester) {
            fragment.setExtendedView();
        }
    }

    public void setShortView() {
        for (SelectiveCourseFragment fragment : selectiveCourseFragmentsFirstSemester) {
            fragment.setShortView();
        }
        for (SelectiveCourseFragment fragment : selectiveCourseFragmentsSecondSemester) {
            fragment.setShortView();
        }
    }
}
