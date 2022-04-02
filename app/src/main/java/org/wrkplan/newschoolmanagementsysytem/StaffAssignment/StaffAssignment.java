package org.wrkplan.newschoolmanagementsysytem.StaffAssignment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.wrkplan.newschoolmanagementsysytem.R;

public class StaffAssignment extends AppCompatActivity {
    ImageView img_back;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.staff_assignment_activity);

        initialzeViews();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initialzeViews() {
        img_back=findViewById(R.id.img_back);
    }
}
