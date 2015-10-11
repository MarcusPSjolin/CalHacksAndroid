package com.marcussjolin.calhacks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private ViewHolder mViewHolder;
    private ArrayList<String> mObjects;
    private LayoutInflater mInflater;

    public GridAdapter(final Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mObjects != null) {
            return mObjects.size();
        }
        return 0;
    }

    @Override
    public Object getItem(final int position) {
        if (mObjects != null) {
            return mObjects.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.item_image);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.item_title);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public void updateObjects(ArrayList<String> objects) {
        mObjects = objects;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        ImageView image;
        TextView title;
    }
}
