package com.example.tarek.tourguideapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tarek.tourguideapp.R;
import com.example.tarek.tourguideapp.categories.CategoryItem;

import java.util.ArrayList;

public class CategoryItemAdapter extends ArrayAdapter<CategoryItem> {

    private Context mContext;

    public CategoryItemAdapter(@NonNull Context context, ArrayList<CategoryItem> categories) {
        super(context, 0, categories);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        ViewHolderItem viewHolderItem;
        if (rootView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rootView = inflater.inflate(R.layout.item_list_view, parent, false);

            //link views by viewHolder
            viewHolderItem = new ViewHolderItem();
            viewHolderItem.categoryName = rootView.findViewById(R.id.id_item_text);
            rootView.setTag(viewHolderItem);

        } else {
            viewHolderItem = (ViewHolderItem) rootView.getTag();
        }
        CategoryItem category = getItem(position);

        viewHolderItem.categoryName.setText(category.getCategoryName());
        viewHolderItem.categoryName.setCompoundDrawablesWithIntrinsicBounds(category.getCategoryImageResourceID(), 0, 0, 0);

        return rootView;
    }


    private static class ViewHolderItem {
        TextView categoryName;
    }
}
