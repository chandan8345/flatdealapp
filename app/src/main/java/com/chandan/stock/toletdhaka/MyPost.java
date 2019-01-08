   package com.chandan.stock.toletdhaka;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

   public class MyPost extends AppCompatActivity {
   EditText _title,_rent,_details,_address,_mobile;
   Spinner _roomFor,_toletType,_month,_subarea;
   ImageView _imagePost;final int IMG_REQUEST=1;
   final int CAM_REQUEST=0;
   Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("POST TO-LET");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeAll();
    }
       private void initializeAll(){
            _title=findViewById(R.id.titlePost);
            _rent=findViewById(R.id.rentPost);
            _mobile=findViewById(R.id.mobilePost);
            _details=findViewById(R.id.detailsPost);
            _address=findViewById(R.id.addressPost);
            _roomFor=findViewById(R.id.roomforPost);
            _toletType=findViewById(R.id.tolettypePost);
            _month=findViewById(R.id.monthPost);
            _subarea=findViewById(R.id.subareaPost);
            _imagePost=(ImageView) findViewById(R.id.postImage);
       }

       public void submit(){
            String url="https://ustock.000webhostapp.com/postApi.php";
           StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   _imagePost.setImageResource(0);
                   _imagePost.setVisibility(View.GONE);
                   _title.setText("");
                   _mobile.setText("");
                   _address.setText("");
                   _month.setSelection(0);
                   _subarea.setSelection(0);
                   _toletType.setSelection(0);
                   _details.setText("");
                   _roomFor.setSelection(0);
                   _rent.setText("");
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {

               }
           }){
               protected Map<String,String> getParams () {
                   Map<String,String> parr=new HashMap<String,String>();
                   parr.put("title",_title.getText().toString());
                   parr.put("rent",_rent.getText().toString());
                   parr.put("mobile",_mobile.getText().toString());
                   parr.put("details",_details.getText().toString());
                   parr.put("roomfor",_roomFor.getSelectedItem().toString());
                   parr.put("tolet_type",_toletType.getSelectedItem().toString());
                   parr.put("month",_month.getSelectedItem().toString());
                   parr.put("subarea",_subarea.getSelectedItem().toString());
                   parr.put("address",_address.getText().toString());
                   parr.put("image",imageTostring(bitmap));
                   return parr;
               }

           };

           AppController.getInstance().addToRequestQueue(stringRequest);
           Toast.makeText(getApplicationContext(),"To-Let Post Successfully",Toast.LENGTH_SHORT).show();
       }

       @Override
       public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.mypostmenu, menu);
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
           if (id == R.id.post_submit) {
               if(_title.getText().toString().equals("")){
                   Toast.makeText(MyPost.this,"Post Title Required",Toast.LENGTH_SHORT).show();
               }else if (_rent.getText().toString().equals("")){
                   Toast.makeText(MyPost.this,"Rent Required",Toast.LENGTH_SHORT).show();
               }else if (_mobile.getText().toString().equals("")){
                   Toast.makeText(MyPost.this,"Mobile Number Required",Toast.LENGTH_SHORT).show();
               }
               else if (_details.getText().toString().equals("")){
                   Toast.makeText(MyPost.this,"Details Required",Toast.LENGTH_SHORT).show();
               }else if (_roomFor.getSelectedItem().toString().equals("")){
                   Toast.makeText(MyPost.this,"Room For Required",Toast.LENGTH_SHORT).show();
               }else if (_toletType.getSelectedItem().toString().equals("")){
                   Toast.makeText(MyPost.this,"To-let Type Required",Toast.LENGTH_SHORT).show();
               }else if (_month.getSelectedItem().toString().equals("")){
                   Toast.makeText(MyPost.this,"Month Required",Toast.LENGTH_SHORT).show();
               }else if (_subarea.getSelectedItem().toString().equals("")){
                   Toast.makeText(MyPost.this,"Subarea Required",Toast.LENGTH_SHORT).show();
               }else if (_address.getText().toString().equals("")){
                   Toast.makeText(MyPost.this,"Address Required",Toast.LENGTH_SHORT).show();
               }
               else{
                   CheckConnection checkConnection=new CheckConnection();
                   if(checkConnection.isConnected(MyPost.this)) {
                       submit();
                   }
               }
               return true;
           }
           if(id == R.id.pickImage){
                showPictureDialog();
               return true;
           }
           return super.onOptionsItemSelected(item);
       }

       private void showPictureDialog(){
           AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
           //pictureDialog.setTitle("Select Action");
           String[] pictureDialogItems = {
                   "Pick Photo from Gallery",
                   "Take Photo on Camera" };
           pictureDialog.setItems(pictureDialogItems,
                   new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           switch (which) {
                               case 0:
                                   gallery();
                                   break;
                               case 1:
                                   cammera();
                                   break;
                           }
                       }
                   });
           pictureDialog.show();
       }


       public void cammera(){
           Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
           startActivityForResult(intent, CAM_REQUEST);
       }

       public void gallery(){
           Intent imageintent = new Intent(Intent.ACTION_PICK,
                   android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           startActivityForResult(imageintent, IMG_REQUEST);
       }

       @Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data !=null ){
                Uri path=data.getData();
               try {
                   bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                   _imagePost.setImageBitmap(bitmap);
                   _imagePost.setVisibility(View.VISIBLE);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if(requestCode == CAM_REQUEST && resultCode == RESULT_OK && data !=null ){
                   //Uri path=data.getData();
                   bitmap= (Bitmap) data.getExtras().get("data");
                   _imagePost.setImageBitmap(bitmap);
                   _imagePost.setVisibility(View.VISIBLE);
           }
           super.onActivityResult(requestCode, resultCode, data);

       }

      private String imageTostring(Bitmap bitmap){
          ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
          bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
          byte[] imgbytes= byteArrayOutputStream.toByteArray();
          return Base64.encodeToString(imgbytes,Base64.DEFAULT);
      }
   }