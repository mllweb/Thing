package com.mllweb.thing;

import android.app.ActivityManager;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.umeng.socialize.Config;
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
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
        } else {
            initImageLoader();
            initEaseIm();
            initShare();
        }
    }

    private void initShare() {
        Config.isloadUrl = true;
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx12eecd86470e90cf", "d4624c36b6795d1d99dcf0547af5443d");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
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
        L.writeDebugLogs(false);
        L.writeLogs(false);
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
