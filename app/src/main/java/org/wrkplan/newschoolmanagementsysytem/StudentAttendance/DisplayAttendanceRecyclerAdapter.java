package org.wrkplan.newschoolmanagementsysytem.StudentAttendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Model.Model_All_Student_Attendance;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;

public class DisplayAttendanceRecyclerAdapter extends RecyclerView.Adapter<DisplayAttendanceRecyclerAdapter.ViewHolder> {

    ArrayList<Model_All_Student_Attendance> displayarrayList=new ArrayList<>();
    Context context;

    public DisplayAttendanceRecyclerAdapter(ArrayList<Model_All_Student_Attendance> displayarrayList, Context context) {
        this.displayarrayList = displayarrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DisplayAttendanceRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_display_attendance,parent,false);
        return new DisplayAttendanceRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayAttendanceRecyclerAdapter.ViewHolder holder, int position) {

        Model_All_Student_Attendance model_all_student_attendance=displayarrayList.get(position);
        holder.txt_name.setText(displayarrayList.get(position).getStudent_name());
        holder.txt_p_a.setText(displayarrayList.get(position).getAttendance());
        if(displayarrayList.get(position).getAttendance().contentEquals("A"))
        {
            holder.rl_p_a.setBackgroundResource(R.drawable.attendence);
        }
        else
        {
            holder.rl_p_a.setBackgroundResource(R.drawable.presents);
        }

    }

    @Override
    public int getItemCount() {
        return displayarrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_p_a;
        RelativeLayout rl_p_a;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_p_a=itemView.findViewById(R.id.txt_p_a);
            rl_p_a=itemView.findViewById(R.id.rl_p_a);
        }
    }
}
