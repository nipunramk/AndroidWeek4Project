package com.example.nipunramk.androidweek4project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by nipun.ramk on 3/10/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> {
    private Context context;
    private List<ParseObject> locationsArray;

    public Adapter(Context context, List<ParseObject> locations) {
        this.context = context;
        locationsArray = locations;
    }

    public void setList(List<ParseObject> list) {
        locationsArray = list;
    }

    /* In simplified terms, a ViewHolder is an object that holds the pointers to the views in each
    each row. What does that mean? Every row has a TextView, ImageView, and CheckBox. Each row has
    a ViewHolder, and that ViewHolder holder these 3 views in it (hence "view holder").
    This function returns a single ViewHolder; it is called once for every row.
    */
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        This "inflates" the views, using the layout R.layout.row_view
        // TODO: 2/26/16 Insert the correct parameter below. You can use Google or other resources to see what belongs there.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }

    /* This function takes the previously made ViewHolder and uses it to actually display the
    data on the screen. Remember how the holder contains (pointers to) the 3 views? By doing, for
    example, "holder.imageView" we are accessing the imageView for that row and setting the
    ImageResource to be the corresponding image for that subject.
     */
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        ParseObject location = locationsArray.get(position);
        ParseFile parseFile = (ParseFile) location.get("image");
        if (parseFile != null) {
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    holder.imageView.setImageBitmap(bitmap);
                }
            });
        }

        holder.locationNameTextView.setText(location.getString("name"));
        holder.locationDescription.setText(location.getString("description"));


        // TODO: 2/26/16 Finish this method. Use the line above as a guide (I was tempted to remove it, but you're welcome!)

    }

    @Override
    public int getItemCount() {
        // TODO: 2/26/16 Finish this method (hint, it's 1 line long)
        return locationsArray.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView locationNameTextView;
        TextView locationDescription;
        ImageView imageView;


        public CustomViewHolder (View view) {
            super(view);
            this.locationNameTextView = (TextView) view.findViewById(R.id.locationNameTextView);
            this.locationDescription = (TextView) view.findViewById((R.id.locationNameDescription));
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            // TODO: 2/26/16 Finish the constructor, using the line above as reference.
            /*Think about what we said in the comment above onCreateViewHolder to determine the
            purpose of the ViewHolder. Does it make sense why we are doing this in the constructor?
            */

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.createUpdateDialog(locationNameTextView.getText().toString(), locationDescription.getText().toString());
                    Log.i("onClick", getAdapterPosition() + "");
                }
            });
        }
    }
}
