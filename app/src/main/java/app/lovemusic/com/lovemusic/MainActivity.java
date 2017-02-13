package app.lovemusic.com.lovemusic;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.CustomListviewAdapter;
import model.TopArtist;
import util.Prefs;

public class MainActivity extends AppCompatActivity {

    private CustomListviewAdapter listviewAdapter;
    private ArrayList<TopArtist> topArtists = new ArrayList<>();
    private String urlLeft = "http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=";
    private String urlRight = "&api_key=8e1da063f24a2d28fc24374b038b7d59&format=json";
    private ListView listView;
    private TextView selectedCountry;
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView) findViewById(R.id.list);
        listviewAdapter = new CustomListviewAdapter(MainActivity.this, R.layout.list_row, topArtists);
        listView.setAdapter(listviewAdapter);

        Prefs prefs = new Prefs(MainActivity.this);
        String country = prefs.getCountry();

        selectedCountry = (TextView)findViewById(R.id.selectedLocationText);
        selectedCountry.setText("Selected Country: " + country);

        showTopArtists(country);

    }

    private void getTopArtists(String country){

        topArtists.clear();

        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Loading.....");
        progDialog.show();

        String finalUrl = urlLeft+country+urlRight;

        JsonObjectRequest eventsRequest = new JsonObjectRequest(Request.Method.GET,
                finalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hidePdialog();

                try {
                    JSONObject topArtistObj = response.getJSONObject("topartists");

                    JSONArray artistArray = topArtistObj.getJSONArray("artist");

                    for (int i = 0; i < artistArray.length(); i++){

                        //get artist name
                        JSONObject jsonObject = artistArray.getJSONObject(i);
                        String artistName = jsonObject.getString("name");

                        //get listeners
                        String listeners = jsonObject.getString("listeners");

                        //get url
                        String url = jsonObject.getString("url");

                        //get mbid
                        String mbid = jsonObject.getString("mbid");

                        //get url image
                        JSONArray imageArray = jsonObject.getJSONArray("image");
                        //get xl image position
                        JSONObject xlImage = imageArray.getJSONObject(3);
                        //get image url
                        String image = xlImage.getString("#text");

                        //set top artist object
                        TopArtist topArtist = new TopArtist();
                        topArtist.setArtist(artistName);
                        topArtist.setListenerNum(listeners);
                        topArtist.setUrl(url);
                        topArtist.setMbid(mbid);
                        topArtist.setBandImage(image);

                        //add to array
                        topArtists.add(topArtist);


                        Log.v("Url: ", mbid);
                    }

                    listviewAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hidePdialog();
            }
        });

        AppController.getInstance().addToRequestQueue(eventsRequest);
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
        if (id == R.id.change_location_id) {

            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInputDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change Country");

        final EditText countryInput = new EditText(MainActivity.this);
        countryInput.setInputType(InputType.TYPE_CLASS_TEXT);
        countryInput.setHint("United Kingdom");
        builder.setView(countryInput);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Prefs countryPreference = new Prefs(MainActivity.this);
                countryPreference.setCountry(countryInput.getText().toString());

                String newCountry = countryPreference.getCountry();

                selectedCountry.setText("Selected country: " + newCountry);

                //re-render everything
                showTopArtists(newCountry);
            }
        });
        builder.show();
    }

    private void showTopArtists(String newCountry) {

        getTopArtists(newCountry);
    }

    private void hidePdialog(){

        if(progDialog != null){
            progDialog.dismiss();
            progDialog = null;
        }
    }
}
