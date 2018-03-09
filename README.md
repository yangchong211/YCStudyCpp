#### 1.0 本案例关联博客[共13篇]
> **1.关于音频基础博客**
>
- 0.0.1 [如何扫描本地音频或者视频](http://www.jcodecraeer.com/plus/view.php?aid=9305)
- 0.0.2 [深入学习酷狗，混沌大学那种锁屏页面原理](http://blog.csdn.net/m0_37700275/article/details/79262793)
- 0.0.3 [为什么要捕获/丢弃音频焦点详细讲解](http://blog.csdn.net/m0_37700275/article/details/79269250)
- 0.0.4 [耳机声控，以及耳机拔出或者插入控制播放暂停](http://blog.csdn.net/m0_37700275/article/details/79269722)
- 0.0.5 [音视频编码解码深入分析总结](http://www.jcodecraeer.com/plus/view.php?aid=9334)
- 0.0.6 [实现音视频1.5倍，2倍播放速率](http://www.jcodecraeer.com/plus/view.php?aid=9313)
- 0.0.7 [音频的加密与解密案例实践深入总结](http://www.jcodecraeer.com/plus/view.php?aid=9331)
- 0.0.8 [音频基础知识和概念的介绍](http://www.jcodecraeer.com/plus/view.php?aid=9322)
- 0.0.9 [音视频混合案例介绍]后期更新
- 0.1.0 [如何构建知识图谱，**重点**](https://zhuanlan.zhihu.com/p/33563691)
- 0.1.1 [完整视频播放器案例总结与分析](http://www.jcodecraeer.com/plus/view.php?aid=9147)
- 0.1.2 [关于博客笔记大汇总](https://www.jianshu.com/p/53017c3fc75d)
- 0.1.3 [Android编码规范](https://my.oschina.net/zbj1618/blog/1620101)
- 0.1.4 [Notification通知控制播放](http://www.jcodecraeer.com/plus/view.php?aid=9482)


#### 1.1 基本实现的功能

> **A基础功能**
>
- 1.1.1 基础的音频播放功能有：播放，暂停，下一首，上一首
- 1.1.2 播放监听，播放完了自动下一首；滑动监听，拖动SeekBar可以控制播放进度，进度条显示播放进度功能
- 1.1.3 设置音量控制监听，手机上音量滑动监听，手机按键控制音量，还有耳机控制音量。注意要同步！
- 1.1.4 播放类型：顺序循环播放，随机循环播放，单曲循环播放等等
- 1.1.5 播放进度快慢设置：参考混沌大学，播放速度可以设置为1.0x,1.5x,2.0x
- 1.1.6 播放快进快退，参考混沌大学，间隔时间是15秒
- 1.1.7 可通过媒体按钮和 Notification 通知栏来控制媒体播放
- 1.1.8 停止播放后，可通过滑动移除 Notification 来关闭应用，这个是参考喜马拉雅APP，因为平时玩的比较多，所以个人感觉这个功能还是不错的。我的喜马拉雅：http://www.ximalaya.com/71989305/profile/


#### 1.2 音频的缓存，下载，播放权限等功能
> **B其他功能**
>
- 1.2.1 音频可以边播放变缓存
- 1.2.2 支持下载到本地，如果有付费音频，还需要转码加密
- 1.2.3 支持设置播放权限


#### 1.3 音频后台播放功能
> **C其他功能**
>
- **1.3.1 音频播放可以支持后台播放**
- 当切换到后台时，显示通知栏，可以通过通知栏来控制上一首，下一首，播放暂停功能，主要需要保证播放进度和音频属性数据信息同步
- **1.3.2 Android系统有自动回收内存机制**
- 如果系统内存紧张，就会触发该机制，应用就有可能被回收，不过Android提供了前台机制，比如当音频播放器切换到后台时，这个时候可以通过通知栏中按钮，点击切换音乐，那么当播放时启动前台机制，而暂停时取消前台机制。**保证内存不足时也不会回收该应用**。


#### 1.4 关于主要页面的示意图
- ![image](http://upload-images.jianshu.io/upload_images/4432347-321ca95b07ceaa6d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image](http://upload-images.jianshu.io/upload_images/4432347-ff0234e301c0018f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image](http://upload-images.jianshu.io/upload_images/4432347-d79a02ff20308fc6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image](http://upload-images.jianshu.io/upload_images/4432347-12af346217b1b2c5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)