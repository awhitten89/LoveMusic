package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import app.lovemusic.com.lovemusic.ActivityEventDetails;
import app.lovemusic.com.lovemusic.AppController;
import app.lovemusic.com.lovemusic.R;
import model.TopArtist;

/**
 * Created by alanwhitten on 21/10/2016.
 */

public class CustomListviewAdapter extends ArrayAdapter<TopArtist> {

    private LayoutInflater inflater;
    private ArrayList<TopArtist> data;
    private Activity mContext;
    private int layoutRecourceID;//id to the xml list row
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();//load images from api

    public CustomListviewAdapter(Activity context, int resource, ArrayList<TopArtist> objs) {
        super(context, resource, objs);
        data = objs;
        mContext = context;
        layoutRecourceID = resource;

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(TopArtist item) {
        return super.getPosition(item);
    }

    @Nullable
    @Override
    public TopArtist getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = null;

        if (row == null) {

            inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(layoutRecourceID, parent, false);

            viewHolder = new ViewHolder();

            //reference our views
            viewHolder.bandImage = (NetworkImageView) row.findViewById(R.id.bandImage);
            viewHolder.artist = (TextView) row.findViewById(R.id.artistText);
            viewHolder.listeners = (TextView) row.findViewById(R.id.listenerText);
            viewHolder.url = (TextView) row.findViewById(R.id.urlText);
            viewHolder.mbid = (TextView) row.findViewById(R.id.mbidText);

            row.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.topArtist = data.get(position);//pass all data from this position to the topArtist

        //now display the data
        viewHolder.artist.setText("Artist: " + viewHolder.topArtist.getArtist());
        viewHolder.listeners.setText("Listeners: " + viewHolder.topArtist.getListenerNum());
        viewHolder.url.setText(viewHolder.topArtist.getUrl());
        viewHolder.mbid.setText("MBID: " + viewHolder.topArtist.getMbid());
        viewHolder.bandImage.setImageUrl(viewHolder.topArtist.getBandImage(), imageLoader);

        final ViewHolder finalViewHolder = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, ActivityEventDetails.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("artistObj", finalViewHolder.topArtist);
                i.putExtras(mBundle);
                mContext.startActivity(i);
            }
        });
        return row;
    }

    /**
     * Allows us to recycle views to improve performance
     */
    public class ViewHolder{

        TopArtist topArtist;
        TextView artist;
        TextView listeners;
        TextView url;
        TextView mbid;
        NetworkImageView bandImage;
    }
}
