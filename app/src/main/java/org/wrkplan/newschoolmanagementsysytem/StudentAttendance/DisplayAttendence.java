package org.wrkplan.newschoolmanagementsysytem.StudentAttendance;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_All_Student_Attendance;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.wrkplan.newschoolmanagementsysytem.StudentAttendance.StudentAttendance.classId;
import static org.wrkplan.newschoolmanagementsysytem.StudentAttendance.StudentAttendance.sectionId;
import static org.wrkplan.newschoolmanagementsysytem.StudentAttendance.StudentAttendance.selectdate;

public class DisplayAttendence extends AppCompatActivity {

    ImageView img_back;
    RecyclerView displayAttendance_recycler_view;
    TextView txt_group, txt_class, txt_section;
    ArrayList<Model_All_Student_Attendance> displayList = new ArrayList<>();
    DisplayAttendanceRecyclerAdapter displayAttendanceRecyclerAdapter;
    String selectedStudentID = "";


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.display_attendance_activity);

        initializeViews();
        displayAttendance_recycler_view.setHasFixedSize(true);
        displayAttendance_recycler_view.setLayoutManager(new LinearLayoutManager(this));


        selectedStudentID = getIntent().getStringExtra("selectedStudentID");
        Log.d("Nr", selectedStudentID);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (StudentAttendance.check_flag == true)
            LoadStudentAttendanceList();
        else
            selectedAttendanceList();

        txt_group.setText(StudentAttendance.group_name);
        txt_class.setText(StudentAttendance.class_name);
        txt_section.setText(StudentAttendance.section_name);
    }

    private void selectedAttendanceList()
    {
        String url = "http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=attendancelist";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if (displayList.isEmpty()) {
                        displayList.clear();
                    }
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("attendance_list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jb1 = jsonArray.getJSONObject(i);
                        String student_id = jb1.getString("student_id");
                        String student_name = jb1.getString("student_name");
                        String attendance = jb1.getString("attendance");

                        Model_All_Student_Attendance model = new Model_All_Student_Attendance();
                        model.setStudent_id(student_id);
                        model.setStudent_name(student_name);
                        model.setAttendance(attendance);

                        displayList.add(model);
                        displayAttendanceRecyclerAdapter = new DisplayAttendanceRecyclerAdapter(displayList, DisplayAttendence.this);
                        displayAttendance_recycler_view.setAdapter(displayAttendanceRecyclerAdapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DisplayAttendence.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DisplayAttendence.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("date", selectdate);
                Log.d("selectdate->", "2021-11-03");
                map.put("class_id", classId);
                map.put("section_id", sectionId);
                map.put("student_id", selectedStudentID);
                return map;
            }
        };
        Volley.newRequestQueue(DisplayAttendence.this).add(stringRequest);

    }

    private void LoadStudentAttendanceList() {
        String url = "http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=attendancelist";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if (displayList.isEmpty()) {
                        displayList.clear();
                    }
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("attendance_list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jb1 = jsonArray.getJSONObject(i);
                        String student_id = jb1.getString("student_id");
                        String student_name = jb1.getString("student_name");
                        String attendance = jb1.getString("attendance");

                        Model_All_Student_Attendance model = new Model_All_Student_Attendance();
                        model.setStudent_id(student_id);
                        model.setStudent_name(student_name);
                        model.setAttendance(attendance);

                        displayList.add(model);
                        displayAttendanceRecyclerAdapter = new DisplayAttendanceRecyclerAdapter(displayList, DisplayAttendence.this);
                        displayAttendance_recycler_view.setAdapter(displayAttendanceRecyclerAdapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DisplayAttendence.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(DisplayAttendence.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("date", selectdate);
                Log.d("selectdate->", "2021-11-03");
                map.put("class_id", classId);
                map.put("section_id", sectionId);
                map.put("student_id", selectedStudentID);
                return map;
            }
        };
        Volley.newRequestQueue(DisplayAttendence.this).add(stringRequest);
    }


    private void initializeViews() {
        img_back = findViewById(R.id.img_back);
        displayAttendance_recycler_view = findViewById(R.id.displayAttendance_recycler_view);
        txt_group = findViewById(R.id.txt_group);
        txt_class = findViewById(R.id.txt_class);
        txt_section = findViewById(R.id.txt_section);


    }
}