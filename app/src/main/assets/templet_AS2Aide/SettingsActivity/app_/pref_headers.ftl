<preference-headers xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- These settings headers are only used on tablets. -->

    <header
        android:fragment="${packagefullname}.${activityname}$GeneralPreferenceFragment"
        android:icon="@drawable/ic_info_black_24dp"
        android:title="@string/pref_header_general"/>

    <header
        android:fragment="${packagefullname}.${activityname}$NotificationPreferenceFragment"
        android:icon="@drawable/ic_notifications_black_24dp"
        android:title="@string/pref_header_notifications"/>

    <header
        android:fragment="${packagefullname}.${activityname}$DataSyncPreferenceFragment"
        android:icon="@drawable/ic_sync_black_24dp"
        android:title="@string/pref_header_data_sync"/>

</preference-headers>
