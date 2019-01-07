package com.lingjuan.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import com.lingjuan.app.R;
import com.lingjuan.app.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    private EditText userEditText;
    private EditText verifyCodeText;

    private Fragment verifyCodeFragment;
    private Fragment checkCodeFragment;

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        if (savedInstanceState == null) {
//            verifyCodeFragment = new VerifyCodeFragment();
//            getFragmentManager().beginTransaction()
//                .add(R.id.activity_register, verifyCodeFragment).commit();
//        }
    }
}
