# GetPerms

Android library to get all granted and requested permissions for installed apps, written in Java. [Get the demo here](/app/build/outputs/apk/debug/app-debug.apk).

**How to use:**: [check out the Wiki](https://gitlab.com/ThomasCat/getperms/-/wikis/)

To add _GetPerms_ to your project

1. Add this in your project root level `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
      // add at the end
      maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency in your app level `build.gradle`:
```
dependencies {
    // add along with other dependencies
    implementation 'com.gitlab.ThomasCat:GetPerms:+'
}
```

_where `+` is the latest [release tag](https://gitlab.com/ThomasCat/getperms/-/tags) available._

[![](https://jitpack.io/v/com.gitlab.thomascat/GetPerms.svg)](https://jitpack.io/#com.gitlab.thomascat/GetPerms)
