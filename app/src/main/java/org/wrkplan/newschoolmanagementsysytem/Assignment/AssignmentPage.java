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
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Assignment;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Subject_Detail;
import org.wrkplan.newschoolmanagementsysytem.R;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.SubjectDetailsRecyclerAdapter;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.SubjectDocumentPage;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.SubjectRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignmentPage extends AppCompatActivity {

    ImageView img_back;
    TextView tv_class;
    RecyclerView assignment_recycler_view;
    AssignmentRecyclerAdapter assignmentRecyclerAdapter;
    ArrayList<Model_Assignment>model_assignmentArrayList=new ArrayList<>();
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.assignment_activity);

        initializeviews();
        assignment_recycler_view.setHasFixedSize(true);
        assignment_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_class.setText(LoginPage.pre_class);

        getAssignmet();
    }

    private void getAssignmet() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=assignment1";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(!model_assignmentArrayList.isEmpty())
                    {
                        model_assignmentArrayList.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("response==>",response);
                    JSONArray jsonArray=jsonObject.getJSONArray("assignment");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        String id=jb1.getString("id");
                        String title=jb1.getString("title");
                        String last_date=jb1.getString("last_date");
                        String filelink=jb1.getString("filelink");

                        Model_Assignment model_assignment=new Model_Assignment();
                        model_assignment.setId(id);
                        model_assignment.setTitle(title);
                        model_assignment.setLast_date(last_date);
                        model_assignment.setFilelink(filelink);

                        model_assignmentArrayList.add(model_assignment);


                    }
                   // progressDialog.dismiss();
                    assignmentRecyclerAdapter=new AssignmentRecyclerAdapter(model_assignmentArrayList, AssignmentPage.this);
                    assignment_recycler_view.setAdapter(assignmentRecyclerAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                 //   progressDialog.dismiss();
                    Toast.makeText(AssignmentPage.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("exception=>",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // progressDialog.dismiss();
                Toast.makeText(AssignmentPage.this, "Could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();

                map.put("subject_id",SubjectAssignmentRecyclerAdapter.id);
                map.put("date",AssignmentSubjectPage.assignment_date);
                Log.d("subject_id=>",SubjectAssignmentRecyclerAdapter.id);
                return map;
            }
        };
        Volley.newRequestQueue(AssignmentPage.this).add(stringRequest);
    }

    private void initializeviews() {
        img_back=findViewById(R.id.img_back);
        tv_class=findViewById(R.id.tv_class);
        assignment_recycler_view=findViewById(R.id.assignment_recycler_view);

    }
}
