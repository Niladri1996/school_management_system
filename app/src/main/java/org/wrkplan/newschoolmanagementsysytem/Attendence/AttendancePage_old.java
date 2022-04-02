package org.wrkplan.newschoolmanagementsysytem.Attendence;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import org.wrkplan.newschoolmanagementsysytem.Config.Url;
import org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage;
import org.wrkplan.newschoolmanagementsysytem.Model.Student_Attendence_Model;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AttendancePage_old extends AppCompatActivity {
    EditText ed_from_date,ed_to_date;
    TextView txt_stud_name,txt_stud_class;
    RelativeLayout rl_btn_view;
    String fromdate,todate;
    ImageView img_cal1,img_cal2;
    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute;
    Date secondDate,firstDate;
    Date date=new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    RecyclerView attendence_recycler_view;

    StudentAttendenceAdapter attendenceAdapter;
    ArrayList<Student_Attendence_Model> modelArrayList=new ArrayList<>();

    ImageView img_back;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.attendence_activity);

        inisializeView();


    txt_stud_name.setText(LoginPage.pre_name+","+LoginPage.pre_class);


        img_cal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfromDatePickerDialog();
            }
        });
        img_cal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtoDatePickerDialog();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        attendence_recycler_view.setHasFixedSize(true);
        attendence_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        rl_btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStudentAttendence();
            }
        });




    }

    private void loadStudentAttendence() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=attendance";
        Log.d("url==>",url);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response=>",response);
                try {
                    if(!modelArrayList.isEmpty())
                    {
                        modelArrayList.clear();
                    }

                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Student_Attendence_Model model=new Student_Attendence_Model();

                        String date=jsonObject.getString("date");
                       String attendance = jsonObject.getString("attendance");

                        model.setDate(date);
                        model.setAttendance(attendance);

                        modelArrayList.add(model);
                        attendenceAdapter=new StudentAttendenceAdapter(modelArrayList, AttendancePage_old.this);
                        attendence_recycler_view.setAdapter(attendenceAdapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("exp==>",e.toString());
                    Toast.makeText(AttendancePage_old.this, "No value for this date", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttendancePage_old.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("userid", Url.user_id);
                Log.d("userid",Url.user_id);
                map.put("to_day",todate);
                Log.d("to=>",todate);
                map.put("from_day",fromdate);
                Log.d("from=>",fromdate);

                return map;
            }
        };
        Volley.newRequestQueue(AttendancePage_old.this).add(stringRequest);

    }

    private void showtoDatePickerDialog() {
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
                        secondDate=c.getTime();
                       // String currentDateString1 = simpleDateFormat.format(c.getTime());
                        todate = simpleDateFormat.format(c.getTime());

                        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        String inputTextFromDate = todate;

                        Date fromDate = null, toDate = null;
                        try {
                            fromDate = simpleDateFormat.parse(inputTextFromDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String outputTextFromDate = outputFormat.format(fromDate);

                        ed_to_date.setText(outputTextFromDate);
                        //fromdate=ed_from_date.getText().toString();
                        Log.d("from=>",todate);


                    }
                }, mYear, mMonth, mDay);
       // datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog1.show();
    }

    private void showfromDatePickerDialog() {
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
                        fromdate = simpleDateFormat.format(c.getTime());

                        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        String inputTextFromDate = fromdate;

                        Date fromDate = null, toDate = null;
                        try {
                            fromDate = simpleDateFormat.parse(inputTextFromDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String outputTextFromDate = outputFormat.format(fromDate);

                        ed_from_date.setText(outputTextFromDate);
                        //fromdate=ed_from_date.getText().toString();
                        Log.d("from=>",fromdate);


                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog1.show();
    }

    private void inisializeView() {

        ed_from_date=findViewById(R.id.ed_from_date);
        ed_to_date=findViewById(R.id.ed_to_date);
        rl_btn_view=findViewById(R.id.rl_btn_view);
        img_cal1=findViewById(R.id.img_cal1);
        img_cal2=findViewById(R.id.img_cal2);
        attendence_recycler_view=findViewById(R.id.attendence_recycler_view);
        txt_stud_name=findViewById(R.id.txt_stud_name);
        img_back=findViewById(R.id.img_back);
    }
}
