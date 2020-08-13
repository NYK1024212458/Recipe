package com.ben.recipe.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import com.ben.recipe.R;
import com.ben.recipe.biz.OkHttpTools;
import com.ben.recipe.config.EditTextChangeListener;
import com.ben.recipe.utils.CheckEditForButton;
import com.ben.recipe.utils.MD5Utils;
import com.ben.recipe.utils.OnSwipeTouchListener;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 */
public class SignUpActivity extends Activity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.et_user_name)
    TextInputLayout etUserName;
    @BindView(R.id.et_user_phone)
    TextInputLayout etUserPhone;
    @BindView(R.id.et_user_password)
    TextInputLayout etUserPassword;
    @BindView(R.id.et_user_password2)
    TextInputLayout etUserPassword2;
    @BindView(R.id.bt_sign_up)
    Button btSignUp;
    @BindView(R.id.bt_sign_in)
    Button btSignIn;

    private ProgressDialog dialog;
    public static final int SHOW_RESPONSE = 1;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        checkUserPassword();
        checkBtSignUp();
        setBackground();
    }

    /**
     * 输入框监听
     */
    private void checkUserPassword() {

        etUserName.setErrorEnabled(true);
        etUserPhone.setErrorEnabled(true);
        etUserPassword.setErrorEnabled(true);
        etUserPassword2.setErrorEnabled(true);

        etUserName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    etUserName.setError("姓名不能为空");
                } else {
                    etUserName.setError(null);
                }
            }
        });


        // 手机号监听
        etUserPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /**
                 * 文本改变前
                 */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /**
                 * 文本改变
                 */
            }

            @Override
            public void afterTextChanged(Editable s) {
                /**
                 * 文本变化之后
                 */
                if (s.length() != 11) {
                    etUserPhone.setError("手机应为11位");
                } else {
                    etUserPhone.setError(null);
                }
            }
        });

        //  密码监听
        etUserPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    etUserPassword.setError("密码不能为空");
                } else {
                    etUserPassword.setError(null);
                }
            }
        });

        //  确认密码监听
        etUserPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != etUserPassword.getEditText()) {
                    etUserPassword.setError("两次密码不同");
                } else {
                    etUserPassword.setError(null);
                }
            }
        });
    }

    /**
     * 根据是否有输入数据，设置注册按钮是否可以点击
     */
    private void checkBtSignUp() {
        btSignUp.setEnabled(false);
        // 创建工具类对象 设置监听空间
        CheckEditForButton checkEditForButton = new CheckEditForButton(btSignUp);

        // 把所有被监听的EditText设置进去
        checkEditForButton.addEditText(etUserName.getEditText(), etUserPhone.getEditText(), etUserPassword.getEditText(), etUserPassword2.getEditText());

        // 根据接口回调的方法,分别进行操作
        checkEditForButton.setListener(new EditTextChangeListener() {
            @Override
            public void allHasContent(boolean isHasContent) {
                if (isHasContent) {
                    btSignUp.setEnabled(true);
                } else {
                    btSignUp.setEnabled(false);
                }
            }
        });
    }

    /**
     * 注册数据
     */
    public void sendRegisterByOkHttp(final String phone, final String pw, final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpTools tools = new OkHttpTools();
                    //传递参数获取响应内容
                    String result = tools.Register(phone, pw, name);

                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = result;
                    handler.sendMessage(message);//使用Message传递消息给线程

                    Log.d("result:", result);
                    Log.d("message:", "" + message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String result = (String) msg.obj;
                    //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    if (result.equals("true")) {
                        Explode explode = new Explode();
                        explode.setDuration(500);
                        getWindow().setExitTransition(explode);
                        getWindow().setEnterTransition(explode);
                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(SignUpActivity.this);
                        Toast.makeText(SignUpActivity.this, "注册成功，请登录...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent, oc2.toBundle());
                        finish();
                    } else if (result.equals("existed")) {
                        Toast.makeText(SignUpActivity.this, "该手机号已注册", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "数据库异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    Toast.makeText(SignUpActivity.this, "注册异常", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @OnClick({R.id.bt_sign_up,R.id.bt_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sign_up:
                dialog = new ProgressDialog(SignUpActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("正在注册，请稍后...");
                dialog.setCancelable(false);
                dialog.show();

                String id = etUserPhone.getEditText().getText().toString().trim();
                String pw = etUserPassword.getEditText().getText().toString().trim();
                String md5Psw = MD5Utils.getPwd(pw);
                String name = etUserName.getEditText().getText().toString().trim();
                sendRegisterByOkHttp(id, md5Psw, name);
                dialog.dismiss();
                break;

            case R.id.bt_sign_in:
                Intent intentback = new Intent(this,SignInActivity.class);
                startActivity(intentback);
        }
    }

    /**
     * 设置背景滑动转换图片
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setBackground() {

        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.mipmap.good_night_img);
                    count = 1;
                } else {
                    imageView.setImageResource(R.mipmap.good_morning_img);
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.mipmap.good_night_img);
                    count = 1;
                } else {
                    imageView.setImageResource(R.mipmap.good_morning_img);
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
    }
}
