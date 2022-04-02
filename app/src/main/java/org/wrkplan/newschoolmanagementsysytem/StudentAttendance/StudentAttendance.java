package org.wrkplan.newschoolmanagementsysytem.StudentAttendance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import org.wrkplan.newschoolmanagementsysytem.Model.Model_ClassList;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_GroupList;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_SectionList;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudentAttendance extends AppCompatActivity {
    public  static String selectedStudentID;
    ImageView img_back;
    TextView txt_view;
    private ArrayList<String> grouplist = new ArrayList<String>();
    private ArrayList<String> classlist = new ArrayList<String>();
    private ArrayList<String> sectionlist = new ArrayList<String>();

    ArrayList<Model_All_Student_Attendance>attendanceArrayList=new ArrayList<>();
    Attendance_All_Recycler_Adapter allRecyclerAdapter;

    ArrayList<Model_GroupList> groupListArrayList=new ArrayList<>();
    ArrayList<Model_ClassList> classListArrayList=new ArrayList<>();
    ArrayList<Model_SectionList> sectionListArrayList=new ArrayList<>();
    public static String groupId,classId,sectionId;
    public static String selectdate;
    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute;
    Date firstDate;
    RecyclerView studentname_recycler_view;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //for popup

    ImageView img_close,img_cal;
    Spinner spinner_group,spinner_class,spinner_section,spinner_students;
    RadioGroup radio_group;
    RelativeLayout btn_cancel,btn_view,rl_view;
    EditText ed_date,ed_search;
    public static boolean check_flag = true;
    CheckBox check_studs;
    boolean is_Select_All = true;
    public static String group_name,class_name,section_name;

    //popup end
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.student_attendance_activity);

        InitializeViews();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        studentname_recycler_view.setHasFixedSize(true);
        studentname_recycler_view.setLayoutManager(new LinearLayoutManager(this));

//        tv_view_option.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openPopup();
//            }
//        });
btn_view.setEnabled(false);
        setAdapter();

        allStudentAttendence();


        txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoadStudentList();
                //  Toast.makeText(StudentAttendance.this, "LOAD VIEWS", Toast.LENGTH_SHORT).show();
            }
        });


        check_studs.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            check_flag = isChecked;
            btn_view.setEnabled(isChecked);
            is_Select_All = isChecked;

            for(int i=0; i<attendanceArrayList.size(); i++)
            {
                attendanceArrayList.get(i).setChecked(isChecked);
            }
            allRecyclerAdapter.refreshlist(attendanceArrayList);
        });


        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedStudentID = "";

                if (!check_flag)
                {
                    for (int i=0; i<attendanceArrayList.size(); i++)
                    {
                        if (attendanceArrayList.get(i).isChecked())
                        {
                            selectedStudentID += attendanceArrayList.get(i).getStudent_id() + ",";
                        }
                    }
                    Log.d("KP", selectedStudentID);



                    if (selectedStudentID.length() > 0) {
                        selectedStudentID = selectedStudentID.substring(0, selectedStudentID.length() - 1);

                        Intent intent = new Intent(StudentAttendance.this, DisplayAttendence.class);
                        intent.putExtra("selectedStudentID", selectedStudentID);
                        startActivity(intent);
                    }



                } else {
                    selectedStudentID = "all";
                    Intent intent = new Intent(StudentAttendance.this, DisplayAttendence.class);
                    intent.putExtra("selectedStudentID", selectedStudentID);
                    startActivity(intent);
                }
            }
        });
    }

    private void setAdapter()
    {
        allRecyclerAdapter = new Attendance_All_Recycler_Adapter(attendanceArrayList, StudentAttendance.this, (pos, flag) ->
        {
            attendanceArrayList.get(pos).setChecked(flag);
            btn_view.setEnabled(true);
            check_flag = false;
        });

        studentname_recycler_view.setAdapter(allRecyclerAdapter);
    }

    private void allStudentAttendence() {
        LoadGroupList();
        spinner_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0)
                {
                    groupId=groupListArrayList.get(position-1).getGroup_id();
                    group_name=groupListArrayList.get(position-1).getGroup_name();
                    Toast.makeText(StudentAttendance.this, "group id= "+groupId, Toast.LENGTH_SHORT).show();
                    // call classlist
                    LoadClassList();

                    spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if(position!=0)
                            {
                                classId=classListArrayList.get(position-1).getClass_id();
                                class_name=classListArrayList.get(position-1).getClass_name();
                                Toast.makeText(StudentAttendance.this, "class id= "+classId, Toast.LENGTH_SHORT).show();
                                // call sectionlist
                                LoadSectionList();
                                spinner_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if(position!=0)
                                        {
                                            sectionId=sectionListArrayList.get(position-1).getSection_id();
                                            section_name=sectionListArrayList.get(position-1).getSection_name();
                                            Toast.makeText(StudentAttendance.this, "section id= "+sectionId, Toast.LENGTH_SHORT).show();
                                            img_cal.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    selectDate();

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void filter(String text) {
        ArrayList<Model_All_Student_Attendance> filterlist=new ArrayList<>();
        for(Model_All_Student_Attendance item:attendanceArrayList)
        {
            if(item.getStudent_name().toLowerCase().contains(text.toLowerCase()))
            {
                filterlist.add(item);
            }

        }

        allRecyclerAdapter.filltered(filterlist);
    }

    private void LoadStudentList() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=attendancelist";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    attendanceArrayList.clear();


                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("attendance_list");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        String student_id=jb1.getString("student_id");
                        String student_name=jb1.getString("student_name");
                        String attendance=jb1.getString("attendance");

                        Model_All_Student_Attendance model=new Model_All_Student_Attendance();
                        model.setStudent_id(student_id);
                        model.setStudent_name(student_name);
                        model.setAttendance(attendance);

                        attendanceArrayList.add(model);
                        allRecyclerAdapter.refreshlist(attendanceArrayList);
                    }
                    btn_view.setEnabled(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentAttendance.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(StudentAttendance.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("date",selectdate);
                Log.d("selectdate->","2021-11-03");
                map.put("class_id", classId);
                map.put("section_id", sectionId);
                map.put("student_id", "all");
                return map;
            }
        };
        Volley.newRequestQueue(StudentAttendance .this).add(stringRequest);
    }

    private void selectDate() {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        firstDate=c.getTime();
                        selectdate = simpleDateFormat.format(c.getTime());
                        LoadStudentList();

                        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        String inputTextFromDate = selectdate;

                        Date fromDate = null, toDate = null;
                        try {
                            fromDate = simpleDateFormat.parse(inputTextFromDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String outputTextFromDate = outputFormat.format(fromDate);

                        ed_date.setText(outputTextFromDate);
                        // rl_view.setVisibility(View.VISIBLE);
                        //fromdate=ed_from_date.getText().toString();
                        Log.d("from=>",selectdate);



                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog1.show();
    }

    private void LoadSectionList() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=sectionlist";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(!sectionListArrayList.isEmpty())

                    {
                        sectionListArrayList.clear();
                        sectionlist.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("sections");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        Model_SectionList model_sectionList=new Model_SectionList();

                        model_sectionList.setSection_id(jb1.getString("section_id"));
                        model_sectionList.setSection_name(jb1.getString("section_name"));


                        sectionListArrayList.add(model_sectionList);
                    }

                    sectionlist.add(0," Select Section");
                    for (int i=0;i<sectionListArrayList.size();i++)
                    {
                        sectionlist.add((i+1), sectionListArrayList.get(i).getSection_name());

                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentAttendance.this, android.R.layout.simple_spinner_item, sectionlist)
                    {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position==0)
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView textview = (TextView) view;
                            if (position == 0) {
                                textview.setTextColor(Color.GRAY);
                            } else {
                                textview.setTextColor(Color.BLACK);
                            }
                            return view;
                        }


                    };
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentAttendance.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(StudentAttendance.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("class_id", classId);
                return map;
            }
        };
        Volley.newRequestQueue(StudentAttendance .this).add(stringRequest);

    }

    private void LoadClassList() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=classlist";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if(!classListArrayList.isEmpty()) {
                        classListArrayList.clear();
                        classlist.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    String group_name=jsonObject.getString("group_name");
                    String group_id=jsonObject.getString("group_id");

                    JSONArray jsonArray=jsonObject.getJSONArray("classes");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        Model_ClassList model_classList=new Model_ClassList();
                        model_classList.setClass_id(jb1.getString("class_id"));
                        model_classList.setClass_name(jb1.getString("class_name"));

                        classListArrayList.add(model_classList);
                    }

                    classlist.add(0," Select Class");
                    for (int i=0;i<classListArrayList.size();i++)
                    {
                        classlist.add((i+1), classListArrayList.get(i).getClass_name());

                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentAttendance.this, android.R.layout.simple_spinner_item, classlist)
                    {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position==0)
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView textview = (TextView) view;
                            if (position == 0) {
                                textview.setTextColor(Color.GRAY);
                            } else {
                                textview.setTextColor(Color.BLACK);
                            }
                            return view;
                        }


                    };
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_class.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentAttendance.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentAttendance.this, "could not connect to the server", Toast.LENGTH_SHORT).show();

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("group_id", groupId);


                return map;
            }
        };
        Volley.newRequestQueue(StudentAttendance .this).add(stringRequest);
    }



    private void LoadGroupList() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=grouplist";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if(!groupListArrayList.isEmpty())
                    {
                        groupListArrayList.clear();
                        grouplist.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("groups");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        Model_GroupList model_groupList=new Model_GroupList();
                        model_groupList.setGroup_id(jb1.getString("group_id"));
                        model_groupList.setGroup_name(jb1.getString("group_name"));

                        groupListArrayList.add(model_groupList);

                    }
                    grouplist.add(0," Select Group");
                    for (int i=0;i<groupListArrayList.size();i++)
                    {
                        grouplist.add((i+1), groupListArrayList.get(i).getGroup_name());

                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentAttendance.this, android.R.layout.simple_spinner_item, grouplist)
                    {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position==0)
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView textview = (TextView) view;
                            if (position == 0) {
                                textview.setTextColor(Color.GRAY);
                            } else {
                                textview.setTextColor(Color.BLACK);
                            }
                            return view;
                        }


                    };
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_group.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentAttendance.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentAttendance.this, "Could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(StudentAttendance.this).add(stringRequest);
    }


    private void InitializeViews() {
        img_back=findViewById(R.id.img_back);
        // tv_view_option=findViewById(R.id.tv_view_option);
        img_cal=findViewById(R.id.img_cal);
        img_close=findViewById(R.id.img_close);
        spinner_group=findViewById(R.id.spinner_group);
        spinner_class=findViewById(R.id.spinner_class);
        spinner_section=findViewById(R.id.spinner_section);
        spinner_students=findViewById(R.id.spinner_students);
        radio_group=findViewById(R.id.radio_group);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_view=findViewById(R.id.btn_view);
        ed_date=findViewById(R.id.ed_date);
        studentname_recycler_view=findViewById(R.id.studentname_recycler_view);
        rl_view=findViewById(R.id.rl_view);
        txt_view=findViewById(R.id.txt_view);
        ed_search=findViewById(R.id.ed_search);
        check_studs=findViewById(R.id.check_studs);

    }
}