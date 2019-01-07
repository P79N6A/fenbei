package com.lingjuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lingjuan.app.R;
import com.lingjuan.app.adapter.YouPinAdapter;
import com.lingjuan.app.api.UserRequest;
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.base.ExampleApplication;
import com.lingjuan.app.broad.BaseUIListener;
import com.lingjuan.app.broad.TestEvent;
import com.lingjuan.app.uitls.Configure;
import com.lingjuan.app.uitls.HttpMethods;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by sks on 2017/8/17.
 * 登录窗口
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Button buttLogin;
    Button buttFenxiang;
    @Bind(R.id.register_referrer)
    EditText editTextReferrer;
    @Bind(R.id.register_mobile)
    EditText editTextMobile;
    @Bind(R.id.register_nick)
    EditText editTextNick;
    @Bind(R.id.register_register)
    Button buttonRegister;
    @Bind(R.id.image_imqq)
    ImageView imageImqq;
    @Bind(R.id.image_imwx)
    ImageView imageImwx;
    @Bind(R.id.image_imwb)
    ImageView imageImwb;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private static final String TAG = "MainActivity";
    private Tencent mTencent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        mTencent = ExampleApplication.mTencent;

        editTextReferrer = (EditText) findViewById(R.id.register_referrer);
        editTextMobile = (EditText) findViewById(R.id.register_mobile);
        editTextNick = (EditText) findViewById(R.id.register_nick);
        buttonRegister = (Button) findViewById(R.id.register_register);
        buttonRegister.setOnClickListener(this);

        imageImqq = (ImageView) findViewById(R.id.image_imqq);
        imageImwx = (ImageView) findViewById(R.id.image_imwb);
        imageImwb = (ImageView) findViewById(R.id.image_imwx);
        imageImqq.setOnClickListener(this);
        imageImwx.setOnClickListener(this);
        imageImwb.setOnClickListener(this);

        //设置为ActionBar
        Toolbar toolbar =(Toolbar) findViewById(R.id.buttlbar);
        setSupportActionBar(toolbar);
        //显示那个箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Configure.CALLBACK != null)
            Configure.CALLBACK.postExec();
    }

    @Override
    protected void init() {

    }


    public void buttonLogin(View v) {
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(LoginActivity.this, "all", mIUiListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register:
                register();
                break;
            case R.id.image_imqq://微信
                buttonLogin(v);
                break;
            case R.id.image_imwb://通讯录
            case R.id.image_imwx://发现
                Toast.makeText(this, "暂时只支持QQ登录,谢谢您的参与", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void register() {
        UserRequest userRequest = new UserRequest(editTextNick.getText().toString(), editTextMobile.getText().toString(), 0L);
        HttpMethods.getInstance().register(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e 是:"+ e.getMessage());
                Toast.makeText(LoginActivity.this, "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                System.out.println("s 是:"+ s);
                // 登录并记录状态
                Configure.USERID = "123";
                finish();
            }
        }, userRequest);
    }


    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            // Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(LoginActivity.this, qqToken);
                mUserInfo.getUserInfo(new BaseUIListener(LoginActivity.this, "get_simple_userinfo"));
                ExampleApplication.IsLogin = true;
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG, "登录成功" + response.toString());
                        finish();
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");
                        finish();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }


    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
