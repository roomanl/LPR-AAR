# LPR-AAR
基于开源车牌识别项目[HyperLPR](https://github.com/zeusees/HyperLPR)打包的Android AAR <br/><br/>
因为项目里要用到车牌识别，于是找到了开源项目HyperLPR，自己从没有做过Android NDK项目，所以自己也是弄了很久才把HyperLPR里面的安卓部分运行起来，为了方便像我一样不会NDK的人调用车牌识别，所以我把车牌识别代码打包成AAR了。把AAR放到自己项目，几行代码就可以调用了。<br/><br/>
AAR包在项目app/libs目录下，AAR包括ocr.aar、openCVLibrary346.aar两个包。<br/><br/>
app/release/app-release.apk是我编译好的apk文件，可下载自行体验。<br/><br/>
## 如何把AAR集成到自己的项目？

1、把ocr.aar、openCVLibrary346.aar两个包放到自己项目的app/libs目录下<br/>
2、在app目录下的build.gradle文件中的defaultConfig节点下添加
``` gradle
ndk {
    abiFilters 'armeabi-v7a'
}
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8

}
repositories {
    flatDir {
        dirs 'libs'
    }
}
```
如图：<br/>
![](https://sddman.oss-cn-shenzhen.aliyuncs.com/android/orc1.jpg)<br/>
3、引入AAR包，因为AAR里面使用了Google 的 CamreaX 来做相机预览，所以同时还要引入CamreaX
``` gradle
    def camerax_version = '1.0.0-beta04'
    implementation "androidx.camera:camera-core:$camerax_version"
    implementation "androidx.camera:camera-camera2:$camerax_version"
    implementation "androidx.camera:camera-lifecycle:$camerax_version"
    implementation 'androidx.camera:camera-view:1.0.0-alpha11'
    implementation(name: 'ocr', ext: 'aar')
    implementation(name: 'openCVLibrary346', ext: 'aar')
```
如图：<br/>![](https://sddman.oss-cn-shenzhen.aliyuncs.com/android/orr2.jpg)<br/>
4、调用：
```java
startActivityForResult(new Intent(this, LPRActivity.class), REQUEST_LPR_CODE);
```
接收识别结果：
```java
@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，车牌识别
        if (requestCode == REQUEST_LPR_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String card = data.getStringExtra("card");
                new AlertDialog.Builder(this)
                        .setMessage(card)
                        .setNegativeButton("OK", (dialog, which) -> {
                        })
                        .show();
            }
        }
    }
```
如图：<br/>![](https://sddman.oss-cn-shenzhen.aliyuncs.com/android/orc3.jpg)<br/>
集成完毕<br/>
文字描述不够清楚话的可以把上面的项目下载下来看看里面是怎么集成的。
## 注意
打包项目为AndroidX。不是AndroidX的项目我没测试过是否可以集成，请自行测试