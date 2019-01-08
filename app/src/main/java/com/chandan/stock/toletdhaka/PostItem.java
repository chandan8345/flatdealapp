package com.chandan.stock.toletdhaka;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostItem extends BaseAdapter {
    String [] postTitle;
    String [] postID;
    Context context;
    String [] postImages;
    String [] postMonth;
    String [] postSubarea;
    String [] postRent;
    String [] postType;
    Intent intent;
    public static LayoutInflater inflater=null;

    public PostItem(String[] postID,String[] postTitle,String[] postImages,String[] postSubarea,String[] postMonth,String[] postRent,String[] postType,Context context) {
        this.postTitle = postTitle;
        this.context = context;
        this.postImages = postImages;
        this.postID =postID;
        this.postSubarea=postSubarea;
        this.postMonth=postMonth;
        this.postRent=postRent;
        this.postType=postType;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return postTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Hodler{
        TextView title,month,subarea,rent,type;
        ImageView imageView;
        //CardView cardView;
        LinearLayout viewBtn;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if(view==null) {
            view = inflater.inflate(R.layout.list_style_2, null);
            Hodler hd = new Hodler();
            //hd.cardView=(CardView) view.findViewById(R.id.myCard);
            hd.title = (TextView) view.findViewById(R.id.t);
            hd.imageView = (ImageView) view.findViewById(R.id.postPhoto);
            hd.month=view.findViewById(R.id.m);
            hd.rent=view.findViewById(R.id.r);
            hd.subarea=view.findViewById(R.id.sa);
            hd.type=view.findViewById(R.id.type);
            hd.viewBtn=(LinearLayout) view.findViewById(R.id.postviewBtn);

            hd.title.setText(postTitle[position]);
            hd.subarea.setText(postSubarea[position]);
            hd.rent.setText(postRent[position]+"৳");
            hd.month.setText(postMonth[position]);
            hd.type.setText(postType[position]);
           // Picasso.with(context).load(imageID[0]).into(hd.imageView);
            Picasso.with(context).load("http://ustock.000webhostapp.com/img/"+postImages[position]+".jpg").fit().into(hd.imageView);
            //final String s=postTitle[position].toString();
            final String id=postID[position].toString();
            hd.viewBtn.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckConnection checkConnection=new CheckConnection();
                    if(checkConnection.isConnected(context)) {
                        Intent intent = new Intent(context, PostView.class);
                        Intent intent1 = intent.putExtra("postId", id);
                        context.startActivity(intent);
                    }
                }
            });

        }
        return view;
    }


}
