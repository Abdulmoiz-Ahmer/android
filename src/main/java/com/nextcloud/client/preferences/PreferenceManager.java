/*
 * ownCloud Android client application
 *
 * @author David A. Velasco
 * Copyright (C) 2016 ownCloud Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nextcloud.client.preferences;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;

import com.owncloud.android.authentication.AccountUtils;
import com.owncloud.android.datamodel.ArbitraryDataProvider;
import com.owncloud.android.datamodel.FileDataStorageManager;
import com.owncloud.android.datamodel.OCFile;
import com.owncloud.android.ui.activity.ComponentsGetter;
import com.owncloud.android.ui.activity.SettingsActivity;
import com.owncloud.android.utils.FileSortOrder;

import static com.owncloud.android.ui.fragment.OCFileListFragment.FOLDER_LAYOUT_LIST;

/**
 * Helper to simplify reading of Preferences all around the app
 */
public final class PreferenceManager implements AppPreferences {
    /**
     * Constant to access value of last path selected by the user to upload a file shared from other app.
     * Value handled by the app without direct access in the UI.
     */
    private static final String AUTO_PREF__LAST_UPLOAD_PATH = "last_upload_path";
    private static final String AUTO_PREF__UPLOAD_FROM_LOCAL_LAST_PATH = "upload_from_local_last_path";
    private static final String AUTO_PREF__UPLOAD_FILE_EXTENSION_MAP_URL = "prefs_upload_file_extension_map_url";
    private static final String AUTO_PREF__UPLOAD_FILE_EXTENSION_URL = "prefs_upload_file_extension_url";
    private static final String AUTO_PREF__UPLOADER_BEHAVIOR = "prefs_uploader_behaviour";
    private static final String AUTO_PREF__GRID_COLUMNS = "grid_columns";
    private static final String AUTO_PREF__SHOW_DETAILED_TIMESTAMP = "detailed_timestamp";
    public static final String AUTO_PREF__LAST_SEEN_VERSION_CODE = "lastSeenVersionCode";
    private static final String PREF__INSTANT_UPLOADING = "instant_uploading";
    private static final String PREF__INSTANT_VIDEO_UPLOADING = "instant_video_uploading";
    private static final String PREF__INSTANT_UPLOAD_PATH_USE_SUBFOLDERS = "instant_upload_path_use_subfolders";
    private static final String PREF__INSTANT_UPLOAD_ON_WIFI = "instant_upload_on_wifi";
    private static final String PREF__INSTANT_VIDEO_UPLOAD_ON_WIFI = "instant_video_upload_on_wifi";
    private static final String PREF__INSTANT_VIDEO_UPLOAD_PATH_USE_SUBFOLDERS
            = "instant_video_upload_path_use_subfolders";
    private static final String PREF__LEGACY_CLEAN = "legacyClean";
    public static final String PREF__KEYS_MIGRATION = "keysMigration";
    private static final String PREF__FIX_STORAGE_PATH = "storagePathFix";
    private static final String PREF__KEYS_REINIT = "keysReinit";
    private static final String PREF__AUTO_UPLOAD_UPDATE_PATH = "autoUploadPathUpdate";
    private static final String PREF__PUSH_TOKEN = "pushToken";
    private static final String PREF__AUTO_UPLOAD_SPLIT_OUT = "autoUploadEntriesSplitOut";
    private static final String PREF__AUTO_UPLOAD_INIT = "autoUploadInit";
    private static final String PREF__FOLDER_SORT_ORDER = "folder_sort_order";
    private static final String PREF__FOLDER_LAYOUT = "folder_layout";
    public static final String PREF__LOCK_TIMESTAMP = "lock_timestamp";
    private static final String PREF__SHOW_MEDIA_SCAN_NOTIFICATIONS = "show_media_scan_notifications";

    private final SharedPreferences preferences;

    public static AppPreferences fromContext(Context context) {
        SharedPreferences prefs = getDefaultSharedPreferences(context.getApplicationContext());
        return new PreferenceManager(prefs);
    }

    PreferenceManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static void setKeysReInit(Context context) {
        saveBooleanPreference(context, PREF__KEYS_REINIT, true);
    }

    public static boolean getKeysReInit(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__KEYS_REINIT, false);
    }

    public static void setPushToken(Context context, String pushToken) {
        saveStringPreferenceNow(context, PREF__PUSH_TOKEN, pushToken);
    }

    public static String getPushToken(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF__PUSH_TOKEN, "");
    }

    @Override
    public boolean instantPictureUploadEnabled() {
        return preferences.getBoolean(PREF__INSTANT_UPLOADING, false);
    }

    @Override
    public boolean instantVideoUploadEnabled() {
        return preferences.getBoolean(PREF__INSTANT_VIDEO_UPLOADING, false);
    }

    public static boolean instantPictureUploadPathUseSubfolders(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__INSTANT_UPLOAD_PATH_USE_SUBFOLDERS, false);
    }

    public static boolean instantPictureUploadViaWiFiOnly(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__INSTANT_UPLOAD_ON_WIFI, false);
    }

    public static boolean instantVideoUploadPathUseSubfolders(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__INSTANT_VIDEO_UPLOAD_PATH_USE_SUBFOLDERS, false);
    }

    public static boolean instantVideoUploadViaWiFiOnly(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__INSTANT_VIDEO_UPLOAD_ON_WIFI, false);
    }

    public static boolean instantPictureUploadWhenChargingOnly(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("instant_upload_on_charging", false);
    }

    public static boolean instantVideoUploadWhenChargingOnly(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("instant_video_upload_on_charging",
                false);
    }

    public static boolean showHiddenFilesEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("show_hidden_files_pref", false);
    }

    @Override
    public int getUploadUrlFileExtensionUrlSelectedPos() {
        return preferences.getInt(AUTO_PREF__UPLOAD_FILE_EXTENSION_URL, 0);
    }

    @Override
    public void setUploadUrlFileExtensionUrlSelectedPos(int selectedPos) {
        preferences.edit().putInt(AUTO_PREF__UPLOAD_FILE_EXTENSION_URL, selectedPos).apply();
    }

    @Override
    public int getUploadMapFileExtensionUrlSelectedPos() {
        return preferences.getInt(AUTO_PREF__UPLOAD_FILE_EXTENSION_MAP_URL, 0);
    }

    @Override
    public void setUploadMapFileExtensionUrlSelectedPos(int selectedPos) {
        preferences.edit().putInt(AUTO_PREF__UPLOAD_FILE_EXTENSION_MAP_URL, selectedPos).apply();
    }

    @Override
    public String getUploadFromLocalLastPath() {
        return preferences.getString(AUTO_PREF__UPLOAD_FROM_LOCAL_LAST_PATH, "");
    }

    @Override
    public void setUploadFromLocalLastPath(String path) {
        preferences.edit().putString(AUTO_PREF__UPLOAD_FROM_LOCAL_LAST_PATH, path).apply();
    }

    @Override
    public String getLastUploadPath() {
        return preferences.getString(AUTO_PREF__LAST_UPLOAD_PATH, "");
    }

    @Override
    public void setLastUploadPath(String path) {
        preferences.edit().putString(AUTO_PREF__LAST_UPLOAD_PATH, path).apply();
    }

    /**
     * Gets the lock preference configured by the user.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return lock     lock preference value.
     */
    public static String getLockPreference(Context context) {
        return getDefaultSharedPreferences(context).getString(SettingsActivity.PREFERENCE_LOCK, "");
    }

    /**
     * Gets the lock via fingerprint preference configured by the user.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return useFingerprint     is lock via fingerprint preference.
     */
    public static boolean isUseFingerprint(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(SettingsActivity.PREFERENCE_USE_FINGERPRINT, false);
    }

    /**
     * Get preferred folder display type.
     *
     * @param context Caller {@link Context}, used to access to preferences manager.
     * @param folder Folder
     * @return preference value, default is
     * {@link com.owncloud.android.ui.fragment.OCFileListFragment#FOLDER_LAYOUT_LIST}
     */
    public static String getFolderLayout(Context context, OCFile folder) {
        return getFolderPreference(context, PREF__FOLDER_LAYOUT, folder, FOLDER_LAYOUT_LIST);
    }

    /**
     * Set preferred folder display type.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @param folder Folder
     * @param layout_name preference value
     */
    public static void setFolderLayout(Context context, OCFile folder, String layout_name) {
        setFolderPreference(context, PREF__FOLDER_LAYOUT, folder, layout_name);
    }

    /**
     * Get preferred folder sort order.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return sort order     the sort order, default is {@link FileSortOrder#sort_a_to_z} (sort by name)
     */
    public static FileSortOrder getSortOrderByFolder(Context context, OCFile folder) {
        return FileSortOrder.sortOrders.get(getFolderPreference(context, PREF__FOLDER_SORT_ORDER, folder,
            FileSortOrder.sort_a_to_z.name));
    }

    /**
     * Set preferred folder sort order.
     *
     * @param context   Caller {@link Context}, used to access to shared preferences manager.
     * @param sortOrder the sort order
     */
    public static void setSortOrder(Context context, OCFile folder, FileSortOrder sortOrder) {
        setFolderPreference(context, PREF__FOLDER_SORT_ORDER, folder, sortOrder.name);
    }

    public static FileSortOrder getSortOrderByType(Context context, FileSortOrder.Type type) {
        return getSortOrderByType(context, type, FileSortOrder.sort_a_to_z);
    }

    /**
     * Get preferred folder sort order.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return sort order     the sort order, default is {@link FileSortOrder#sort_a_to_z} (sort by name)
     */
    public static FileSortOrder getSortOrderByType(Context context, FileSortOrder.Type type,
                                                   FileSortOrder defaultOrder) {
        Account account = AccountUtils.getCurrentOwnCloudAccount(context);

        if (account == null) {
            return defaultOrder;
        }

        ArbitraryDataProvider dataProvider = new ArbitraryDataProvider(context.getContentResolver());

        String value = dataProvider.getValue(account.name, PREF__FOLDER_SORT_ORDER + "_" + type);

        return value.isEmpty() ? defaultOrder : FileSortOrder.sortOrders.get(value);
    }

    /**
     * Set preferred folder sort order.
     *
     * @param context   Caller {@link Context}, used to access to shared preferences manager.
     * @param sortOrder the sort order
     */
    public static void setSortOrder(Context context, FileSortOrder.Type type, FileSortOrder sortOrder) {
        Account account = AccountUtils.getCurrentOwnCloudAccount(context);
        ArbitraryDataProvider dataProvider = new ArbitraryDataProvider(context.getContentResolver());

        dataProvider.storeOrUpdateKeyValue(account.name, PREF__FOLDER_SORT_ORDER + "_" + type, sortOrder.name);
    }

    /**
     * Get preference value for a folder.
     * If folder is not set itself, it finds an ancestor that is set.
     *
     * @param context Context object.
     * @param preferenceName Name of the preference to lookup.
     * @param folder Folder.
     * @param defaultValue Fallback value in case no ancestor is set.
     * @return Preference value
     */
    public static String getFolderPreference(Context context, String preferenceName, OCFile folder,
                                             String defaultValue) {
        Account account = AccountUtils.getCurrentOwnCloudAccount(context);

        if (account == null) {
            return defaultValue;
        }

        ArbitraryDataProvider dataProvider = new ArbitraryDataProvider(context.getContentResolver());
        FileDataStorageManager storageManager = ((ComponentsGetter)context).getStorageManager();

        if (storageManager == null) {
            storageManager = new FileDataStorageManager(account, context.getContentResolver());
        }

        String value = dataProvider.getValue(account.name, getKeyFromFolder(preferenceName, folder));
        while (folder != null && value.isEmpty()) {
            folder = storageManager.getFileById(folder.getParentId());
            value = dataProvider.getValue(account.name, getKeyFromFolder(preferenceName, folder));
        }
        return value.isEmpty() ? defaultValue : value;
    }

    /**
     * Get preference value for a view.
     *
     * @param context        Context object.
     * @param preferenceName Name of the preference to lookup.
     * @param type           view which view
     * @param defaultValue   Fallback value in case nothing is set.
     * @return Preference value
     */
    public static String getTypePreference(Context context, String preferenceName, String type, String defaultValue) {
        Account account = AccountUtils.getCurrentOwnCloudAccount(context);

        if (account == null) {
            return defaultValue;
        }

        ArbitraryDataProvider dataProvider = new ArbitraryDataProvider(context.getContentResolver());

        String value = dataProvider.getValue(account.name, preferenceName + "_" + type);

        return value.isEmpty() ? defaultValue : value;
    }

    /**
     * Set preference value for a folder.
     *
     * @param context Context object.
     * @param preferenceName Name of the preference to set.
     * @param folder Folder.
     * @param value Preference value to set.
     */
    public static void setFolderPreference(Context context, String preferenceName, OCFile folder, String value) {
        Account account = AccountUtils.getCurrentOwnCloudAccount(context);
        ArbitraryDataProvider dataProvider = new ArbitraryDataProvider(context.getContentResolver());
        dataProvider.storeOrUpdateKeyValue(account.name, getKeyFromFolder(preferenceName, folder), value);
    }

    private static String getKeyFromFolder(String preferenceName, OCFile folder) {
        final String folderIdString = String.valueOf(folder != null ? folder.getFileId() :
                FileDataStorageManager.ROOT_PARENT_ID);

        return preferenceName + "_" + folderIdString;
    }

    public static boolean getAutoUploadInit(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__AUTO_UPLOAD_INIT, false);
    }

    /**
     * Gets the legacy cleaning flag last set.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return ascending order     the legacy cleaning flag, default is false
     */
    public static boolean getLegacyClean(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__LEGACY_CLEAN, false);
    }

    public static boolean getKeysMigration(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__KEYS_MIGRATION, false);
    }

    public static boolean getStoragePathFix(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__FIX_STORAGE_PATH, false);
    }


    /**
     * Gets the auto upload paths flag last set.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return ascending order     the legacy cleaning flag, default is false
     */
    public static boolean getAutoUploadPathsUpdate(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__AUTO_UPLOAD_UPDATE_PATH, false);
    }

    /**
     * Gets the auto upload split out flag last set.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return ascending order     the legacy cleaning flag, default is false
     */
    public static boolean getAutoUploadSplitEntries(Context context) {
        return getDefaultSharedPreferences(context).getBoolean(PREF__AUTO_UPLOAD_SPLIT_OUT, false);
    }

    /**
     * Saves the legacy cleaning flag which the user has set last.
     *
     * @param context     Caller {@link Context}, used to access to shared preferences manager.
     * @param legacyClean flag if it is a legacy cleaning
     */
    public static void setLegacyClean(Context context, boolean legacyClean) {
        saveBooleanPreference(context, PREF__LEGACY_CLEAN, legacyClean);
    }

    public static void setKeysMigration(Context context, boolean keysMigration) {
        saveBooleanPreference(context, PREF__KEYS_MIGRATION, keysMigration);
    }

    public static void setStoragePathFix(Context context, boolean storagePathFix) {
        saveBooleanPreference(context, PREF__FIX_STORAGE_PATH, storagePathFix);
    }

    public static void setAutoUploadInit(Context context, boolean autoUploadInit) {
        saveBooleanPreference(context, PREF__AUTO_UPLOAD_INIT, autoUploadInit);
    }

    /**
     * Saves the legacy cleaning flag which the user has set last.
     *
     * @param context    Caller {@link Context}, used to access to shared preferences manager.
     * @param pathUpdate flag if it is a auto upload path update
     */
    public static void setAutoUploadPathsUpdate(Context context, boolean pathUpdate) {
        saveBooleanPreference(context, PREF__AUTO_UPLOAD_UPDATE_PATH, pathUpdate);
    }

    /**
     * Saves the flag for split entries magic
     *
     * @param context    Caller {@link Context}, used to access to shared preferences manager.
     * @param splitOut flag if it is a auto upload path update
     */
    public static void setAutoUploadSplitEntries(Context context, boolean splitOut) {
        saveBooleanPreference(context, PREF__AUTO_UPLOAD_SPLIT_OUT, splitOut);
    }

    /**
     * Gets the uploader behavior which the user has set last.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return uploader behavior     the uploader behavior
     */
    public static int getUploaderBehaviour(Context context) {
        return getDefaultSharedPreferences(context).getInt(AUTO_PREF__UPLOADER_BEHAVIOR, 1);
    }

    /**
     * Saves the uploader behavior which the user has set last.
     *
     * @param context           Caller {@link Context}, used to access to shared preferences manager.
     * @param uploaderBehaviour the uploader behavior
     */
    public static void setUploaderBehaviour(Context context, int uploaderBehaviour) {
        saveIntPreference(context, AUTO_PREF__UPLOADER_BEHAVIOR, uploaderBehaviour);
    }

    /**
     * Gets the grid columns which the user has set last.
     *
     * @return grid columns     grid columns
     */
    @Override
    public float getGridColumns() {
        return preferences.getFloat(AUTO_PREF__GRID_COLUMNS, 4.0f);
    }

    /**
     * Saves the grid columns which the user has set last.
     *
     * @param gridColumns the uploader behavior
     */
    @Override
    public void setGridColumns(float gridColumns) {
        preferences.edit().putFloat(AUTO_PREF__GRID_COLUMNS, gridColumns).apply();
    }

    /**
     * Gets the last seen version code right before updating.
     *
     * @param context Caller {@link Context}, used to access to shared preferences manager.
     * @return grid columns     grid columns
     */
    public static int getLastSeenVersionCode(Context context) {
        return getDefaultSharedPreferences(context).getInt(AUTO_PREF__LAST_SEEN_VERSION_CODE, 0);
    }

    /**
     * Saves the version code as the last seen version code.
     *
     * @param context   Caller {@link Context}, used to access to shared preferences manager.
     * @param versionCode the app's version code
     */
    public static void setLastSeenVersionCode(Context context, int versionCode) {
        saveIntPreference(context, AUTO_PREF__LAST_SEEN_VERSION_CODE, versionCode);
    }

    public static long getLockTimestamp(Context context) {
        return getDefaultSharedPreferences(context).getLong(PREF__LOCK_TIMESTAMP, 0);
    }

    public static void setLockTimestamp(Context context, long timestamp) {
        saveLongPreference(context, PREF__LOCK_TIMESTAMP, timestamp);
    }

    @Override
    public boolean isShowDetailedTimestampEnabled() {
        return preferences.getBoolean(AUTO_PREF__SHOW_DETAILED_TIMESTAMP, false);
    }

    @Override
    public void setShowDetailedTimestampEnabled(boolean showDetailedTimestamp) {
        preferences.edit().putBoolean(AUTO_PREF__SHOW_DETAILED_TIMESTAMP, showDetailedTimestamp).apply();
    }

    @Override
    public boolean isShowMediaScanNotifications() {
        return preferences.getBoolean(PREF__SHOW_MEDIA_SCAN_NOTIFICATIONS, true);
    }

    @Override
    public void setShowMediaScanNotifications(boolean value) {
        preferences.edit().putBoolean(PREF__SHOW_MEDIA_SCAN_NOTIFICATIONS, value).apply();
    }

    private static void saveBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences.Editor appPreferences = getDefaultSharedPreferences(context.getApplicationContext()).edit();
        appPreferences.putBoolean(key, value).apply();
    }

    private static void saveStringPreference(Context context, String key, String value) {
        SharedPreferences.Editor appPreferences = getDefaultSharedPreferences(context.getApplicationContext()).edit();
        appPreferences.putString(key, value).apply();
    }

    private static void saveStringPreferenceNow(Context context, String key, String value) {
        SharedPreferences.Editor appPreferences = getDefaultSharedPreferences(context.getApplicationContext()).edit();
        appPreferences.putString(key, value);
        appPreferences.apply();
    }

    private static void saveIntPreference(Context context, String key, int value) {
        SharedPreferences.Editor appPreferences = getDefaultSharedPreferences(context.getApplicationContext()).edit();
        appPreferences.putInt(key, value).apply();
    }

    private static void saveFloatPreference(Context context, String key, float value) {
        SharedPreferences.Editor appPreferences = getDefaultSharedPreferences(context.getApplicationContext()).edit();
        appPreferences.putFloat(key, value).apply();
    }

    private static void saveLongPreference(Context context, String key, long value) {
        SharedPreferences.Editor appPreferences = getDefaultSharedPreferences(context.getApplicationContext()).edit();
        appPreferences.putLong(key, value).apply();
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    @Override
    public void removeLegacyPreferences() {
        preferences.edit()
                .remove("instant_uploading")
                .remove("instant_video_uploading")
                .remove("instant_upload_path")
                .remove("instant_upload_path_use_subfolders")
                .remove("instant_upload_on_wifi")
                .remove("instant_upload_on_charging")
                .remove("instant_video_upload_path")
                .remove("instant_video_upload_path_use_subfolders")
                .remove("instant_video_upload_on_wifi")
                .remove("instant_video_uploading")
                .remove("instant_video_upload_on_charging")
                .remove("prefs_instant_behaviour")
                .apply();
    }
}
