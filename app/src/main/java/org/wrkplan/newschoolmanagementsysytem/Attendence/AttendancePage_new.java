package org.wrkplan.newschoolmanagementsysytem.Attendence;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrkplan.newschoolmanagementsysytem.Config.Url;
import org.wrkplan.newschoolmanagementsysytem.Dashboard.Dashboard;
import org.wrkplan.newschoolmanagementsysytem.Model.Student_Attendence_Model;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hirondelle.date4j.DateTime;

public class AttendancePage_new extends AppCompatActivity {
    ImageView img_back;
    RelativeLayout rl_cal;

    StudentAttendenceAdapter attendenceAdapter;
    ArrayList<Student_Attendence_Model> modelArrayList=new ArrayList<>();
    //---------------Caldroid function name-----------------------//
    public final static String
            DIALOG_TITLE = "dialogTitle",
            MONTH = "month",
            YEAR = "year",
            SHOW_NAVIGATION_ARROWS = "showNavigationArrows",
            DISABLE_DATES = "disableDates",
            SELECTED_DATES = "selectedDates",
            MIN_DATE = "minDate",
            MAX_DATE = "maxDate",
            ENABLE_SWIPE = "enableSwipe",
            START_DAY_OF_WEEK = "startDayOfWeek",
            SIX_WEEKS_IN_CALENDAR = "sixWeeksInCalendar",
            ENABLE_CLICK_ON_DISABLED_DATES = "enableClickOnDisabledDates",
            SQUARE_TEXT_VIEW_CELL = "squareTextViewCell",
            THEME_RESOURCE = "themeResource";
    //-------------end caldroid function name---------------------//

    CaldroidFragment caldroidFragment;
    private FragmentTransaction t;
    private String dateString;
    private String attendance;
    RelativeLayout rlcall;
    private String startDateStr,endDateStr;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AttendancePage_new.this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setContentView(R.layout.attendance_new_activity);
        img_back=findViewById(R.id.img_back);
        rl_cal=findViewById(R.id.rl_cal);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDateStr = df.format(monthFirstDay);
        endDateStr = df.format(monthLastDay);

        Log.d("onload=>",startDateStr+ " ,"+endDateStr);




        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendancePage_new.this, Dashboard.class);

                startActivity(intent);
            }
        });

        openCaldroidCalendar();


    }

    private void openCaldroidCalendar() {
        //  Date date=new Date();
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();

        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
        caldroidFragment.setArguments(args);
        loadStudentAttendance();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final CaldroidListener listener=new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Log.d("date==", date.toString());
                SimpleDateFormat inputformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                try {
                    Date d1 = inputformat.parse(date.toString());
                    Log.d("niladri=>", d1.toString());
                    String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(d1);
                    Log.d("DraftDate1-=>", formateDate);

                    for(int i=0;i<modelArrayList.size();i++)
                    {
                        if(formateDate.contentEquals(modelArrayList.get(i).getDate()))
                        {

                            //  Toast.makeText(AttendancePage_new.this, ""+modelArrayList.get(i).getAttendance(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
                catch (Exception e)
                {
                    Toast.makeText(AttendancePage_new.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                super.onLongClickDate(date, view);
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
//                Toast.makeText(getApplicationContext(), text,
//                        Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR,year);

                calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                Date monthFirstDay = calendar.getTime();
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date monthLastDay = calendar.getTime();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                startDateStr = df.format(monthFirstDay);
                endDateStr = df.format(monthLastDay);
                loadStudentAttendance();

                Log.e("DateFirstLast",startDateStr+" "+endDateStr);


            }

            @Override
            public void onCaldroidViewCreated() {
                super.onCaldroidViewCreated();
                // caldroidFragment.getWeekdayGridView().setAdapter();
            }
        };
        caldroidFragment.setCaldroidListener(listener);
        try {


            t = getSupportFragmentManager().beginTransaction();
            t.addToBackStack(null);
            t.replace(R.id.rl_cal, caldroidFragment, null);
            t.commit();


        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    private void loadStudentAttendance() {
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
                        attendance = jsonObject.getString("attendance");

                        model.setDate(date);
                        model.setAttendance(attendance);

                        modelArrayList.add(model);
                        attendenceAdapter=new StudentAttendenceAdapter(modelArrayList, AttendancePage_new.this);

                        //   attendence_recycler_view.setAdapter(attendenceAdapter);

                        for (int j = 0; j < modelArrayList.size(); j++) {
                            dateString = modelArrayList.get(j).getDate();

                            //  holiday_name1 = arrayList1.get(j).getHoliday_name();

                        }
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date draft_date_current_format = inputFormat.parse(dateString);
                        Log.d("datenew==", dateString);
                        String draft_date_otput_format = outputFormat.format(draft_date_current_format);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        if(attendance.contentEquals("P"))
                        {
                            ColorDrawable color = new ColorDrawable(Color.parseColor("#aaff80"));
                            caldroidFragment.setBackgroundDrawableForDate(color, dateFormat.parse(draft_date_otput_format));
                        }
                        else if(attendance.contentEquals("A"))
                        {
                            ColorDrawable color = new ColorDrawable(Color.parseColor("#F98380"));
                            caldroidFragment.setBackgroundDrawableForDate(color, dateFormat.parse(draft_date_otput_format));
                        }
                        Log.d("DraftDate-=>", draft_date_otput_format.toString());



                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    Log.d("exp==>",e.toString());
                    Toast.makeText(AttendancePage_new.this, "No value for this date", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttendancePage_new.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("userid", Url.user_id);
                Log.d("userid",Url.user_id);
                map.put("to_day",endDateStr);
                map.put("from_day",startDateStr);


                return map;
            }
        };
        Volley.newRequestQueue(AttendancePage_new.this).add(stringRequest);

    }

}