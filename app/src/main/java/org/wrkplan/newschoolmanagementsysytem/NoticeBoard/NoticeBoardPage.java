package org.wrkplan.newschoolmanagementsysytem.NoticeBoard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.text.HtmlCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrkplan.newschoolmanagementsysytem.R;
import org.wrkplan.newschoolmanagementsysytem.SwipeGesture.OnSwipeTouchListener;
import org.wrkplan.newschoolmanagementsysytem.TimeTable.TimeTableActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoticeBoardPage extends AppCompatActivity {
    ImageView img_back,img_right,img_left;
    TextView txt_title,txt_date,webview;
    ArrayList<Model_Notice_Board>model_notice_boardArrayList=new ArrayList<>();
    int count=0;
    int arraysize;
    RelativeLayout rl_main;
    CoordinatorLayout co_main;
    private WebView wb;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.notice_board_activity);

        InitializeViews();
        getNotice(0);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        WebView myWebView = (WebView) findViewById(R.id.webview);
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
//        myWebView.loadUrl("http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=notice");


        findViewById(R.id.co_main).setOnTouchListener(new OnSwipeTouchListener(NoticeBoardPage.this) {

            public void onSwipeRight() {

                if (count > 0) {
                    count = count - 1;
                    getNotice(count);


                    if (count == 0) {
                        img_left.setVisibility(View.GONE);
                        img_right.setVisibility(View.VISIBLE);
                    }
                }
                img_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (count > 0) {
                            count = count - 1;
                            getNotice(count);


                            if (count == 0) {
                                img_left.setVisibility(View.GONE);
                                img_right.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });


            }

            public void onSwipeLeft() {


                img_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (count < arraysize - 1) {
                            count = count + 1;
                            getNotice(count);
                            //img_right.setVisibility(View.VISIBLE);
                            if (count == arraysize - 1) {
                                img_right.setVisibility(View.GONE);
                                img_left.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                });
                if (count < arraysize - 1) {
                    count = count + 1;
                    getNotice(count);
                    //img_right.setVisibility(View.VISIBLE);
                    if (count == arraysize - 1) {
                        img_right.setVisibility(View.GONE);
                        img_left.setVisibility(View.VISIBLE);
                    }
                }


            }
        });


    }




    private void getNotice(int count) {
        String url="http://14.99.211.61/school-ERP-Intro/api/api_student.php?action=notice";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    if(!model_notice_boardArrayList.isEmpty())
                    {
                        model_notice_boardArrayList.clear();
                    }
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("notice");
                    arraysize=jsonArray.length();
                    Log.d("arraysize=>",arraysize+"");
                    for(int i=count;i<jsonArray.length();i++)
                    {
                        JSONObject jb1=jsonArray.getJSONObject(count);
                        String id=jb1.getString("id");
                        String title=jb1.getString("title");
                        String msg=jb1.getString("msg");
                        String date=jb1.getString("date");

                        Model_Notice_Board model_notice_board=new Model_Notice_Board();
                        model_notice_board.setId(id);
                        model_notice_board.setTitle(title);
                        model_notice_board.setMsg(msg);
                        model_notice_board.setDate(date);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                        Date myFromDate = null;

                        try {
                            myFromDate = sdf.parse(date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        sdf.applyPattern("dd-MMM-yyyy");
                        //sdf.applyPattern("d MMM YYYY");
                        String noticedate = sdf.format(myFromDate);


                        model_notice_boardArrayList.add(model_notice_board);

                        txt_title.setText(title);

                        txt_date.setText(noticedate);

                       //webview.setText(Html.fromHtml(msg, Html.FROM_HTML_MODE_COMPACT));
                        webview.setText(HtmlCompat.fromHtml(msg, HtmlCompat.FROM_HTML_MODE_LEGACY));
                      //  webview.setTextSize(15);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NoticeBoardPage.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NoticeBoardPage.this, "could not connect to the server", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(NoticeBoardPage.this).add(stringRequest);
    }

    private void InitializeViews() {
        img_back=findViewById(R.id.img_back);
        txt_title=findViewById(R.id.txt_title);
        txt_date=findViewById(R.id.txt_date);
        rl_main=findViewById(R.id.rl_main);
        co_main=findViewById(R.id.co_main);
       webview=findViewById(R.id.webview);
        img_left=findViewById(R.id.img_left);
        img_right=findViewById(R.id.img_right);
    }

    class MyJavaScriptInterface {

        private Context ctx;
        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            System.out.println(html);
        }

    }

}
