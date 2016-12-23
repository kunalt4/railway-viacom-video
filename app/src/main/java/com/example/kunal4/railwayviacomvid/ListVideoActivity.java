package com.example.kunal4.railwayviacomvid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListVideoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private Config config;

    //public String[] video_list_name,video_list_links,video_list_ratings,video_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("APP","IN");




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        getJSON();

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
        getMenuInflater().inflate(R.menu.list_video, menu);
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




    private void getJSON() {

        class GetJson extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.d("DATA----------->",s);
                parseJSON(s);
                ;

            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader;
                try {
                    URL url = new URL("http://192.168.0.106/viacom-railway/php/get_files_list.php");
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();


                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    Log.d("JSON---->",sb.toString().trim());
                    return sb.toString().trim();


                }catch(Exception e){
                    Log.d("Exception",e+"");
                    return e+"a";
                }

            }

        }


        GetJson gJ = new GetJson();
        gJ.execute();

    }

    public void showData(){


        adapter = new CardAdapter(ListVideoActivity.this, Config.names,Config.ratings, Config.bitmaps, Config.links);
        ItemClickSupport.addTo(recyclerView, ListVideoActivity.this)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // do it
                    }
                });
        recyclerView.setAdapter(adapter);




//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, video_list_name);
//        listView = (ListView) findViewById(R.id.list_videos);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String message =  parent.getAdapter().getItem(position).toString();
//                String link = video_list_links[position];
//                Intent intent = new Intent(ListVideoActivity.this,VideoPlayerActivity.class);
//                intent.putExtra("LINK",link);
//                startActivity(intent);
//
//
//
//                Log.d("IN check",link);
//
//
//            }
//        });

    }




    private void parseJSON(String json){
        try {


            JSONArray jsonArray = new JSONArray(json);
            Log.d("JSONARRAY",jsonArray+"");
//            video_list_name = new String[jsonArray.length()];
//            video_list_links = new String[jsonArray.length()];
//            video_list_ratings = new String[jsonArray.length()];

            config = new Config(jsonArray.length());

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject k = jsonArray.getJSONObject(i);

                Config.names[i] = getName(k);
                Config.links[i] = getLink(k);
                Config.ratings[i] = getRating(k);

            }


            GetBitmap gb = new GetBitmap(this,this, Config.links);
            gb.execute();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String getName(JSONObject j){
        String category = null;
        try {
            category = j.getString(Config.TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return category;
    }
    private String getLink(JSONObject j){
        String category = null;
        try {
            category = j.getString(Config.TAG_LINK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return category;
    }
    private String getRating(JSONObject j){
        String category = null;
        try {
            category = j.getString(Config.TAG_RATING);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return category;
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


