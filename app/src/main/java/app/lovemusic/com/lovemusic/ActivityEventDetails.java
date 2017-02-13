package app.lovemusic.com.lovemusic;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import model.TopArtist;

public class ActivityEventDetails extends AppCompatActivity {

    private TopArtist topArtist;
    private TextView artistName;
    private TextView listeners;
    private TextView url;
    private TextView mbid;
    private NetworkImageView bandImage;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        //get Serializable
        topArtist = (TopArtist) getIntent().getSerializableExtra("artistObj");

        artistName = (TextView) findViewById(R.id.detsArtistName);
        bandImage = (NetworkImageView) findViewById(R.id.detsBandImage);
        listeners = (TextView) findViewById(R.id.detsListeners);
        url = (TextView) findViewById(R.id.detsUrl);
        mbid = (TextView) findViewById(R.id.detsMbid);

        artistName.setText("Aritist: " + topArtist.getArtist());
        bandImage.setImageUrl(topArtist.getBandImage(), imageLoader);
        listeners.setText("Listeners: " + topArtist.getListenerNum());
        url.setText("URL: " + topArtist.getUrl());
        mbid.setText("MBID: " + topArtist.getMbid());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_websiteID) {

            String url = topArtist.getUrl();

            if(!url.equals("")){

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);

            } else {

                Toast.makeText(getApplicationContext(), "No website avialable", Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
