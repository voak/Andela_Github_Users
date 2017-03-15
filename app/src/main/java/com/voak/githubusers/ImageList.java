package com.voak.githubusers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Voak on 08-Mar-17.
 */

import android.graphics.Bitmap;

public class ImageList extends BaseAdapter {

    private Activity context;
    private ArrayList<HashMap<String, Bitmap>> users;

    public ImageList(Activity context, ArrayList<HashMap<String, Bitmap>> users)
    {
        this.context = context;
        this.users =  users;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        HashMap<String,Bitmap> userdata;
        TextView usernameView;
        ImageView avatarView;

        LayoutInflater inflater = context.getLayoutInflater();

        if (view == null)
        {
            view = inflater.inflate(R.layout.list_item, null);
            usernameView = (TextView) view.findViewById(R.id.list_username);
            avatarView = (ImageView) view.findViewById(R.id.list_avatar);

            userdata = users.get(position);

            for (String key: userdata.keySet())
            {
                Bitmap avatar = userdata.get(key);

                usernameView.setText(key.toUpperCase());
                avatarView.setImageBitmap(avatar);
            }

        }

        return view;
    }

    public int getCount() {
        return users.size();
    }

    public Object getItem(int position) {
        return users.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }
}
