package org.wrkplan.newschoolmanagementsysytem.Holiday;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Holidays;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;

public class HolidayPage extends AppCompatActivity {
    ImageView img_back;
    RelativeLayout rl_recycler;
    RecyclerView holiday_recycler_view;
    HolidayRecyclerAdapter holidayRecyclerAdapter;
    ArrayList<Model_Holidays>model_holidaysArrayList=new ArrayList<>();
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.holiday_activity);


        InitializeViews();
        holiday_recycler_view.setHasFixedSize(true);
        holiday_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getHolidayList();
    }

    private void getHolidayList() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=holiday";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if(!model_holidaysArrayList.isEmpty())
                    {
                        model_holidaysArrayList.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("holidays");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        String id=jb1.getString("id");
                        String holiday_name=jb1.getString("holiday_name");
                        String from_date=jb1.getString("from_date");
                        String to_date=jb1.getString("to_date");
                        String total_days=jb1.getString("total_days");

                        Model_Holidays model_holidays=new Model_Holidays();

                        model_holidays.setId(id);
                        model_holidays.setHoliday_name(holiday_name);
                        model_holidays.setFrom_date(from_date);
                        model_holidays.setTo_date(to_date);
                        model_holidays.setTotal_days(total_days);

                        model_holidaysArrayList.add(model_holidays);
                        holidayRecyclerAdapter=new HolidayRecyclerAdapter(model_holidaysArrayList, HolidayPage.this);
                        holiday_recycler_view.setAdapter(holidayRecyclerAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(HolidayPage.this).add(stringRequest);
    }

    private void InitializeViews() {
        img_back=findViewById(R.id.img_back);
        rl_recycler=findViewById(R.id.rl_recycler);
        holiday_recycler_view=findViewById(R.id.holiday_recycler_view);
    }
}
