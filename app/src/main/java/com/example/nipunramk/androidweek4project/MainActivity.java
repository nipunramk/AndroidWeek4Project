package com.example.nipunramk.androidweek4project;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static Context context;
    Activity activity;
    ImageView imageView;
    static Adapter locationAdaptor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Parse.initialize(getApplicationContext(), "IU2D1ej4N8QcYVLhzYLU5Kpmbz7j0N9KBauQ8a1A",
                "w6j6KHFyEi1LLCymib2XcKXr9D2LmsZ7qFVUhE6P");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        ParseQuery.getQuery("Img").findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                locationAdaptor = new Adapter(getApplicationContext(), objects);
                recyclerView.setAdapter(locationAdaptor);

            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ParseQuery.getQuery("Img").findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        locationAdaptor.setList(objects);
                        locationAdaptor.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });

                context = getApplicationContext();
                activity = MainActivity.this;
                imageView = (ImageView) findViewById(R.id.imageView);

//        ArrayList<Location> parseObjects = new ArrayList<>();


                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
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


            public void startDetailsActivity(Intent intent) {
//                if (Build.VERSION.SDK_INT > 21) {
//                    ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(activity, imageView, "agreedname");

                    startActivity(intent);

            }

        public static void search(String queryString) {
            ParseQuery query = ParseQuery.getQuery("Img");
            query.whereEqualTo("name", queryString);
            try {
                List<ParseObject> list = query.find();
                locationAdaptor.setList(list);
                locationAdaptor.notifyDataSetChanged();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public static void update(String name, final String newDescription) {
            ParseQuery query1 = ParseQuery.getQuery("Img");
            query1.whereEqualTo("name", name);
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0) {
                        ParseObject parseObject = objects.get(0);
                        parseObject.put("description", newDescription);


                        try {
                            parseObject.save();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }

        public static void createUpdateDialog(final String name, String currentDescription) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            final EditText editText = new EditText(builder.getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            editText.setLayoutParams(lp);
            editText.setText(currentDescription);
            editText.setSelection(currentDescription.length());
            builder.setView(editText);

            builder.setTitle("Enter new description")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            update(name, editText.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }




            }
