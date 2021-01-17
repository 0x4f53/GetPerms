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

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.owais.getperms.GetPerms;

import org.json.JSONException;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText appNameInput = findViewById(R.id.appSearchInput);
        EditText permissionNameInput = findViewById(R.id.permissionNameInput);

        TextView appNameOutput = findViewById(R.id.appNameOutput);
        TextView appIDOutput = findViewById(R.id.appIDOutput);
        ImageView appIconOutput = findViewById(R.id.appIconOutput);
        TextView appSizeOutput = findViewById(R.id.appSizeOutput);
        TextView getSignatureOutput = findViewById(R.id.getCertHashCodeOutput);
        TextView getRequestedOutput = findViewById(R.id.getRequestedOutput);
        TextView getGrantedOutput = findViewById(R.id.getGrantedOutput);
        TextView noOfAppsOutput = findViewById(R.id.noOfAppsOutput);
        TextView listAppsByIDOutput = findViewById(R.id.appIDAllOutput);
        TextView getAppIDAllOutput = findViewById(R.id.getRequestedAllOutput);
        TextView getGrantedAllOutput = findViewById(R.id.getGrantedAllOutput);
        TextView appsRequestingOutput = findViewById(R.id.appsRequestingOutput);
        TextView appsGrantedOutput = findViewById(R.id.appsGrantedOutput);
        TextView installedOnOutput = findViewById(R.id.installedOnOutput);
        TextView lastUpdatedOutput = findViewById(R.id.lastUpdatedOutput);
        TextView appSizeAllOutput = findViewById(R.id.appSizeAllOutput);

        TextView GetPermsSource = findViewById(R.id.GetPerms_Source);
        TextView GetPermsVersion = findViewById(R.id.GetPerms_Version);

        Button otherMethodsButton = findViewById(R.id.otherMethodsButton);

        GetPerms gp = new GetPerms(this);

        appNameInput.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String searchTerms = appNameInput.getText().toString();
                if (!gp.isInstalled(gp.appID(searchTerms))) {
                    new AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("Application not found!")
                            .setPositiveButton("Retry", (dialog, which) -> {}).show();
                            finish();
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                }
                String appID = gp.appID(searchTerms);
                appIDOutput.setText(appID);
                appNameOutput.setText(gp.appName(appID));
                String unit = "MB";
                appSizeOutput.setText(String.valueOf(gp.appSize(appID, unit)).concat(unit));
                appIconOutput.setImageDrawable(gp.appIcon(appID));
                getSignatureOutput.setText(gp.getCertHashCode(appID));
                try {
                    getRequestedOutput.setText(gp.getRequested(appID).toString(4));
                    getGrantedOutput.setText(gp.getGranted(appID).toString(4));
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
                installedOnOutput.setText(String.valueOf(gp.installedOn(appID)));
                lastUpdatedOutput.setText(String.valueOf(gp.lastUpdated(appID)));
            }
            return false;
        });

        permissionNameInput.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String permissionName = permissionNameInput.getText().toString();
                try {
                    appsRequestingOutput.setText(gp.appsRequesting(permissionName).toString(4));
                    appsGrantedOutput.setText(gp.appsGranted(permissionName).toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        });

        otherMethodsButton.setOnClickListener(v -> {
            ProgressDialog pleaseWait = new ProgressDialog(this);
            pleaseWait.setCancelable(false);
            pleaseWait.setMessage("Please wait...");
            pleaseWait.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pleaseWait.setProgress(0);
            pleaseWait.show();
            Runnable otherMethods = () -> {
                try {
                    String noOfApps = String.valueOf(gp.noOfApps());
                    String listAppsByID = gp.appID().toString(4);
                    String getRequested = gp.getRequested().toString(4);
                    String getGranted = gp.getGranted().toString(4);
                    String appSize = gp.appSize().toString(4);
                    runOnUiThread(() -> {
                        noOfAppsOutput.setText(noOfApps);
                        listAppsByIDOutput.setText(listAppsByID);
                        getAppIDAllOutput.setText(getRequested);
                        getGrantedAllOutput.setText(getGranted);
                        appSizeAllOutput.setText(appSize);
                        pleaseWait.dismiss();
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            Executor otherMethodsExecutor = Executors.newSingleThreadExecutor();
            otherMethodsExecutor.execute(otherMethods);
        });
        GetPermsVersion.setText("v ".concat(BuildConfig.VERSION_NAME));
        GetPermsSource.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.source_code)));
            startActivity(intent);
        });
    }
}