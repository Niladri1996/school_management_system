package org.wrkplan.newschoolmanagementsysytem.StudentFees;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.wrkplan.newschoolmanagementsysytem.R;

public class StudentFees extends AppCompatActivity {
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.student_fees_activity);
    }
}
