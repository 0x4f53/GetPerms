/* ******************************************************************************
 * MIT License
 *
 * Copyright (c) 2020 Owais Shaikh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package com.owais.getperms_demo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.owais.getperms.GetPerms;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText appNameInput = findViewById(R.id.appNameInput);
        EditText appIDInput = findViewById(R.id.appIDInput);
        EditText permissionNameInput = findViewById(R.id.permissionNameInput);

        TextView getAppNameOutput = findViewById(R.id.getAppNameOutput);
        TextView getAppIDOutput = findViewById(R.id.getAppIDOutput);
        TextView getSignatureOutput = findViewById(R.id.getSignatureOutput);
        TextView getRequestedOutput = findViewById(R.id.getRequestedOutput);
        TextView getGrantedOutput = findViewById(R.id.getGrantedOutput);
        TextView noOfAppsOutput = findViewById(R.id.noOfAppsOutput);
        TextView listAppsByIDOutput = findViewById(R.id.getAppIDAllOutput);
        TextView getAppIDAllOutput = findViewById(R.id.getRequestedAllOutput);
        TextView getGrantedAllOutput = findViewById(R.id.getGrantedAllOutput);
        TextView appsRequestingOutput = findViewById(R.id.appsRequestingOutput);
        TextView appsGrantedOutput = findViewById(R.id.appsGrantedOutput);

        Button otherFunctionsButton = findViewById(R.id.otherFunctionsButton);

        GetPerms gp = new GetPerms(this);

        appNameInput.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String appName = appNameInput.getText().toString();
                getAppIDOutput.setText(gp.getAppID(appName));
            }
            return false;
        });

        appIDInput.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String appID = appIDInput.getText().toString();
                getAppNameOutput.setText(gp.getAppName(appID));
                getSignatureOutput.setText(gp.getSignature(appID));
                getRequestedOutput.setText(gp.getRequested(appID).toString());
                getGrantedOutput.setText(gp.getGranted(appID).toString());
            }
            return false;
        });

        permissionNameInput.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String permissionName = permissionNameInput.getText().toString();
                appsRequestingOutput.setText(gp.appsRequesting(permissionName).toString());
                appsGrantedOutput.setText(gp.appsGranted(permissionName).toString());
            }
            return false;
        });

        otherFunctionsButton.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this,"Please wait, this could take a while...", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            noOfAppsOutput.setText(String.valueOf(gp.noOfApps()));
            listAppsByIDOutput.setText(gp.getAppID().toString());
            getAppIDAllOutput.setText(gp.getRequested().toString());
            getGrantedAllOutput.setText(gp.getGranted().toString());
        });
    }
}