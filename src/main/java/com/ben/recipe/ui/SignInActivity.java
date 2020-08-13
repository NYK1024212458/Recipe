package com.ben.recipe.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
 * 登录
 */
public class SignInActivity extends Activity {

    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.tv_morinig_night)
    TextView tvMorinigNight;
    @BindView(R.id.et_password)
    TextInputLayout etPassword;
    @BindView(R.id.et_user_phone)
    TextInputLayout etUserPhone;
    @BindView(R.id.bt_sign_in)
    Button btSignIn;
    @BindView(R.id.bt_sign_up)
    Button btSignUp;


    private ProgressDialog dialog;
    public static final int SHOW_RESPONSE = 1;
    //记住密码
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean isremember;
    //自动登录
    private boolean auto_login;
    int count = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);
        checkUserPassword();
        checkBtSignIn();
        setBackground();

        start_shili();//实例化组件对象
        remember_password();//记住密码，将账号和密码设置到UI
        Auto_login();//自动登录的函数
    }

    /**
     * 用户名、密码输入框监听
     */
    private void checkUserPassword() {

        EditText editTextUserPhone = etUserPhone.getEditText();
        EditText editTextPassword = etPassword.getEditText();
        etUserPhone.setErrorEnabled(true);
        etPassword.setErrorEnabled(true);

        // 用户名监听
        editTextUserPhone.addTextChangedListener(new TextWatcher() {
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
                if (s.length() > 11) {
                    etUserPhone.setError("手机号过长");
                } else {
                    etUserPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                /**
                 * 文本变化之后
                 */
                if (s.length() == 0) {
                    etUserPhone.setError("用户名不能为空");
                } else {
                    etUserPhone.setError(null);
                }
            }
        });


        //  密码监听
        editTextPassword.addTextChangedListener(new TextWatcher() {
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
                if (s.length() == 0) {
                    etPassword.setError("密码不能为空");
                } else {
                    etPassword.setError(null);
                }
            }
        });
    }

    /**
     * 根据是否有输入数据，设置登录按钮是否可以点击
     */
    private void checkBtSignIn() {
        btSignIn.setEnabled(false);
        // 创建工具类对象 设置监听空间
        CheckEditForButton checkEditForButton = new CheckEditForButton(btSignIn);

        // 把所有被监听的EditText设置进去
        checkEditForButton.addEditText(etUserPhone.getEditText(), etPassword.getEditText());

        // 根据接口回调的方法,分别进行操作
        checkEditForButton.setListener(new EditTextChangeListener() {
            @Override
            public void allHasContent(boolean isHasContent) {
                if (isHasContent) {
                    btSignIn.setEnabled(true);
                } else {
                    btSignIn.setEnabled(false);
                }
            }
        });
    }

    /**
     * 登录验证
     */
    public void sendLoginByOkHttp(final String phone, final String pw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpTools tools = new OkHttpTools();
                    //传递参数获取响应内容
                    String result = tools.Login(phone, pw);

                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = result;
                    handler.sendMessage(message);//使用Message传递消息给线程

                    Log.d("result:", result);
                    Log.d("message:", "" + message);

                    /**
                     * 数据库返回true保存账号密码
                     */
                    if(result.equals("true")){
                        //将账号和密码存进user表
                        editor = pref.edit();
                        editor.putBoolean("auto_login",true);
                        editor.putBoolean("isremember",true);
                        editor.putString("userphone",phone);
                        editor.putString("password",pw);
                        editor.commit();
                    }

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
                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(SignInActivity.this);
                        Intent i2 = new Intent(SignInActivity.this, TabActivity.class);
                        startActivity(i2, oc2.toBundle());
                        finish();
                    } else {
                        Toast.makeText(SignInActivity.this, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
                    }
                    //dialog.dismiss();
                    break;
                default:
                    Toast.makeText(SignInActivity.this, "登录异常", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     *  获取实例
     */
    private void start_shili(){
        pref = getSharedPreferences("user",MODE_PRIVATE); //  信息表，存所记住密码的信息
        isremember =  pref.getBoolean("isremember",false); //判断是否记住密码
        auto_login = pref.getBoolean("auto_login",false); //判断是否自动登录
    }

    /**
     *  记住密码，将账号和密码设置到UI
     */
    private void remember_password(){
        if (isremember){
            //从文件中获取数据
            String account = pref.getString("userphone","");
            String password = pref.getString("password","");
            //将数据设置到UI上
            etUserPhone.getEditText().setText(account);
            etPassword.getEditText().setText(password);
        }
    }

    /**
     *  自动登录
     */
    private void Auto_login(){
        if (auto_login){
            //也可以直接写Intent跳转活动
            String id = etUserPhone.getEditText().getText().toString().trim();
            String pw = etPassword.getEditText().getText().toString().trim();
            dialog = new ProgressDialog(SignInActivity.this);
            dialog.setTitle("提示");
            dialog.setMessage("正在登陆，请稍后...");
            dialog.setCancelable(false);
            dialog.show();
            sendLoginByOkHttp(id, pw);
            dialog.dismiss();
        }
    }

    /**
     * 登录
     */
    private void login_activity(){
        String id = etUserPhone.getEditText().getText().toString().trim();
        String pw = etPassword.getEditText().getText().toString().trim();
        String md5Psw = MD5Utils.getPwd(pw);
        //控件的密码进行加密
        //String md5Psw = MD5Utils.md5(psw);
        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("正在登陆，请稍后...");
        dialog.setCancelable(false);
        dialog.show();
        sendLoginByOkHttp(id, md5Psw);
        dialog.dismiss();
    }

    @OnClick({R.id.bt_sign_in, R.id.bt_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //登录
            case R.id.bt_sign_in:
                login_activity();
                break;
            //注册
            case R.id.bt_sign_up:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 设置背景滑动转换图片
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setBackground() {

        ivBackground.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    ivBackground.setImageResource(R.mipmap.good_night_img);
                    tvMorinigNight.setText("Night");
                    count = 1;
                } else {
                    ivBackground.setImageResource(R.mipmap.good_morning_img);
                    tvMorinigNight.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    ivBackground.setImageResource(R.mipmap.good_night_img);
                    tvMorinigNight.setText("Night");
                    count = 1;
                } else {
                    ivBackground.setImageResource(R.mipmap.good_morning_img);
                    tvMorinigNight.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
    }

}



