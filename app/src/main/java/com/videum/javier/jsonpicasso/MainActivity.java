package com.videum.javier.jsonpicasso;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    ProgressBar progressBar;
    ImageView imageView;
    TextView textViewName, textViewUserName;
    String[] objetos = new String[3];
    String id = "javiersegoviacordoba";
    String url1 = "http://graph.facebook.com/" + id;
    String url2 = "http://graph.facebook.com/" + id + "/picture?redirect=0&type=large";
    JSONObject jsonObjectTexts, jsonObjectPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTaskExample().execute(url1,url2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class AsyncTaskExample extends AsyncTask<String, String, String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(String... url) {

            try {
                jsonObjectTexts = JsonParser.readJsonFromUrl(url[0]);
                objetos[0] = jsonObjectTexts.getString("name");
                objetos[1] = jsonObjectTexts.getString("username");
                jsonObjectPicture = JsonParser.readJsonFromUrl(url[1]);
                objetos[2] = jsonObjectPicture.getJSONObject("data").getString("url");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return objetos;
        }

        @Override
        protected void onPostExecute(String[] stringFromDoInBackground) {

            textViewName = (TextView) findViewById(R.id.textViewName);
            textViewUserName = (TextView) findViewById(R.id.textViewUsername);
            imageView = (ImageView) findViewById(R.id.imageView);

            textViewName.setText(stringFromDoInBackground[0]);
            textViewUserName.setText(stringFromDoInBackground[1]);
            Picasso.with(MainActivity.this).load(stringFromDoInBackground[2]).placeholder(R.mipmap.ic_launcher).error(R.drawable.abc_btn_check_material).into(imageView);

            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }
    }
}
