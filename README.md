[![License](https://img.shields.io/badge/License-MIT-purple)](LICENSE)
[![JitPack](https://jitpack.io/v/com.gitlab.thomascat/GetPerms.svg)](https://jitpack.io/#com.gitlab.thomascat/GetPerms)
[![Maintenance](https://img.shields.io/badge/Maintained-Yes-green.svg)](https://github.com/4f77616973/GetPerms/graphs/commit-activity)
[![Java](https://img.shields.io/badge/Made%20with-Java-1f425f.svg)](https://java.com)
[![Preview](https://img.shields.io/badge/Preview-Click%20Here!-blue)](/app/build/outputs/apk/debug/app-debug.apk)

# GetPerms

An Android library to get permissions that apps request, permissions you grant them explicitly and more. 

## DEMO / PREVIEW OF THE LIBRARY

### Installing the Demo App

[A demo app can be found here](/app/build/outputs/apk/debug/app-debug.apk). The minimum API for the app is API28 / Android Pie. Install the APK file to your device and launch it.

To get the latest build artifact, with the latest changes to the library, you can visit the [CI / CD jobs page](https://gitlab.com/ThomasCat/getperms/-/jobs) and grab the latest archive.

### Using the Demo App

<img src="/demo.gif" alt="Demonstration video" width="240">

In the app, enter an app on your phone (_YouTube_, for example) in the first box to  to search for it. Pressing Enter gives you data about the app, including it's ID, icon, name, signature's hashcode, the date it was installed on, and JSON objects of what permissions it requests and what permissions were granted to it. You can copy a permission from the results (such as `android.permission.FOREGROUND_SERVICE`) by pressing and holding on it and paste this in the next box. This lets you do a reverse lookup of all applications that request or are granted the permission you entered.

Clicking the _Demo Methods Below_ button shows you every single application and its requested and granted permissions on your phone. (**Note:** clicking this button may take a while to show results.)

## ADDING / IMPORTING TO YOUR PROJECT

This is a library written in Java, uploaded to https://jitpack.io. To use it with your project, add it via Gradle (or download it manually if you want to).

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
    implementation 'com.gitlab.ThomasCat:GetPerms:+'
  }
  ```

_where `+` is the latest [release tag](https://gitlab.com/ThomasCat/getperms/-/tags) available._ The latest version on JitPack as of now is: [![](https://jitpack.io/v/com.gitlab.thomascat/GetPerms.svg)](https://jitpack.io/#com.gitlab.thomascat/GetPerms)

### Via Direct Download

1. Download library by clicking the _Download_ button at the top of this page.
2. Extract the library.
3. Open Android Studio and go to **File** → **New** → **Import Module**.
5. Go to the extracted library's path (`/GetPerms-master/GetPerms`) and select it.
6. Uncheck other modules and add `GetPerms`.
7. Add the following Gradle dependency in your app-level `build.gradle`
  
  ```
  implementation project(path: ':GetPerms')
  ```

## USAGE / IMPLEMENTING IN YOUR PROJECT

To use this library create an object for GetPerms in your Java or Kotlin file using: `GetPerms object = new GetPerms(context);`, _where `context` is the application's context and `object` is the object name._

### Included Methods

This library contains the following methods:

- `int noOfApps()` : list the number of installed applications.

- `BigInteger appSize(String application_id)` : get an application's size (Bytes by default).
- `BigDecimal appSize(String application_id, String unit)` : get an application's size in a specific unit (where `unit` can be _kB_, _MB_, _GB_, _TB_, _KiB_, _MiB_ and _TiB_).

- `Drawable appIcon(String application_id)` : get the icon of an application.

- `LocalDateTime installedOn(String application_id)` : show when an application was first installed.
- `LocalDateTime lastUpdated(String application_id)` : show when an application was last updated.

- `boolean isInstalled(String application_id)` : check if an application is installed.
- `boolean isRequesting(String application_id, String permission_name)` : check if an application requests specified permission (Eg: `android.permission.BLUETOOTH`).
- `boolean isGranted(String application_id, String permission_name)` : check if a specified permission is granted to an application.

- `String appID(String application_name)` : get an application's ID based on its name (Eg: _Gmail_).
- `String appName(String application_id)` : get an application's name based on its ID (Eg: `com.google.android.gm`).
- `String getCertHashCode(String application_id)` : get an application's signing certificate's hash code (SHA hash code, looks like gibberish to humans).

- `JSONObject appID()` : list all applications by their ID.
- `JSONObject appsRequesting(String permission_name)` : get all applications requesting a specified permission.
- `JSONObject appsGranted(String permission_name)` : get applications granted a specified permission.
- `JSONObject getRequested(String application_id)` : get all requested permissions for a particular application.
- `JSONObject getRequested()` : get all requested permissions from all applications.
- `JSONObject getGranted(String application_id)` : get all granted permissions for a particular application.
- `JSONObject getGranted()` : get all granted permissions from all applications.

### A Simple Example

Let's try this with an inbuilt app on most phones, [Google Maps](https://play.google.com/store/apps/details?id=com.google.android.apps.maps). Of course, this can be used with third-party applications too.

Create an object in your Java or Kotlin file as such:

```
GetPerms appScanner = new GetPerms(getApplicationContext());
JSONObject granted_permissions = appScanner.getGranted ("com.google.android.apps.maps");
```

The object `granted_permissions` now returns a JSON object with whatever permissions the user granted to the Google Maps app. On a test emulator device running stock Android 11 (API 30), this returned:

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

Use this data as you wish. You can store it in a database if you'd like, using something like [Google's JSON.simple](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple).

One interesting use case of this could be in a [Security Information and Event Management (SIEM)](https://en.wikipedia.org/wiki/Security_information_and_event_management) application, where an employee's work phone can be monitored for malicious apps that may compromise security and incur loss to an organization.

## CREDITS

* [JitPack](https://jitpack.io) - Hosting
* [Android Studio](https://developer.android.com/studio/) - Development
* [GitLab CI/CD](https://docs.gitlab.com/ee/ci/) - Demo builds

## CONTRIBUTING

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on the code of conduct, and the process for submitting pull requests.

## VERSIONING

This project uses [Semantic Versioning](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://gitlab.com/ThomasCat/GetPerms/tags).

## AUTHORS

* **Owais Shaikh** - *Developer* - GitLab: [ThomasCat](https://gitlab.com/ThomasCat), GitHub: [4f77616973](https://github.com/4f77616973).

## LICENSE

This project is licensed under the MIT License - see [LICENSE.md](LICENSE.md) for details.

## ACKNOWLEDGEMENT

* [Xerfia](https://www.xerfia.com/), for the motivation.
* [Google](https://android.google.com), for Android.
