package com.lingjuan.app.uitls;

import android.content.Context;
import android.content.Intent;
import com.lingjuan.app.activity.LoginActivity;
import mtopsdk.common.util.StringUtils;

import java.lang.ref.WeakReference;

public class LoginUtil {
    public static void checkLogin(Context context, final LoginForCallBack callBack) {
        // 弱引用，防止内存泄露，
        WeakReference<Context> reference = new WeakReference<>(context);
        String userid = Configure.USERID;
        if (StringUtils.isEmpty(userid)) { // 判断是否登录，否返回true
            Configure.CALLBACK = new ICallBack() {

                @Override
                public void postExec() {
                    // 登录回调后执行登录回调前需要做的操作
                    if (!StringUtils.isEmpty(Configure.USERID)) {
                        // 这里需要再次判断是否登录，防止用户取消登录，取消则不执行登录成功需要执行的回调操作
                        callBack.callBack();
                        //防止调用界面的回调方法中有传进上下文的引用导致内存泄漏
                        Configure.CALLBACK = null;
                    }
                }
            };
            Context mContext = reference.get();
            if (mContext != null) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                reference = null;
            }
        } else {
            // 登录状态直接执行登录回调前需要做的操作
            callBack.callBack();
        }
    }

    public void clear() {
        try {
            finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 声明一个登录成功回调的接口
    public interface ICallBack {
        // 在登录操作及信息获取完成后调用这个方法来执行登录回调需要做的操作
        public abstract void postExec();
    }

    @FunctionalInterface//Java8 函数注解，没有升级java8的去掉这一句
    public interface LoginForCallBack {
        void callBack();
    }
}
