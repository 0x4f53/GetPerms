package com.example.getperms_demo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static android.content.pm.PackageInfo.REQUESTED_PERMISSION_GRANTED;
import static android.content.pm.PackageManager.GET_PERMISSIONS;

public class GetPerms {
    //getRequested();
    //getGranted();
    Context context;
    public GetPerms(Context context_arg){
        context = context_arg;
    }

    public JSONObject getRequested(){
        HashMap<String, String> perms = new HashMap<>();
        try {
            final PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo packageInfo : packages) {
                perms.put(packageInfo.packageName, "None");
            }
        } catch (Exception ex) {
            // return perms.put("NotFound", "NoPerms");
        }
        return new JSONObject(perms);
    }

    public JSONObject getRequested(String package_name){
        HashMap<String, String[]> perms = new HashMap<>();
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
            perms.put(package_name, permissions_array);
        } catch (PackageManager.NameNotFoundException noPackage){
            perms.put("NotFound", new String[]{"NoPerms"});
            Toast toast=Toast.makeText(context,"Package not found!",Toast.LENGTH_SHORT);
            toast.show();
        }
        return new JSONObject(perms);
    }

    public String getGranted(){

        return "";
    }

    public String getGranted(String package_name){

        return "";
    }
}
