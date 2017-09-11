package com.p2p.dsad.sixplayler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.p2p.dsad.sixplayler.appliction.MyAppLiction;
import com.p2p.dsad.sixplayler.base.BaseActivity;
import com.p2p.dsad.sixplayler.bean.UserBean;
import com.p2p.dsad.sixplayler.utils.ConnectionUtils;
import com.p2p.dsad.sixplayler.utils.Contacts;
import com.p2p.dsad.sixplayler.utils.UniCoderUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.top_login_bar)
    Toolbar topLoginBar;
    @Bind(R.id.edit_login_user)
    EditText editLoginUser;
    @Bind(R.id.edit_login_password)
    EditText editLoginPassword;
    @Bind(R.id.btn_login_login)
    Button btnLoginLogin;
    private String url;
    private String TAG="Login";
    private UserBean user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        onlogin();
    }
    //登陆
    private void onlogin()
    {
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editLoginUser.getText().toString().trim();
                String password = editLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)&&TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                    editLoginPassword.setText(null);
                    editLoginUser.setText(null);
                    return;
                }
                url = Contacts.LOGIN_USER+"UserAccount="+username+"&UserPassWord="+password;
                ConnectionUtils.getData(url, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,response);
                        parseData(response);
                    }
                });
            }
        });
    }
    //处理数据
    private void parseData(String response)
    {
        if (response.equals("")||response==null)
        {
            Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
            editLoginUser.setText(null);
            editLoginPassword.setText(null);

        }
        else
        {
            String[] rs = response.split(",");
            String username = UniCoderUtils.decodeUnicode(rs[0]);
            String useraccont = rs[1];
            String userimg = rs[2];
            user = new UserBean();
            user.setUsername(username);
            user.setUseraccont(useraccont);
            user.setUserimg(userimg);
            /**
            SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("name","");
            ed.putString("userimg","");
            ed.commit();
             **/
            try
            {
                MyAppLiction.getUserdb().save(user);
            }
            catch (DbException e)
            {
                e.printStackTrace();
            }
            finish();
        }

    }
}
