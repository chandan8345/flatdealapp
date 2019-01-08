package com.chandan.stock.toletdhaka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PostView extends AppCompatActivity {
ProgressDialog progressDialog;Button callbtn;
TextView titleView,rentView,detailsView,roomForView,toletTypeView,monthView,locationView;
String mobileView;RelativeLayout view;
ImageView viewImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        progressDialog = new ProgressDialog(PostView.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        Intent intent = getIntent();
        String id = intent.getStringExtra("postId");
        String apiUrl = "https://ustock.000webhostapp.com/getPostId/"+id;
        initializedAll();
        String mobile=postView(apiUrl);
        call(mobile);
    }
    public void initializedAll(){
        view=(RelativeLayout) findViewById(R.id.first);
        viewImage=(ImageView) findViewById(R.id.singleImage);
        rentView=(TextView) findViewById(R.id.rentView);
        detailsView=(TextView) findViewById(R.id.detailsView);
        roomForView=(TextView) findViewById(R.id.roomforView);
        toletTypeView=(TextView) findViewById(R.id.tolettypeView);
        monthView=(TextView) findViewById(R.id.monthView);
        locationView=(TextView) findViewById(R.id.locationView);
        callbtn=(Button) findViewById(R.id.call);
    }
    public void call(final String mobile){
       callbtn.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               switch(event.getAction()){
                   case MotionEvent.ACTION_DOWN:
                        callbtn.getBackground().setAlpha(150);
                            Intent dialIntent = new Intent();
                            dialIntent.setAction(Intent.ACTION_CALL);
                            dialIntent.setData(Uri.parse("tel:"+mobile));
                            startActivity(dialIntent);
                       break;
                   case MotionEvent.ACTION_UP:
                       callbtn.getBackground().setAlpha(255);
                       break;
               }

               return false;
           }
       });
    }
    public String postView(String apiUrl) {
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(apiUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        view.setVisibility(View.VISIBLE);
                        rentView.setText(jsonObject.getString("rent"));
                        detailsView.setText(jsonObject.getString("details"));
                        mobileView=jsonObject.getString("mobile").toString();
                        roomForView.setText(jsonObject.getString("roomfor"));
                        toletTypeView.setText(jsonObject.getString("tolet_type"));
                        monthView.setText(jsonObject.getString("month"));
                        locationView.setText(jsonObject.getString("address"));
                        String _images=jsonObject.getString("image");
                        Picasso.with(PostView.this).load("http://ustock.000webhostapp.com/img/"+_images+".jpg").fit().centerCrop().into(viewImage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.hide();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });
        com.chandan.stock.toletdhaka.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
      return mobileView;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
