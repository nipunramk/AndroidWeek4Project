package com.example.nipunramk.androidweek4project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.parse.ParseObject;

public class AddDetail extends AppCompatActivity {

    public EditText editText;
    public static final String NAME_KEY = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        editText = (EditText) findViewById(R.id.editText);

    }

    @Override
    protected void onStop() {
        super.onStop();
        ParseObject parseObject = new ParseObject("Img");
        parseObject.put(NAME_KEY, editText.getText());
    }
}
