package org.wrkplan.newschoolmanagementsysytem.Dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.wrkplan.newschoolmanagementsysytem.Assignment.AssignmentCalendar;
import org.wrkplan.newschoolmanagementsysytem.Assignment.AssignmentSubjectPage;
import org.wrkplan.newschoolmanagementsysytem.Attendence.AttendancePage_new;
import org.wrkplan.newschoolmanagementsysytem.Attendence.AttendancePage_old;
import org.wrkplan.newschoolmanagementsysytem.Config.Url;
import org.wrkplan.newschoolmanagementsysytem.Holiday.HolidayPage;
import org.wrkplan.newschoolmanagementsysytem.NoticeBoard.NoticeBoardPage;
import org.wrkplan.newschoolmanagementsysytem.R;
import org.wrkplan.newschoolmanagementsysytem.StaffAssignment.StaffAssignment;
import org.wrkplan.newschoolmanagementsysytem.StudentAttendance.StudentAttendance;
import org.wrkplan.newschoolmanagementsysytem.StudentFees.StudentFees;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.StudyMaterialCalendar;
import org.wrkplan.newschoolmanagementsysytem.UserProfile.StudentProfilePage;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.StudyMaterialPage;
import org.wrkplan.newschoolmanagementsysytem.TimeTable.TimeTableActivity;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    String username,userid,type;
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer_layout;
    ImageView student_profile,admin_profile,staff_profile,img_staff_assignment,img_staff_attendence;
    RelativeLayout rl_students,rl_admin,rl_staf;
    TextView txt_header;
    ImageView img_time_table,img_study_material,img_attendence,img_assignment,img_holiday,img_notice_board,img_admin_attendence,
            img_fees;

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce)
        {
            //  super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

        }
        else {
            doubleBackToExitPressedOnce = true;

            Snackbar snackbar = Snackbar.make(rl_students, "Press BACK once more to exit", Snackbar.LENGTH_SHORT);
            snackbar.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;

                }

            }, 2000);
        }


    }

    private void closeapp() {
        String title="Exit!";
        String message="Are you sure you want to close the app?";


        AlertDialog.Builder save=new AlertDialog.Builder(Dashboard.this);
        save.setMessage(message);
        save.setCancelable(true);
        save.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        save.setTitle(title);
        save.show();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        username=getIntent().getStringExtra("user_name");
        userid=getIntent().getStringExtra("user_id");
        type=getIntent().getStringExtra("user_type");
        //initialize views
        rl_students=findViewById(R.id.rl_students);

        rl_admin=findViewById(R.id.rl_admin);
        rl_staf=findViewById(R.id.rl_staf);
        img_time_table=findViewById(R.id.img_time_table);
        img_study_material=findViewById(R.id.img_study_material);
        img_attendence=findViewById(R.id.img_attendence);
        img_assignment=findViewById(R.id.img_assignment);
        img_holiday=findViewById(R.id.img_holiday);
        img_notice_board=findViewById(R.id.img_notice_board);
        img_admin_attendence=findViewById(R.id.img_admin_attendence);
        img_staff_assignment=findViewById(R.id.img_staff_assignment);
        img_staff_attendence=findViewById(R.id.img_staff_attendence);
        img_fees=findViewById(R.id.img_fees);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav_view=findViewById(R.id.nav_view);
        drawer_layout=findViewById(R.id.drawer_layout);

        toggle=new ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();


        //FOR STUDENT PROFILE START
        View headerview = nav_view.getHeaderView(0);
        student_profile=(ImageView) headerview.findViewById(R.id.student_profile);
        staff_profile=(ImageView) headerview.findViewById(R.id.staff_profile);
        admin_profile=(ImageView) headerview.findViewById(R.id.admin_profile);
        txt_header=(TextView)headerview.findViewById(R.id.txt_header);

        img_time_table.setOnClickListener(this);
        img_study_material.setOnClickListener(this);
        img_attendence.setOnClickListener(this);
        img_assignment.setOnClickListener(this);
        img_holiday.setOnClickListener(this);
        img_notice_board.setOnClickListener(this);
        img_admin_attendence.setOnClickListener(this);
        img_staff_assignment.setOnClickListener(this);
        img_staff_attendence.setOnClickListener(this);
        img_fees.setOnClickListener(this);

        student_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, StudentProfilePage.class);
                intent.putExtra("user_id",userid);
                startActivity(intent);
            }
        });
        //END STUDENT PROFILE
        staff_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, StudentProfilePage.class);
                intent.putExtra("user_id",userid);
                startActivity(intent);
            }
        });
        //END STAFF PROFILE
        admin_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, StudentProfilePage.class);
                intent.putExtra("user_id",userid);
                startActivity(intent);
            }
        });
        //END ADMIN PROFILE

        if(Url.user_type.contentEquals("student"))
        {
            rl_students.setVisibility(View.VISIBLE);
            rl_admin.setVisibility(View.GONE);
            rl_staf.setVisibility(View.GONE);
            txt_header.setText("Student Profile");
            admin_profile.setVisibility(View.GONE);
            staff_profile.setVisibility(View.GONE);

        }
        else if(Url.user_type.contentEquals("Admin"))
        {
            rl_students.setVisibility(View.GONE);
            rl_staf.setVisibility(View.GONE);
            rl_admin.setVisibility(View.VISIBLE);
            txt_header.setText("Admin Profile");
            student_profile.setVisibility(View.GONE);
            staff_profile.setVisibility(View.GONE);



        }
        else if(Url.user_type.contentEquals("staff"))
        {
            rl_students.setVisibility(View.GONE);
            rl_admin.setVisibility(View.GONE);
            rl_staf.setVisibility(View.VISIBLE);
            txt_header.setText("Staff Profile");
            admin_profile.setVisibility(View.GONE);
            student_profile.setVisibility(View.GONE);

        }

        hidemenu();

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.time_table:
                        Intent intent = new Intent(Dashboard.this, TimeTableActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.attendence:
                        Intent intent1 = new Intent(Dashboard.this, AttendancePage_new.class);
                        intent1.putExtra("user_id",userid);
                        startActivity(intent1);
                        break;
                    case R.id.study_metarial:
                        Intent intent2 = new Intent(Dashboard.this, StudyMaterialCalendar.class);
                        startActivity(intent2);
                        break;
                    case R.id.assignment:
                        Intent intent4 = new Intent(Dashboard.this, AssignmentCalendar.class);
                        startActivity(intent4);
                        break;
                    case R.id.holidays:
                        Intent intent5 = new Intent(Dashboard.this, HolidayPage.class);
                        startActivity(intent5);
                        break;
                    case R.id.notice_board:
                        Intent intent6= new Intent(Dashboard.this, NoticeBoardPage.class);
                        startActivity(intent6);
                        break;
                    case R.id.admin_Staff_attendence:
                        Intent intent7= new Intent(Dashboard.this, StudentAttendance.class);
                        startActivity(intent7);
                        break;
                    case R.id.staff_assignment:
                        Intent intent8= new Intent(Dashboard.this, StaffAssignment.class);
                        startActivity(intent8);
                        break;
                    case R.id.student_fees:
                        Intent intent9= new Intent(Dashboard.this, StudentFees.class);
                        startActivity(intent9);
                        break;
                    case R.id.nav_logout:
                        alertLogout();
                        break;
                }



                return true;

            }
        });

        //  Toast.makeText(this, ""+userid+ "  "+username+"   "+type, Toast.LENGTH_SHORT).show();

    }

    private void hidemenu() {
        if(Url.user_type.contentEquals("Admin"))
        {
            nav_view=(NavigationView) findViewById(R.id.nav_view);
            Menu menuItem=nav_view.getMenu();
            menuItem.findItem(R.id.time_table).setVisible(false);
            menuItem.findItem(R.id.study_metarial).setVisible(false);
            menuItem.findItem(R.id.attendence).setVisible(false);
            menuItem.findItem(R.id.assignment).setVisible(false);
            menuItem.findItem(R.id.holidays).setVisible(false);
            menuItem.findItem(R.id.notice_board).setVisible(false);
            menuItem.findItem(R.id.staff_assignment).setVisible(false);
            menuItem.findItem(R.id.student_fees).setVisible(false);

        }
        else if(Url.user_type.contentEquals("student"))
        {
            nav_view=(NavigationView) findViewById(R.id.nav_view);
            Menu menuItem=nav_view.getMenu();
            menuItem.findItem(R.id.admin_Staff_attendence).setVisible(false);
            menuItem.findItem(R.id.staff_assignment).setVisible(false);
        }
        else if(Url.user_type.contentEquals("staff"))
        {
            nav_view=(NavigationView) findViewById(R.id.nav_view);
            Menu menuItem=nav_view.getMenu();
            menuItem.findItem(R.id.time_table).setVisible(false);
            menuItem.findItem(R.id.study_metarial).setVisible(false);
            menuItem.findItem(R.id.attendence).setVisible(false);
            menuItem.findItem(R.id.assignment).setVisible(false);
            menuItem.findItem(R.id.holidays).setVisible(false);
            menuItem.findItem(R.id.notice_board).setVisible(false);
            menuItem.findItem(R.id.student_fees).setVisible(false);

        }
    }

    private void alertLogout() {
        String title="Logout!";
        String message="Are you sure you want to exit?";


        AlertDialog.Builder save=new AlertDialog.Builder(Dashboard.this);
        save.setMessage(message);
        save.setCancelable(true);
        save.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        save.setTitle(title);
        save.show();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_time_table:
                Intent intent=new Intent(Dashboard.this, TimeTableActivity.class);
                startActivity(intent);
                break;
            case R.id.img_study_material:
                Intent intent1=new Intent(Dashboard.this, StudyMaterialCalendar.class);
                startActivity(intent1);
                break;
            case R.id.img_attendence:
                Intent intent2=new Intent(Dashboard.this, AttendancePage_new.class);
                intent2.putExtra("user_id",userid);
                startActivity(intent2);
                break;
            case R.id.img_assignment:
                Intent intent3=new Intent(Dashboard.this, AssignmentCalendar.class);
                startActivity(intent3);
                break;
            case R.id.img_holiday:
                Intent intent4=new Intent(Dashboard.this, HolidayPage.class);
                startActivity(intent4);
                break;
            case R.id.img_notice_board:
                Intent intent5=new Intent(Dashboard.this, NoticeBoardPage.class);
                startActivity(intent5);
                break;
            case R.id.img_admin_attendence:
                Intent intent6=new Intent(Dashboard.this, StudentAttendance.class);

                Log.d("Clicked--=>","testing");
                startActivity(intent6);
                break;

            case R.id.img_staff_attendence:
                Intent intent8=new Intent(Dashboard.this, StudentAttendance.class);

                startActivity(intent8);
                break;

            case R.id.img_staff_assignment:
                Intent intent7=new Intent(Dashboard.this, StaffAssignment.class);
                startActivity(intent7);
                break;
            case R.id.img_fees:
                Intent intent9=new Intent(Dashboard.this, StudentFees.class);
                startActivity(intent9);
                break;
        }
    }
}
