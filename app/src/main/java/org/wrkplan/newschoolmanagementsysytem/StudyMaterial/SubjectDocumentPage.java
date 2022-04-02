package org.wrkplan.newschoolmanagementsysytem.StudyMaterial;

import android.app.ProgressDialog;
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
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Subject_Detail;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubjectDocumentPage extends AppCompatActivity {

    ImageView img_back;
    TextView tv_class,txt_subject;
    RecyclerView subject_details_recycler_view;
    SubjectDetailsRecyclerAdapter detailsRecyclerAdapter;
    ArrayList<Model_Subject_Detail>model_subject_detailArrayList=new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.subject_document_activity);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        initializeviews();
        tv_class.setText(LoginPage.pre_class);
        txt_subject.setText(SubjectRecyclerAdapter.subject);

        subject_details_recycler_view.setHasFixedSize(true);
        subject_details_recycler_view.setLayoutManager(new LinearLayoutManager(this));


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSubjectDetails();

    }

    private void getSubjectDetails() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=study_material";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(!model_subject_detailArrayList.isEmpty())
                    {
                        model_subject_detailArrayList.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("response==>",response);
                    JSONArray jsonArray=jsonObject.getJSONArray("assignment");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(i);
                        String id=jb1.getString("id");
                        String title=jb1.getString("title");
                        String details=jb1.getString("details");
                        String filelink=jb1.getString("filelink");

                        Model_Subject_Detail model_subject_detail=new Model_Subject_Detail();
                        model_subject_detail.setId(id);
                        model_subject_detail.setTitle(title);
                        model_subject_detail.setDetails(details);
                        model_subject_detail.setFilelink(filelink);

                        model_subject_detailArrayList.add(model_subject_detail);


                    }
                    progressDialog.dismiss();
                    detailsRecyclerAdapter=new SubjectDetailsRecyclerAdapter(model_subject_detailArrayList, SubjectDocumentPage.this);
                    subject_details_recycler_view.setAdapter(detailsRecyclerAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(SubjectDocumentPage.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("exception=>",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(SubjectDocumentPage.this, "Could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("subject_id",SubjectRecyclerAdapter.id);
                map.put("date",StudyMaterialPage.study_materialt_date);
                Log.d("subject_id=>",SubjectRecyclerAdapter.id);
                return map;
            }
        };
        Volley.newRequestQueue(SubjectDocumentPage.this).add(stringRequest);
    }

    private void initializeviews() {
        img_back=findViewById(R.id.img_back);
        tv_class=findViewById(R.id.tv_class);
        txt_subject=findViewById(R.id.txt_subject);
        subject_details_recycler_view=findViewById(R.id.subject_details_recycler_view);
    }
}
