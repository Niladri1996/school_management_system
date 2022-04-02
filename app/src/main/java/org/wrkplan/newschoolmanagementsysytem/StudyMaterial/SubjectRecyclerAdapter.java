package org.wrkplan.newschoolmanagementsysytem.StudyMaterial;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Attendence.StudentAttendenceAdapter;
import org.wrkplan.newschoolmanagementsysytem.Holiday.HolidayRecyclerAdapter;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Subject;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder> {

    ArrayList<Model_Subject>model_subjectArrayList=new ArrayList<>();
    Context context;
    public static String id="";
    public static String subject="";

    public SubjectRecyclerAdapter(ArrayList<Model_Subject> model_subjectArrayList, Context context) {
        this.model_subjectArrayList = model_subjectArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_study_material_layout,parent,false);
        return new SubjectRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerAdapter.ViewHolder holder, int position) {

        Model_Subject model_subject=model_subjectArrayList.get(position);
        holder.txt_serial_no.setText(position+1+"");
        holder.txt_sub_name.setText(model_subjectArrayList.get(position).getSubject_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=model_subjectArrayList.get(position).getSubject_id();
                subject=model_subjectArrayList.get(position).getSubject_name();

                Intent intent=new Intent(context,SubjectDocumentPage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model_subjectArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_serial_no,txt_sub_name;
        ImageView img_next;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_serial_no=itemView.findViewById(R.id.txt_serial_no);
            txt_sub_name=itemView.findViewById(R.id.txt_sub_name);
            img_next=itemView.findViewById(R.id.img_next);
        }
    }
}
