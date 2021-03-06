package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view;

import android.annotation.SuppressLint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
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
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.TypeCycle;

public class SelectiveCoursesAdapter extends RecyclerView.Adapter<SelectiveCoursesAdapter.ViewHolder> implements Filterable {
    // 1 - bak
    // 3 - magistr

    private final SelectedCoursesCounter selectedCoursesCounter;
    private final List<SelectiveCourse> selectiveCoursesList;
    private final List<SelectiveCourse> selectiveCoursesListFull;
    @Getter
    private final List<SelectiveCourse> selectedCoursesList;
    private static boolean showExtendView = true;
    private TextView itemCountView;
    @Getter
    private final Semester semester;

    public SelectiveCoursesAdapter(List<SelectiveCourse> selectiveCoursesList, SelectedCoursesCounter selectedCoursesCounter, Semester semester) {
        this.selectiveCoursesList = selectiveCoursesList;
        this.selectiveCoursesListFull = new ArrayList<>(selectiveCoursesList);
        this.selectedCoursesList = new ArrayList<>(selectiveCoursesList.size());
        this.selectedCoursesCounter = selectedCoursesCounter;
        if (selectedCoursesCounter != null) {
            itemCountView = selectedCoursesCounter.getCountOfCourseView();
        }
        this.semester = semester;

        //Label selective courses from first round
        for (SelectiveCourse course : selectiveCoursesList) {
            if (course.isSelected()) {
                course.setSelectedFromFirstRound(true);
                if (course.isAvailable()) {
                    selectedCoursesList.add(course);
                }
            }
        }
        this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                updateItemCountView();
            }
        });
    }

    @NonNull
    @Override
    public SelectiveCoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(parent);
    }

    public static SelectiveCoursesAdapter.ViewHolder createViewHolder(@NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_selectivecourse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        SelectiveCourse course = selectiveCoursesList.get(position);
        bindViewHolder(viewHolder, course, selectedCoursesCounter);
        viewHolder.setListener(this::onClick);
    }

    @SuppressLint("SetTextI18n")
    public static void bindViewHolder(@NonNull ViewHolder viewHolder, SelectiveCourse course, SelectedCoursesCounter selectedCoursesCounter) {
        viewHolder.setSelectiveCourse(course);
        boolean blockedCourses = false;

        boolean courseDisqualified = !course.isAvailable();
        viewHolder.setDisqualified(courseDisqualified);
        blockedCourses = courseDisqualified;

        String courseName = course.getCourse().getCourseName().getName();
        SpannableString coloredCourseName = course.getTrainingCycle() == TypeCycle.GENERAL ?
                new SpannableString(courseName + " (Заг.) ") :
                new SpannableString(courseName + " (Проф.)");

        int generalColor = viewHolder.itemView.getResources().getColor(R.color.general_training_cycle, null);
        int professionalColor = viewHolder.itemView.getResources().getColor(R.color.professional_training_cycle, null);

        coloredCourseName.setSpan(course.getTrainingCycle() == TypeCycle.GENERAL ?
                        new ForegroundColorSpan(generalColor) :
                        new ForegroundColorSpan(professionalColor),
                courseName.length() + 1, courseName.length() + 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        viewHolder.textCourseName.setText(coloredCourseName);

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

        if (showExtendView) {
            viewHolder.setExtendedView();
        } else {
            viewHolder.setShortView();
        }

        if (selectedCoursesCounter != null) {
            boolean courseCrowded = course.getStudentsCount() >= selectedCoursesCounter.getMaxStudentsCount();
            viewHolder.setCrowded(courseCrowded);
            blockedCourses = blockedCourses || courseCrowded;
        }

        blockedCourses = blockedCourses || course.isSelectedFromFirstRound();

        viewHolder.setBlocked(blockedCourses);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<SelectiveCourse> filteredSelectiveCourses = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredSelectiveCourses.addAll(selectiveCoursesListFull);
                } else {
                    String facultyFilterPattern = constraint.toString().toUpperCase().trim();
                    for (SelectiveCourse course : selectiveCoursesListFull) {
                        if (facultyFilterPattern.contains(course.getDepartment().getFaculty().getAbbr().toUpperCase())) {
                            filteredSelectiveCourses.add(course);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredSelectiveCourses;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                selectiveCoursesList.clear();
                selectiveCoursesList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView textTeacherName, textDepartmentName, textStudentCount, textCourseName;
        final LinearLayout informationBlock;
        final CheckBox checkBox;
        final ViewGroup.LayoutParams normalParams;
        final static int
                studentCountBadgeCrowed = R.drawable.student_count_badge_crowded,
                studentCountBadgeNormal = R.drawable.student_count_badge;
        private boolean interactive = true;
        @Setter
        @Getter
        private SelectiveCourse selectiveCourse;
        @Setter
        private OnClickListener listener;
        private boolean isStudentsCountVisible = true;

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
                if (selectiveCourse.getTrainingCycle() == TypeCycle.GENERAL) {
                    int generalColor = itemView.getResources().getColor(R.color.general_training_cycle, null);
                    ((TextView) dialogView.findViewById(R.id.selectiveCourseTrainingCycle)).setText("Загальний рівень");
                    ((TextView) dialogView.findViewById(R.id.selectiveCourseTrainingCycle)).setTextColor(generalColor);
                }
                ((TextView) dialogView.findViewById(R.id.selectiveCourseFaculty)).setText(selectiveCourse.getDepartment().getFaculty().getName());
                ((TextView) dialogView.findViewById(R.id.selectiveCourseDepartment)).setText(selectiveCourse.getDepartment().getName());
                TextView studentsCountText = dialogView.findViewById(R.id.selectiveCourseStudentCount);
                if (isStudentsCountVisible) {
                    studentsCountText.setText("Кількість записаних студентів: " + selectiveCourse.getStudentsCount());
                    studentsCountText.setVisibility(View.VISIBLE);
                } else {
                    studentsCountText.setVisibility(View.INVISIBLE);
                }

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

        public void setDisqualified(boolean isDisqualified) {
            TextView disqualifiedLabel = itemView.findViewById(R.id.labelDisqulifiedCourse);
            if (isDisqualified) {
                disqualifiedLabel.setVisibility(View.VISIBLE);
                int fondColor = itemView.getContext().getResources().getColor(R.color.disqualified_course_fond, null);
                itemView.setBackgroundColor(fondColor);
            } else {
                disqualifiedLabel.setVisibility(View.GONE);
                int fondColor = itemView.getContext().getResources().getColor(R.color.white, null);
                itemView.setBackgroundColor(fondColor);
            }
        }

        public void setTextStudentCountVisible(boolean isVisible) {
            isStudentsCountVisible = isVisible;
            textStudentCount.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }

        public void setChecked(boolean checked) {
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

        @SuppressLint("UseCompatLoadingForDrawables")
        public void setCrowded(boolean didCrowed) {
            if (didCrowed) {
                textStudentCount.setBackground(itemView.getContext().getDrawable(studentCountBadgeCrowed));
            } else {
                textStudentCount.setBackground(itemView.getContext().getDrawable(studentCountBadgeNormal));
            }
        }

        @Override
        public void onClick(View view) {
            if (!interactive || selectiveCourse.isSelectedFromFirstRound()) return;

            boolean isSuccess = listener.onClick(this);
            if (isSuccess) {
                setChecked(!checkBox.isChecked());
            }
        }

        public void setBlocked(boolean isBlock) {
            if (isBlock) {
                Log.e("L", "Block!");
                checkBox.setAlpha(0.37f);
                interactive = false;
            } else {
                checkBox.setAlpha(1f);
                interactive = true;
            }
        }

        public interface OnClickListener {
            boolean onClick(ViewHolder viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return selectiveCoursesList.size();
    }

    public void updateItemCountView() {
        if (selectedCoursesCounter != null) {
            if (getItemCount() == selectiveCoursesListFull.size()) {
                itemCountView.setEnabled(true);
                itemCountView.setText("(" + getItemCount() + ")");
            } else {
                itemCountView.setEnabled(false);
                itemCountView.setText("(" + getItemCount() + "/" + selectiveCoursesListFull.size() + ")");
            }
        }
    }

    public boolean onClick(ViewHolder viewHolder) {
        if (selectedCoursesCounter == null) return false;
        SelectiveCourse course = viewHolder.getSelectiveCourse();

        boolean isSuccess = addOrRemoveToSelectedList(course, selectedCoursesList);
        int[] selectedCoursesCounts = new int[2];
        for (SelectiveCourse selectiveCourse : selectedCoursesList) {
            int cycleType = selectiveCourse.getTrainingCycle() == TypeCycle.GENERAL ? 1 : 0;
            selectedCoursesCounts[cycleType] += 1;
        }

        if (semester == Semester.FIRST) {
            selectedCoursesCounter.setSelectedCountFirstSemester(selectedCoursesCounts);
        } else {
            selectedCoursesCounter.setSelectedCountSecondSemester(selectedCoursesCounts);
        }
        return isSuccess;
    }

    private boolean addOrRemoveToSelectedList(SelectiveCourse course, List<SelectiveCourse> list) {
        boolean listIsFull = semester == Semester.FIRST ?
                selectedCoursesCounter.isFirstSemesterFull(course.getTrainingCycle()) :
                selectedCoursesCounter.isSecondSemesterFull(course.getTrainingCycle());

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

    public void setExtendedView() {
        showExtendView = true;
        notifyDataSetChanged();
    }

    public void setShortView() {
        showExtendView = false;
        notifyDataSetChanged();
    }
}
