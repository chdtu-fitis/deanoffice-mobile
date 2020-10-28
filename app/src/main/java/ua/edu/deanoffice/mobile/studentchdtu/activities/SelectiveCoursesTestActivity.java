package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.activities.views.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.course.SelectiveCourse;

public class SelectiveCoursesTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_courses_test);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.selectivecourses_actionbar);

        String json = "    {\n" +
                "        \"id\": 1,\n" +
                "        \"available\": true,\n" +
                "        \"course\": {\n" +
                "            \"id\": 1,\n" +
                "            \"courseName\": {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"CALS-технології в системах автоматизованого проектування\",\n" +
                "                \"nameEng\": \"CALS-technology in computer-aided design\"\n" +
                "            },\n" +
                "            \"semester\": 2,\n" +
                "            \"knowledgeControl\": {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"залік\"\n" +
                "            },\n" +
                "            \"hours\": 144,\n" +
                "            \"credits\": 4.0,\n" +
                "            \"hoursPerCredit\": 36\n" +
                "        },\n" +
                "        \"teacher\": {\n" +
                "            \"id\": 1,\n" +
                "            \"surname\": \"Первунінський\",\n" +
                "            \"name\": \"Станіслав\",\n" +
                "            \"patronimic\": \"Михайлович\",\n" +
                "            \"sex\": \"MALE\",\n" +
                "            \"active\": true,\n" +
                "            \"academicTitle\": null,\n" +
                "            \"department\": {\n" +
                "                \"id\": 4,\n" +
                "                \"name\": \"кафедра програмного забезпечення автоматизованих систем\"\n" +
                "            },\n" +
                "            \"position\": {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"професор\"\n" +
                "            },\n" +
                "            \"scientificDegree\": null\n" +
                "        },\n" +
                "        \"degree\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Бакалавр\"\n" +
                "        },\n" +
                "        \"department\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"кафедра комп'ютерних систем\",\n" +
                "            \"active\": true,\n" +
                "            \"abbr\": \"КС\"\n" +
                "        },\n" +
                "        \"fieldsOfKnowledge\": null,\n" +
                "        \"trainingCycle\": \"PROFESSIONAL\",\n" +
                "        \"description\": \"\",\n" +
                "        \"studyYear\": 2020\n" +
                "    }";

        List<SelectiveCourse> selectiveCourseList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            selectiveCourseList.add(new Gson().fromJson(json, SelectiveCourse.class));
            Log.d("Test", selectiveCourseList.get(i).getCourse().getCourseName().getName());
        }

        RecyclerView recyclerView = findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourseList);
        recyclerView.setAdapter(adapter);

        Button btn = findViewById(R.id.confirm_selectivecourses);
        btn.setOnClickListener((view)->{
            List<SelectiveCourse> selectiveCourseListFinal = new ArrayList<>();

            for (SelectiveCourse course : selectiveCourseList) {
                if(course.selected) {
                    selectiveCourseListFinal.add(course);
                }
            }

            if(selectiveCourseListFinal.size() < 5){
                Toast.makeText(
                        this,
                        "Оберіть 5 дисциплін", Toast.LENGTH_LONG).show();
                return;
            }
            ChdtuAdapter newAdapter = new ChdtuAdapter(selectiveCourseListFinal);
            recyclerView.setAdapter(newAdapter);
            TextView textView = findViewById(R.id.text_body);
            textView.setText("Підтвердіть обрані предмети");
            btn.setText("Підтвердити");
        });
    }
}
