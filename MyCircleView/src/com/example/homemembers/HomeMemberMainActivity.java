package com.femote.homeui.ui.homemembers;

import com.femote.homeui.R;
import com.femote.homeui.ui.CallBookActivity;
import com.femote.homeui.ui.MyApp;
import com.femote.homeui.ui.OriginActivity;
import com.femote.homeui.ui.VideoCallActivity;
import com.femote.homeui.utils.L;
import com.femote.homeui.utils.T;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMServiceNotReadyException;

import java.util.ArrayList;
import java.util.List;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by xiangxiang on 2016/5/26.
 * name: mr.k from in china
 */
public class HomeMemberMainActivity extends OriginActivity {
    public static final String TAG = "HomeMemberMainActivity";
    private boolean isShowOfDialog;
    private static final int REQUEST_ADD_CONTACTOR_SUCCESS = 1 << 1;
    private static final int REQUEST_ADD_CONTACTOR_FAILED = 1 << 2;
    List<String> list;
    private MyCircleView mCircleView;
    MyAdapter<String> m;
    private EditText et;
    private Handler hr = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String userData = (String) msg.obj;


            switch (msg.what) {
                case REQUEST_ADD_CONTACTOR_SUCCESS:
                    addContactorInfo(userData);
                    updateDataList();

                    break;
                case REQUEST_ADD_CONTACTOR_FAILED:
                    break;

            }
        }
    };
    private AlertDialog alertDialog;

    /**
     * 更新数据源
     */
    private void updateDataList() {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.home_member_activity);

        L.i(TAG, "onCreate()");

        mCircleView = (MyCircleView) findViewById(R.id.myCircleView);
        // 在xml文件中就可以声明，不过需要在attr定义 和使用
        mCircleView.setRoundCircleCount(8);
        list = new ArrayList<String>();// 数据源

        for (int i = 0; i < 3; i++) {
            list.add("xiao"+i);
        }

        /**
         * 添加事件监听
         */
        m = new MyAdapter<String>(this, list);
        // 添加 添加控件点击监听
        mCircleView.setOnAddViewClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (list.size() >= mCircleView.getDefRoundCircleCount()) {
                    Toast.makeText(HomeMemberMainActivity.this, "mainActivity list 结合不能超过" + mCircleView.getDefRoundCircleCount(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //弹出一个添加对话框,请求添加到网络服务器
                if(alertDialog!=null){
                    if(!alertDialog.isShowing()){
                        addDialogShow();
                    }

                }else{
                    addDialogShow();
                }


            }
        });
        /**
         * item点击事件的监听
         * 1.点击每一项代表的每一个联系人，进行视频通话功能
         * 2.只需要获取联系人username就可以完成视频间通话的呼叫
         */
        m.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(MyCircleView view, View itemVIew, int position, int id) {
                // TODO Auto-generated method stub
                Toast.makeText(HomeMemberMainActivity.this, list.get(position) + " ", Toast.LENGTH_SHORT).show();

                /**
                 * 双向视频呼叫
                  */
                tanster2Activity("视频呼叫提示", "按确定将发起呼叫，否则请取消", new BothVideoWatcherMode(),list.get(position));


               // tanster2Activity("监听呼叫提示", "按确定将发起监听呼叫，否则请取消", new WatcherVideoWatcherMode());



            }

            @Override
            public boolean onItemLongClick(MyCircleView view, View itemVIew, final int position, int id) {
                // TODO Auto-generated method stub
                AlertDialog.Builder ab = new Builder(HomeMemberMainActivity.this);
                ab.setTitle("是否删除此成员");
                ab.setMessage("点击确定将会删除此成员，否则取消");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m.removeViewAtPostion(position);
                        m.notifyDataSetChanged();


                    }

                });

                ab.create().show();
                return true;
            }
        });

        mCircleView.setAdapter(m);

    }

    /**
     * 添加对话框显示
     */
    private void addDialogShow() {

        AlertDialog.Builder add = new AlertDialog.Builder(this);
        add.setTitle("添加联系人：");
        add.setCancelable(false);
        add.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            String username = null;

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!TextUtils.isEmpty(username = et.getText().toString())) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //联网请求服务器中的用户名添加申请，并返回username的个人信息（头像，电话，手机）
                            requestServerToAddContact(username);

                        }
                    }).start();

                }


            }
        });
        add.setNegativeButton("取消", null);
        et = new EditText(this);
        et.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        et.setFocusable(true);
        et.hasFocus();
        add.setView(et);
         alertDialog = add.create();
        alertDialog.show();
        //isShowOfDialog = true;


    }

    /**
     * 请求服务添加指定名字的联系人 如果
     *
     * @param username
     */
    private void requestServerToAddContact(String username) {


                netRequest("url" + username);
                //联网请求完成之后。success/failed
                Message msg = hr.obtainMessage();
                msg.what = REQUEST_ADD_CONTACTOR_SUCCESS;
                msg.obj = username;
                hr.sendMessage(msg);



    }

    private void netRequest(String s) {

    }

    /**
     * 添加联系人信息
     *
     * @param data
     */
    private void addContactorInfo(String data) {



        list.add(data);// 数据的集合 可以是beans 网上下载后的json处理的beans
        L.i(TAG, data + "is added~~~");
        m.notifyDataSetChanged();
    }


    /**
     * 视频呼叫跳转方法
     * call this method ,will pop up a dialog  to show a videoActivity that you want to sure beginning
     * to .
     * @param title   the title of dialog popping up
     * @param message the message of guild
     * @param watcherModeListener   呼叫模式类
     * @param toCallUsername        呼叫对象用户名
     */
    private void tanster2Activity(String title, String message, final WatcherModeListener watcherModeListener, final String toCallUsername) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeMemberMainActivity.this);
        dialog.setTitle("视频呼叫提示");
        dialog.setMessage("按确定将发起呼叫，否则请取消");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                T.showShort(HomeMemberMainActivity.this, "正在呼叫Agnes......");

                watcherModeListener.beginWatcher(toCallUsername);

              /*  *//**
                 * 拨打视频通话
                 * @param to
                 * @throws EMServiceNotReadyException
                 *//*
                try {
                    EMClient.getInstance().callManager().makeVideoCall(toUsername);
                } catch (EMServiceNotReadyException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
*/
            }

        }).create();//创建确认按钮
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        }).create(); // 创建取消按钮
        dialog.show();
    }






    /**
     * 拨打视频电话
     */
    protected void startVideoCall(String toUsername) {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(HomeMemberMainActivity.this, R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(HomeMemberMainActivity.this, VideoCallActivity.class).putExtra("username", toUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
        }
    }






    /**
     * 视屏模式接口
     */
    public interface WatcherModeListener {

        /**
         * 启动界面
         *
         * @param toUsername
         */
        public void beginWatcher(String toUsername);

    }

    /**
     * 双端视屏聊天模式
     */
    public class BothVideoWatcherMode implements WatcherModeListener {

        @Override
        public void beginWatcher(String toUsername) {
            startVideoCall(toUsername);
        }
    }

    /**
     * 监控视屏模式
     */
    public class WatcherVideoWatcherMode implements WatcherModeListener {

        @Override
        public void beginWatcher(String toUsername) {
            this.startVideoCall(toUsername);
        }


        /**
         * 监听视屏电话
         *
         * @param toUsername
         */
        public void startVideoCall(String toUsername) {


            Intent i = new Intent(HomeMemberMainActivity.this, VideoCallActivity.class);
            startActivity(i);

            Toast.makeText(HomeMemberMainActivity.this, "监听模式启动···", Toast.LENGTH_SHORT).show();



          /*  if (!EMClient.getInstance().isConnected())
                Toast.makeText(CallBookActivity.this, R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
            else {
                startActivity(new Intent(CallBookActivity.this, VideoCallActivity.class).putExtra("username", toUsername)
                        .putExtra("isComingCall", false));
                // videoCallBtn.setEnabled(false);*/
        }

    }




}
