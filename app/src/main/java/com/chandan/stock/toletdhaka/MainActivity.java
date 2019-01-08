package com.chandan.stock.toletdhaka;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.MediaMetadata;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        CardView office,flat,garage,luxury,rooms,factory,retail,land;
        ViewFlipper slider;
        Intent intent;ProgressDialog progressDialog;
        TextView article;
         CheckConnection checkConnection;//boolean doubleTap=false;
        public int[] images={R.drawable.l1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkConnection=new CheckConnection();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
        article.setSelected(true);
        flat();office();luxury();factory();retail();rooms();garage();garage();
        for (int i=0;i<images.length;i++){
            slider(images[i]);
        }
    }

    public void slider(int images){
        ImageView imageView=new ImageView(MainActivity.this);
        imageView.setBackgroundResource(images);
        slider.addView(imageView);
        slider.setFlipInterval(5000);
        slider.setAutoStart(true);
        //slider.setOutAnimation(MainActivity.this,android.R.anim.slide_out_right);
        //slider.setInAnimation(MainActivity.this,android.R.anim.slide_in_left);
    }

    public void flat(){
        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getHostel", "Flat | Apertment");
                    }
            }
        });
    }
    public void office(){
        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getMess","Office Space");}
            }
        });
    }
    public void luxury(){
        luxury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getFlat","Luxury House");}
            }
        });
    }
    public void factory(){
        factory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getSublet", "Factory | Warehouse");
                    }
            }
        });
    }
    public void retail(){
        retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getGarage","Retail Space");}
            }
        });
    }
    public void land(){
        land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getGarage","Land | Plot");}
            }
        });
    }
    public void rooms(){
        rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getGarage","Rooms");
                    }
            }
        });
    }
    public void garage(){
        garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getGarage","Garage");
                    }
            }
        });
    }
/*    public void office(){
        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doubleTap){
                    if(checkConnection.isConnected(MainActivity.this)) {
                        progressDialog.show();
                        hasPost("https://ustock.000webhostapp.com/getWorkplace", "Workplace");
                    }
                }else{
                    doubleTap=true;
                    Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleTap=false;
                        }
                    },500);
                }
            }
        });
    }*/
    public void init(){
        article=findViewById(R.id.article);
        flat=findViewById(R.id.flat);
        office=findViewById(R.id.office);
        luxury=findViewById(R.id.luxury);
        factory=findViewById(R.id.factory);
        retail=findViewById(R.id.retail);
        land=findViewById(R.id.land);
        rooms=findViewById(R.id.rooms);
        garage=findViewById(R.id.garage);
        slider=findViewById(R.id.slider);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
    }

    public void hasPost(String testUrl,final String category) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(testUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int j=0;
                if(j == response.length()) {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this,"Sorry, TO-LET NOT FOUND",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.hide();
                    Intent intent=new Intent(MainActivity.this,post.class);
                    final Intent intent1 = intent.putExtra("intentTitle", category);
                    startActivity(intent);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });
        com.chandan.stock.toletdhaka.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
             startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
