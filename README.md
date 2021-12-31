 <h1 align="center">HMS 3D Modelling Collection App Github Documentation</h3>
 
 ![Latest Version](https://img.shields.io/badge/latestVersion-1.0.0-yellow) ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
<br>
![Supported Platforms](https://img.shields.io/badge/Supported_Platforms:-Native_Android-orange)


# Introduction

In this documentation we explained development steps of 3D Modelling Collection App.

# Compatibility

|   | Show 3D Model | 3D Object Reconstruction |
| --- | --- | --- |
| Native (Java) | ✅ | ✅ |

# How to start?
  
# Register a developer account on HUAWEI Developers and configure.

1. Register in to [Huawei Developer Console] (https://developer.huawei.com/consumer/en/console) and Create and configure an app and enable 3D Modeling Kit in AppGallery Connect.
2. To use 3D Modeling Kit, you need to enable it in AppGallery Connect. For details, please refer to Enabling Services(https://developer.huawei.com/consumer/en/doc/distribution/app/agc-help-enabling-service-0000001146598793).


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
    // For reconstruct we need to login
    implementation 'com.huawei.hms:hwid:6.3.0.300'
    implementation 'com.huawei.hms:modeling3d-object-reconstruct:{version}' 
     
     // CameraX
    def camerax_version = "1.0.0-rc01"
    // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation "androidx.camera:camera-core:${camerax_version}"
    implementation "androidx.camera:camera-camera2:${camerax_version}"
    // If you want to additionally use the CameraX Lifecycle library
    implementation "androidx.camera:camera-lifecycle:${camerax_version}"
    // If you want to additionally use the CameraX View class
    implementation "androidx.camera:camera-view:1.0.0-alpha20"
}
```
## **Permissions**
```xml
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.CAMERA" />
```
## **3D Reconstruct**
```java
      //Init Engine
      magic3dReconstructEngine = Modeling3dReconstructEngine.getInstance(Modeling3dApp.app);
     //Create Listener
       private Modeling3dReconstructDownloadListener magic3dReconstructDownloadListener = new Modeling3dReconstructDownloadListener() {
        @Override
        public void onDownloadProgress(String taskId, double progress, Object ext) {
            dialog.setCurrentProgress(progress);
        }

        @Override
        public void onResult(String taskId, Modeling3dReconstructDownloadResult result, Object ext) {
            ((Activity) mContext).runOnUiThread(() -> {
                Toast.makeText(getContext(), "Download completed", Toast.LENGTH_SHORT).show();
                TaskInfoAppDbUtils.updateDownloadByTaskId(taskId, 1);
                dialog.dismiss();
            });
        }

        @Override
        public void onError(String taskId, int errorCode, String message) {
            LogUtil.e(taskId + " <---> " + errorCode + message);
            ((Activity) mContext).runOnUiThread(() -> {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        }
    };
        private final Modeling3dReconstructUploadListener uploadListener = new Modeling3dReconstructUploadListener() {
        @Override
        public void onUploadProgress(String taskId, double progress, Object ext) {
            dialog.setCurrentProgress(progress);
        }

        @Override
        public void onResult(String taskId, Modeling3dReconstructUploadResult result, Object ext) {
            if (result.isComplete()) {
                LogUtil.i(TAG + result.isComplete());
                TaskInfoAppDbUtils.updateStatusByTaskId(taskId, ConstantBean.MODELS_UPLOAD_COMPLETED_STATUS);
                updateTaskStatus(adapter, taskId, ConstantBean.MODELS_UPLOAD_COMPLETED_STATUS);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String taskId, int errorCode, String message) {
            LogUtil.i("UPLOAD FAILED-->" + errorCode + "<----->" + message);
            ((Activity) mContext).runOnUiThread(() -> {
                dialog.dismiss();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            });
        }
    };
     // Upload Images
     magic3dReconstructEngine.uploadFile(news.getTaskId(), news.getFileUploadPath());
     // Settings
       Modeling3dReconstructSetting setting = new Modeling3dReconstructSetting.Factory()
                    .setReconstructMode(type)
                    .create();
```
## **3D Object Preview**
We used Android 3D Model Viewer for preview ready objects.
https://github.com/the3deers/android-3D-model-viewer
```java
     // This component will draw the actual models using OpenGL
     drawer = new DrawerFactory(modelSurfaceView.getContext());
     // Create an OpenGL ES 2.0 context.
     setEGLContextClientVersion(2);

     // This is the actual renderer of the 3D space
     mRenderer = new ModelRenderer(this);
     setRenderer(mRenderer);

     touchHandler = new TouchController(this, mRenderer);
```
## **Main Page**

![image](https://user-images.githubusercontent.com/8115505/147744514-9d74ce47-746b-4611-a444-9d5432e6cc2a.png)

## **3D Modelling Page**

![image](https://user-images.githubusercontent.com/8115505/147745090-d7a76a0e-bb64-48b5-a384-68ed54c10f95.png)

## **Capture Photos**
![image](https://user-images.githubusercontent.com/8115505/147745166-8ff5527f-d90c-455c-975a-3b0921e6e810.png)

![image](https://user-images.githubusercontent.com/8115505/147744988-0b163891-ee91-4955-a9ba-0c3150cddfce.png)
