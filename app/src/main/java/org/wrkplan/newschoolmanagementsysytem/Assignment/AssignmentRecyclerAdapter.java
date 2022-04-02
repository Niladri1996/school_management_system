package org.wrkplan.newschoolmanagementsysytem.Assignment;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Model.Model_Assignment;
import org.wrkplan.newschoolmanagementsysytem.R;
import org.wrkplan.newschoolmanagementsysytem.StudyMaterial.SubjectDetailsRecyclerAdapter;

import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class AssignmentRecyclerAdapter extends RecyclerView.Adapter<AssignmentRecyclerAdapter.ViewHolder> {

    ArrayList<Model_Assignment>model_assignmentArrayList=new ArrayList<>();
    Context context;

    public AssignmentRecyclerAdapter(ArrayList<Model_Assignment> model_assignmentArrayList, Context context) {
        this.model_assignmentArrayList = model_assignmentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AssignmentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_assignment_layout,parent,false);
        return new AssignmentRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentRecyclerAdapter.ViewHolder holder, int position) {

        Model_Assignment model_assignment=model_assignmentArrayList.get(position);
        holder.txt_count.setText(position+1+"");
        holder.txt_title.setText(model_assignmentArrayList.get(position).getTitle());
        holder.txt_last_date.setText(model_assignmentArrayList.get(position).getLast_date());

        holder.img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileUrl = (model_assignmentArrayList.get(position).getFilelink());

                Log.d("fileurl=>",fileUrl);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl))
                        .setTitle(context.getString(R.string.app_name))
                        .setDescription("Downloading " + fileUrl)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileUrl);
                DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model_assignmentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title,txt_last_date,txt_count;
        ImageView img_download;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title=itemView.findViewById(R.id.txt_title);
            txt_count=itemView.findViewById(R.id.txt_count);
            txt_last_date=itemView.findViewById(R.id.txt_last_date);
            img_download=itemView.findViewById(R.id.img_download);
        }
    }
}
