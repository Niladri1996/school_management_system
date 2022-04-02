package org.wrkplan.newschoolmanagementsysytem.Attendence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Model.Student_Attendence_Model;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentAttendenceAdapter extends RecyclerView.Adapter<StudentAttendenceAdapter.ViewHolder> {


    ArrayList<Student_Attendence_Model> attendenceModelArrayList=new ArrayList<>();
    Context context;

    public StudentAttendenceAdapter(ArrayList<Student_Attendence_Model> attendenceModelArrayList, Context context) {
        this.attendenceModelArrayList = attendenceModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAttendenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attendence_layout,parent,false);
        return new StudentAttendenceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAttendenceAdapter.ViewHolder holder, int position) {

        Student_Attendence_Model model=attendenceModelArrayList.get(position);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String inputTextFromDate = attendenceModelArrayList.get(position).getDate();

        Date fromDate = null, toDate = null;
        try {
            fromDate = inputFormat.parse(inputTextFromDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputTextFromDate = outputFormat.format(fromDate);



        holder.txt_date.setText(outputTextFromDate);
        holder.txt_p_a.setText(attendenceModelArrayList.get(position).getAttendance());

        if(attendenceModelArrayList.get(position).getAttendance().contentEquals("A"))
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
        return attendenceModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_date,txt_p_a;
        RelativeLayout rl_p_a;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_p_a=itemView.findViewById(R.id.txt_p_a);
            rl_p_a=itemView.findViewById(R.id.rl_p_a);
        }
    }
}
