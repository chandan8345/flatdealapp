package com.chandan.stock.toletdhaka;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class post extends AppCompatActivity {
Intent intent;ListView listView;
PostItem postAdapter;
String [] postTitle;
String [] postID;
String [] postMonth;
String [] postSubarea;
String [] postRent;
String [] postMonth1;
String [] postSubarea1;
String [] postRent1;
String [] postType;
String [] postType1;
String [] postimages;
String [] postTitle1;
String [] postID1;
String [] postimages1;
String Intenttitle;
CheckConnection checkConnection;
ArrayList<String> t = new ArrayList<String>();
ArrayList<String> postrent = new ArrayList<String>();
    ArrayList<String> postsubarea = new ArrayList<String>();
    ArrayList<String> postmonth = new ArrayList<String>();
    ArrayList<String> posttype = new ArrayList<String>();
ArrayList<String> id = new ArrayList<String>();
ArrayList<String> arrayimage = new ArrayList<String>();
//public static int[] images={R.drawable.hostel};
ProgressDialog progressDialog;
EditText searchTitle,searchStart,searchEnd;
Spinner searchSubarea,searchRomfor,searchMonth;
String area,month,room,start,end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        intent = getIntent();
        String apiUrl = "";
        Intenttitle = intent.getStringExtra("intentTitle");
        if(Intenttitle.equals("Hostel")){
            apiUrl="https://ustock.000webhostapp.com/getHostel";
        }else if(Intenttitle.equals("Mess")){
            apiUrl="https://ustock.000webhostapp.com/getMess";
        }else if(Intenttitle.equals("Flat")){
            apiUrl="https://ustock.000webhostapp.com/getFlat";
        }else if(Intenttitle.equals("Sublet")){
            apiUrl="https://ustock.000webhostapp.com/getSublet";
        }else if(Intenttitle.equals("Workplace")){
            apiUrl="https://ustock.000webhostapp.com/getWorkplace";
        }else if(Intenttitle.equals("Garage")){
            apiUrl="https://ustock.000webhostapp.com/getGarage";
        }else{

        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Intenttitle);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_post));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent=new Intent(post.this,MyPost.class);
                startActivity(intent);
            }
        });
        listView=findViewById(R.id.list_View);
        progressDialog = new ProgressDialog(post.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        checkConnection=new CheckConnection();
        if(checkConnection.isConnected(post.this)) {
            myTitle(t, id,postsubarea,postmonth,postrent, arrayimage,posttype, apiUrl);
        }
    }

    public void openDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(post.this);
        View view=getLayoutInflater().inflate(R.layout.layout_dialog,null);
        searchStart= view.findViewById(R.id.searchStart);
        searchMonth=view.findViewById(R.id.searchMonth);
        searchEnd=view.findViewById(R.id.searchEnd);
        searchSubarea=view.findViewById(R.id.searchSubarea);
        searchRomfor=view.findViewById(R.id.searchRoomfor);
        if(area != null){
            selectValue(searchSubarea,area);
        }
        if(room != null){
            selectValue(searchRomfor,room);
        }
        if(start != null){
            selectText(searchStart,start);
        }
        if(end != null){
            selectText(searchEnd,end);
        }
        if(month != null){
            selectValue(searchMonth,month);
        }
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                start=searchStart.getText().toString();
                end=searchEnd.getText().toString();
                area=searchSubarea.getSelectedItem().toString();
                month=searchMonth.getSelectedItem().toString();
                room=searchRomfor.getSelectedItem().toString();
                posttype.clear();t.clear();id.clear();arrayimage.clear();postsubarea.clear();postmonth.clear();postrent.clear();
                if(checkConnection.isConnected(post.this)) {
                    apiSearch(start, end,month, area, room, t, id,postsubarea,postmonth,postrent, arrayimage,posttype);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setTitle("Filter Options");
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void selectText(EditText editText, String value) {
        editText.setText(value);
    }

    public void apiSearch(final String start, final String end, final String month, final String subarea, final String roomfor, final ArrayList<String> ex, final ArrayList<String> ex1, final ArrayList<String> a, final ArrayList<String> m, final ArrayList<String> r, final ArrayList<String> arrayimage, final ArrayList<String> posttype) {
        progressDialog.show();
        final String wurl = "https://ustock.000webhostapp.com/searchApi.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, wurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                if(!response.equals("null")){
                if(checkConnection.isConnected(post.this)) {
                    response(response, ex, ex1,a,m,r, arrayimage,posttype);
                }
                }else{
                    listView.setAdapter(null);
                    Toast.makeText(post.this,"Sorry To-let not found",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> parr = new HashMap<String, String>();
                parr.put("s", subarea);
                parr.put("r", roomfor);
                parr.put("m",month);
                parr.put("start",start);
                parr.put("end",end);
                parr.put("t", Intenttitle);
                return parr;
            }

        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void response(String response,ArrayList<String> ex,ArrayList<String> ex1,ArrayList<String> a,ArrayList<String> m,ArrayList<String> r,ArrayList<String> arrayimage,ArrayList<String> posttype) {
        try {
            int k=0;
            JSONArray array = new JSONArray(response);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String name = jsonObject.getString("title");
                String id = jsonObject.getString("id");
                String image = jsonObject.getString("image");
                String rent= jsonObject.getString("rent");
                String subarea= jsonObject.getString("subarea");
                String month=jsonObject.getString("month");
                String type=jsonObject.getString("roomfor");
                ex.add(name);
                ex1.add(id);
                m.add(month);
                a.add(subarea);
                r.add(rent);
                posttype.add(type);
                arrayimage.add(image);
                k++;
            }
            progressDialog.hide();
            postimages1 = new String[arrayimage.size()];
            postimages1 = arrayimage.toArray(postimages1);
            postSubarea1 = new String[a.size()];
            postSubarea1 = a.toArray(postSubarea1);
            postMonth1= new String[m.size()];
            postMonth1 = m.toArray(postMonth1);
            postRent1 = new String[r.size()];
            postRent1 = r.toArray(postRent1);
            postType1 = new String[posttype.size()];
            postType1 = posttype.toArray(postType1);
            postID1 = new String[ex1.size()];
            postID1 = ex1.toArray(postID1);
            postTitle1 = new String[ex.size()];
            postTitle1 = ex.toArray(postTitle1);
            PostItem pAdapter = new PostItem(postID1, postTitle1, postimages1,postSubarea1,postMonth1,postRent1,postTitle1, post.this);
            listView.setAdapter(pAdapter);
          }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> myTitle(final ArrayList<String> ex, final ArrayList<String> ex1, final ArrayList<String> a, final ArrayList<String> m, final ArrayList<String> r,final ArrayList<String> arrayimage,final ArrayList<String> posttype, String apiUrl) {
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(apiUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String name=jsonObject.getString("title");
                        String id=jsonObject.getString("id");
                        String image=jsonObject.getString("image");
                        String rent= jsonObject.getString("rent");
                        String subarea= jsonObject.getString("subarea");
                        String mon=jsonObject.getString("month");
                        String type=jsonObject.getString("roomfor");
                        m.add(mon);
                        a.add(subarea);
                        r.add(rent);
                        ex.add(name);
                        ex1.add(id);
                        posttype.add(type);
                        arrayimage.add(image);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                progressDialog.hide();
                int j=0;
                if(j < response.length()) {
                    postimages=new String[arrayimage.size()];
                    postimages=arrayimage.toArray(postimages);
                    postID=new String[ex1.size()];
                    postID=ex1.toArray(postID);
                    postTitle = new String[ex.size()];
                    postTitle = ex.toArray(postTitle);
                    postSubarea = new String[a.size()];
                    postSubarea = a.toArray(postSubarea);
                    postMonth= new String[m.size()];
                    postMonth = m.toArray(postMonth);
                    postType = new String[posttype.size()];
                    postType = posttype.toArray(postType);
                    postRent = new String[r.size()];
                    postRent = r.toArray(postRent);
                    postAdapter = new PostItem(postID,postTitle,postimages,postSubarea,postMonth,postRent,postType,post.this);
                    listView.setAdapter(postAdapter);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });
        com.chandan.stock.toletdhaka.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return (ex);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.postingmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        if (id == R.id.btnSearch) {
            openDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
