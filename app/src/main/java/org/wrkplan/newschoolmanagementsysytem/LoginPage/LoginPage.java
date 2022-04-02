package org.wrkplan.newschoolmanagementsysytem.LoginPage;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.wrkplan.newschoolmanagementsysytem.Config.Url;
import org.wrkplan.newschoolmanagementsysytem.Dashboard.Dashboard;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static org.wrkplan.newschoolmanagementsysytem.Config.Url.user_id;
import static org.wrkplan.newschoolmanagementsysytem.Config.Url.user_name;

public class LoginPage extends AppCompatActivity {
    EditText ed_username,ed_password;
    RelativeLayout rl_btn_login;
    Spinner spinner_1;
    String[] user_type = { "student", "Admin", "staff"};
     String usertype;
    JSONObject jsonObject=new JSONObject();
    ProgressDialog progressDialog;

    // for student information start//
    public static String username="";
    public static String dob="";
    public static String father_name="";
    public static String pre_name="";
    public static String pre_mobile="";
    public static String pre_gender="";
    public static String pre_age="";
    public static String pre_blood_group="";
    public static String pre_class="";
    public static String pre_class_id="";
    public static String caste="";
    public static String present_address1="";
    public static String present_pincode1="";
    public static String present_city1="";

    // for student information end//


    //for staff information start  //
    public static String subject="";
    public static String username_staff="";
    public static String type="";
    public static String firstname="";
    public static String lastname="";
    public static String gender="";
    public static String dob_staff="";
    public static String marital="";
    public static String experience="";
    public static String email="";
    public static String mobileno="";

    //for staff information end  //


    //for admin information start //
    public static String username_admin="";
    public static String fname_admin="";
    public static String lname_admin="";
    public static String email_admin="";
    public static String phoneno_admin="";
    public static String type_admin="";
    //for admin information end //


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
        InisializeViews();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        // spinner code start
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, user_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usertype=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner code end

        rl_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             validation();


            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                 //   downloadPdfFile();
                }else{
                  //  progress.dismiss();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                 //   SharePdfFile();
                }else{
                 //   progress.dismiss();
                }
                break;
        }
    }
    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }


    private void validation() {
        final String username = ed_username.getText().toString();
        final String password = ed_password.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ed_username.setError("Please enter your username");
            ed_username.requestFocus();
            return;
        }
       else if (TextUtils.isEmpty(password)) {
            ed_password.setError("Please enter your password");
            ed_password.requestFocus();
            return;
        }
       else if (usertype.toString().trim().equals("Type")) {
            Toast.makeText(LoginPage.this, "Please select Type", Toast.LENGTH_SHORT).show();
        }


       else {
            getLogin(username,password,usertype);
                 }
    }



    private void getLogin(String username, String password, String usertype) {
        progressDialog.setMessage("Loggin in...");
        progressDialog.show();

        String url = "http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=login";
        Log.d("url=>",url);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("respomse=>",response);
                    String msg=jsonObject.getString("msg");
                    if(msg.contentEquals("sucess"))
                    {
                        progressDialog.dismiss();
                         Url.user_type=jsonObject.getString("user_type");
                         user_id=jsonObject.getString("user_id");
                         user_name=jsonObject.getString("user_name");

//                        Intent intent=new Intent(LoginPage.this,Dashboard.class);
//                        intent.putExtra("user_type",user_type);
//                        intent.putExtra("user_id",user_id);
//                        intent.putExtra("user_name",user_name);
//                        startActivity(intent);

                        if(Url.user_type.contentEquals("student"))
                        {
                            // call api for student information
                            getStudentsDetails();
                        }

                        else if(Url.user_type.contentEquals("Admin"))
                        {
                            // call api for admin information
                            getAdminDetails();
                        }
                        else if(Url.user_type.contentEquals("staff"))
                        {
                            // call api for staff information
                          getStaffDetails();
                        }

                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginPage.this, "Credentials mismatch !!,please try again", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("msgg=>",response);
                    //Toast.makeText(LoginPage.this, ""+msg, Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.d("error=>",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginPage.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
           {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("username",username);
                Log.d("username=>",username);
                map.put("password",password);
                    Log.d("password=>",password);
                map.put("usertype",usertype);
                    Log.d("usertype=>",usertype);
                return map;
            }
            };
        Volley.newRequestQueue(LoginPage.this).add(stringRequest);
    }

    private void getAdminDetails() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=admin_profile";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    username_admin=jsonObject.getString("username");
                    fname_admin=jsonObject.getString("fname");
                    lname_admin=jsonObject.getString("lname");
                    email_admin=jsonObject.getString("email");
                    phoneno_admin=jsonObject.getString("phoneno");
                    type_admin=jsonObject.getString("type");

                    Intent intent=new Intent(LoginPage.this,Dashboard.class);
                    startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginPage.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginPage.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("user_id", user_id);
                Log.d("userid=>", user_id);
                return map;
            }
        };
        Volley.newRequestQueue(LoginPage.this).add(stringRequest);

    }

    private void getStaffDetails() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=staff_profile";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    subject=jsonObject.getString("subject");
                    username_staff=jsonObject.getString("username");
                    type=jsonObject.getString("type");
                    firstname=jsonObject.getString("firstname");
                    lastname=jsonObject.getString("lastname");
                    gender=jsonObject.getString("gender");
                    dob_staff=jsonObject.getString("dob");
                    marital=jsonObject.getString("marital");
                    experience=jsonObject.getString("experience");
                    email=jsonObject.getString("email");
                    mobileno=jsonObject.getString("mobileno");

                    Intent intent=new Intent(LoginPage.this,Dashboard.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginPage.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginPage.this, "Could not connect to the server", Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("user_id", user_id);
                Log.d("userid=>", user_id);
                return map;
            }
        };
        Volley.newRequestQueue(LoginPage.this).add(stringRequest);

    }

    private void getStudentsDetails() {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=stu_profile";
        Log.d("url==>",url);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("response=>",response);
                     username=jsonObject.getString("username");
                    dob=jsonObject.getString("dob");
                     father_name=jsonObject.getString("father_name");
                    pre_name=jsonObject.getString("pre_name");
                    pre_mobile=jsonObject.getString("pre_mobile");
                     pre_gender=jsonObject.getString("pre_gender");
                     pre_age=jsonObject.getString("pre_age");
                     pre_blood_group=jsonObject.getString("pre_blood_group");
                    pre_class=jsonObject.getString("pre_class");
                    pre_class_id=jsonObject.getString("pre_class_id");
                     caste=jsonObject.getString("caste");
                     present_address1=jsonObject.getString("present_address1");
                    present_pincode1=jsonObject.getString("present_pincode1");
                     present_city1=jsonObject.getString("present_city1");



                     Intent intent=new Intent(LoginPage.this,Dashboard.class);
                     startActivity(intent);





                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("execp=>",e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(LoginPage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error=>",error.toString());
                progressDialog.dismiss();

                Toast.makeText(LoginPage.this, "Could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("user_id", user_id);
                Log.d("userid=>", user_id);
                return map;
            }
        };
        Volley.newRequestQueue(LoginPage.this).add(stringRequest);
    }


    private void InisializeViews() {
        ed_password=findViewById(R.id.ed_password);
        ed_username=findViewById(R.id.ed_username);
        rl_btn_login=findViewById(R.id.rl_btn_login);
        spinner_1=findViewById(R.id.spinner_1);
    }
}
