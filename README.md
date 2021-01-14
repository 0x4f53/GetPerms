# GetPerms

An Android library to get permissions that apps request, permissions you grant them explicitly and more. 

## DEMO / PREVIEW OF THE LIBRARY

### Installing the demo

[A demo app can be found here](/app/build/outputs/apk/debug/app-debug.apk). The minimum API for the app is API29 / Android 10. 

To get the latest build artifact, with the latest changes to the library, you can visit the [CI/CD jobs page](https://gitlab.com/ThomasCat/getperms/-/jobs) and grab whatever is on there.

### Using the demo

To use it, just install the APK and open it. In the app, use the first box to search an application ID on your phone by entering the app's name (for example: Typing 'Gmail' gives `com.android.gm`). Entering this ID in the second box gives you some data about the app, including it's signature's hashcode, the date it was installed on, what permissions it requests and what you granted it. In the third text box, you can do a lookup of all the applications that request or have been granted a particular permission. Clicking the purple button shows you every single application, its requested and granted permission on your phone. (**Note:** clicking this button may slow your phone down for 10 seconds or so! The library isn't really meant to be used this way anyway.)

## ADDING / IMPORTING TO YOUR PROJECT

This is a library written in Java, uploaded to https://jitpack.io. To use it with your project, add it via Gradle (or download it manually if you want to).

To add _GetPerms_ to your project

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

## USAGE / SAMPLE IMPLEMENTATION

To use this library, first create an object for it. An example of this is: `GetPerms object = new GetPerms(context);`, _where `context` is the application's context and `object` is the object name._

This library contains the following methods:

- `int noOfApps()` : list the number of installed applications.
- `JSONObject getAppID()` : list all applications by their ID.
- `String getAppID(String application_name)` : get an application's ID based on its name (Eg: _Gmail_).
- `String getAppName(String application_id)` : get an application's name based on its ID (Eg: `com.google.android.gm`).
- `String getCertHashCode(String application_id)` : get an application's signing certificate's hash code (SHA hash code, looks like gibberish to humans).
- `JSONObject getRequested(String application_id)` : get all requested permissions for a particular application.
- `JSONObject getGranted(String application_id)` : get all granted permissions for a particular application.
- `JSONObject getRequested()` : get all requested permissions from all applications.
- `JSONObject getGranted()` : get all granted permissions from all applications.
- `boolean isRequesting(String application_id, String permission_name)` : check if an application requests specified permission (Eg: `android.permission.BLUETOOTH`).
- `boolean isGranted(String application_id, String permission_name)` : check if a specified permission is granted to an application.
- `JSONObject appsRequesting(String permission_name)` : get all applications requesting a specified permission.
- `JSONObject appsGranted(String permission_name)` : get applications granted a specified permission.
- `LocalDateTime getInstallationDate(String application_id)` : show when an application was first installed.
- `LocalDateTime getLastUpdatedDate(String application_id)` : show when an application was last updated.

### A Simple Example

Let's try this with an inbuilt app on most phones, [Google Maps](https://play.google.com/store/apps/details?id=com.google.android.apps.maps). Of course, this can be used with third-party applications too.

```
GetPerms appScanner = new GetPerms(getApplicationContext());
JSONObject granted_permissions = appScanner.getGranted ("com.google.android.apps.maps");
```

The object `granted_permissions` now returns a JSON object with whatever permissions the user granted to the [Google Maps](https://play.google.com/store/apps/details?id=com.google.android.apps.maps) app. On a test emulator device running stock Android 11 (API 30), this returned:
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

You can use this data as you wish. You can store it to a database if you'd like, using something like [Google's JSON.simple](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple).

One interesting use case of this could be in a[ Security Information and Event Management (SIEM)](https://en.wikipedia.org/wiki/Security_information_and_event_management) application, where an employee's work phone can be monitored for malicious apps that may compromise security and incur loss to an organization.

## Credits

* [JitPack](https://jitpack.io) - Hosting
* [Android Studio](https://developer.android.com/studio/) - Development
* [GitLab CI/CD](https://docs.gitlab.com/ee/ci/) - Demo builds

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [Semantic Versioning](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Owais Shaikh** - *Developer* - GitLab: [ThomasCat](https://gitlab.com/ThomasCat), GitHub: [4f77616973](https://github.com/4f77616973).

## License

This project is licensed under the MIT License - see [LICENSE.md](LICENSE.md) for details.

## Acknowledgments

* [Xerfia](https://www.xerfia.com/), for the motivation and ass-kicking.
* [Google](https://android.google.com), for letting us use Android liberally.


