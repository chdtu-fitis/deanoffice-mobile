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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.activities.views.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.course.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.student.SelectiveCourses;

public class SelectiveCoursesTestActivity extends AppCompatActivity {

    String json = "{\n" +
            "    \"selectiveCoursesFirstSemester\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 23,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 7,\n" +
            "                    \"name\": \"Web-дизайн\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 144,\n" +
            "                \"credits\": 4.0,\n" +
            "                \"hoursPerCredit\": 36\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 1,\n" +
            "                \"surname\": \"Первунінський\",\n" +
            "                \"name\": \"Станіслав\",\n" +
            "                \"patronimic\": \"Михайлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра програмного забезпечення автоматизованих систем\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"професор\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 29,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 7,\n" +
            "                    \"name\": \"Web-дизайн\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 180,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 36\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 3,\n" +
            "                \"surname\": \"Лукашенко\",\n" +
            "                \"name\": \"Валентина\",\n" +
            "                \"patronimic\": \"Максимівна\",\n" +
            "                \"sex\": \"FEMALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"професор\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 3,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 51,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 12,\n" +
            "                    \"name\": \"Web-технології та Web-дизайн\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 180,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 36\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 3,\n" +
            "                \"surname\": \"Лукашенко\",\n" +
            "                \"name\": \"Валентина\",\n" +
            "                \"patronimic\": \"Максимівна\",\n" +
            "                \"sex\": \"FEMALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"професор\"\n" +
            "                },\n" +
            "\"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 4,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 60,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 16,\n" +
            "                    \"name\": \"Автоматизація бухобліку і аудиту\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 135,\n" +
            "                \"credits\": 4.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 4,\n" +
            "                \"surname\": \"Мусієнко\",\n" +
            "                \"name\": \"Максим\",\n" +
            "                \"patronimic\": \"Павлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"доцент\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 5,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 62,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 17,\n" +
            "                    \"name\": \"Автоматизація економічних систем підприємств\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 162,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 5,\n" +
            "                \"surname\": \"Підгорний\",\n" +
            "                \"name\": \"Олег\",\n" +
            "                \"patronimic\": \"Віталійович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 6,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 63,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 17,\n" +
            "                    \"name\": \"Автоматизація економічних систем підприємств\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 54,\n" +
            "                \"credits\": 1.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "\"id\": 6,\n" +
            "                \"surname\": \"Бойко\",\n" +
            "                \"name\": \"Тетяна\",\n" +
            "                \"patronimic\": \"Анатоліївна\",\n" +
            "                \"sex\": \"FEMALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 7,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 64,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 18,\n" +
            "                    \"name\": \"Автоматизація економічних систем підприємства\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 162,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 7,\n" +
            "                \"surname\": \"Тичков\",\n" +
            "                \"name\": \"Володимир\",\n" +
            "                \"patronimic\": \"Володимирович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 8,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 71,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 19,\n" +
            "                    \"name\": \"Автоматизація експериментальних досліджень\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 162,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 5,\n" +
            "                \"surname\": \"Підгорний\",\n" +
            "                \"name\": \"Олег\",\n" +
            "                \"patronimic\": \"Віталійович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 9,\n" +
            "\"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 72,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 19,\n" +
            "                    \"name\": \"Автоматизація експериментальних досліджень\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"курсова робота\"\n" +
            "                },\n" +
            "                \"hours\": 90,\n" +
            "                \"credits\": 3.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 6,\n" +
            "                \"surname\": \"Бойко\",\n" +
            "                \"name\": \"Тетяна\",\n" +
            "                \"patronimic\": \"Анатоліївна\",\n" +
            "                \"sex\": \"FEMALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 10,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 107,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 24,\n" +
            "                    \"name\": \"Автоматизовані економічні системи\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 7,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 162,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 4,\n" +
            "                \"surname\": \"Мусієнко\",\n" +
            "                \"name\": \"Максим\",\n" +
            "                \"patronimic\": \"Павлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"доцент\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        }\n" +
            "    ],\n" +
            "    \"selectiveCoursesSecondSemester\": [\n" +
            "        {\n" +
            "            \"id\": 11,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 14,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 4,\n" +
            "                    \"name\": \"CASE-технології\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 108,\n" +
            "                \"credits\": 3.0,\n" +
            "                \"hoursPerCredit\": 36\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 4,\n" +
            "                \"surname\": \"Мусієнко\",\n" +
            "                \"name\": \"Максим\",\n" +
            "                \"patronimic\": \"Павлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"доцент\"\n" +
            "                },\n" +
            "\"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 12,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 55,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 13,\n" +
            "                    \"name\": \"Аватоматизація бухгалтерського обліку і аудиту\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 162,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 6,\n" +
            "                \"surname\": \"Бойко\",\n" +
            "                \"name\": \"Тетяна\",\n" +
            "                \"patronimic\": \"Анатоліївна\",\n" +
            "                \"sex\": \"FEMALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 13,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 56,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 13,\n" +
            "                    \"name\": \"Аватоматизація бухгалтерського обліку і аудиту\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 135,\n" +
            "                \"credits\": 4.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 1,\n" +
            "                \"surname\": \"Первунінський\",\n" +
            "                \"name\": \"Станіслав\",\n" +
            "                \"patronimic\": \"Михайлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра програмного забезпечення автоматизованих систем\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"професор\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 14,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 57,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 14,\n" +
            "                    \"name\": \"Аватоматизація бухобліку і аудиту\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 135,\n" +
            "                \"credits\": 4.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "\"id\": 2,\n" +
            "                \"surname\": \"Шарапов\",\n" +
            "                \"name\": \"Валерій\",\n" +
            "                \"patronimic\": \"Михайлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"професор\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 15,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 65,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 18,\n" +
            "                    \"name\": \"Автоматизація економічних систем підприємства\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 162,\n" +
            "                \"credits\": 5.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 3,\n" +
            "                \"surname\": \"Лукашенко\",\n" +
            "                \"name\": \"Валентина\",\n" +
            "                \"patronimic\": \"Максимівна\",\n" +
            "                \"sex\": \"FEMALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"професор\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 16,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 66,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 18,\n" +
            "                    \"name\": \"Автоматизація економічних систем підприємства\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"залік\"\n" +
            "                },\n" +
            "                \"hours\": 135,\n" +
            "                \"credits\": 4.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 4,\n" +
            "                \"surname\": \"Мусієнко\",\n" +
            "                \"name\": \"Максим\",\n" +
            "                \"patronimic\": \"Павлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"доцент\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 17,\n" +
            "            \"available\": true,\n" +
            "\"course\": {\n" +
            "                \"id\": 70,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 19,\n" +
            "                    \"name\": \"Автоматизація експериментальних досліджень\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 108,\n" +
            "                \"credits\": 3.0,\n" +
            "                \"hoursPerCredit\": 36\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 5,\n" +
            "                \"surname\": \"Підгорний\",\n" +
            "                \"name\": \"Олег\",\n" +
            "                \"patronimic\": \"Віталійович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 18,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 85,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 20,\n" +
            "                    \"name\": \"Автоматизація проектування комп'ютерних систем\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"курсовий проект\"\n" +
            "                },\n" +
            "                \"hours\": 0,\n" +
            "                \"credits\": 0.0,\n" +
            "                \"hoursPerCredit\": 36\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 9,\n" +
            "                \"surname\": \"Кунченко\",\n" +
            "                \"name\": \"Юрій\",\n" +
            "                \"patronimic\": \"Петрович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра радіотехніки\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"професор\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 19,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 86,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 20,\n" +
            "                    \"name\": \"Автоматизація проектування комп'ютерних систем\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"іспит\"\n" +
            "                },\n" +
            "                \"hours\": 135,\n" +
            "                \"credits\": 4.0,\n" +
            "                \"hoursPerCredit\": 30\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 4,\n" +
            "                \"surname\": \"Мусієнко\",\n" +
            "                \"name\": \"Максим\",\n" +
            "                \"patronimic\": \"Павлович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"доцент\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "\"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 20,\n" +
            "            \"available\": true,\n" +
            "            \"course\": {\n" +
            "                \"id\": 89,\n" +
            "                \"courseName\": {\n" +
            "                    \"id\": 20,\n" +
            "                    \"name\": \"Автоматизація проектування комп'ютерних систем\",\n" +
            "                    \"nameEng\": \"\"\n" +
            "                },\n" +
            "                \"semester\": 8,\n" +
            "                \"knowledgeControl\": {\n" +
            "                    \"name\": \"курсова робота\"\n" +
            "                },\n" +
            "                \"hours\": 0,\n" +
            "                \"credits\": 0.0,\n" +
            "                \"hoursPerCredit\": 36\n" +
            "            },\n" +
            "            \"teacher\": {\n" +
            "                \"id\": 5,\n" +
            "                \"surname\": \"Підгорний\",\n" +
            "                \"name\": \"Олег\",\n" +
            "                \"patronimic\": \"Віталійович\",\n" +
            "                \"sex\": \"MALE\",\n" +
            "                \"active\": true,\n" +
            "                \"academicTitle\": null,\n" +
            "                \"department\": {\n" +
            "                    \"name\": \"кафедра комп'ютеризованих та інформаційних технологій в приладобудуванні\"\n" +
            "                },\n" +
            "                \"position\": {\n" +
            "                    \"name\": \"старший викладач\"\n" +
            "                },\n" +
            "                \"scientificDegree\": null\n" +
            "            },\n" +
            "            \"degree\": {\n" +
            "                \"name\": \"Бакалавр\"\n" +
            "            },\n" +
            "            \"department\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"кафедра комп'ютерних систем\",\n" +
            "                \"active\": true,\n" +
            "                \"abbr\": \"КС\"\n" +
            "            },\n" +
            "            \"fieldsOfKnowledge\": null,\n" +
            "            \"trainingCycle\": \"PROFESSIONAL\",\n" +
            "            \"description\": \"Опис\",\n" +
            "            \"studyYear\": 2021\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    TextView selectiveCoursesCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_courses_test);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.selectivecourses_actionbar);

        selectiveCoursesCounter = findViewById(R.id.text_body);

        /*Mobile.getInstance().getClient().getSelectiveCourses(new Client.OnResponseCallback() {
            @Override
            public void onResponseSuccess(ResponseBody response) {
                onResponse(response);
            }

            @Override
            public void onResponseFailure(ResponseBody response) {
                Log.d("Test", "Error");
            }
        });*/
        Draw();
    }


    public void onResponse(ResponseBody response) {
        runOnUiThread(() -> {
            try {
                Log.d("Test", response.string());
                SelectiveCourses selectiveCourses = new Gson().fromJson(json, SelectiveCourses.class);

                RecyclerView recyclerView = findViewById(R.id.listview);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);

                ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getSupportFragmentManager(), selectiveCoursesCounter);
                recyclerView.setAdapter(adapter);



                Button btn = findViewById(R.id.confirm_selectivecourses);
                btn.setOnClickListener((view) -> {


                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void Draw() {
        SelectiveCourses selectiveCourses = new Gson().fromJson(json, SelectiveCourses.class);

        RecyclerView recyclerView = findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getSupportFragmentManager(), selectiveCoursesCounter);

        recyclerView.setAdapter(adapter);

        Button btn_confirm = findViewById(R.id.confirm_selectivecourses);
        btn_confirm.setOnClickListener((view) -> {

        });

        Button btn_clear = findViewById(R.id.clear_selectivecourses);
        btn_clear.setOnClickListener((view) -> {
            adapter.clearSelected();
        });
    }
}
