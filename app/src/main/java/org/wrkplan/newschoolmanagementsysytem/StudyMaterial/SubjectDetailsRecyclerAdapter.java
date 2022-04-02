package org.wrkplan.newschoolmanagementsysytem.StudyMaterial;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.wrkplan.newschoolmanagementsysytem.Model.Model_Subject_Detail;
import org.wrkplan.newschoolmanagementsysytem.R;

import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class SubjectDetailsRecyclerAdapter extends RecyclerView.Adapter<SubjectDetailsRecyclerAdapter.ViewHolder> {

    ArrayList<Model_Subject_Detail>model_subject_detailArrayList=new ArrayList<>();
    Context context;

    public SubjectDetailsRecyclerAdapter(ArrayList<Model_Subject_Detail> model_subject_detailArrayList, Context context) {
        this.model_subject_detailArrayList = model_subject_detailArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectDetailsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subject_details,parent,false);
        return new SubjectDetailsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectDetailsRecyclerAdapter.ViewHolder holder, int position) {

        Model_Subject_Detail model_subject_detail=model_subject_detailArrayList.get(position);
        holder.txt_count.setText(position+1+"");
        holder.txt_title.setText(model_subject_detailArrayList.get(position).getTitle());
        holder.txt_msg.setText(HtmlCompat.fromHtml(model_subject_detailArrayList.get(position).getDetails(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileUrl = (model_subject_detailArrayList.get(position).getFilelink());

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
        return model_subject_detailArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_count,txt_title,txt_msg;
        ImageView img_download;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_count=itemView.findViewById(R.id.txt_count);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_msg=itemView.findViewById(R.id.txt_msg);
            img_download=itemView.findViewById(R.id.img_download);
        }
    }
}
