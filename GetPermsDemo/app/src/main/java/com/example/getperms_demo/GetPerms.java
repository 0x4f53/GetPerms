package com.example.getperms_demo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.pm.PackageInfo.REQUESTED_PERMISSION_GRANTED;
import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GetPerms {
    Context context;
    public GetPerms(Context context_arg){
        context = context_arg;
    }

    public JSONObject getRequested() {
        HashMap<String, String> perms = new HashMap<>();
        final PackageManager pm = context.getPackageManager();
        try {
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo packageInfo : packages) {
                perms.put(packageInfo.packageName, "");
            }

        } catch (Exception ex) {
             //return perms.put("NotFound", "NoPerms");
        }
        return new JSONObject(perms);
    }

    public JSONObject getRequested(String package_name) {
        HashMap<String, String[]> requested_perms = new HashMap<>();
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(package_name, GET_PERMISSIONS);
            int permissions_length = packageInfo.requestedPermissions.length;
            String[] permissions_array = new String[permissions_length];
            for (int i = 0; i < permissions_length; i++) {
                if ((packageInfo.requestedPermissionsFlags[i] & REQUESTED_PERMISSION_GRANTED) != 0) {
                    String requested_permissions = packageInfo.requestedPermissions[i];
                    permissions_array[i]=requested_permissions;
                }
            }
            requested_perms.put(package_name, permissions_array);
        } catch (PackageManager.NameNotFoundException noPackage){
            requested_perms.put("NotFound", new String[]{"NoPerms"});
            Toast toast=Toast.makeText(context,"Package not found!",Toast.LENGTH_SHORT);
            toast.show();
        }
        return new JSONObject(requested_perms);
    }

    public String getGranted() {

        return "";
    }

    public JSONObject getGranted(String package_name) {
        HashMap<String, String[]> granted_perms = new HashMap<>();
        final PackageManager pm = context.getPackageManager();
        int perm_index = 0;
        String[] granted_array = new String[1];
            if(pm.checkPermission ("android.permission.INTERNET", package_name) == PERMISSION_GRANTED){
                granted_array[1]="all downloads";
            }
            granted_perms.put(package_name, granted_array);
        return new JSONObject(granted_perms);
    }
}
