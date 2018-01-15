apply plugin: 'com.android.application'

android {
    compileSdkVersion 24
    buildToolsVersion "24.0.0"
    defaultConfig {
        applicationId "${packagefullname}"
        minSdkVersion 15
        targetSdkVersion 24
        versionCode 1
        versionName "1.0"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:+'
    compile 'com.android.support:support-v4:+'
    compile 'com.android.support:support-vector-drawable:+'
}
