package com.mllweb.thing;

import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.umeng.socialize.PlatformConfig;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
        }else {
            initImageLoader();
            initEaseIm();
            initShare();
        }
    }
private void initShare(){
    //微信 appid appsecret
    PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
    //新浪微博 appkey appsecret
    PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");
    // QQ和Qzone appid appkey
    PlatformConfig.setQQZone("1105370635", "LRy2r96UVlc6vf6A");
}



    private void initEaseIm() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//              .memoryCacheExtraOptions(480, 800)//即保存的每个缓存文件的最大长宽
//              .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)//设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3) // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)   // 线程优先级
//              .denyCacheImageMultipleSizesInMemory()
//              .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
//              .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024) //硬盘缓存50MB
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//              .diskCacheFileCount(100) //缓存的File数量
//              .diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
//              .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//              .imageDownloader(new BaseImageDownloader(context, 5 * 1000,   30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//              .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    private DisplayImageOptions getListOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
//                .showImageOnLoading(R.drawable.ic_stub)
                // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageForEmptyUri(R.drawable.ic_stub)
                // 设置图片加载/解码过程中错误时候显示的图片
//                .showImageOnFail(R.drawable.ic_error)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)
                // 保留Exif信息
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)// int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();
        return options;
    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
