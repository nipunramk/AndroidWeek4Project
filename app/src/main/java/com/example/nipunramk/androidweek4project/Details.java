package com.example.nipunramk.androidweek4project;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nipun.ramk on 3/11/2016.
 */
public class Details extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {

        setContentView(R.layout.row_view);
        TextView textViewDescription = (TextView) findViewById(R.id.locationNameDescription);
        textViewDescription.setVisibility(View.VISIBLE);
    }
}
