package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ExistingId;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public class SelectiveCoursesFragment extends BaseSelectiveCoursesFragment {
    private SelectiveCoursesAdapter currentAdapter;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch btnExtendedView;
    private RecyclerView recyclerView;
    private TextView sortLabel;
    private View sortPanel;
    private final SelectiveCourses availableSelectiveCourses;

    public SelectiveCoursesFragment(SelectedCoursesCounter selectedCoursesCounter, SelectiveCourses availableSelectiveCourses) {
        super(selectedCoursesCounter);
        this.availableSelectiveCourses = availableSelectiveCourses;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selectivecourses, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        btnExtendedView = view.findViewById(R.id.switchExtendedView);
        btnExtendedView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentAdapter.setExtendedView();
                setSortPanelVisible(View.VISIBLE);
            } else {
                currentAdapter.setShortView();
                setSortPanelVisible(View.GONE);
            }
        });

        fillSelectiveCoursesList();
/*
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMessage("Завантаження");
        progressDoalog.setProgressStyle(R.style.ProgressBar);
        progressDoalog.show();

        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class)
                .studentDegree(App.getInstance().getJwt().getToken(), App.getInstance().getCurrentStudent().getDegrees()[0].getId()).enqueue(new Callback<SelectiveCoursesStudentDegree>() {
            @Override
            public void onResponse(@NotNull Call<SelectiveCoursesStudentDegree> call,@NotNull  Response<SelectiveCoursesStudentDegree> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSelectiveCourses() != null) {
                        List<SelectiveCourse> selectiveCoursesFirst = new ArrayList<SelectiveCourse>();
                        List<SelectiveCourse> selectiveCoursesSecond = new ArrayList<SelectiveCourse>();

                        for (SelectiveCourse course : response.body().getSelectiveCourses()) {
                            course.setSelected(true);
                            if (course.getCourse().getSemester() % 2 != 0) {
                                selectiveCoursesFirst.add(course);
                            } else {
                                selectiveCoursesSecond.add(course);
                            }
                        }

                        SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                        selectiveCoursesFinal.setSelectiveCoursesFirstSemester(selectiveCoursesFirst);
                        selectiveCoursesFinal.setSelectiveCoursesSecondSemester(selectiveCoursesSecond);

                        if (getFragmentManager() != null) {
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectiveCoursesConfirmedFragment(selectiveCoursesFinal)).commit();
                        }
                    } else {
                        RequestAvailableSelectiveCoursesList();
                    }
                }
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<SelectiveCoursesStudentDegree> call, Throwable t) {
                progressDoalog.dismiss();
                error("Помилка під час підключення до серверу, спробуйте пізніше.");
            }
        });
*/
    }

    /*
        public void RequestAvailableSelectiveCoursesList() {
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Завантаження");
            progressDialog.setProgressStyle(R.style.ProgressBar);
            progressDialog.show();

            App.getInstance().getClient()
                    .createRequest(SelectiveCourseRequests.class)
                    .requestSelectiveCourses(App.getInstance().getJwt().getToken(),
                            App.getInstance().getCurrentStudent().getDegrees()[0].getId(),
                            true).enqueue(new Callback<SelectiveCourses>() {
                @Override
                public void onResponse(Call<SelectiveCourses> call, Response<SelectiveCourses> response) {
                    if (response.isSuccessful()) {
                        SelectiveCoursesFragment.this.onResponse(response.body());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<SelectiveCourses> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }

        public void onResponse(SelectiveCourses selectiveCourses) {
            recyclerView = getView().findViewById(R.id.listview);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            isMasterDegree = App.getInstance().getCurrentStudent().getDegrees()[0].getSpecialization().getDegree().getId() == 3;

            currentAdapter = new ChdtuAdapter(selectiveCourses, getFragmentManager(), selectiveCoursesCounter, true, isMasterDegree);
            recyclerView.setAdapter(currentAdapter);

           TextView btnSortByFaculty = (TextView) getView().findViewById(R.id.sortByFaculty);

            btnSortByFaculty.setOnClickListener(v -> {
                sort(SelectiveCourse.ByFacultyName, selectiveCourses);
                setSortLabel("Факультетом");
            });

            TextView btnSortByName = (TextView) getView().findViewById(R.id.sortByCourse);

            btnSortByName.setOnClickListener(v -> {
                sort(SelectiveCourse.ByCourseName, selectiveCourses);
                setSortLabel("Назвою");
            });

            TextView btnSortByStudentCount = getView().findViewById(R.id.sortByStudentCount);

            btnSortByStudentCount.setOnClickListener(v -> {
                sort(SelectiveCourse.ByStudentCount, selectiveCourses);
                setSortLabel("К-стю записаних студентів");
            });

            Button clearBtn = getView().findViewById(R.id.clear_selectivecourses);
            clearBtn.setOnClickListener((view) -> {
                currentAdapter.clearSelected();
            });

            sortLabel = getView().findViewById(R.id.sortLabel);
            sortPanel = getView().findViewById(R.id.sortPanel);

            Button confirmBtn = getView().findViewById(R.id.confirm_selectivecourses);
            confirmBtn.setOnClickListener((view) -> {
                if (currentAdapter.getSelectedCourseFirstSemester().size() == currentAdapter.getMaxCoursesFirstSemester() && currentAdapter.getSelectedCourseSecondSemester().size() == currentAdapter.getMaxCoursesSecondSemester()) {
                    selectiveCoursesCounter.setText("Підтвердіть обрані дисципліни");
                    btnExtendedView.setVisibility(View.GONE);
                    setSortPanelVisible(View.GONE);
                    confirmBtn.setText("Підтвердити");
                    clearBtn.setText("Скасувати");

                    clearBtn.setOnClickListener((viewClear) -> {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new SelectiveCoursesFragment(availableSelectiveCourses)).commit();
                    });

                    SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                    selectiveCoursesFinal.setSelectiveCoursesFirstSemester(currentAdapter.getSelectedCourseFirstSemester());
                    selectiveCoursesFinal.setSelectiveCoursesSecondSemester(currentAdapter.getSelectedCourseSecondSemester());

                    ChdtuAdapter adapterFinal = new ChdtuAdapter(selectiveCoursesFinal, getFragmentManager(), null, false);
                    recyclerView.setAdapter(adapterFinal);
                    adapterFinal.disableCheckBoxes();

                    confirmBtn.setOnClickListener((viewConfirm) -> {
                        showLoadingProgress();

                        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();
                        confirmedSelectiveCourses.setSelectiveCourses(selectiveCoursesFinal.getSelectiveCoursesIds());
                        ExistingId existingId = new ExistingId(App.getInstance().getCurrentStudent().getDegrees()[0].getId());
                        confirmedSelectiveCourses.setStudentDegree(existingId);

                        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class).confirmedSelectiveCourses(
                                App.getInstance().getJwt().getToken(), confirmedSelectiveCourses).enqueue(new Callback<StudentDegreeSelectiveCoursesIds>() {
                            @Override
                            public void onResponse(Call<StudentDegreeSelectiveCoursesIds> call, Response<StudentDegreeSelectiveCoursesIds> response) {
                                if (response.isSuccessful()) {
                                    boolean confirmed = true;

                                    for (int i = 0; i < confirmedSelectiveCourses.getSelectiveCourses().size(); i++) {
                                        if (!confirmedSelectiveCourses.getSelectiveCourses().contains(response.body().getSelectiveCourses().get(i).getId())) {
                                            confirmed = false;
                                        }
                                    }

                                    if (confirmed) {
                                        if (getFragmentManager() != null) {
                                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectiveCoursesConfirmedFragment(selectiveCoursesFinal)).commit();
                                        }
                                    }
                                } else {
                                    try {
                                        //Log.d("Test", "" + response.errorBody().string());
                                        showError(response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                hideLoadingProgress();
                            }

                            @Override
                            public void onFailure(Call<StudentDegreeSelectiveCoursesIds> call, Throwable t) {
                                hideLoadingProgress();
                                t.printStackTrace();
                                showError("Помилка під час підключення до серверу, спробуйте пізніше.");
                            }
                        });
                    });
                } else {
                    Snackbar.make(view.findViewById(android.R.id.content), "Оберіть дисципліни", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        }
        */
    private void fillSelectiveCoursesList() {
        if (getView() == null || availableSelectiveCourses == null) return;

        recyclerView = getView().findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Init Main Control Button
        Button clearButton = getView().findViewById(R.id.clear_selectivecourses);
        Button confirmButton = getView().findViewById(R.id.confirm_selectivecourses);

        //Init Sorting Buttons
        TextView btnSortByName = getView().findViewById(R.id.sortByCourse);
        TextView btnSortByFaculty = getView().findViewById(R.id.sortByFaculty);
        TextView btnSortByStudentCount = getView().findViewById(R.id.sortByStudentCount);

        btnSortByFaculty.setOnClickListener(v -> {
            sort(SelectiveCourse.ByFacultyName, availableSelectiveCourses);
            setSortLabel(getRString(R.string.label_sort_by_faculty));
        });

        btnSortByName.setOnClickListener(v -> {
            sort(SelectiveCourse.ByCourseName, availableSelectiveCourses);
            setSortLabel(getRString(R.string.label_sort_by_name));
        });

        btnSortByStudentCount.setOnClickListener(v -> {
            sort(SelectiveCourse.ByStudentCount, availableSelectiveCourses);
            setSortLabel(getRString(R.string.label_sort_by_students_count));
        });

        sortLabel = getView().findViewById(R.id.sortLabel);
        sortPanel = getView().findViewById(R.id.sortPanel);

        SelectiveCoursesAdapter adapter = new SelectiveCoursesAdapter(availableSelectiveCourses, getFragmentManager(), selectedCoursesCounter, true);
        currentAdapter = adapter;
        selectedCoursesCounter.setSelectListener(new SelectedCoursesCounter.SelectListener() {
            @Override
            public void onOneSelected() {
                enableButton(clearButton);
            }

            @Override
            public void onAllSelected() {
                enableButton(confirmButton);
            }

            @Override
            public void onLastDeselected() {
                disableButton(clearButton);
            }

            @Override
            public void onNotAllSelected() {
                disableButton(confirmButton);
            }
        });
        recyclerView.setAdapter(adapter);

        clearButton.setOnClickListener((button) -> adapter.clearSelected());
        confirmButton.setOnClickListener((button) -> {
            if (selectedCoursesCounter.confirmIsAvailable()) {

//                selectiveCoursesCounter.setText(getRString(R.string.info_confirm_sel_courses)); TODO: add text view
                confirmButton.setText(getRString(R.string.button_confirm));
                clearButton.setText(getRString(R.string.button_cancel));
                btnExtendedView.setVisibility(View.GONE);
                setSortPanelVisible(View.GONE);

                clearButton.setOnClickListener((v) -> {
                    FragmentManager fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        SelectiveCoursesFragment selectiveCoursesFragment = new SelectiveCoursesFragment(selectedCoursesCounter, availableSelectiveCourses);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, selectiveCoursesFragment)
                                .commit();
                    }
                });

                SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
                selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourseSecondSemester());

                SelectiveCoursesAdapter adapterFinal = new SelectiveCoursesAdapter(selectiveCoursesFinal, getFragmentManager());
                recyclerView.setAdapter(adapterFinal);
                adapterFinal.disableCheckBoxes();

                confirmButton.setOnClickListener((viewConfirm) -> saveUserChoice(selectiveCoursesFinal));
            } else {
                Snackbar.make(button.findViewById(android.R.id.content), getRString(R.string.worn_select_courses), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    protected void setSortLabel(String attribute) {
        String sortedBy = getRString(R.string.info_sorted_by_attribute);
        sortedBy = sortedBy.replace("{attribute}", attribute);
        sortLabel.setText(sortedBy);
    }

    protected void setSortPanelVisible(int visibility) {
        sortLabel.setVisibility(visibility);
        sortPanel.setVisibility(visibility);
    }

    protected void sort(Comparator<SelectiveCourse> comparator, SelectiveCourses selectiveCourses) {
        Collections.sort(selectiveCourses.getSelectiveCoursesFirstSemester(), comparator);
        Collections.sort(selectiveCourses.getSelectiveCoursesSecondSemester(), comparator);
//        updateRecyclerView(selectiveCourses);
        SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected void updateRecyclerView(SelectiveCourses selectiveCourses) {
//        currentAdapter = new SelectiveCoursesAdapter(selectiveCourses, getFragmentManager(), selectedCoursesCounter, true);
//        currentAdapter.onClick(null);
//        recyclerView.setAdapter(currentAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
