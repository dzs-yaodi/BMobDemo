package com.xw.bmobdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * bmob 操作文档
 * http://doc.bmob.cn/data/android/develop_doc/
 */
public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btn;
    private List<UserData> userDataList = new ArrayList<>();
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter();
        recyclerView.setAdapter(mainAdapter);

        loadData();

        /**
         * 添加
         */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)){

                    UserData userData = new UserData(name,pwd);
                    BmobUtils.getInstance().saveOneObject(userData, new BmobResponce() {
                        @Override
                        public void onSucc(Object o) {
                            Toast.makeText(MainActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                            loadData();
                        }

                        @Override
                        public void onFail(String error) {
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        /**
         * 删除
         */
        findViewById(R.id.btn_delet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = username.getText().toString().trim();
                if (!TextUtils.isEmpty(id)){
                    UserData userData = new UserData();

                    BmobUtils.getInstance().deletOne(userData, id, new BmobResponce() {
                        @Override
                        public void onSucc(Object o) {
                            Toast.makeText(MainActivity.this,o.toString(), Toast.LENGTH_SHORT).show();
                            loadData();
                        }

                        @Override
                        public void onFail(String error) {
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        /**
         * 修改
         */
        findViewById(R.id.btn_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = username.getText().toString().trim();
                String userName = password.getText().toString().trim();
                UserData userData = new UserData();
                userData.setUserName(userName);

                BmobUtils.getInstance().updataOne(userData, id, new BmobResponce() {
                    @Override
                    public void onSucc(Object o) {
                        Toast.makeText(MainActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                        loadData();
                    }

                    @Override
                    public void onFail(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 查询
     */
    private void loadData() {
        BmobQuery<UserData> query = new BmobQuery<>();
        query.findObjects(new FindListener<UserData>() {
            @Override
            public void done(List<UserData> list, BmobException e) {
                userDataList.clear();
                userDataList.addAll(list);
                if (userDataList != null && userDataList.size() > 0){
                    mainAdapter.addList(userDataList);
                }
            }
        });
    }
}
