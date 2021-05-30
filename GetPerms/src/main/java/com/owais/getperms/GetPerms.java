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

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GetPerms {
    Context context_argument;
    final PackageManager packageManager;

    public GetPerms (Context context){
        context_argument = context;
        packageManager = context.getPackageManager();
    }

    public Drawable appIcon (String packageName) {  // method to get the icon of an application
        Drawable icon = null;
        try {
            icon = packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException noPackage) {
            Log.e ("GetPerms > appIcon ()", "Could not find package(s)!");
        }
        return icon;
    }

    public String getVersionName(String packageName) {  // method to list the number of installed applications
        String versionName = null;
        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e ("GetPerms > getPackageVersionName ()", "Could not find package(s)!");
        }
        return versionName;
    }

    public long getVersionCode(String packageName) {  // method to list the number of installed applications
        long version = 0;
        try {
            version = packageManager.getPackageInfo(packageName, 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e ("GetPerms > getPackageVersionCode ()", "Could not find package(s)!");
        }
        return version;
    }

    public int noOfApps() {  // method to list the number of installed applications
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        return packages.size();
    }

    public LocalDateTime installedOn (String packageName) {  // method which shows when an application was first installed
        long installDate;
        try {
            installDate = packageManager.getPackageInfo(packageName, 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException noPackage) {
            Log.e("GetPerms > installedOn ()", "Could not find package(s)!");
            return null;
        }
        return Instant.ofEpochMilli(installDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime lastModified (String packageName) {  // method which shows when an application was last updated
        long lastUpdated;
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            lastUpdated = new File(appInfo.sourceDir).lastModified();
        } catch (PackageManager.NameNotFoundException noPackage) {
            Log.e("GetPerms > lastUpdated ()", "Could not find package(s)!");
            return null;
        }
        return Instant.ofEpochMilli(lastUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public Map<String, String> packageName() {  // method to list all applications with their ID
        final PackageManager pm = context_argument.getPackageManager();
        Map<String, String> perms = new HashMap<>();
        try {
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo packageInfo : packages) {
                String appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA));
                perms.put(appName, packageInfo.packageName);
            }
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > packageName ()", "Could not find package(s)!");
            return null;
        }
        return perms;
    }

    public String packageName (String appName) {  // method to get an application's ID based on its name
        Map<String, String> all_packages = packageName();
        String package_name = "";
        try {
            for (String key: all_packages.keySet()) {
                if (key.toLowerCase().contains(appName.toLowerCase())) {
                    package_name = all_packages.get(key);
                    break;
                }
            }
        } catch (NullPointerException e) {
            Log.e("GetPerms > packageName ()", "Couldn't find " + appName + " on device!");
            package_name = null;
        }
        return package_name;
    }

    public String appName (String packageName) {  // method to get application name based on package name
        Map<String, String> all_packages = packageName();
        String app_name = "";
        for (String key: all_packages.keySet()) {
            try {
                Object value = all_packages.get(key);
                if (value.equals(packageName)){
                    app_name = key;
                }
            } catch (NullPointerException e) {
                Log.e("GetPerms > appName ()", "Couldn't find " + packageName + " on device!");
                app_name = null;
            }
        }
        return app_name;
    }

    public String getCertHashCode (String packageName) {  // method to get an application's signing certificate's hash code.
        String signatureBase64 = "";
        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);
            final Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
            final MessageDigest md = MessageDigest.getInstance("SHA");
            for (Signature signature : signatures) {
                md.update(signature.toByteArray());
                signatureBase64 = new String(Base64.encode(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms", "Package not found on device!");
            signatureBase64 = null;
        } catch (NoSuchAlgorithmException noAlgo){
            Log.e("GetPerms > getCertHashCode ()", "Cannot find SHA algorithm on device!");
            signatureBase64 = null;
        }
        return signatureBase64;
    }

    public BigDecimal appSize (String packageName, String unit) {  // method to get an application's size.
        BigDecimal size = appSize(packageName);
        try {
            if (unit.toLowerCase().contains("kilo") || unit.equals("kb")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1000,1)), 2, RoundingMode.CEILING);
            } else if (unit.toLowerCase().contains("mega") || unit.toLowerCase().equals("mb")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1000,2)), 2, RoundingMode.CEILING);
            } else if (unit.toLowerCase().contains("giga") || unit.toLowerCase().equals("gb")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1000,3)), 2, RoundingMode.CEILING);
            } else if (unit.toLowerCase().contains("tera") || unit.toLowerCase().equals("tb")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1000,4)), 2, RoundingMode.CEILING);
            } else if (unit.toLowerCase().contains("kibi") || unit.toLowerCase().equals("kib")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1024,1)), 2, RoundingMode.CEILING);
            } else if (unit.toLowerCase().contains("mebi") || unit.toLowerCase().equals("mib")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1024,2)), 2, RoundingMode.CEILING);
            } else if (unit.toLowerCase().contains("gibi") || unit.toLowerCase().equals("gib")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1024,3)), 2, RoundingMode.CEILING);
            } else if (unit.toLowerCase().contains("tebi") || unit.toLowerCase().equals("tib")) {
                size=size.divide(BigDecimal.valueOf((long) Math.pow(1024,4)), 2, RoundingMode.CEILING);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException | NullPointerException illegalSize) {
            Log.e("GetPerms > appSize ()", "Illegal size specified! Use only kB, GB, TB, kiB, MiB, GiB or TiB");
            return null;
        }
        return size;
    }

    public BigDecimal appSize (String packageName) {  // method to get an application's size.
        BigDecimal size;
        try {
            size = BigDecimal.valueOf(new File(packageManager.getApplicationInfo(packageName, 0).publicSourceDir).length());
        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > appSize ()", "Package not found on device!");
            return null;
        }
        return size;
    }

    public Map<String, BigDecimal> appSize() {  // method to get all application sizes.
        Map<String, BigDecimal> perms = new HashMap<>();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            perms.put(packageInfo.packageName, appSize(packageInfo.packageName));
        }
        return perms;
    }

    public Map<String, String[]> getRequested (String packageName) {  // method to get all requested permissions for a particular application.
        Map<String, String[]> requested_perms = new HashMap<>();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, GET_PERMISSIONS);
            int permissions_length = packageInfo.requestedPermissions.length;
            String[] permissions_array = new String[permissions_length];
            for (int i = 0; i < permissions_length; i++) {
                if (packageInfo.requestedPermissionsFlags[i] != 0) {
                    String requested_permissions = packageInfo.requestedPermissions[i];
                    permissions_array[i]=requested_permissions;
                }
            }

            requested_perms.put(packageName, permissions_array);

        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > getRequested ()", "Package not found on device!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > getRequested ()", "Package requests no permissions!");
        }
        return requested_perms;
    }

    public Map<String, String[]> getGranted (String packageName) {  // method to get all granted permissions for a particular application.
        Map<String, String[]> granted_perms = new HashMap<>();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, GET_PERMISSIONS);
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
                if (packageManager.checkPermission (permissions_array[i], packageName) == PERMISSION_GRANTED) {
                    granted_array[i]=permissions_array[i];
                }
            }

            String[] cleanedArray = Arrays.stream(granted_array).filter(Objects::nonNull).toArray(String[]::new);   // remove null values
            granted_perms.put(packageName, cleanedArray);

        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > getGranted ()", "Package not found on device!");
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > getGranted ()", "Package requests no permissions!");
        }
        return granted_perms;
    }

    public Map<String, String[]> getRequested () {  // method to get all requested permissions from all applications.
        Map<String, String[]> perms = new HashMap<>();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                perms.put(packageInfo.packageName, getRequested(packageInfo.packageName).get(packageInfo.packageName));
            } catch (NullPointerException noPermissions){
                Log.e("GetPerms > getRequested ()", "Package "+packageInfo.packageName+"requests no permissions!");
            }
        }
        return perms;
    }

    public Map<String, String[]> getGranted () {  // method to get all granted permissions from all applications.
        Map<String, String[]> perms = new HashMap<>();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                perms.put(packageInfo.packageName, getGranted(packageInfo.packageName).get(packageInfo.packageName));
            } catch (NullPointerException noPermissions){
                Log.e("GetPerms > getGranted ()", "Package "+packageInfo.packageName+"requests no permissions!");
            }
        }
        return perms;
    }

    public boolean isInstalled (String packageName) {  // method to check if an application is installed.
        boolean flag = false;
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                if (packageInfo.packageName.equals(packageName)) {
                    flag = true;
                    break;
                }
            } catch (NullPointerException noPermissions){
                Log.e("GetPerms > isInstalled ()", "Package "+packageInfo.packageName+" requests no permissions!");
                return false;
            }
        }

        return flag;
    }

    String fullRequestedPermissionName = null;
    String fullGrantedPermissionName = null;

    public boolean isRequesting (String packageName, String permission_name) {  // method to check if an application requests a specified permission.
        boolean flag = false;
        try{
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, GET_PERMISSIONS);
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
                    fullRequestedPermissionName = permissions_array[i];
                    flag = true;
                    break;
                }
            }

        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > isRequesting ()", "Package not found on device!");
            return false;
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > isRequesting ()", "Package requests no permissions!");
            return false;
        }
        return flag;
    }

    public boolean isGranted (String packageName, String permission_name) {  // method to check if a specified permission is granted to an application.
        boolean flag = false;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, GET_PERMISSIONS);
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
                if (packageManager.checkPermission (permissions_array[i], packageName) == PERMISSION_GRANTED) {
                    granted_array[i]=permissions_array[i];
                }
            }

            String[] cleanedArray = Arrays.stream(granted_array).filter(Objects::nonNull).toArray(String[]::new);
            for (String s : cleanedArray) {
                if (s.toLowerCase().contains(permission_name)) {
                    fullGrantedPermissionName = s;
                    flag = true;
                    break;
                }
            }

        } catch (PackageManager.NameNotFoundException noPackage){
            Log.e("GetPerms > isGranted ()", "Package not found on device!");
            return false;
        } catch (NullPointerException noPermissions){
            Log.e("GetPerms > isGranted ()", "Package requests no permissions!");
            return false;
        }
        return flag;
    }

    public Map<String, ArrayList<String>> appsRequesting (String permission_name) {  // method to get applications requesting a specific permission type.
        Map<String, ArrayList<String>> apps = new HashMap<>();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<String> apps_list = new ArrayList<>();
        for (ApplicationInfo packageInfo : packages) {
            try {
                if (isRequesting(packageInfo.packageName, permission_name)) {
                    apps_list.add(packageInfo.packageName);
                }
            } catch (NullPointerException noPermissions){
                Log.e("GetPerms > appsRequesting ()", "Package "+packageInfo.packageName+" requests no permissions!");
                return null;
            }
        }
        apps.put(fullRequestedPermissionName, apps_list);
        return apps;
    }

    public String getPermissionFullName (String permission_name) { // get permission full name
        String permissionFullName = null;
        List<PermissionGroupInfo> lstGroups = packageManager.getAllPermissionGroups(0);
        for (PermissionGroupInfo pgi : lstGroups) {
            try {
                List<PermissionInfo> lstPermissions = packageManager.queryPermissionsByGroup(pgi.name, 0);
                for (PermissionInfo pi : lstPermissions) {
                    if (pi.name.contains(permission_name)) permissionFullName = pi.name;

                }
            } catch (Exception ex) {
                Log.e("GetPerms > getPermissionFullName ()", "Couldn't find any permissions matching '" + permission_name + "'!");
                return null;
            }
        }
        return permissionFullName;
    }

    public String getPermissionLabel (String permission_name) { // get permission label
        CharSequence csPermissionLabel;
        String permissionLabel = null;
        List<PermissionGroupInfo> lstGroups = packageManager.getAllPermissionGroups(0);
        for (PermissionGroupInfo pgi : lstGroups) {
            try {
                List<PermissionInfo> lstPermissions = packageManager.queryPermissionsByGroup(pgi.name, 0);
                for (PermissionInfo pi : lstPermissions) {
                    csPermissionLabel = pi.loadLabel(packageManager);
                    permissionLabel = csPermissionLabel.toString();
                }
            } catch (Exception ex) {
                Log.e("GetPerms > getPermissionLabel ()", "Couldn't find any permissions matching '" + permission_name + "'!");
                return null;
            }
        }
        return permissionLabel;
    }

    public Map<String, ArrayList<String>> appsGranted (String permission_name) {  // method to get applications granted a specific permission type.
        Map<String, ArrayList<String>> apps = new HashMap<>();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<String> apps_list = new ArrayList<>();
        for (ApplicationInfo packageInfo : packages) {
            try {
                if (isGranted(packageInfo.packageName, permission_name.toUpperCase())) {
                    apps_list.add(packageInfo.packageName);
                }
            } catch (NullPointerException noPermissions) {
                Log.e("GetPerms > appsGranted ()", "Package " + packageInfo.packageName + " requests no permissions!");
                return null;
            }
        }
        apps.put(fullGrantedPermissionName, apps_list);
        return apps;
    }
}