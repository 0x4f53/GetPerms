[![Android](https://img.shields.io/badge/Made%20for-Android%20-41c27b.svg?style=flat&logo=android)](https://android.com)
[![Java](https://img.shields.io/badge/Made%20with-Java-1f425f.svg)](https://java.com)
[![Open Source](https://img.shields.io/badge/Open%20Source-Yes-blue)](https://opensource.org/)
[![License](https://img.shields.io/badge/License-MIT-purple)](LICENSE)
[![JitPack](https://jitpack.io/v/com.github.4f77616973/GetPerms.svg)](https://jitpack.io/#com.github.4f77616973/GetPerms)
[![Maintenance](https://img.shields.io/badge/Maintained-Yes-green.svg)](https://github.com/4f77616973/GetPerms/graphs/commit-activity)
[![APK](https://img.shields.io/badge/Download%20APK-Click%20Here!-blue)](/app/build/outputs/apk/debug/app-debug.apk)

# GetPerms

<img src = "getperms-logo.png" alt = "GetPerms logo" width = "75dp">

An Android library to get requested and granted app permissions, app metadata (icon, size, signature) and more!

Want to quickly get an application's icon in just one line of code? Making a banking app and need to check the see if your app has been illegaly modified? Or maybe you just want to know a hidden app's package name to remove it from your phone. GetPerms does it all! [Try a demo app of the library here](/app/build/outputs/apk/debug/app-debug.apk).

## ADDING / IMPORTING TO YOUR PROJECT

To add _GetPerms_ to your project

### Via Gradle

1. Add this to your project-level (root) `build.gradle` at the end of `repositories`:
  ```
  allprojects {
    repositories {
      // add at the end
      maven { url 'https://jitpack.io' }
    }
  }
  ```

2. Add the dependency to your app-level `build.gradle`:
  ```
  dependencies {
    // add along with other dependencies
    implementation 'com.github.4f77616973:GetPerms:+'
  }
  ```

_where `+` is the latest [release tag](https://github.com/4f77616973/getperms/-/tags) available._ The latest version on JitPack as of now is: [![Tag](https://img.shields.io/github/v/tag/4f77616973/GetPerms?label="")](https://jitpack.io/#com.github.4f77616973/GetPerms)

### Via Direct Download

1. Download library by clicking the _Download_ button at the top of this page and extract the library.
3. Open Android Studio and go to **File** → **New** → **Import Module**.
5. Go to the extracted library's path (`/GetPerms-master/GetPerms`) and select it. Uncheck other modules and check `GetPerms`.
7. Add the following Gradle dependency in your app-level `build.gradle`
  
  ```
  implementation project(path: ':GetPerms')
  ```

## USAGE / IMPLEMENTING IN YOUR PROJECT

To use this library, first create an object for GetPerms in your Java or Kotlin file using: 

```
GetPerms gp = new GetPerms(context);
```

_where `context` is the current application context and `gp` is the GetPerms object name._ You can then call the following methods from the library

### Built-in Methods

#### `Drawable appIcon (String packageName)`

input: package name as string (example: `com.google.android.apps.photos`)

output: returns a drawable of a package's app icon.

#### `String getVersionName (String packageName)`

* input: package name as string (example: `com.google.android.apps.photos`)

* output: returns the package version name (example: `v1.12.234-alpha`) as string.

#### `String getVersionCode (String packageName)`

* input: package name as string (example: `com.google.android.apps.photos`)

* output: returns the package version code (example: `20200821`) as long.

#### `int noOfApps ()`

* output: returns the number of installed apps as integer.

#### `LocalDateTime installedOn (String packageName)`

* input: package name as string (example: `com.google.android.apps.photos`)

* output: returns the package installation date as LocalDateTime.

#### `LocalDateTime lastModified (String packageName)`

* input: package name as string (example: `com.google.android.apps.photos`)

* output: returns the package modification (like an app update) date as LocalDateTime.

#### `String packageName (String appName)`

* input: app name as string (example: `YouTube`)

* output: returns the package name as string (example: `com.google.android.youtube`).

#### `String appName (String packageName)`

* input: package name as string (example: `com.google.android.youtube`)

* output: returns the package name as string (example: `YouTube`).

#### `String getCertHashCode (String packageName)`

* input: package name as string (example: `com.google.android.apps.photos`)

* output: returns a SHA-1 hash code of the signing certificate used to sign the package as String. Can be used for tamper detection.

#### `BigDecimal appSize (String packageName, String unit)`

* inputs: package name as string (example: `com.google.android.apps.photos`), unit as string (example: `kb`).

* output: returns package size as BigDecimal (example: `19343`).

#### `Map <String, String> getRequested (String packageName)`

* input: package name as string (example: `com.google.android.apps.photos`).

* output: returns a list of all permissions a package needs as a map of strings.

#### `Map <String, String> getGranted (String packageName)`

* input: package name as string (example: `com.google.android.apps.photos`)

* output: returns a list of all permissions a package is granted by the user as a map of strings.

#### `Map <String, ArrayList<String>> appsRequesting (String permission_name)`

* input: permission name as string (example: `android.permission.INTERNET`)

* output: returns a list of all packages requesting a specific permission as a map of strings.

#### `Map <String, ArrayList<String>> appsGranted (String permission_name)`

* input: permission name as string (example: `android.permission.INTERNET`)

* output: returns a list of all packages granted a specific permission as a map of strings.

#### `boolean isRequesting (String packageName, String permission_name)`

* input: permission name as string (example: `android.permission.INTERNET`)

* output: returns true if a package requests the specified permission or false if it doesn't.

#### `boolean isGranted (String packageName, String permission_name)`

* input: permission name as string (example: `android.permission.INTERNET`)

* output: returns true if the user grants a package the specified permission or false if the user didn't.

#### `String getPermissionFullName (String permission_name)`

* input: incomplete permission name (example: `camera`)

* output: complete permission name as string (example: `android.permission.CAMERA`).

#### `String getPermissionLabel (String permission_name)`

* input: permission name as string (example: `android.permission.PHONE`)

* output: permission label as string (example: `directly call phone numbers`).

### Overriding functions (use with threads to prevent lag)

`Map <String, String> packageName ()`: lists all applications with their ID

`Map <String, BigDecimal> appSize ()`: gets all application sizes.

`Map <String, String[]> getRequested ()`: gets all requested permissions from all applications.

`Map <String, String[]> getGranted ()`: gets all granted permissions from all applications.

### A Simple Example

Let's try this with an in-built app on most phones, [Google Maps](https://play.google.com/store/apps/details?id=com.google.android.apps.maps). Of course, this can be used with third-party apps too.

Create an object (here, `gp`) in your Java file as such

```
GetPerms gp = new GetPerms(getApplicationContext());
```

To get granted permissions, we invoke `getGranted()` from the newly created object, which is of type `Map`.  We then supply Google Maps' package name as shown below. 

```
Map <*,*> granted_permissions = gp.getGranted ("com.google.android.apps.maps");
```

To get granted permissions as a JSONObject, we invoke `getGranted()` as before, but cast it as a JSONObject.

```
JSONObject granted_permissions = JSONObject(gp.getGranted ("com.google.android.apps.maps"));
```

The object `granted_permissions` now returns a JSON object with whatever permissions the user granted to the Google Maps app. On a test emulator device running stock Android 11 (API 30), this returned

```
{
  "com.google.android.apps.maps":
  [
    "android.permission.INTERNET",
    "com.google.android.providers.gsf.permission.READ_GSERVICES",
    "com.google.android.gms.permission.ACTIVITY_RECOGNITION"
    "android.permission.ACTIVITY_RECOGNITION",
    "com.android.launcher.permission.INSTALL_SHORTCUT",
    "android.permission.MANAGE_ACCOUNTS",
    "android.permission.USE_CREDENTIALS",
    "android.permission.READ_SYNC_SETTINGS",
    "android.permission.WRITE_SYNC_SETTINGS",
    "android.permission.DISABLE_KEYGUARD",
    "android.permission.ACCESS_WIFI_STATE",
    "android.permission.ACCESS_NETWORK_STATE",
    "android.permission.CHANGE_WIFI_STATE",
    "com.google.android.c2dm.permission.RECEIVE",
    "com.google.android.apps.maps.permission.C2D_MESSAGE",
    "android.permission.VIBRATE","android.permission.NFC",
    "android.permission.FOREGROUND_SERVICE",
    "com.google.android.apps.maps.permission.PREFETCH",
    "android.permission.WAKE_LOCK",
    "android.permission.RECEIVE_BOOT_COMPLETED",
    "android.permission.BLUETOOTH",
    "android.permission.BLUETOOTH_ADMIN",
    "android.permission.BROADCAST_STICKY",
    "android.permission.GET_PACKAGE_SIZE",
    "com.google.android.googlequicksearchbox.permission.LENSVIEW_BROADCAST"
  ]
}
```

You can store it in a database if you'd like, using something like [Google's JSON.simple](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple), or even convert it to a CSV instead of JSON using [SuperCSV](https://super-csv.github.io/super-csv/examples_writing.html).

One interesting use case of this could be in a [Security Information and Event Management (SIEM)](https://en.wikipedia.org/wiki/Security_information_and_event_management) application, where an employee's work phone can be monitored for malicious apps that may compromise security and incur loss to an organization.

## DEMO APP

### Installing the Demo App

[A demo app can be found here](/app/build/outputs/apk/debug/app-debug.apk). The minimum API for the app is API28 / Android Pie. Install the APK file to your device and launch it.

To get the latest build artifact, with the latest changes to the library, you can visit the [CI / CD jobs page](https://gitlab.com/ThomasCat/getperms/-/jobs) and grab the latest archive.

### Using the Demo App

<img src="/demo.gif" alt="Demonstration GIF" width="240">

In the app, enter an app on your phone (_YouTube_, for example) in the first box to  to search for it. Pressing Enter gives you data about the app, including it's ID, icon, name, signature's hashcode, the date it was installed on, and JSON objects of what permissions it requests and what permissions were granted to it. You can copy a permission from the results (such as `android.permission.FOREGROUND_SERVICE`) by pressing and holding on it and paste this in the next box. This lets you do a reverse lookup of all applications that request or are granted the permission you entered.

Clicking the _Demo Methods Below_ button shows you every single application and its requested and granted permissions on your phone. (**Note:** clicking this button may take a while to show results.)

## CREDITS

* [JitPack](https://jitpack.io) - Hosting

* [GitLab CI/CD](https://docs.gitlab.com/ee/ci/) - Demo builds

* [Coffee Icon Pack - Samy](https://dribbble.com/shots/2606705-Dropbox-Redesign-Material-Design-Icon) - Icon inspiration

* [PackageManager.java](https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/content/pm/PackageManager.java) - PackageManager source code to retrieve data.

## CONTRIBUTING

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on the code of conduct, and the process for submitting pull requests.

## VERSIONING

This project uses [Semantic Versioning](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://gitlab.com/ThomasCat/GetPerms/tags).

## LICENSE

[Copyright © 2021 Owais Shaikh](LICENSE)

GitLab: [ThomasCat](https://gitlab.com/ThomasCat) | GitHub: [4f77616973](https://github.com/4f77616973) | Contact: [Email](mailto://0x4f@tuta.io)
