package com.voak.githubusers;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Voak on 08-Mar-17.
 */

import android.graphics.Bitmap;

public class ImageList extends BaseAdapter {

    private Activity context;
    private ArrayList<HashMap<String, Bitmap>> users;
    public ImageBuilder imageBuilder;

    public ImageList(Activity context, ArrayList<HashMap<String, Bitmap>> users)
    {
        this.context = context;
        this.users =  users;
        imageBuilder = new ImageBuilder();
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        HashMap<String,Bitmap> userdata = new HashMap<String,Bitmap>();
        userdata = users.get(position);
        String username = userdata.keySet().toArray()[0].toString();
        Bitmap avatar = userdata.get(username);

        if (view == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.list_item, null);
            TextView usernameView = (TextView) view.findViewById(R.id.list_username);
            usernameView.setText(username.toUpperCase());
            ImageView avatarView = (ImageView) view.findViewById(R.id.list_avatar);
            avatarView.setImageBitmap(avatar);
        }

        return view;
    }

    public int getCount() {
        return users.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
}
