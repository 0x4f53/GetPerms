package com.owais.getperms_demo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GetPerms {
    Context context;
    public GetPerms(Context context_arg){
        context = context_arg;
    }

    public JSONObject listPackages() {  // method to list all packages
        HashMap<String, String> perms = new HashMap<>();
        final PackageManager pm = context.getPackageManager();
        try {
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo packageInfo : packages) {
                perms.put((String) pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA)),packageInfo.packageName);
            }
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms", "Could not find package(s)!");
        }
        return new JSONObject(perms);
    }

    public JSONObject getRequested(String package_name) {  // method to get all requested permissions for a particular package
        HashMap<String, String[]> requested_perms = new HashMap<>();
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(package_name, GET_PERMISSIONS);
            int permissions_length = packageInfo.requestedPermissions.length;
            String[] permissions_array = new String[permissions_length];
            for (int i = 0; i < permissions_length; i++) {
                if (packageInfo.requestedPermissionsFlags[i] != 0) {
                    String requested_permissions = packageInfo.requestedPermissions[i];
                    permissions_array[i]=requested_permissions;
                }
            }
            requested_perms.put(package_name, permissions_array);
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms", "Package not found on system!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms", "Package requests no permissions!");
        }
        return new JSONObject(requested_perms);
    }

    public JSONObject getGranted(String package_name) {  // method to get all granted permissions for a particular package
        HashMap<String, String[]> granted_perms = new HashMap<>();
        final PackageManager pm = context.getPackageManager();
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(package_name, GET_PERMISSIONS);
            int permissions_length = packageInfo.requestedPermissions.length;
            String[] permissions_array = new String[permissions_length];
            String[] granted_array = new String[permissions_length];
            // first create list of all requested permissions
            for (int i = 0; i < permissions_length; i++) {
                if (packageInfo.requestedPermissionsFlags[i] != 0) {
                    String requested_permissions = packageInfo.requestedPermissions[i];
                    permissions_array[i]=requested_permissions;
                }
            }
            // then put in a new string list of granted permissions
            for (int i = 0; i < permissions_length; i++) {
                if (pm.checkPermission (permissions_array[i], package_name) == PERMISSION_GRANTED) {
                    granted_array[i]=permissions_array[i];
                }
            }
            String[] cleanedArray = Arrays.stream(granted_array).filter(Objects::nonNull).toArray(String[]::new);   // remove null values
            granted_perms.put(package_name, cleanedArray);
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms", "Package not found on system!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms", "Package requests no permissions!");
        }
        return new JSONObject(granted_perms);
    }

    public JSONObject getRequested() {  // method to get all requested permissions from all packages
        HashMap<String, JSONArray> perms = new HashMap<>();
        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                perms.put(packageInfo.packageName, getRequested(packageInfo.packageName).getJSONArray(packageInfo.packageName));
            } catch (NullPointerException | JSONException noPermissions){
                Log.e("GetPerms", "Package "+packageInfo.packageName+"requests no permissions!");
            }
        }
        return new JSONObject(perms);
    }

    public JSONObject getGranted() {  // method to get all granted permissions from all packages
        HashMap<String, JSONArray> perms = new HashMap<>();
        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                perms.put(packageInfo.packageName, getGranted(packageInfo.packageName).getJSONArray(packageInfo.packageName));
            } catch (NullPointerException | JSONException noPermissions){
                Log.e("GetPerms", "Package "+packageInfo.packageName+"requests no permissions!");
            }
        }
        return new JSONObject(perms);
    }
}