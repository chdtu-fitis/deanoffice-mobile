package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ExistingId;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesStudentDegree;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public class SelectiveCoursesFragment extends Fragment {

    private View view;
    private TextView selectiveCoursesCounter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selectivecourses, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Реєстрація на вибіркові дисципліни");
        this.view = view;
        selectiveCoursesCounter = view.findViewById(R.id.text_body);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMessage("Завантаження");
        progressDoalog.setProgressStyle(R.style.ProgressBar);
        progressDoalog.show();

        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class)
                .studentDegree(App.getInstance().getJwt().getToken(), App.getInstance().getCurrentStudent().getDegrees()[0].getId()).enqueue(new Callback<SelectiveCoursesStudentDegree>() {
            @Override
            public void onResponse(Call<SelectiveCoursesStudentDegree> call, Response<SelectiveCoursesStudentDegree> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSelectiveCourses() != null) {
                        List<SelectiveCourse> selectiveCoursesFirst = new ArrayList<SelectiveCourse>();
                        List<SelectiveCourse> selectiveCoursesSecond = new ArrayList<SelectiveCourse>();

                        for (SelectiveCourse course : response.body().getSelectiveCourses()) {
                            course.selected = true;
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
    }

    public void RequestAvailableSelectiveCoursesList() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Завантаження");
        progressDialog.setProgressStyle(R.style.ProgressBar);
        progressDialog.show();
        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class)
                .requestSelectiveCourses(App.getInstance().getJwt().getToken(),
                        App.getInstance().getCurrentStudent().getDegrees()[0].getId()).enqueue(new Callback<SelectiveCourses>() {
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
        getActivity().runOnUiThread(() -> {
            RecyclerView recyclerView = view.findViewById(R.id.listview);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            boolean isMagister = App.getInstance().getCurrentStudent().getDegrees()[0].getSpecialization().getDegree().getId() == 3;

            ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getFragmentManager(), selectiveCoursesCounter, true, isMagister);
            recyclerView.setAdapter(adapter);

            Button clearBtn = view.findViewById(R.id.clear_selectivecourses);
            clearBtn.setOnClickListener((view) -> {
                adapter.clearSelected();
            });

            Button confirmBtn = view.findViewById(R.id.confirm_selectivecourses);
            confirmBtn.setOnClickListener((view) -> {
                if (adapter.getSelectedCourseFirstSemester().size() == adapter.getMaxCoursesFirstSemester() && adapter.getSelectiveCourseSecondSemester().size() == adapter.getMaxCoursesSecondSemester()) {
                    selectiveCoursesCounter.setText("Підтвердіть обрані дисципліни");
                    confirmBtn.setText("Підтвердити");
                    clearBtn.setText("Скасувати");

                    clearBtn.setOnClickListener((viewClear) -> {
                        Intent intent = new Intent(getContext(), SelectiveCoursesActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });

                    SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                    selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
                    selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectiveCourseSecondSemester());

                    ChdtuAdapter adapterFinal = new ChdtuAdapter(selectiveCoursesFinal, getFragmentManager(), null, false);
                    recyclerView.setAdapter(adapterFinal);
                    adapterFinal.disableCheckBoxes();

                    confirmBtn.setOnClickListener((viewConfirm) -> {
                        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();
                        confirmedSelectiveCourses.setSelectiveCourses(selectiveCoursesFinal.getSelectiveCoursesIds());
                        ExistingId existingId = new ExistingId(App.getInstance().getCurrentStudent().getDegrees()[0].getId());
                        confirmedSelectiveCourses.setStudentDegreeId(existingId);

                        final ProgressDialog progressDoalog;
                        progressDoalog = new ProgressDialog(getContext());
                        progressDoalog.setMessage("Завантаження");
                        progressDoalog.setProgressStyle(R.style.ProgressBar);
                        progressDoalog.show();

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
                                        error(response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                progressDoalog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<StudentDegreeSelectiveCoursesIds> call, Throwable t) {
                                progressDoalog.dismiss();
                                t.printStackTrace();
                                error("Помилка під час підключення до серверу, спробуйте пізніше.");
                            }
                        });

                    });

                } else {
                    Snackbar.make(view.findViewById(android.R.id.content), "Оберіть дисципліни", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        });
    }

    public void error(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_selectivecourse_info, viewGroup, false);
        ((TextView) dialogView.findViewById(R.id.selectiveCourseName)).setText("Помилка");
        ((TextView) dialogView.findViewById(R.id.selectiveCourseDescription)).setText(text);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ((Button) dialogView.findViewById(R.id.buttonOk)).setOnClickListener((viewOk) -> {
            alertDialog.dismiss();
        });
    }
}
