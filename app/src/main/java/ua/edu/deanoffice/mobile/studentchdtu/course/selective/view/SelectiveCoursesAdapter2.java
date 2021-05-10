package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.Department;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.Teacher;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.Semester;

public class SelectiveCoursesAdapter2 extends RecyclerView.Adapter<SelectiveCoursesAdapter2.ViewHolder> {
    // 1 - bak
    // 3 - magistr

    private final SelectedCoursesCounter selectedCoursesCounter;
    private final List<SelectiveCourse> selectiveCoursesList;
    @Getter
    private final List<SelectiveCourse> selectedCourse;
    private final List<SelectiveCourse> selectedCourseFromFirstRound;
    private final boolean showTrainingCycle;
    private static boolean showExtendView = true;
    private boolean showUncheckedCourses = true;
    private boolean interactive = true;
    @Getter
    private final Semester semester;

    public SelectiveCoursesAdapter2(List<SelectiveCourse> selectiveCoursesList, SelectedCoursesCounter selectedCoursesCounter, Semester semester) {
        this.selectiveCoursesList = selectiveCoursesList;
        this.selectedCourse = new ArrayList<>(selectiveCoursesList.size());
        this.selectedCoursesCounter = selectedCoursesCounter;
        this.showTrainingCycle = hasGeneralAndProfessional();
        this.semester = semester;

        this.selectedCourseFromFirstRound = new ArrayList<>();

        //Add selective courses from first round
        for (SelectiveCourse course : selectiveCoursesList) {
            if (course.isSelected()) {
                selectedCourseFromFirstRound.add(course);
            }
        }
        for (SelectiveCourse course : selectedCourseFromFirstRound) {
            if (course.isAvailable()) {
                selectedCourse.add(course);
            }
        }
    }

    @NonNull
    @Override
    public SelectiveCoursesAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_selectivecourse, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        SelectiveCourse course = selectiveCoursesList.get(position);

        viewHolder.setSelectiveCourse(course);

        if (!course.isAvailable()) {
            viewHolder.makeDisqualified();
        }

        String courseNameString = course.getCourse().getCourseName().getName();
        if (showTrainingCycle) {
            if (course.getTrainingCycle().equals("GENERAL")) {
                courseNameString += " (Загальний рівень)";
            } else {
                courseNameString += " (Професійний рівень)";
            }
        }
        viewHolder.textCourseName.setText(courseNameString);

        if (course.getTeacher() != null) {
            Teacher teacher = course.getTeacher();
            String teacherNameString = teacher.getSurname() + " " + teacher.getName() + " " + teacher.getPatronimic();
            viewHolder.textTeacherName.setText(teacherNameString);
        } else {
            viewHolder.informationBlock.removeView(viewHolder.textTeacherName);
        }

        if (course.getDepartment() != null) {
            Department department = course.getDepartment();
            String departmentNameString = department.getFaculty().getAbbr() + ", " + department.getName();
            viewHolder.textDepartmentName.setText(departmentNameString);
        }
        viewHolder.textStudentCount.setText(Integer.toString(course.getStudentsCount()));

        viewHolder.checkBox.setChecked(course.isSelected());
        viewHolder.setListener(this::onClick);
        viewHolder.setSelectedFromFirstRound(course.isSelected());

        if (showExtendView) {
            viewHolder.setExtendedView();
        } else {
            viewHolder.setShortView();
        }

        if (!course.isSelected()) {
            viewHolder.setVisible(showUncheckedCourses);
        }

        viewHolder.setInteractive(interactive);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView textTeacherName, textDepartmentName, textStudentCount, textCourseName;
        final LinearLayout informationBlock;
        final CheckBox checkBox;
        final ViewGroup.LayoutParams normalParams;

        @Setter
        private boolean selectedFromFirstRound = false;
        @Setter
        private boolean interactive = true;
        @Setter
        @Getter
        private SelectiveCourse selectiveCourse;
        @Setter
        private OnClickListener listener;

        public ViewHolder(View view) {
            super(view);
            normalParams = view.getLayoutParams();

            checkBox = view.findViewById(R.id.selectivecheckbox);
            ImageView imageInfo = view.findViewById(R.id.selectivecourseinfo);

            Button btnCheckBox = view.findViewById(R.id.buttonBox);

            textTeacherName = view.findViewById(R.id.teacherName);
            textDepartmentName = view.findViewById(R.id.departmentName);
            textStudentCount = view.findViewById(R.id.studentCount);
            textCourseName = view.findViewById(R.id.selectivecoursename);

            informationBlock = view.findViewById(R.id.textlayout);

            imageInfo.setOnClickListener((viewClick) -> {
                if (selectiveCourse == null) return;

                AlertDialog.Builder builder = new AlertDialog.Builder(viewClick.getContext());
                ViewGroup viewGroup = viewClick.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(viewClick.getContext()).inflate(R.layout.dialog_selectivecourse_info, viewGroup, false);

                ((TextView) dialogView.findViewById(R.id.selectiveCourseName)).setText(selectiveCourse.getCourse().getCourseName().getName());
                ((TextView) dialogView.findViewById(R.id.selectiveCourseFaculty)).setText(selectiveCourse.getDepartment().getFaculty().getName());
                ((TextView) dialogView.findViewById(R.id.selectiveCourseDepartment)).setText(selectiveCourse.getDepartment().getName());
                ((TextView) dialogView.findViewById(R.id.selectiveCourseStudentCount)).setText("Кількість записаних студентів: " + selectiveCourse.getStudentsCount());
                Teacher teacher = selectiveCourse.getTeacher();
                String teacherFullName = teacher.getSurname() + " " + teacher.getName() + " " + teacher.getPatronimic();
                ((TextView) dialogView.findViewById(R.id.selectiveCourseTeacher)).setText(teacherFullName + " (" + teacher.getPosition().getName() + ")");

                ((TextView) dialogView.findViewById(R.id.selectiveCourseDescription)).setText(selectiveCourse.getDescription());
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                dialogView.findViewById(R.id.buttonOk).setOnClickListener((viewOk) -> {
                    alertDialog.dismiss();
                });
            });

            btnCheckBox.setOnClickListener(this);
        }

        public void makeDisqualified() {
            TextView disqualifiedLabel = itemView.findViewById(R.id.labelDisqulifiedCourse);
            disqualifiedLabel.setVisibility(View.VISIBLE);
            int fondColor = itemView.getContext().getResources().getColor(R.color.disqualified_course_fond, null);
            itemView.setBackgroundColor(fondColor);
        }

        public void setChecked(boolean checked) {
            if (selectedFromFirstRound) return;
            checkBox.setChecked(checked);
            selectiveCourse.setSelected(checked);
        }

        public void setShortView() {
            setVisible(true);
            textTeacherName.setVisibility(View.GONE);
            textDepartmentName.setVisibility(View.GONE);
            textStudentCount.setVisibility(View.GONE);
        }

        public void setExtendedView() {
            setVisible(true);
            textTeacherName.setVisibility(View.VISIBLE);
            textDepartmentName.setVisibility(View.VISIBLE);
            textStudentCount.setVisibility(View.VISIBLE);
        }

        public void setVisible(boolean isVisible) {
            if (isVisible) {
                itemView.setVisibility(View.VISIBLE);
                itemView.setLayoutParams(normalParams);
            } else {
                itemView.setVisibility(View.GONE);
                itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            }
        }

        @Override
        public void onClick(View view) {
            if (!interactive) return;

            boolean isSuccess = listener.onClick(this);
            if (isSuccess) {
                setChecked(!checkBox.isChecked());
            }
        }

        public interface OnClickListener {
            boolean onClick(ViewHolder viewHolder);
        }
    }

    public boolean hasGeneralAndProfessional() {
        boolean hasGeneral = false;
        boolean hasProfessional = false;
        for (SelectiveCourse selectiveCourse : selectedCourse) {
            if (selectiveCourse.getTrainingCycle().equals("GENERAL")) {
                hasGeneral = true;
            } else {
                hasProfessional = true;
            }
        }
        return hasGeneral && hasProfessional;
    }

    @Override
    public int getItemCount() {
        return selectiveCoursesList.size();
    }

    public boolean onClick(ViewHolder viewHolder) {
        if (selectedCoursesCounter == null) return false;
        SelectiveCourse course = viewHolder.getSelectiveCourse();

        boolean isSuccess;
        if (semester == Semester.FIRST) {
            isSuccess = addOrRemoveToSelectedList(course, selectedCourse, selectedCoursesCounter.isFirstSemesterFull());
            selectedCoursesCounter.setSelectedFirstSemester(selectedCourse.size());
        } else {
            isSuccess = addOrRemoveToSelectedList(course, selectedCourse, selectedCoursesCounter.isSecondSemesterFull());
            selectedCoursesCounter.setSelectedSecondSemester(selectedCourse.size());
        }
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

    public void disableCheckBoxes(boolean disable) {
        interactive = !disable;
        notifyDataSetChanged();
    }

    public void clearSelected() {
        List<SelectiveCourse> coursesListToDelete = new ArrayList<>();
        for (SelectiveCourse course : selectedCourse) {
            if (course.isSelected() && !selectedCourseFromFirstRound.contains(course)) {
                course.setSelected(false);
                coursesListToDelete.add(course);
            }
        }
        for (SelectiveCourse course : coursesListToDelete) {
            selectedCourse.remove(course);
        }

        if (semester == Semester.FIRST) {
            selectedCoursesCounter.setSelectedFirstSemester(selectedCourse.size());
        } else {
            selectedCoursesCounter.setSelectedSecondSemester(selectedCourse.size());
        }

        notifyDataSetChanged();
    }

    public void hideAllUncheckedCourseFragments() {
        showUncheckedCourses = false;
        notifyDataSetChanged();
    }

    public void showAllHiddenCourseFragments() {
        showUncheckedCourses = true;
        notifyDataSetChanged();
    }

    public void setExtendedView() {
        showExtendView = true;
        notifyDataSetChanged();
    }

    public void setShortView() {
        showExtendView = false;
        notifyDataSetChanged();
    }
}
