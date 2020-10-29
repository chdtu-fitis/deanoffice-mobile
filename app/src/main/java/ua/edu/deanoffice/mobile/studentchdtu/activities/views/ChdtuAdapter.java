package ua.edu.deanoffice.mobile.studentchdtu.activities.views;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.activities.fragments.YearSelectiveCourseFragment;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.course.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.student.SelectiveCourses;

public class ChdtuAdapter extends RecyclerView.Adapter<ChdtuAdapter.ViewHolder> implements View.OnClickListener {

    private SelectiveCourses selectiveCourses;
    private FragmentManager fragmentManager;

    public ChdtuAdapter(SelectiveCourses selectiveCourses, FragmentManager supportFragmentManager) {
        this.selectiveCourses = selectiveCourses;
        this.fragmentManager = supportFragmentManager;
    }

    @NonNull
    @Override
    public ChdtuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.chdtu_list_selectivecourse, null);

        // create ViewHolder
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final int pos = position;

        for (SelectiveCourse course : selectiveCourses.getSelectiveCoursesSemester(pos+1)) {
            YearSelectiveCourseFragment fragment = new YearSelectiveCourseFragment(course, R.layout.yearselectivecourse_fragment);
            viewHolder.semeterNumber.setText((pos+1) + " семестр");
            fragmentManager.beginTransaction().add(viewHolder.id, fragment).commit();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        int id;
        TextView semeterNumber;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            id = R.id.containterCourses;
            semeterNumber = itemLayoutView.findViewById(R.id.semesterNumber);
            container = itemLayoutView.findViewById(R.id.containterCourses);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItemId(position);
        String selectiveCourse = (String) object;

        switch (v.getId()) {
            case R.id.selectivecourseinfo:
                Snackbar.make(v, "Release date " + selectiveCourse, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String selectiveCourse = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.chdtu_list_selectivecourse, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.selectivecoursename);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.selectivecheckbox);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.selectivecourseinfo);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.name.setText(selectiveCourse);
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }*/
}
