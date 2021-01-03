package com.example.getperms_demo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText)findViewById(R.id.packagenameinput);
        TextView requested_specific_output = (TextView)findViewById(R.id.method1output);
        TextView granted_specific_output = (TextView)findViewById(R.id.method2output);
        TextView list_all_output = findViewById(R.id.method3output);
        TextView requested_all_output = findViewById(R.id.method4output);
        TextView granted_all_output = findViewById(R.id.method5output);
        GetPerms gp = new GetPerms(getApplicationContext());
        try {
            editText.setOnKeyListener((View.OnKeyListener) (v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String package_name = editText.getText().toString();
                    requested_specific_output.setText(gp.getRequested(package_name).toString());
                    granted_specific_output.setText(gp.getGranted(package_name).toString());
                }
                return false;
            });
            list_all_output.setText(gp.listPackages().toString());
            requested_all_output.setText(gp.getRequested().toString());
            granted_all_output.setText(gp.getGranted().toString());
        } catch (Exception ex) {
            Toast toast=Toast.makeText(getApplicationContext(),"Please check logcat",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}