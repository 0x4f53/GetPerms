/*
*
* getPerms usage:
*   getPerms("ALL"); - all permissions for all packages
*   getPerms("GRANTED"); - granted permissions for all packages
*   getPerms("package_name", "ALL"); - all permissions for a specific package
*   getPerms("package_name", "GRANTED"); - granted permissions for a specific package
*
* */

package getperms_demo;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.TextView;
import com.example.getperms_demo.R;

import java.util.HashMap;
import java.util.List;

import static android.content.pm.PackageInfo.REQUESTED_PERMISSION_GRANTED;
import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PermissionInfo.PROTECTION_DANGEROUS;
import static android.content.pm.PermissionInfo.PROTECTION_SIGNATURE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView output1 = (TextView)findViewById(R.id.method1output);
        TextView output2 = (TextView)findViewById(R.id.method2output);
        TextView output3 = (TextView)findViewById(R.id.method3output);
        TextView output4 = (TextView)findViewById(R.id.method4output);

        output1.setText(getPerms("ALL"));
        output2.setText(getPerms("GRANTED"));
        output3.setText(getPerms("com.google.android.networkstack.tethering", "ALL"));
        output4.setText(getPerms("com.google.android.networkstack.tethering", "GRANTED"));
    }

    /*
       1. getPerms("ALL"); - all permissions for all packages
       AND
       2. getPerms("GRANTED"); - granted permissions for all packages
    */

    protected String getPerms(String mode){
        StringBuilder builder = new StringBuilder();
        try {
            if (mode.equals("ALL")){
                final PackageManager pm = getPackageManager();
                List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
                builder.append("{");
                for (ApplicationInfo appInfo : packages) {
                    String package_name = appInfo.packageName;
                    builder.append("\n\"").append(package_name).append("\":[");
                    PackageInfo packageInfo = getPackageManager().getPackageInfo(package_name, GET_PERMISSIONS);
                    for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                        if ((packageInfo.requestedPermissionsFlags[i] & REQUESTED_PERMISSION_GRANTED) != 0) {
                            String requested_permissions =packageInfo.requestedPermissions[i];
                            // shorter permissions: permission = permission.substring(permission.lastIndexOf(".")+1);
                            builder.append("\"").append(requested_permissions).append("\",");
                        }
                    }
                    builder.deleteCharAt(builder.length()-1);
                    builder.append("],");
                }
                builder.deleteCharAt(builder.length()-1);
                builder.append("\n}");
            } else if (mode.equals("GRANTED")){
                final PackageManager pm = getPackageManager();
                // TODO: 09/11/20 check if a permission has been granted to each app with if-else one by one and add to json.
            } else {
                throw new IllegalArgumentException("Illegal arguments passed to getPerms(). Please refer to the library guide at the top of the class file or at https://gitlab.com/ThomasCat/getperms.");
            }
        } catch (IllegalArgumentException wrongArgs){
            return "Illegal arguments. Check documentation.";
        } catch (PackageManager.NameNotFoundException nnfe) {
            return "Couldn't find package.";
        }
        return builder.toString();
    }

    /*
       3. getPerms("package_name", "ALL"); - all permissions for a specific package
       AND
       4. getPerms("package_name", "GRANTED"); - granted permissions for a specific package
    */
    protected String getPerms(String package_name, String mode){
        StringBuilder builder = new StringBuilder();
        try {
            if (mode.equals("ALL")){
                PackageInfo packageInfo = getPackageManager().getPackageInfo(package_name, GET_PERMISSIONS);
                builder.append("{").append("\n\"").append(package_name).append("\":[");
                for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                    if ((packageInfo.requestedPermissionsFlags[i] & REQUESTED_PERMISSION_GRANTED) != 0) {
                        String requested_permissions =packageInfo.requestedPermissions[i];
                        // shorter permissions: permission = permission.substring(permission.lastIndexOf(".")+1);
                        builder.append("\"").append(requested_permissions).append("\",");
                    }
                }
                builder.deleteCharAt(builder.length()-1);
                builder.append("]").append("\n}");
            } else if (mode.equals("GRANTED")){
                final PackageManager pm = getPackageManager();
                // TODO: 09/11/20 check if a permission has been granted with if-else one by one and add to json.
            } else {
                throw new IllegalArgumentException("Illegal arguments passed to getPerms(). Please refer to the library guide at the top of the class file or at https://gitlab.com/ThomasCat/getperms.");
            }
        } catch (IllegalArgumentException iae){
            return "Illegal arguments. Check documentation.";
        } catch (PackageManager.NameNotFoundException nnfe) {
            return "Couldn't find package.";
        }
        return builder.toString();
    }
}
