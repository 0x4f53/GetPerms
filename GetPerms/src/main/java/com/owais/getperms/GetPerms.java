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


package com.owais.getperms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GetPerms {
    Context context;

    public GetPerms(Context context_argument){
        context = context_argument;
    }

    public int noOfApps() {  // method to list the number of installed applications
        int no_of_apps = 0;
        final PackageManager pm = context.getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo ignored : packages) {
            no_of_apps++;
        }
        return no_of_apps;
    }

    public JSONObject getAppID() {  // method to list all applications.
        final PackageManager pm = context.getPackageManager();
        HashMap<String, String> perms = new HashMap<>();
        try {
            @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo packageInfo : packages) {
                perms.put((String) pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA)),packageInfo.packageName);
            }
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > listAppsByID()", "Could not find package(s)!");
        }
        return new JSONObject(perms);
    }

    public String getAppID(String application_name) {  // method to get application name based on a substring.
        JSONObject all_packages = getAppID();
        String package_name = null;
        for (int i = 0; i< Objects.requireNonNull(all_packages.names()).length(); i++) {
            try {
                String key = Objects.requireNonNull(all_packages.names()).getString(i);
                if (key.toLowerCase().contains(application_name.toLowerCase())) {
                    package_name = (String) all_packages.get(Objects.requireNonNull(all_packages.names()).getString(i));
                    break;
                }
            } catch (JSONException | NullPointerException e) {
                Log.e("GetPerms > getAppID()", "Couldn't find " + application_name + " on device!");
            }
        }
        return package_name;
    }

    public String getAppName(String application_id) {  // method to get application name based on a substring.
        JSONObject all_packages = getAppID();
        String app_name = null;
        Iterator<?> keys = all_packages.keys();
        try{
            while(keys.hasNext()) {
                String key = (String) keys.next();
                Object value = all_packages.get(key);
                if (((String) value).equals(application_id)){
                    app_name = key;
                }
            }
        } catch (JSONException | NullPointerException e) {
            Log.e("GetPerms > getAppName()", "Couldn't find " + application_id + " on device!");
        }
        return app_name;
    }

    public String getSignature(String application_id) {  // method to get an application's signature.
        final PackageManager pm = context.getPackageManager();
        String signatureBase64 = "";
        try {
            final PackageInfo packageInfo = pm.getPackageInfo(application_id, PackageManager.GET_SIGNING_CERTIFICATES);
            final Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
            final MessageDigest md = MessageDigest.getInstance("SHA");
            for (Signature signature : signatures) {
                md.update(signature.toByteArray());
                signatureBase64 = new String(Base64.encode(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms", "Package not found on device!");
        } catch (NoSuchAlgorithmException noAlgo){
            Log.e("GetPerms > getSignature()", "Cannot find SHA algorithm on device!");
        }
        return signatureBase64;
    }

    public JSONObject getRequested(String application_id) {  // method to get all requested permissions for a particular application.
        HashMap<String, String[]> requested_perms = new HashMap<>();
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(application_id, GET_PERMISSIONS);
            int permissions_length = packageInfo.requestedPermissions.length;
            String[] permissions_array = new String[permissions_length];
            for (int i = 0; i < permissions_length; i++) {
                if (packageInfo.requestedPermissionsFlags[i] != 0) {
                    String requested_permissions = packageInfo.requestedPermissions[i];
                    permissions_array[i]=requested_permissions;
                }
            }
            requested_perms.put(application_id, permissions_array);
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > getRequested()", "Package not found on device!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > getRequested()", "Package requests no permissions!");
        }
        return new JSONObject(requested_perms);
    }

    public JSONObject getGranted(String application_id) {  // method to get all granted permissions for a particular application.
        final PackageManager pm = context.getPackageManager();
        HashMap<String, String[]> granted_perms = new HashMap<>();
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(application_id, GET_PERMISSIONS);
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
                if (pm.checkPermission (permissions_array[i], application_id) == PERMISSION_GRANTED) {
                    granted_array[i]=permissions_array[i];
                }
            }
            String[] cleanedArray = Arrays.stream(granted_array).filter(Objects::nonNull).toArray(String[]::new);   // remove null values
            granted_perms.put(application_id, cleanedArray);
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > getGranted()", "Package not found on device!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > getGranted()", "Package requests no permissions!");
        }
        return new JSONObject(granted_perms);
    }

    public JSONObject getRequested() {  // method to get all requested permissions from all application.
        final PackageManager pm = context.getPackageManager();
        HashMap<String, JSONArray> perms = new HashMap<>();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                perms.put(packageInfo.packageName, getRequested(packageInfo.packageName).getJSONArray(packageInfo.packageName));
            } catch (NullPointerException | JSONException noPermissions){
                Log.e("GetPerms > getRequested()", "Package "+packageInfo.packageName+"requests no permissions!");
            }
        }
        return new JSONObject(perms);
    }

    public JSONObject getGranted() {  // method to get all granted permissions from all applications.
        final PackageManager pm = context.getPackageManager();
        HashMap<String, JSONArray> perms = new HashMap<>();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                perms.put(packageInfo.packageName, getGranted(packageInfo.packageName).getJSONArray(packageInfo.packageName));
            } catch (NullPointerException | JSONException noPermissions){
                Log.e("GetPerms > getGranted()", "Package "+packageInfo.packageName+"requests no permissions!");
            }
        }
        return new JSONObject(perms);
    }

    public boolean isRequesting(String application_id, String permission_name) {  // method to check if an application requests specific permission.
        boolean flag = false;
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(application_id, GET_PERMISSIONS);
            int permissions_length = packageInfo.requestedPermissions.length;
            String[] permissions_array = new String[permissions_length];
            for (int i = 0; i < permissions_length; i++) {
                if (packageInfo.requestedPermissionsFlags[i] != 0) {
                    String requested_permissions = packageInfo.requestedPermissions[i];
                    permissions_array[i]=requested_permissions;
                }
            }
            for (int i = 0; i < permissions_length; i++) {
                if (permissions_array[i].toLowerCase().contains(permission_name.toLowerCase())) {
                    flag = true;
                    break;
                }
            }
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > isRequesting()", "Package not found on device!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > isRequesting()", "Package requests no permissions!");
        }
        return flag;
    }

    public boolean isGranted(String application_id, String permission_name) {  // method to check if a specific permission is granted to an application.
        final PackageManager pm = context.getPackageManager();
        boolean flag = false;
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(application_id, GET_PERMISSIONS);
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
                if (pm.checkPermission (permissions_array[i], application_id) == PERMISSION_GRANTED) {
                    granted_array[i]=permissions_array[i];
                }
            }
            String[] cleanedArray = Arrays.stream(granted_array).filter(Objects::nonNull).toArray(String[]::new);   // remove null values.
            for (String s : cleanedArray) {
                if (s.toLowerCase().contains(permission_name.toLowerCase())) {
                    flag = true;
                    break;
                }
            }
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > isGranted()", "Package not found on device!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > isGranted()", "Package requests no permissions!");
        }
        return flag;
    }

    public JSONObject appsRequesting(String permission_name) {  // method to get applications requesting a specific permission type.
        final PackageManager pm = context.getPackageManager();
        HashMap<String, ArrayList<String>> apps = new HashMap<>();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<String> apps_list = new ArrayList<>();
        for (ApplicationInfo packageInfo : packages) {
            try {
                if (isRequesting(packageInfo.packageName, permission_name)) {
                    apps_list.add(packageInfo.packageName);
                }
            } catch (NullPointerException noPermissions){
                Log.e("GetPerms > appsRequesting()", "Package "+packageInfo.packageName+" requests no permissions!");
            }
        }
        apps.put(permission_name, apps_list);
        return new JSONObject(apps);
    }

    public JSONObject appsGranted(String permission_name) {  // method to get applications granted a specific permission type.
        final PackageManager pm = context.getPackageManager();
        HashMap<String, ArrayList<String>> apps = new HashMap<>();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<String> apps_list = new ArrayList<>();
        for (ApplicationInfo packageInfo : packages) {
            try {
                if (isGranted(packageInfo.packageName, permission_name)) {
                    apps_list.add(packageInfo.packageName);
                }
            } catch (NullPointerException noPermissions){
                Log.e("GetPerms > getGranted()", "Package "+packageInfo.packageName+" requests no permissions!");
            }
        }
        apps.put(permission_name, apps_list);
        return new JSONObject(apps);
    }
}