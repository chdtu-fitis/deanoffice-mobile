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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.activity.LoginActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainMenuActivity;

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
        this.view = view;
        selectiveCoursesCounter = view.findViewById(R.id.text_body);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMessage("Завантаження");
        progressDoalog.setProgressStyle(R.style.ProgressBar);
        progressDoalog.show();

        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class)
                .requestSelectiveCourses(App.getInstance().getJwt().getToken(),
                        App.getInstance().getCurrentStudent().getDegrees()[0].getId()).enqueue(new Callback<SelectiveCourses>() {
            @Override
            public void onResponse(Call<SelectiveCourses> call, Response<SelectiveCourses> response) {
                if (response.isSuccessful()) {
                    SelectiveCoursesFragment.this.onResponse(response.body());
                }
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<SelectiveCourses> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    public void onResponse(SelectiveCourses selectiveCourses) {
        getActivity().runOnUiThread(() -> {
            RecyclerView recyclerView = view.findViewById(R.id.listview);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            recyclerView.setLayoutManager(layoutManager);

            ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getFragmentManager(), selectiveCoursesCounter, true);

            recyclerView.setAdapter(adapter);

            Button clearBtn = view.findViewById(R.id.clear_selectivecourses);
            clearBtn.setOnClickListener((view) -> {
                adapter.clearSelected();
            });

            Button confirmBtn = view.findViewById(R.id.confirm_selectivecourses);
            confirmBtn.setOnClickListener((view) -> {
                if (adapter.getSelectedCourseFirstSemester().size() == 3 && adapter.getSelectiveCourseSecondSemester().size() == 2) {
                    selectiveCoursesCounter.setText("Підтвердіть вибрані дисципліни");
                    confirmBtn.setText("Підтвердити");
                    clearBtn.setText("Скасувати");

                    confirmBtn.setOnClickListener((viewConfirm) -> {
                        Intent intent = new Intent(getContext(), MainMenuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });

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
                } else {
                    Snackbar.make(view.findViewById(android.R.id.content), "Оберіть дисципліни", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        });
    }
}
