package com.ocit.newsfeedlistview;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by ar-android on 04/11/2015.
 */
public class AdapterListview extends BaseAdapter{

    private LayoutInflater inflater;
    private final ArrayList<FeedModel> data;
    private Activity mActivity;

    public AdapterListview(Context context, ArrayList<FeedModel> data, Activity mActivity) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        v = inflater.inflate(R.layout.item_feed, null);

        if (inflater == null)
            inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_feed, null);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        ImageView profilePic = (ImageView) convertView
                .findViewById(R.id.profilePic);
        ImageView feedImageView = (ImageView) convertView
                .findViewById(R.id.feedImage1);

        name.setText(data.get(position).getName());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(data.get(position).getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(data.get(position).getStatus())) {
            statusMsg.setText(data.get(position).getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (data.get(position).getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + data.get(position).getUrl() + "\">"
                    + data.get(position).getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }


        String urlImage = data.get(position).getProfilePic();

        Glide.with(mActivity)
                .load(urlImage)
                .centerCrop()
                .crossFade()
                .into(profilePic);
        // user profile pic

        // Feed image
        if (data.get(position).getImge() != null) {
            String urlImg = data.get(position).getImge();
            Glide.with(mActivity)
                    .load(urlImg)
                    .into(feedImageView);
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
