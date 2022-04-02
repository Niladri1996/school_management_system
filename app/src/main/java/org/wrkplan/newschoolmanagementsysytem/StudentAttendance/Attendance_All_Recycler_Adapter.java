package org.wrkplan.newschoolmanagementsysytem.StudentAttendance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Attendence.StudentAttendenceAdapter;
import org.wrkplan.newschoolmanagementsysytem.Interface.SelectInterface;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_All_Student_Attendance;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;

public class Attendance_All_Recycler_Adapter extends RecyclerView.Adapter<Attendance_All_Recycler_Adapter.ViewHolder> {
    ArrayList<Model_All_Student_Attendance>attendanceArrayList=new ArrayList<>();
    Context context;
    public static   ArrayList<String> studentId=new ArrayList<>();
    public static String selectedStudentID = "";
    SelectInterface selectInterface;

    public Attendance_All_Recycler_Adapter(ArrayList attendanceArrayList, Context context, SelectInterface selectInterface) {
        this.attendanceArrayList = attendanceArrayList;
        this.context = context;
        this.selectInterface=selectInterface;
    }

    @NonNull
    @Override
    public Attendance_All_Recycler_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_name_layout,parent,false);
        return new Attendance_All_Recycler_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Attendance_All_Recycler_Adapter.ViewHolder holder, int position) {

        Model_All_Student_Attendance model=attendanceArrayList.get(position);
        holder.txt_stud_name.setText(attendanceArrayList.get(position).getStudent_name());
        holder.check_student.setChecked(model.isChecked());


        holder.check_student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//              if(isChecked)
//              {
//                  attendanceArrayList.get(holder.getAdapterPosition()).setChecked(false);
//              } else {
//
//                  attendanceArrayList.get(holder.getAdapterPosition()).setChecked(true);
//          }

//                  selectedStudentID = "";
//                  for (int i=0; i<attendanceArrayList.size(); i++)
//                  {
//                      if(attendanceArrayList.get(i).isChecked()==true)
//                      {
//                          selectedStudentID += attendanceArrayList.get(i).getStudent_id()+",";
//                      }
//
//                  }
//
//                  selectedStudentID = selectedStudentID.substring(0, selectedStudentID.length() - 1);
//                  Log.d("getParams:", selectedStudentID);
//                  Toast.makeText(context, "ID: "+selectedStudentID, Toast.LENGTH_LONG).show();
//              }
                selectInterface.uncheck_particular_position(holder.getAdapterPosition(),isChecked);
                //  selectInterface.uncheck_all();
            }
        });

    }

    @Override
    public int getItemCount() {
        return attendanceArrayList.size();
    }

    public void filltered(ArrayList<Model_All_Student_Attendance> filterlist) {
        attendanceArrayList=filterlist;
        notifyDataSetChanged();
    }

    public void refreshlist(ArrayList<Model_All_Student_Attendance> attendanceArrayList) {
        this.attendanceArrayList=attendanceArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox check_student;
        TextView txt_stud_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check_student=itemView.findViewById(R.id.check_student);
            txt_stud_name=itemView.findViewById(R.id.txt_stud_name);
        }
    }
}