package com.example.getperms_demo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.pm.PackageInfo.REQUESTED_PERMISSION_GRANTED;
import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GetPerms {
    //getRequested();
    //getGranted();
    Context context;
    public GetPerms(Context context_arg){
        context = context_arg;
    }
    String[] permissionsList = new String[]{
            "android.permission.ACCESS_ALL_DOWNLOADS",
            "android.permission.ACCESS_BLUETOOTH_SHA",
            "android.permission.ACCESS_CACHE_FILESYS",
            "android.permission.ACCESS_CHECKIN_PROPE",
            "android.permission.ACCESS_CONTENT_PROVI",
            "android.permission.ACCESS_DOWNLOAD_MANA",
            "android.permission.ACCESS_DOWNLOAD_MANA",
            "android.permission.ACCESS_DRM_CERTIFICA",
            "android.permission.ACCESS_EPHEMERAL_APP",
            "android.permission.ACCESS_FM_RADIO",
            "android.permission.ACCESS_INPUT_FLINGER",
            "android.permission.ACCESS_KEYGUARD_SECU",
            "android.permission.ACCESS_LOCATION_EXTR",
            "android.permission.ACCESS_MOCK_LOCATION",
            "android.permission.ACCESS_MTP",
            "android.permission.ACCESS_NETWORK_CONDI",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.ACCESS_NOTIFICATIONS",
            "android.permission.ACCESS_NOTIFICATION_",
            "android.permission.ACCESS_PDB_STATE",
            "android.permission.ACCESS_SURFACE_FLING",
            "android.permission.ACCESS_VOICE_INTERAC",
            "android.permission.ACCESS_VR_MANAGER",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.ACCESS_WIMAX_STATE",
            "android.permission.ACCOUNT_MANAGER",
            "android.permission.ALLOW_ANY_CODEC_FOR_",
            "android.permission.ASEC_ACCESS",
            "android.permission.ASEC_CREATE",
            "android.permission.ASEC_DESTROY",
            "android.permission.ASEC_MOUNT_UNMOUNT",
            "android.permission.ASEC_RENAME",
            "android.permission.AUTHENTICATE_ACCOUNT",
            "android.permission.BACKUP",
            "android.permission.BATTERY_STATS",
            "android.permission.BIND_ACCESSIBILITY_S",
            "android.permission.BIND_APPWIDGET",
            "android.permission.BIND_CARRIER_MESSAGI",
            "android.permission.BIND_CARRIER_SERVICE",
            "android.permission.BIND_CHOOSER_TARGET_",
            "android.permission.BIND_CONDITION_PROVI",
            "android.permission.BIND_CONNECTION_SERV",
            "android.permission.BIND_DEVICE_ADMIN",
            "android.permission.BIND_DIRECTORY_SEARC",
            "android.permission.BIND_DREAM_SERVICE",
            "android.permission.BIND_INCALL_SERVICE",
            "android.permission.BIND_INPUT_METHOD",
            "android.permission.BIND_INTENT_FILTER_V",
            "android.permission.BIND_JOB_SERVICE",
            "android.permission.BIND_KEYGUARD_APPWID",
            "android.permission.BIND_MIDI_DEVICE_SER",
            "android.permission.BIND_NFC_SERVICE",
            "android.permission.BIND_NOTIFICATION_LI",
            "android.permission.BIND_NOTIFICATION_RA",
            "android.permission.BIND_PACKAGE_VERIFIE",
            "android.permission.BIND_PRINT_RECOMMEND",
            "android.permission.BIND_PRINT_SERVICE",
            "android.permission.BIND_PRINT_SPOOLER_S",
            "android.permission.BIND_QUICK_SETTINGS_",
            "android.permission.BIND_REMOTEVIEWS",
            "android.permission.BIND_REMOTE_DISPLAY",
            "android.permission.BIND_ROUTE_PROVIDER",
            "android.permission.BIND_RUNTIME_PERMISS",
            "android.permission.BIND_SCREENING_SERVI",
            "android.permission.BIND_TELECOM_CONNECT",
            "android.permission.BIND_TEXT_SERVICE",
            "android.permission.BIND_TRUST_AGENT",
            "android.permission.BIND_TV_INPUT",
            "android.permission.BIND_TV_REMOTE_SERVI",
            "android.permission.BIND_VOICE_INTERACTI",
            "android.permission.BIND_VPN_SERVICE",
            "android.permission.BIND_VR_LISTENER_SER",
            "android.permission.BIND_WALLPAPER",
            "android.permission.BLUETOOTH",
            "android.permission.BLUETOOTH_ADMIN",
            "android.permission.BLUETOOTH_MAP",
            "android.permission.BLUETOOTH_PRIVILEGED",
            "android.permission.BLUETOOTH_STACK",
            "android.permission.BRICK",
            "android.permission.BROADCAST_CALLLOG_IN",
            "android.permission.BROADCAST_NETWORK_PR",
            "android.permission.BROADCAST_PACKAGE_RE",
            "android.permission.BROADCAST_PHONE_ACCO",
            "android.permission.BROADCAST_SMS",
            "android.permission.BROADCAST_STICKY",
            "android.permission.BROADCAST_WAP_PUSH",
            "android.permission.CACHE_CONTENT",
            "android.permission.CALL_PRIVILEGED",
            "android.permission.CAMERA_DISABLE_TRANS",
            "android.permission.CAMERA_SEND_SYSTEM_E",
            "android.permission.CAPTURE_AUDIO_HOTWOR",
            "android.permission.CAPTURE_AUDIO_OUTPUT",
            "android.permission.CAPTURE_SECURE_VIDEO",
            "android.permission.CAPTURE_TV_INPUT",
            "android.permission.CAPTURE_VIDEO_OUTPUT",
            "android.permission.CARRIER_FILTER_SMS",
            "android.permission.CHANGE_APP_IDLE_STAT",
            "android.permission.CHANGE_BACKGROUND_DA",
            "android.permission.CHANGE_COMPONENT_ENA",
            "android.permission.CHANGE_CONFIGURATION",
            "android.permission.CHANGE_DEVICE_IDLE_T",
            "android.permission.CHANGE_NETWORK_STATE",
            "android.permission.CHANGE_WIFI_MULTICAS",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.CHANGE_WIMAX_STATE",
            "android.permission.CLEAR_APP_CACHE",
            "android.permission.CLEAR_APP_GRANTED_UR",
            "android.permission.CLEAR_APP_USER_DATA",
            "android.permission.CONFIGURE_DISPLAY_CO",
            "android.permission.CONFIGURE_WIFI_DISPL",
            "android.permission.CONFIRM_FULL_BACKUP",
            "android.permission.CONNECTIVITY_INTERNA",
            "android.permission.CONTROL_INCALL_EXPER",
            "android.permission.CONTROL_KEYGUARD",
            "android.permission.CONTROL_LOCATION_UPD",
            "android.permission.CONTROL_VPN",
            "android.permission.CONTROL_WIFI_DISPLAY",
            "android.permission.COPY_PROTECTED_DATA",
            "android.permission.CREATE_USERS",
            "android.permission.CRYPT_KEEPER",
            "android.permission.DELETE_CACHE_FILES",
            "android.permission.DELETE_PACKAGES",
            "android.permission.DEVICE_POWER",
            "android.permission.DIAGNOSTIC",
            "android.permission.DISABLE_KEYGUARD",
            "android.permission.DISPATCH_NFC_MESSAGE",
            "android.permission.DISPATCH_PROVISIONIN",
            "android.permission.DOWNLOAD_CACHE_NON_P",
            "android.permission.DUMP",
            "android.permission.DVB_DEVICE",
            "android.permission.EXPAND_STATUS_BAR",
            "android.permission.FACTORY_TEST",
            "android.permission.FILTER_EVENTS",
            "android.permission.FLASHLIGHT",
            "android.permission.FORCE_BACK",
            "android.permission.FORCE_STOP_PACKAGES",
            "android.permission.FRAME_STATS",
            "android.permission.FREEZE_SCREEN",
            "android.permission.GET_ACCOUNTS_PRIVILE",
            "android.permission.GET_APP_GRANTED_URI_",
            "android.permission.GET_APP_OPS_STATS",
            "android.permission.GET_DETAILED_TASKS",
            "android.permission.GET_INTENT_SENDER_IN",
            "android.permission.GET_PACKAGE_IMPORTAN",
            "android.permission.GET_PACKAGE_SIZE",
            "android.permission.GET_PASSWORD",
            "android.permission.GET_PROCESS_STATE_AN",
            "android.permission.GET_TASKS",
            "android.permission.GET_TOP_ACTIVITY_INF",
            "android.permission.GLOBAL_SEARCH",
            "android.permission.GLOBAL_SEARCH_CONTRO",
            "android.permission.GRANT_RUNTIME_PERMIS",
            "android.permission.HARDWARE_TEST",
            "android.permission.HDMI_CEC",
            "android.permission.INJECT_EVENTS",
            "android.permission.INSTALL_GRANT_RUNTIM",
            "android.permission.INSTALL_LOCATION_PRO",
            "android.permission.INSTALL_PACKAGES",
            "android.permission.INTENT_FILTER_VERIFI",
            "android.permission.INTERACT_ACROSS_USER",
            "android.permission.INTERACT_ACROSS_USER",
            "android.permission.INTERNAL_SYSTEM_WIND",
            "android.permission.INTERNET",
            "android.permission.INVOKE_CARRIER_SETUP",
            "android.permission.KILL_BACKGROUND_PROC",
            "android.permission.KILL_UID",
            "android.permission.LAUNCH_TRUST_AGENT_S",
            "android.permission.LOCAL_MAC_ADDRESS",
            "android.permission.LOCATION_HARDWARE",
            "android.permission.LOOP_RADIO",
            "android.permission.MANAGE_ACCOUNTS",
            "android.permission.MANAGE_ACTIVITY_STAC",
            "android.permission.MANAGE_APP_OPS_RESTR",
            "android.permission.MANAGE_APP_TOKENS",
            "android.permission.MANAGE_CA_CERTIFICAT",
            "android.permission.MANAGE_DEVICE_ADMINS",
            "android.permission.MANAGE_DOCUMENTS",
            "android.permission.MANAGE_FINGERPRINT",
            "android.permission.MANAGE_MEDIA_PROJECT",
            "android.permission.MANAGE_NETWORK_POLIC",
            "android.permission.MANAGE_NOTIFICATIONS",
            "android.permission.MANAGE_PROFILE_AND_D",
            "android.permission.MANAGE_SOUND_TRIGGER",
            "android.permission.MANAGE_USB",
            "android.permission.MANAGE_USERS",
            "android.permission.MANAGE_VOICE_KEYPHRA",
            "android.permission.MASTER_CLEAR",
            "android.permission.MEDIA_CONTENT_CONTRO",
            "android.permission.MODIFY_APPWIDGET_BIN",
            "android.permission.MODIFY_AUDIO_ROUTING",
            "android.permission.MODIFY_AUDIO_SETTING",
            "android.permission.MODIFY_CELL_BROADCAS",
            "android.permission.MODIFY_DAY_NIGHT_MOD",
            "android.permission.MODIFY_NETWORK_ACCOU",
            "android.permission.MODIFY_PARENTAL_CONT",
            "android.permission.MODIFY_PHONE_STATE",
            "android.permission.MOUNT_FORMAT_FILESYS",
            "android.permission.MOUNT_UNMOUNT_FILESY",
            "android.permission.MOVE_PACKAGE",
            "android.permission.NET_ADMIN",
            "android.permission.NET_TUNNELING",
            "android.permission.NFC",
            "android.permission.NFC_HANDOVER_STATUS",
            "android.permission.NOTIFY_PENDING_SYSTE",
            "android.permission.OBSERVE_GRANT_REVOKE",
            "android.permission.OEM_UNLOCK_STATE",
            "android.permission.OVERRIDE_WIFI_CONFIG",
            "android.permission.PACKAGE_USAGE_STATS",
            "android.permission.PACKAGE_VERIFICATION",
            "android.permission.PACKET_KEEPALIVE_OFF",
            "android.permission.PEERS_MAC_ADDRESS",
            "android.permission.PERFORM_CDMA_PROVISI",
            "android.permission.PERFORM_SIM_ACTIVATI",
            "android.permission.PERSISTENT_ACTIVITY",
            "android.permission.PROCESS_CALLLOG_INFO",
            "android.permission.PROCESS_PHONE_ACCOUN",
            "android.permission.PROVIDE_TRUST_AGENT",
            "android.permission.QUERY_DO_NOT_ASK_CRE",
            "android.permission.READ_BLOCKED_NUMBERS",
            "android.permission.READ_DREAM_STATE",
            "android.permission.READ_FRAME_BUFFER",
            "android.permission.READ_INPUT_STATE",
            "android.permission.READ_INSTALL_SESSION",
            "android.permission.READ_LOGS",
            "android.permission.READ_NETWORK_USAGE_H",
            "android.permission.READ_OEM_UNLOCK_STAT",
            "android.permission.READ_PRECISE_PHONE_S",
            "android.permission.READ_PRIVILEGED_PHON",
            "android.permission.READ_PROFILE",
            "android.permission.READ_SEARCH_INDEXABL",
            "android.permission.READ_SOCIAL_STREAM",
            "android.permission.READ_SYNC_SETTINGS",
            "android.permission.READ_SYNC_STATS",
            "android.permission.READ_USER_DICTIONARY",
            "android.permission.READ_WIFI_CREDENTIAL",
            "android.permission.REAL_GET_TASKS",
            "android.permission.REBOOT",
            "android.permission.RECEIVE_BLUETOOTH_MA",
            "android.permission.RECEIVE_BOOT_COMPLET",
            "android.permission.RECEIVE_DATA_ACTIVIT",
            "android.permission.RECEIVE_EMERGENCY_BR",
            "android.permission.RECEIVE_MEDIA_RESOUR",
            "android.permission.RECEIVE_STK_COMMANDS",
            "android.permission.RECEIVE_WIFI_CREDENT",
            "android.permission.RECOVERY",
            "android.permission.REGISTER_CALL_PROVID",
            "android.permission.REGISTER_CONNECTION_",
            "android.permission.REGISTER_SIM_SUBSCRI",
            "android.permission.REGISTER_WINDOW_MANA",
            "android.permission.REMOTE_AUDIO_PLAYBAC",
            "android.permission.REMOVE_DRM_CERTIFICA",
            "android.permission.REMOVE_TASKS",
            "android.permission.REORDER_TASKS",
            "android.permission.REQUEST_IGNORE_BATTE",
            "android.permission.REQUEST_INSTALL_PACK",
            "android.permission.RESET_FINGERPRINT_LO",
            "android.permission.RESET_SHORTCUT_MANAG",
            "android.permission.RESTART_PACKAGES",
            "android.permission.RETRIEVE_WINDOW_CONT",
            "android.permission.RETRIEVE_WINDOW_TOKE",
            "android.permission.REVOKE_RUNTIME_PERMI",
            "android.permission.SCORE_NETWORKS",
            "android.permission.SEND_CALL_LOG_CHANGE",
            "android.permission.SEND_DOWNLOAD_COMPLE",
            "android.permission.SEND_RESPOND_VIA_MES",
            "android.permission.SEND_SMS_NO_CONFIRMA",
            "android.permission.SERIAL_PORT",
            "android.permission.SET_ACTIVITY_WATCHER",
            "android.permission.SET_ALWAYS_FINISH",
            "android.permission.SET_ANIMATION_SCALE",
            "android.permission.SET_DEBUG_APP",
            "android.permission.SET_INPUT_CALIBRATIO",
            "android.permission.SET_KEYBOARD_LAYOUT",
            "android.permission.SET_ORIENTATION",
            "android.permission.SET_POINTER_SPEED",
            "android.permission.SET_PREFERRED_APPLIC",
            "android.permission.SET_PROCESS_LIMIT",
            "android.permission.SET_SCREEN_COMPATIBI",
            "android.permission.SET_TIME",
            "android.permission.SET_TIME_ZONE",
            "android.permission.SET_WALLPAPER",
            "android.permission.SET_WALLPAPER_COMPON",
            "android.permission.SET_WALLPAPER_HINTS",
            "android.permission.SHUTDOWN",
            "android.permission.SIGNAL_PERSISTENT_PR",
            "android.permission.START_ANY_ACTIVITY",
            "android.permission.START_PRINT_SERVICE_",
            "android.permission.START_TASKS_FROM_REC",
            "android.permission.STATUS_BAR",
            "android.permission.STATUS_BAR_SERVICE",
            "android.permission.STOP_APP_SWITCHES",
            "android.permission.STORAGE_INTERNAL",
            "android.permission.SUBSCRIBED_FEEDS_REA",
            "android.permission.SUBSCRIBED_FEEDS_WRI",
            "android.permission.SUBSTITUTE_NOTIFICAT",
            "android.permission.SYSTEM_ALERT_WINDOW",
            "android.permission.TABLET_MODE",
            "android.permission.TEMPORARY_ENABLE_ACC",
            "android.permission.TETHER_PRIVILEGED",
            "android.permission.TRANSMIT_IR",
            "android.permission.TRUST_LISTENER",
            "android.permission.TV_INPUT_HARDWARE",
            "android.permission.TV_VIRTUAL_REMOTE_CO",
            "android.permission.UPDATE_APP_OPS_STATS",
            "android.permission.UPDATE_CONFIG",
            "android.permission.UPDATE_DEVICE_STATS",
            "android.permission.UPDATE_LOCK",
            "android.permission.UPDATE_LOCK_TASK_PAC",
            "android.permission.USER_ACTIVITY",
            "android.permission.USE_CREDENTIALS",
            "android.permission.VIBRATE",
            "android.permission.WAKE_LOCK",
            "android.permission.WRITE_APN_SETTINGS",
            "android.permission.WRITE_BLOCKED_NUMBER",
            "android.permission.WRITE_DREAM_STATE",
            "android.permission.WRITE_GSERVICES",
            "android.permission.WRITE_MEDIA_STORAGE",
            "android.permission.WRITE_PROFILE",
            "android.permission.WRITE_SECURE_SETTING",
            "android.permission.WRITE_SETTINGS",
            "android.permission.WRITE_SMS",
            "android.permission.WRITE_SOCIAL_STREAM",
            "android.permission.WRITE_SYNC_SETTINGS",
            "android.permission.WRITE_USER_DICTIONARY"};
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
        HashMap<String, String[]> requested_perms = new HashMap<>();
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
            requested_perms.put(package_name, permissions_array);
        } catch (PackageManager.NameNotFoundException noPackage){
            requested_perms.put("NotFound", new String[]{"NoPerms"});
            Toast toast=Toast.makeText(context,"Package not found!",Toast.LENGTH_SHORT);
            toast.show();
        }
        return new JSONObject(requested_perms);
    }

    public String getGranted(){

        return "";
    }

    public JSONObject getGranted(String package_name){
        HashMap<String, String[]> granted_perms = new HashMap<>();
        final PackageManager pm = context.getPackageManager();
        int perm_index = 0;
        String[] granted_array = new String[permissionsList.length];
            for (perm_index = 0; perm_index <= permissionsList.length; perm_index++) {
                if(pm.checkPermission (permissionsList[perm_index], package_name) == PERMISSION_GRANTED){
                    granted_array[perm_index]=permissionsList[perm_index];
                }
            }
            granted_perms.put(package_name, granted_array);
        return new JSONObject(granted_perms);
    }
}
