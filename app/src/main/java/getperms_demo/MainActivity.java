package getperms_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.getperms_demo.R;

import java.util.HashMap;
import java.util.List;

import static android.content.pm.PackageInfo.REQUESTED_PERMISSION_GRANTED;
import static android.content.pm.PackageManager.GET_PERMISSIONS;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the application context
        Context mContext = getApplicationContext();
        Activity mActivity = MainActivity.this;
        // Get the widgets reference from xml layout
        Spinner mSpinner = findViewById(R.id.spinner);
        mTextView = findViewById(R.id.text);
        // Make text view scrollable
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        // Get the installed package list
        HashMap<String,String> map = getInstalledPackages();
        // Get the values array from hash map
        final String[] values = map.values().toArray(new String[0]);
        final String[] keys = map.keySet().toArray(new String[0]);
        // Initialize a new array adapter instance
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                mActivity,
                android.R.layout.simple_spinner_item,
                values
        );
        // Set the drop down view resource for array adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Data bind the spinner with array adapter
        mSpinner.setAdapter(adapter);
        // Set an item selected listener for the spinner object
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String packageName = keys[i];
                String label = values[i];
                mTextView.setText(label + "\n" + packageName+"\n\n");
                // Display the selected package granted permissions list
                mTextView.append(getPermissionsByPackageName(packageName));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // Custom method to get all installed package names
    protected HashMap<String,String> getInstalledPackages(){
        PackageManager packageManager = getPackageManager();
        // Initialize a new intent
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        // Set the intent category
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // Set the intent flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        // Initialize a new list of resolve info
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent,0);
        // Initialize a new hash map of package names and application label
        HashMap<String,String> map = new HashMap<>();
        // Loop through the resolve info list
        for(ResolveInfo resolveInfo : resolveInfoList){
            // Get the activity info from resolve info
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            // Get the package name
            String packageName = activityInfo.applicationInfo.packageName;
            // Get the application label
            String label = (String) packageManager.getApplicationLabel(activityInfo.applicationInfo);
            // Put the package name and application label to hash map
            map.put(packageName,label);
        }
        return map;
    }
    // Custom method to get app requested and granted permissions from package name
    protected String getPermissionsByPackageName(String packageName){
        // Initialize a new string builder instance
        StringBuilder builder = new StringBuilder();
        try {
            // Get the package info
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, GET_PERMISSIONS);
            // Permissions counter
            int counter = 1;
            /*
                PackageInfo
                    Overall information about the contents of a package. This corresponds to all of
                    the information collected from AndroidManifest.xml.
            */
            /*
                String[] requestedPermissions
                    Array of all <uses-permission> tags included under <manifest>, or null if there
                    were none. This is only filled in if the flag GET_PERMISSIONS was set. This list
                    includes all permissions requested, even those that were not granted or known
                    by the system at install time.
            */
            /*
                int[] requestedPermissionsFlags
                    Array of flags of all <uses-permission> tags included under <manifest>, or null
                    if there were none. This is only filled in if the flag GET_PERMISSIONS was set.
                    Each value matches the corresponding entry in requestedPermissions, and will
                    have the flag REQUESTED_PERMISSION_GRANTED set as appropriate.
            */
            /*
                int REQUESTED_PERMISSION_GRANTED
                    Flag for requestedPermissionsFlags: the requested permission is currently
                    granted to the application.
            */
                    // Loop through the package info requested permissions
            for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                if ((packageInfo.requestedPermissionsFlags[i] & REQUESTED_PERMISSION_GRANTED) != 0) {
                    String requested_permissions =packageInfo.requestedPermissions[i];
                    // To make permission name shorter: permission = permission.substring(permission.lastIndexOf(".")+1);
                    builder.append("").append(counter).append(". ").append(requested_permissions).append("\n");
                    counter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
