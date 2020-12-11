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

    // 1 - bak
    // 3 - magistr

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

    private List<SelectiveCourse> selectiveCourseFirstSemester;
    private List<SelectiveCourse> selectiveCourseSecondSemester;

    private boolean interactive;
    private boolean forMagister;
    private boolean showTrainingCycle;

    private int maxCoursesFirstSemester;
    private int maxCoursesSecondSemester;

    private int maxProfCoursesFirstSemester;
    private int maxGeneralCoursesFirstSemester;

    private int maxProfCoursesSecondSemester;
    private int maxGeneralCoursesSecondSemester;

    public int getMaxCoursesFirstSemester() {
        return maxCoursesFirstSemester;
    }

    public int getMaxCoursesSecondSemester() {
        return maxCoursesSecondSemester;
    }

    public ChdtuAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager, TextView selectiveCoursesCounter, boolean disableCheckBoxes) {
        initAdapter(selectiveCourses, supportFragmentManager, selectiveCoursesCounter, disableCheckBoxes);
        this.forMagister = false;
        initMaxCourses();
    }

    public ChdtuAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager, TextView selectiveCoursesCounter, boolean disableCheckBoxes, boolean forMagister) {
        initAdapter(selectiveCourses, supportFragmentManager, selectiveCoursesCounter, disableCheckBoxes);
        this.forMagister = forMagister;
        initMaxCourses();
    }

    public boolean hasGeneralAndProfessional() {
        boolean hasGeneral = false;
        boolean hasProfessional = false;
        for(SelectiveCourse selectiveCourse : selectiveCourseFirstSemester) {
            if(selectiveCourse.getTrainingCycle().equals("GENERAL")) {
                hasGeneral = true;
            }else{
                hasProfessional = true;
            }
        }
        boolean hasFirstSemester = hasGeneral && hasProfessional;

        hasGeneral = false;
        hasProfessional = false;
        for(SelectiveCourse selectiveCourse : selectiveCourseSecondSemester) {
            if(selectiveCourse.getTrainingCycle().equals("GENERAL")) {
                hasGeneral = true;
            }else{
                hasProfessional = true;
            }
        }
        return hasFirstSemester && (hasGeneral && hasProfessional);
    }

    public void initAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager, TextView selectiveCoursesCounter, boolean disableCheckBoxes) {
        this.selectiveCourses = selectiveCourses;
        this.fragmentManager = supportFragmentManager;

        selectiveCourseFragmentsFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectiveCourseFragmentsSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());

        selectiveCourseFirstSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesFirstSemester().size());
        selectiveCourseSecondSemester = new ArrayList<>(selectiveCourses.getSelectiveCoursesSecondSemester().size());
        this.selectiveCoursesCounter = selectiveCoursesCounter;
        interactive = disableCheckBoxes;
        showTrainingCycle = hasGeneralAndProfessional();
    }

    public void initMaxCourses() {
        if (forMagister) {
            maxCoursesFirstSemester = 3;
            maxCoursesSecondSemester = 3;
            maxProfCoursesFirstSemester = maxProfCoursesSecondSemester = 2;
            maxGeneralCoursesFirstSemester = maxGeneralCoursesSecondSemester = 1;
        } else {
            maxCoursesFirstSemester = 3;
            maxCoursesSecondSemester = 2;
            maxProfCoursesFirstSemester = maxProfCoursesSecondSemester = 100;
            maxGeneralCoursesFirstSemester = maxGeneralCoursesSecondSemester = 100;
        }
        if(selectiveCoursesCounter != null) {
            selectiveCoursesCounter.setText(maxCoursesFirstSemester + " в 1 семестрі (" + 0 + "/" + maxCoursesFirstSemester + ")" +
                    ", " + maxCoursesSecondSemester + " в 2 семестрі(" + 0 + "/" + maxCoursesSecondSemester + ")");
        }
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


            int profCount = 0;
            int genCount = 0;
            selectiveCourseFirstSemester.clear();
            for (int i = 0; i < selectiveCourses.getSelectiveCoursesFirstSemester().size(); i++) {
                if (selectiveCourses.getSelectiveCoursesFirstSemester().get(i).selected) {
                    SelectiveCourse course = selectiveCourses.getSelectiveCoursesFirstSemester().get(i);
                    if (isProfCourse(course)) {
                        profCount++;
                    } else {
                        genCount++;
                    }

                    selectiveCourseFirstSemester.add(course);
                }
                if(profCount >= maxProfCoursesFirstSemester) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
                        if(isProfCourse(frag.getSelectiveCourse())){
                            if (!frag.isChecked()) {
                                frag.setCheckBoxInteractive(false);
                            }
                        }
                    }
                }
                if(genCount >= maxGeneralCoursesFirstSemester) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
                        if(!isProfCourse(frag.getSelectiveCourse())){
                            if (!frag.isChecked()) {
                                frag.setCheckBoxInteractive(false);
                            }
                        }
                    }
                }
                if (selectiveCourseFirstSemester.size() >= maxCoursesFirstSemester) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsFirstSemester) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }

            profCount = 0;
            genCount = 0;
            selectiveCourseSecondSemester.clear();
            for (int i = 0; i < selectiveCourses.getSelectiveCoursesSecondSemester().size(); i++) {
                if (selectiveCourses.getSelectiveCoursesSecondSemester().get(i).selected) {
                    SelectiveCourse course = selectiveCourses.getSelectiveCoursesSecondSemester().get(i);
                    if (isProfCourse(course)) {
                        profCount++;
                    } else {
                        genCount++;
                    }

                    selectiveCourseSecondSemester.add(course);
                }
                if(profCount >= maxProfCoursesSecondSemester) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
                        if(isProfCourse(frag.getSelectiveCourse())){
                            if (!frag.isChecked()) {
                                frag.setCheckBoxInteractive(false);
                            }
                        }
                    }
                }
                if(genCount >= maxGeneralCoursesSecondSemester) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
                        if(!isProfCourse(frag.getSelectiveCourse())){
                            if (!frag.isChecked()) {
                                frag.setCheckBoxInteractive(false);
                            }
                        }
                    }
                }
                if (selectiveCourseSecondSemester.size() >= maxCoursesSecondSemester) {
                    for (SelectiveCourseFragment frag : selectiveCourseFragmentsSecondSemester) {
                        if (!frag.isChecked()) {
                            frag.setCheckBoxInteractive(false);
                        }
                    }
                }
            }

            selectiveCoursesCounter.setText(maxCoursesFirstSemester + " в 1 семестрі (" + getSelectiveCourseFirstSemester().size() + "/" + maxCoursesFirstSemester + ")" +
                    "," + maxCoursesSecondSemester + " в 2 семестрі(" + getSelectiveCourseSecondSemester().size() + "/" + maxCoursesSecondSemester + ")");
        }
    }

    public boolean isProfCourse(SelectiveCourse course) {
        return (course.getTrainingCycle().equals("GENERAL")) ? false : true;
    }

    public List<SelectiveCourse> getSelectiveCourseFirstSemester() {
        return selectiveCourseFirstSemester;
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
