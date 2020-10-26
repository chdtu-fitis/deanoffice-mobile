package ua.edu.deanoffice.mobile.studentchdtu.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.course.SelectiveCourse;

public class ChdtuAdapter extends ArrayAdapter<String> implements View.OnClickListener {

    private ArrayList<String> dataSet;
    Context mContext;

    private int lastPosition = -1;

    private static class ViewHolder {
        TextView name;
        CheckBox checkBox;
        ImageView info;
    }

    public ChdtuAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.chdtu_list_selectivecourse, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        String selectiveCourse =( String)object;

        switch (v.getId())
        {
            case R.id.selectivecourseinfo:
                Snackbar.make(v, "Release date " + selectiveCourse, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    @Override
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
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.name.setText(selectiveCourse);
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
