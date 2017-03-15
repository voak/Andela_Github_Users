package com.voak.githubusers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    private ImageView _avatar;
    private Button _url;
    private TextView _username;
    private String username;
    String url;
    byte[] byteArray;
    Bitmap avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("Username");
        url = extras.getString("URL");
        byteArray = extras.getByteArray("Avatar");
        avatar = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        _avatar = (ImageView) findViewById(R.id.profile_avatar);
        _url = (Button) findViewById(R.id.profile_url);
        _username = (TextView) findViewById(R.id.profile_username);

        _avatar.setImageBitmap(avatar);
        _username.setText(username);
        _url.setText(url);
    }

    public void Share(View view)
    {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @<"+username+">, <"+url+">.");
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    public void OpenURL(View view)
    {
        Uri link = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, link);
        startActivity(intent);
    }
}
