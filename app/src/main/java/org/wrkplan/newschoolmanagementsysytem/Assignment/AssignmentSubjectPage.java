package org.wrkplan.newschoolmanagementsysytem.Assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Subject;
import org.wrkplan.newschoolmanagementsysytem.R;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.StudyMaterialPage;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.SubjectRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignmentSubjectPage extends AppCompatActivity {

    ImageView img_back;
    RecyclerView subject_recycler_view;
    TextView tv_class;
    ArrayList<Model_Subject> model_subjectArrayList=new ArrayList<>();
    SubjectAssignmentRecyclerAdapter subjectRecyclerAdapter;
   public static String assignment_date;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.assignment_subject_activity);
        initialViews();

        subject_recycler_view.setHasFixedSize(true);
        subject_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        tv_class.setText(LoginPage.pre_class);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        assignment_date=getIntent().getStringExtra("Assignment_date");
        Toast.makeText(this, ""+assignment_date, Toast.LENGTH_SHORT).show();

        getSubjects();

    }

    private void getSubjects() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=assignmentclasswise";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if(!model_subjectArrayList.isEmpty())
                    {
                        model_subjectArrayList.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("subject");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        String subject_id=jb1.getString("subject_id");
                        String subject_name=jb1.getString("subject_name");

                        Model_Subject model_subject=new Model_Subject();
                        model_subject.setSubject_id(subject_id);
                        model_subject.setSubject_name(subject_name);

                        model_subjectArrayList.add(model_subject);
                        subjectRecyclerAdapter=new SubjectAssignmentRecyclerAdapter(model_subjectArrayList, AssignmentSubjectPage.this);
                        subject_recycler_view.setAdapter(subjectRecyclerAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AssignmentSubjectPage.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AssignmentSubjectPage.this, "Could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("class_id",LoginPage.pre_class_id);
                map.put("date",assignment_date);
                Log.d("class_id=>",LoginPage.pre_class_id);
                Log.d("date=>",assignment_date);
                return map;
            }
        };
        Volley.newRequestQueue(AssignmentSubjectPage.this).add(stringRequest);
    }

    private void initialViews() {
        img_back=findViewById(R.id.img_back);
        subject_recycler_view=findViewById(R.id.subject_recycler_view);
        tv_class=findViewById(R.id.tv_class);
    }
}
