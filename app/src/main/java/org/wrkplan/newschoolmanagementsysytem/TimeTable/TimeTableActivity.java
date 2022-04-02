package org.wrkplan.newschoolmanagementsysytem.TimeTable;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Time_table;
import org.wrkplan.newschoolmanagementsysytem.R;
import org.wrkplan.newschoolmanagementsysytem.SwipeGesture.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeTableActivity extends AppCompatActivity {

    TextView txt_stud_class,txt_day,tv_class;
    ImageView img_back;
    TimeTableRecyclerAdapter tableRecyclerAdapter;
    ArrayList<Model_Time_table> modelTimeTableArrayList=new ArrayList<>();
    RecyclerView timetable_recycler_view;
    int count=0;
    int arraysize;
    RelativeLayout rl_recycler;
    CoordinatorLayout co_main;
    ProgressDialog progressDialog;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.time_table_activity);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        img_back=findViewById(R.id.img_back);
        txt_day=findViewById(R.id.txt_day);
        co_main=findViewById(R.id.co_main);
        tv_class=findViewById(R.id.tv_class);
        rl_recycler=findViewById(R.id.rl_recycler);

        tv_class.setText(LoginPage.pre_class);



        //txt_stud_class.setText(LoginPage.pre_class);
        timetable_recycler_view=findViewById(R.id.timetable_recycler_view);

        timetable_recycler_view.setHasFixedSize(true);
        timetable_recycler_view.setLayoutManager(new LinearLayoutManager(this));


        //back---- start
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //back---- end

            getTimeTable(0);

        findViewById(R.id.co_main).setOnTouchListener(new OnSwipeTouchListener(TimeTableActivity.this) {

            public void onSwipeRight() {
                if(count>0)
                {
                    count=count-1;
                    getTimeTable(count);
                }

            }

            public void onSwipeLeft() {


                if(count<arraysize-1)
                {
                    count=count+1;
                    getTimeTable(count);
                }

            }
        });


    }

    private void getTimeTable(int count) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=routine";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if(!modelTimeTableArrayList.isEmpty())
                    {
                        modelTimeTableArrayList.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    //String classS=jsonObject.getString("class");
                    JSONArray jsonArray=jsonObject.getJSONArray("timetable");
                    arraysize=jsonArray.length();
                    Log.d("arraysize=>",arraysize+"");
                    for(int i=count;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(count);
                        String day=jb1.getString("day");
                        JSONArray jb2=jb1.getJSONArray("classes");
                        if(!modelTimeTableArrayList.isEmpty())
                        {
                            modelTimeTableArrayList.clear();
                        }
                        for(int j=0;j<jb2.length();j++)
                        {

                            JSONObject jb3=jb2.getJSONObject(j);
                            String time_to=jb3.getString("time_to");
                            String time_from=jb3.getString("time_from");
                            String subject=jb3.getString("subject");
                            String teacher=jb3.getString("teacher");
                            String break_yn=jb3.getString("break_yn");

                            txt_day.setText(day);
                            Model_Time_table model_time_table=new Model_Time_table();
                            model_time_table.setTime_to(time_to);
                            model_time_table.setTime_from(time_from);
                            model_time_table.setSubject(subject);
                            model_time_table.setTeacher(teacher);
                            model_time_table.setBreak_yn(break_yn);


                            modelTimeTableArrayList.add(model_time_table);
                        }
                        progressDialog.dismiss();
                        tableRecyclerAdapter=new TimeTableRecyclerAdapter(modelTimeTableArrayList, TimeTableActivity.this);
                        timetable_recycler_view.setAdapter(tableRecyclerAdapter);
                    }





                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(TimeTableActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("excep=>",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(TimeTableActivity.this, "Could't connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("userid",Url.user_id);
                Log.d("userid=>",Url.user_id);
                return map;
            }
        };
        Volley.newRequestQueue(TimeTableActivity.this).add(stringRequest);
    }
}
