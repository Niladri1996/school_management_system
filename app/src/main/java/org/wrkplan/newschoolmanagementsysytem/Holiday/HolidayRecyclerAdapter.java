package org.wrkplan.newschoolmanagementsysytem.Holiday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Attendence.StudentAttendenceAdapter;
import org.wrkplan.newschoolmanagementsysytem.Model.Model_Holidays;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HolidayRecyclerAdapter extends RecyclerView.Adapter<HolidayRecyclerAdapter.ViewHolder> {

    ArrayList<Model_Holidays> model_holidaysArrayList=new ArrayList<>();
    Context context;

    public HolidayRecyclerAdapter(ArrayList<Model_Holidays> model_holidaysArrayList, Context context) {
        this.model_holidaysArrayList = model_holidaysArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolidayRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_holiday,parent,false);
        return new HolidayRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayRecyclerAdapter.ViewHolder holder, int position) {
        Model_Holidays model_holidays=model_holidaysArrayList.get(position);
        holder.txt_count.setText(position+1+"");
        holder.txt_holiday_name.setText(model_holidaysArrayList.get(position).getHoliday_name());
        holder.txt_total_holiday.setText(model_holidaysArrayList.get(position).getTotal_days());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
        Date myFromDate = null;
        Date myToDate = null;

        try {
            myFromDate = sdf.parse(model_holidaysArrayList.get(position).getFrom_date());
            myToDate = sdf.parse(model_holidaysArrayList.get(position).getTo_date());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern("dd-MMM-yyyy");
        //sdf.applyPattern("d MMM YYYY");
        String fromdate = sdf.format(myFromDate);
        String todate = sdf.format(myToDate);

        holder.txt_date.setText(fromdate+ " To "+todate);

    }

    @Override
    public int getItemCount() {
        return model_holidaysArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_count,txt_holiday_name,txt_date,txt_total_holiday,txt_days;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_count=itemView.findViewById(R.id.txt_count);
            txt_holiday_name=itemView.findViewById(R.id.txt_holiday_name);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_total_holiday=itemView.findViewById(R.id.txt_total_holiday);
            txt_days=itemView.findViewById(R.id.txt_days);

        }
    }
}
