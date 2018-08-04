package com.ycbjie.aptools;


import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import java.util.List;

import cn.ycbjie.ycaudioplayer.AppInfo;
import cn.ycbjie.ycaudioplayer.ICheckAppInfoManager;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/05/29
 *     desc  : 主页面
 *     revise:
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvBindService;
    private TextView mTvNetName;
    private TextView mTvGetInfo;
    private RecyclerView mRecyclerView;
    private Button mBtnSetApp;
    private TextView mTvScheme;
    private TextView mTvScheme2;
    private TextView mTvWeb;

    public static final String packName = "cn.ycbjie.ycaudioplayer";
    public static final String action = "cn.ycbjie.ycaudioplayer.service.aidl.AppInfoService";

    //由AIDL文件生成的Java类
    private ICheckAppInfoManager messageCenter = null;
    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;


    @Override
    protected void onDestroy() {
        if(connection!=null){
            unbindService(connection);
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        mTvBindService = (TextView) findViewById(R.id.tv_bind_service);
        mTvGetInfo = (TextView) findViewById(R.id.tv_get_info);
        mTvNetName = (TextView) findViewById(R.id.tv_net_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mBtnSetApp = (Button) findViewById(R.id.btn_set_app);
        mTvScheme = (TextView) findViewById(R.id.tv_scheme);
        mTvScheme2 = (TextView) findViewById(R.id.tv_scheme2);
        mTvWeb = (TextView) findViewById(R.id.tv_web);
    }

    private void initListener() {
        mTvBindService.setOnClickListener(this);
        mTvGetInfo.setOnClickListener(this);
        mBtnSetApp.setOnClickListener(this);
        mTvScheme.setOnClickListener(this);
        mTvScheme2.setOnClickListener(this);
        mTvWeb.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_bind_service:
                attemptToBindService();
                break;
            case R.id.tv_get_info:
                getAppInfo();
                break;
            case R.id.btn_set_app:
                startActivity(new Intent(this,SetAppActivity.class));
                break;
            case R.id.tv_scheme:
                PackageManager packageManager = getPackageManager();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("yc://ycbjie:8888/from?type=yangchong"));
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                boolean isValid = !activities.isEmpty();
                if (isValid) {
                    startActivity(intent);
                }
                break;
            case R.id.tv_scheme2:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("yc://app/?page=main"));
                startActivity(intent2);
                //startActivity(new Intent(this,SchemeActivity.class));
                break;
            case R.id.tv_web:
                startActivity(new Intent(this,WebActivity.class));
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        if(NetWorkUtils.checkWifiState(this)){
            WifiManager wifiManager = (WifiManager) getApplicationContext()
                    .getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo ;
            if (wifiManager != null) {
                wifiInfo = wifiManager.getConnectionInfo();
                mTvNetName.setText("wifi名称"+wifiInfo.getSSID()+"   /  "+"SSID"+wifiInfo.getBSSID());
            }
        }else if(NetWorkUtils.is4G(this)){
            mTvNetName.setText("4G网络");
        }else {
            mTvNetName.setText("其他情况");
        }
    }


    private void getAppInfo() {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (messageCenter == null) {
            return;
        }
        try {
            List<AppInfo> info = messageCenter.getAppInfo(Utils.getSign(packName));
            if(info==null || (info.size()==0)){
                Toast.makeText(this, "无法获取数据，可能是签名错误！", Toast.LENGTH_SHORT).show();
            }else {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                FirstAdapter adapter = new FirstAdapter(info, this);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new FirstAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跨进程绑定服务
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        //通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
        //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
        intent.setAction(action);
        //Android5.0后无法只通过隐式Intent绑定远程Service
        //需要通过setPackage()方法指定包名
        intent.setPackage(packName);
        //绑定服务,传入intent和ServiceConnection对象
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    /**
     * 创建ServiceConnection的匿名类
     */
    private ServiceConnection connection = new ServiceConnection() {
        //重写onServiceConnected()方法和onServiceDisconnected()方法
        // 在Activity与Service建立关联和解除关联的时候调用
        @Override public void onServiceDisconnected(ComponentName name) {
            Log.e(getLocalClassName(), "无法绑定aidlServer的AIDLService服务");
            ToastUtil.showToast(MainActivity.this,"无法绑定aidlServer的AIDLService服务");
            mBound = false;
        }

        //在Activity与Service建立关联时调用
        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            ToastUtil.showToast(MainActivity.this,"完成绑定aidlServer的AIDLService服务");
            Log.e(getLocalClassName(), "完成绑定aidlServer的AIDLService服务");
            //使用IAppInfoManager.Stub.asInterface()方法获取服务器端返回的IBinder对象
            //将IBinder对象传换成了mAIDL_Service接口对象
            messageCenter = ICheckAppInfoManager.Stub.asInterface(service);
            mBound = true;
            if (messageCenter != null) {
                try {
                    //链接成功
                    Toast.makeText(MainActivity.this,"链接成功",Toast.LENGTH_SHORT).show();
                    // 在创建ServiceConnection的匿名类中的onServiceConnected方法中
                    // 设置死亡代理
                    //messageCenter.asBinder().linkToDeath(deathRecipient, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    /**
     * 给binder设置死亡代理
     */
    /*private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            if(messageCenter == null){
                return;
            }
            messageCenter.asBinder().unlinkToDeath(deathRecipient, 0);
            messageCenter = null;
            //这里重新绑定服务
            attemptToBindService();
        }
    };*/


}
