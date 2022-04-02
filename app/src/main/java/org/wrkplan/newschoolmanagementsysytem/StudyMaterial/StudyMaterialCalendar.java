package org.wrkplan.newschoolmanagementsysytem.StudyMaterial;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
import org.wrkplan.newschoolmanagementsysytem.Assignment.AssignmentCalendar;
import org.wrkplan.newschoolmanagementsysytem.Assignment.AssignmentSubjectPage;
import org.wrkplan.newschoolmanagementsysytem.Dashboard.Dashboard;
import org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Assignment_Date;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudyMaterialCalendar extends AppCompatActivity {

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
    ImageView img_back;
    public  static String date;
    private String dateString;
    ArrayList<Model_Assignment_Date> model_assignment_dates=new ArrayList<>();

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StudyMaterialCalendar.this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.study_material_calendar);
        img_back=findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMaterialCalendar.this, Dashboard.class);
                startActivity(intent);
            }
        });

        openCaldroidCalendar();
    }

    private void openCaldroidCalendar() {
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();

        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
        caldroidFragment.setArguments(args);

        loadStudyMaterialDate();

        final CaldroidListener listener= new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {

                SimpleDateFormat inputformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                try {
                    Date d1 = inputformat.parse(date.toString());
                    Log.d("niladri=>", d1.toString());
                    String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(d1);
                    Log.d("DraftDate1-=>", formateDate);

                    for(int i=0;i<model_assignment_dates.size();i++)
                    {
                        if(formateDate.contentEquals(model_assignment_dates.get(i).getDate()))
                        {

                            Intent intent=new Intent(StudyMaterialCalendar.this,StudyMaterialPage.class);
                            intent.putExtra("Study_Material_date",model_assignment_dates.get(i).getDate());
                            startActivity(intent);
                        }

                    }


                }
                catch (Exception e)
                {
                    Toast.makeText(StudyMaterialCalendar.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onLongClickDate(Date date, View view) {
                super.onLongClickDate(date, view);
            }

            @Override
            public void onChangeMonth(int month, int year) {
                super.onChangeMonth(month, year);
            }

            @Override
            public void onCaldroidViewCreated() {
                super.onCaldroidViewCreated();
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

    private void loadStudyMaterialDate() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=study_material_day";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if(!model_assignment_dates.isEmpty())
                    {
                        model_assignment_dates.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);

                    JSONArray jsonArray=jsonObject.getJSONArray("date_meterial");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        date=jb1.getString("date");

                        Model_Assignment_Date model=new Model_Assignment_Date();

                        model.setDate(date);

                        model_assignment_dates.add(model);


                        for(int j=0;j<model_assignment_dates.size();j++)
                        {
                            dateString=model_assignment_dates.get(j).getDate();
                        }
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date draft_date_current_format = inputFormat.parse(dateString);
                        Log.d("datenew==", dateString);
                        String draft_date_otput_format = outputFormat.format(draft_date_current_format);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                        ColorDrawable color = new ColorDrawable(Color.parseColor("#ABA3EA"));
                        caldroidFragment.setBackgroundDrawableForDate(color, dateFormat.parse(draft_date_otput_format));


                    }


                } catch (JSONException | ParseException e) {

                    e.printStackTrace();
                    Toast.makeText(StudyMaterialCalendar.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(StudyMaterialCalendar.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("class_id", LoginPage.pre_class_id);
                Log.d("class_id=>",LoginPage.pre_class_id);

                return map;
            }
        };
        Volley.newRequestQueue(StudyMaterialCalendar.this).add(stringRequest);
    }
}

