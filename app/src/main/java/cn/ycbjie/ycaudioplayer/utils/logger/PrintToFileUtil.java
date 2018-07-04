package cn.ycbjie.ycaudioplayer.utils.logger;


import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211/YCStatusBar
 *     time  : 2018/06/4
 *     desc  : 写入和读取工具类
 *     revise:
 * </pre>
 */
public class PrintToFileUtil {

    private static ExecutorService sExecutor;
    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT         = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");

    /**
     * 将内容直接写过文件中，自己设置路径
     * 这个是一边打印日志，一边将日志写入到file
     * 不建议直接new一个子线程做写入逻辑，建议开启线程池，避免到处开启线程损耗性能
     * @param input                     写入内容
     * @param filePath                  路径
     * @return
     */
    static boolean input2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newScheduledThreadPool(5);
        }
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    // 构造给定文件名的FileWriter对象，并使用布尔值指示是否追加写入的数据。
                    FileWriter fileWriter = new FileWriter(filePath, true);
                    bw = new BufferedWriter(fileWriter);
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            return submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }



    private static boolean captureLogThreadOpen = true;
    private static int logCount;

    /**
     * 遇到问题：
     * 1.什么时候调用这个方法？
     * 2.为什么调用这个方法会造成程序无响应，卡在什么地方？
     * 3.如何避免造成线程堵塞
     * 4.当打印到一定日志后，如何切换到下一个.txt文件？
     * @param logPath
     */
    public static void setPrint(String logPath){
        if (sExecutor == null) {
            sExecutor = Executors.newScheduledThreadPool(5);
        }

        //以xx月xx日为命名文件名，这样每天的文件都是不一样的
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String date = format.substring(0, 5);
        final String newLogPath = logPath +date;

        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                /*核心代码*/
                while(captureLogThreadOpen){
                    /*命令的准备*/
                    ArrayList<String> getLog = new ArrayList<>();
                    getLog.add("logcat");
                    getLog.add("-d");
                    getLog.add("-v");
                    getLog.add("time");
                    ArrayList<String> clearLog = new ArrayList<>();
                    clearLog.add("logcat");
                    clearLog.add("-c");
                    try{
                        //抓取当前的缓存日志
                        Process process = Runtime.getRuntime().exec(getLog.toArray(new String[getLog.size()]));
                        //获取输入流
                        BufferedReader buffRead = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        //清除是为了下次抓取不会从头抓取
                        Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));


                        String str = null;
                        //打开文件
                        File logFile = new File(newLogPath + "log.txt");
                        //true表示在写的时候在文件末尾追加
                        FileOutputStream fos = new FileOutputStream(logFile, true);
                        //换行的字符串
                        String newline = System.getProperty("line.separator");
                        //Date date = new Date(System.currentTimeMillis());
                        //String time = format.format(date);

                        //Log.i(TAG, "thread");
                        //打印设备信息
                        fos.write(printDeviceInfo().getBytes());

                        while((str=buffRead.readLine())!=null){         //循环读取每一行
                            //Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));
                            //Log.i(TAG, str);
                            @SuppressLint("SimpleDateFormat")
                            Date date = new Date(System.currentTimeMillis());
                            String time = FORMAT.format(date);
                            //加上年
                            fos.write((time + str).getBytes());
                            //换行
                            fos.write(newline.getBytes());
                            logCount++;
                            //大于100000行就退出
                            if(logCount>100000){
                                captureLogThreadOpen = false;
                                fos.close();
                                break;
                            }
                        }
                        fos.close();
                        String[] strings = clearLog.toArray(new String[clearLog.size()]);
                        Runtime.getRuntime().exec(strings);
                        return true;
                    }catch(Exception e){
                        e.printStackTrace();
                        return false;
                    }
                }
                return true;
            }
        });


        try {
            submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    static String printDeviceInfo() {
        String versionName = "";
        int versionCode = 0;
        try {
            PackageInfo pi = BaseApplication.getInstance()
                    .getPackageManager()
                    .getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String head = "************* Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK 版本
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Log Head ****************\n\n";
        return head;
    }


}
