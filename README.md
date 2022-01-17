 <h1 align="center">HMS Audio Editor - Musika App Github Documentation</h3>
 
 ![Latest Version](https://img.shields.io/badge/latestVersion-1.0.0-yellow) ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
<br>
![Supported Platforms](https://img.shields.io/badge/Supported_Platforms:-Native_Android-orange)


# Introduction

In this documentation we explained development steps of HMS Audio Editor - Musika App.

This project help you to edit audio and set style, adjusting pitch and sound tracks. It also provides the recording feature and user can export the audio file to the directory and shows how to convert audio file types each other like MP3, WAV, or FLAC. 

# Functions
- Supports basic audio editing operations such as changing the volume, adjusting the tempo or pitch, copying and deleting audio.
- Supports setting style, arrange sound field
- Supports audio recording and importing
- Converts audio format to MP3, WAV or FLAC.

# Compatibility

|   | Audio Edit | Convert Audio File Type |
| --- | --- | --- |
| Native (Java) | ✅ | ✅ |

# How to start?
  
# Register a developer account on HUAWEI Developers and configure.

1. Register in to [Huawei Developer Console] (https://developer.huawei.com/consumer/en/console) and Create and configure an app and enable Audio Editor in AppGallery Connect.
2. To use Audio Editor Kit, you need to enable it in AppGallery Connect. For details, please refer to Enabling Services(https://developer.huawei.com/consumer/en/doc/distribution/app/agc-help-enabling-service-0000001146598793).


##   Adding the AppGallery Connect Configuration File of Your App
1. Sign in to AppGallery Connect and click My projects.
2. Find your project and click the app for which you want to integrate the HMS Core SDK.
3. Go to Project settings > General information. In the App information area, download the agconnect-services.json file.

 ##  Configuring the Maven Repository Address for the HMS Core SDK
1. Configuring the Maven Repository Address for the HMS Core SDK
2. Open the build.gradle file in the root directory of your Android Studio project.
3. Add the AppGallery Connect plugin and the Maven repository.

**Note** : A device with Huawei Mobile Services (HMS) installed is required
In the **project-level** build.gradle, include Huawei's Maven repository.

```groovy
buildscript { 
    repositories { 
        google() 
        jcenter() 
        // Configure the Maven repository address for the HMS Core SDK. 
        maven {url 'https://developer.huawei.com/repo/'} 
    } 
    dependencies { 
        ... 
        // Add the AppGallery Connect plugin configuration. You are advised to use the latest plugin version. 
        classpath 'com.huawei.agconnect:agcp:1.6.0.300' 
    } 
} 
 
allprojects { 
    repositories { 
        google() 
        jcenter() 
        // Configure the Maven repository address for the HMS Core SDK. 
        maven {url 'https://developer.huawei.com/repo/'} 
    } 
} 
```
4. Adding Build Dependencies
```groovy
dependencies { 
   implementation 'com.huawei.hms:audio-editor-sdk:1.1.0.300'
   implementation 'com.huawei.hms:audio-editor-ui:1.1.0.300'
}
```
## **Permissions**
```xml
 <!-- Vibrate -->
 <uses-permission android:name="android.permission.VIBRATE" />
 <!-- Microphone -->
 <uses-permission android:name="android.permission.RECORD_AUDIO" />
 <!-- Write into storage -->
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 <!-- Read from storage -->
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 <!-- Connect to Internet -->
 <uses-permission android:name="android.permission.INTERNET" />
 <!-- Obtain the network status -->
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 <!-- Obtain the changed network connectivity state -->
 <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
 <!-- Manage files. Declare this permission only when the version of Android in use is 11 or later. -->
 <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
```
## **Initializing the Running Environment**
```java
 // Create an instance of HuaweiAudioEditor.
 HuaweiAudioEditor mEditor = HuaweiAudioEditor.create(mContext);
 // Initialize the running environment for HuaweiAudioEditor.
 mEditor.initEnvironment();
 // Create a timeline.
 HAETimeLine mTimeLine = mEditor.getTimeLine();
 // Create a lane.
 HAEAudioLane audioLane = mTimeLine.appendAudioLane();
```

## **Import audio**
```java
 // Add an audio asset to the end of the audio lane.
 HAEAudioAsset audioAsset = audioLane.appendAudioAsset("/sdcard/download/test.mp3", mTimeLine.getCurrentTime());
```
## **Conver audio file **
```java
private fun convertFileToSelectedFormat(context: Context) {
        // API for converting the audio format.
        HAEAudioExpansion.getInstance()
            .transformAudio(context, sourceFilePath, destFilePath, object : OnTransformCallBack {
                // Called to receive the progress which ranges from 0 to 100.
                @SuppressLint("SetTextI18n")
                override fun onProgress(progress: Int) {
                    progressBar!!.visibility = View.VISIBLE
                    txtProgress!!.visibility = View.VISIBLE
                    progressBar!!.progress = progress
                    txtProgress!!.text = "$progress/100"
                }
                // Called when the conversion fails.
                override fun onFail(errorCode: Int) {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                }
                // Called when the conversion succeeds.
                @SuppressLint("SetTextI18n")
                override fun onSuccess(outPutPath: String) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    txtDestFilePath!!.text = "Destination Path : $outPutPath"
                }
                // Cancel conversion.
                override fun onCancel() {
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                }
            })
    }
  ```

## **Main Page**
 
![audio1](https://user-images.githubusercontent.com/8115505/147818609-cce8bad9-2ffd-4b10-9600-69537daf4726.jpg)
![image](https://user-images.githubusercontent.com/8115505/149723835-599315ba-cb1c-4d16-8681-2c69176a24ea.png)
![Screenshot_20220117_100911_com huawei musika](https://user-images.githubusercontent.com/8115505/149723616-2ae1f446-eff7-430a-b7d9-5b2252a9d8b1.jpg)
![Screenshot_20220117_100928_com huawei musika](https://user-images.githubusercontent.com/8115505/149723618-d7e45d98-6200-4601-aabe-540a5d5f56a8.jpg)
