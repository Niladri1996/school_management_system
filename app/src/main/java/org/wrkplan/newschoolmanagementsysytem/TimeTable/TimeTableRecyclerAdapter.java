package org.wrkplan.newschoolmanagementsysytem.TimeTable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Attendence.StudentAttendenceAdapter;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Time_table;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;

public class TimeTableRecyclerAdapter extends RecyclerView.Adapter<TimeTableRecyclerAdapter.ViewHolder> {

    ArrayList<Model_Time_table> modelTimeTableArrayList=new ArrayList<>();
    Context context;

    public TimeTableRecyclerAdapter(ArrayList<Model_Time_table> modelTimeTableArrayList, Context context) {
        this.modelTimeTableArrayList = modelTimeTableArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeTableRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_timetable_layuout,parent,false);
        return new TimeTableRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableRecyclerAdapter.ViewHolder holder, int position) {
        Model_Time_table model_time_table=modelTimeTableArrayList.get(position);
      //  holder.txt_time.setText(modelTimeTableArrayList.get(position).getTime_from()+ "-"+modelTimeTableArrayList.get(position).getTime_to());
        holder.txt_subject.setText(modelTimeTableArrayList.get(position).getSubject());
        holder.txt_subject_teacher.setText(modelTimeTableArrayList.get(position).getTeacher());
        if(modelTimeTableArrayList.get(position).getBreak_yn().contentEquals("yes"))
        {
            holder.txt_break.setVisibility(View.VISIBLE);
            holder.txt_break.setText("Break");
        }
//        for (String retval: modelTimeTableArrayList.get(position).getTime().split(" ",3)) {
//            Log.d("split=>",retval);
//        }
        String currentString = modelTimeTableArrayList.get(position).getTime_from();
        String currentString1 = modelTimeTableArrayList.get(position).getTime_to();
        String[] separated = currentString.split(" ");
        String[] separated1 = currentString1.split(" ");
        holder.tv_from.setText(separated[0]);
        holder.tv_am.setText(separated[1]);

        holder.tv_to.setText(separated1[0]);
        holder.tv_pm.setText(separated1[1]);
    }

    @Override
    public int getItemCount() {
        return modelTimeTableArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_time,txt_subject,txt_subject_teacher,txt_break;
        TextView tv_from,tv_am,tv_to,tv_pm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_subject=itemView.findViewById(R.id.txt_subject);
           // txt_time=itemView.findViewById(R.id.txt_time);
            txt_subject_teacher=itemView.findViewById(R.id.txt_subject_teacher);
            txt_break=itemView.findViewById(R.id.txt_break);
            tv_from=itemView.findViewById(R.id.tv_from);
            tv_am=itemView.findViewById(R.id.tv_am);
            tv_to=itemView.findViewById(R.id.tv_to);
            tv_pm=itemView.findViewById(R.id.tv_pm);
        }
    }
}
