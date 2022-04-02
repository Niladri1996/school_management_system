package org.wrkplan.newschoolmanagementsysytem.UserProfile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.wrkplan.newschoolmanagementsysytem.Config.Url;
import org.wrkplan.newschoolmanagementsysytem.R;

import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.caste;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.dob;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.dob_staff;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.email;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.email_admin;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.experience;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.father_name;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.firstname;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.fname_admin;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.gender;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.lastname;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.lname_admin;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.marital;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.mobileno;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.phoneno_admin;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.pre_age;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.pre_blood_group;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.pre_class;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.pre_gender;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.pre_mobile;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.pre_name;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.present_address1;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.present_city1;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.present_pincode1;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.subject;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.type;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.type_admin;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.username;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.username_admin;
import static org.wrkplan.newschoolmanagementsysytem.LoginPage.LoginPage.username_staff;

public class StudentProfilePage extends AppCompatActivity {

    //for student
    TextView txt_name,txt_username,txt_f_name,txt_dob,txt_phoneno,txt_gender,txt_age,txt_blood_grp,txt_class,txt_caste,txt_full_address;
    //for admin
    TextView txt_staff_name,txt_staff_username,txt_staff_gender,txt_staff_dob,txt_staff_status,txt_staff_exp,txt_staff_type,txt_staff_email,txt_staff_ph,txt_staff_subject;

    //for staff
    TextView txt_admin_name,txt_admin_username,txt_admin_email,txt_admin_phone,txt_admin_type;

    String userid;
    ProgressDialog progressDialog;
    ImageView img_back;
    RelativeLayout rl_students,rl_staff,rl_admin;
    TextView tv_title;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.student_profile_acivity);
        //get user id==>>
        userid=getIntent().getStringExtra("user_id");
        //Toast.makeText(this, ""+userid, Toast.LENGTH_SHORT).show();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        InisializeView();

        if(Url.user_type.contentEquals("student"))
        {

            rl_students.setVisibility(View.VISIBLE);
            rl_staff.setVisibility(View.GONE);
            rl_admin.setVisibility(View.GONE);

            getStudentsDetails();
        }
       else if(Url.user_type.contentEquals("Admin"))
        {


            rl_students.setVisibility(View.GONE);
            rl_staff.setVisibility(View.GONE);
            rl_admin.setVisibility(View.VISIBLE);
            getAdminDetails();
        }
       else if(Url.user_type.contentEquals("staff"))
        {

            rl_students.setVisibility(View.GONE);
            rl_staff.setVisibility(View.VISIBLE);
            rl_admin.setVisibility(View.GONE);
            getStaffDetails();
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getAdminDetails() {

        txt_admin_name.setText(fname_admin+" "+lname_admin);
        txt_admin_username.setText(username_admin);
        txt_admin_email.setText(email_admin);
        txt_admin_phone.setText(phoneno_admin);
        txt_admin_type.setText(type_admin);
    }

    private void getStaffDetails() {
        txt_staff_username.setText(username_staff);
        txt_staff_name.setText(firstname+ " "+lastname);
        txt_staff_type.setText(type);
        txt_staff_gender.setText(gender);
        txt_staff_dob.setText(dob_staff);
        txt_staff_status.setText(marital);
        txt_staff_exp.setText(experience);
        txt_staff_email.setText(email);
        txt_staff_ph.setText(mobileno);
        txt_staff_subject.setText(subject);

    }

    private void getStudentsDetails() {






                    txt_username.setText(username);
                    txt_dob.setText(dob);
                    txt_f_name.setText(father_name);
                    txt_name.setText(pre_name);
                    txt_phoneno.setText(pre_mobile);
                    txt_gender.setText(pre_gender);
                    txt_age.setText(pre_age);
                    txt_blood_grp.setText(pre_blood_group);
                    txt_class.setText(pre_class);
                    txt_caste.setText(caste);
                    txt_full_address.setText(present_address1+ " ,"+present_pincode1+" ,"+present_city1);


    }

    private void InisializeView() {
        //for student
        txt_name=findViewById(R.id.txt_name);
        txt_username=findViewById(R.id.txt_username);
        txt_f_name=findViewById(R.id.txt_f_name);
        txt_dob=findViewById(R.id.txt_dob);
        txt_phoneno=findViewById(R.id.txt_phoneno);
        txt_gender=findViewById(R.id.txt_gender);
        txt_age=findViewById(R.id.txt_age);
        txt_blood_grp=findViewById(R.id.txt_blood_grp);
        txt_class=findViewById(R.id.txt_class);
        txt_caste=findViewById(R.id.txt_caste);
        txt_full_address=findViewById(R.id.txt_full_address);
        img_back=findViewById(R.id.img_back);
        //end student

        //for staff
        txt_staff_name=findViewById(R.id.txt_staff_name);
        txt_staff_username=findViewById(R.id.txt_staff_username);
        txt_staff_gender=findViewById(R.id.txt_staff_gender);
        txt_staff_dob=findViewById(R.id.txt_staff_dob);
        txt_staff_status=findViewById(R.id.txt_staff_status);
        txt_staff_exp=findViewById(R.id.txt_staff_exp);
        txt_staff_type=findViewById(R.id.txt_staff_type);
        txt_staff_email=findViewById(R.id.txt_staff_email);
        txt_staff_ph=findViewById(R.id.txt_staff_ph);

        //end staff

        // for admin
        txt_admin_name=findViewById(R.id.txt_admin_name);
        txt_admin_username=findViewById(R.id.txt_admin_username);
        txt_admin_email=findViewById(R.id.txt_admin_email);
        txt_admin_phone=findViewById(R.id.txt_admin_phone);
        txt_admin_type=findViewById(R.id.txt_admin_type);

        //end admin
        rl_students=findViewById(R.id.rl_students);
        tv_title=findViewById(R.id.tv_title);
        rl_staff=findViewById(R.id.rl_staff);
        rl_admin=findViewById(R.id.rl_admin);
        txt_staff_subject=findViewById(R.id.txt_staff_subject);

    }
}
