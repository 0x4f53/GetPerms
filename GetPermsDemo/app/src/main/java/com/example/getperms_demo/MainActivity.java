package com.example.getperms_demo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText)findViewById(R.id.packagenameinput);
        TextView requested_specific_output = (TextView)findViewById(R.id.method3output);
        editText.setOnKeyListener((View.OnKeyListener) (v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String package_name = editText.getText().toString();
                GetPerms gp = new GetPerms(getApplicationContext());
                requested_specific_output.setText(gp.getRequested(package_name).toString());
            }
            return false;
        });
        TextView requested_all_output = (TextView)findViewById(R.id.method1output);
        GetPerms gp = new GetPerms(getApplicationContext());
        requested_all_output.setText(gp.getRequested().toString());
    }
}