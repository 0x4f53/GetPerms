# GetPerms

Android library to get all granted and requested permissions of installed packages.

To use _GetPerms_ with your project


1. Add this in your project root `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
      // add at the end
      maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency in the app `build.gradle`:
```
dependencies {
    // add along with other dependencies
    implementation 'com.gitlab.ThomasCat:GetPerms:<TAG VERSION>'
}
```

_where `<TAG VERSION>` is the latest release tag available (this can be found in the [Tags](https://gitlab.com/ThomasCat/getperms/-/tags) page)._

**Usage**: [check out the Wiki](https://gitlab.com/ThomasCat/getperms/-/wikis/)

[![](https://jitpack.io/v/com.gitlab.thomascat/GetPerms.svg)](https://jitpack.io/#com.gitlab.thomascat/GetPerms)
