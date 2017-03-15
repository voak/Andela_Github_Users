package com.voak.githubusers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressBar;
    private ListView usersList;
    private ObtainList getList;
    private ArrayList<HashMap<String,Bitmap>> useritem;
    private HashMap<String,String> userurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.show();

        userurl = new HashMap<String, String>();
        useritem = new ArrayList<HashMap<String, Bitmap>>();

        usersList = (ListView) findViewById(R.id.userList);

        getList = new ObtainList();
        getList.execute("https://api.github.com/search/users?q=+location:lagos+language:java");


    }

    public void buildList(final JSONArray jsonArray)
    {
        ArrayList<HashMap<String,Bitmap>> _usersArray = new ArrayList<HashMap<String,Bitmap>>();

        try
        {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, Bitmap> listitem = new HashMap<String, Bitmap>();
                ImageBuilder imageBuilder = new ImageBuilder();

                JSONObject item = jsonArray.getJSONObject(i);
                String username = item.getString("login");
                String avatar_url = item.getString("avatar_url");
                String profile_url = item.getString("html_url");

                Bitmap bmp = imageBuilder.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, avatar_url).get();

                userurl.put(username,profile_url);
                listitem.put(username, bmp);
                _usersArray.add(listitem);
            }

            useritem = _usersArray;
            ImageList adapter = new ImageList(MainActivity.this, useritem);
            usersList.setAdapter(adapter);

            usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                {
                    HashMap<String, Bitmap> listitem = useritem.get(position);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    String username = listitem.keySet().toArray()[0].toString();
                    String profile_url = userurl.get(username);
                    Bitmap avatar = listitem.get(username);
                    avatar.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Intent nextPage = new Intent(getApplicationContext(), UserActivity.class);
                    nextPage.putExtra("Username", username);
                    nextPage.putExtra("URL", profile_url);
                    nextPage.putExtra("Avatar", byteArray);
                    startActivity(nextPage);
                }
            });

        }
        catch (JSONException ex)
        {
            Log.e("Read Users", ex.getMessage());
        }
        catch(ExecutionException ee)
        {
            Log.e("Execution Exception", ee.getMessage());
        }
        catch (InterruptedException ie)
        {
            Log.e("Interrupt Exception", ie.getMessage());
        }
    }

    private class ObtainList extends AsyncTask<String, Integer, JSONArray>
    {
        protected JSONArray doInBackground(String... link)
        {
            HttpURLConnection urlConnection;
            BufferedReader bufferedReader;
            JSONArray jsonArrayResult = null;

            try
            {
                URL url= new URL(link[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(50000);
                urlConnection.setDoOutput(true);

                bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

                char[] buffer = new char[1024];
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                jsonArrayResult = jsonObject.getJSONArray("items");

            }
            catch (IOException io)
            {
                Log.e("IO Error", io.getMessage());
            }
            catch (JSONException json)
            {
                Log.e("JSON Exception", json.getMessage());
            }

            return jsonArrayResult;
        }

        protected void onProgressUpdate(Integer... progress)
        {
            progressBar.setProgress(progress[0]);
        }

        protected void onPostExecute(JSONArray result)
        {
            progressBar.hide();
            buildList(result);
        }
    }
}
