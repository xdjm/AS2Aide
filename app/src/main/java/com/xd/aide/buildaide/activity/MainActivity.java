package com.xd.aide.buildaide.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.views.PgyerDialog;

import com.xd.aide.buildaide.bean.PackageName;
import com.xd.aide.buildaide.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CoordinatorLayout coordinatorLayout;
    private EditText edt_ProjectName;
    private EditText edt_Project;
    private TextView packageFullName;
    private Button clear;
    private AlertDialog dialog;
    private String[] NameList = {"mayapp", "maycompany", "com"};
    private boolean a = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initOnClickEvent();
    }

    //TODO 初始化
    private void init() {
        setContentView(R.layout.activity_main);
        File file = new File(Environment.getExternalStorageDirectory().getPath()
                + "/templet_AS2Aide");
        if (!file.exists()) copyTemplateFile2Local();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageResource(R.drawable.logo);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(getResources().getString(R.string.app_name));
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this,
                android.R.color.transparent));
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.mCoordinatorLayout);
        edt_ProjectName = (EditText) findViewById(R.id.edt_application_name);
        edt_Project = (EditText) findViewById(R.id.edt_project_name);
        packageFullName = (TextView) findViewById(R.id.package_FullName);
        packageFullName.setText(getString(R.string.package_name) +
                " myapp.mycompany.com.myapplication");
        findViewById(R.id.next).setOnClickListener(this);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
    }

    private void initOnClickEvent() {
        //TODO 设置工程名
        edt_ProjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                clear.setEnabled(true);
                packageFullName.setText(getString(R.string.package_name) + edt_Project.getText().toString() + "."
                        + edt_ProjectName.getText().toString().toLowerCase());
                if (edt_ProjectName.getText().toString().equals("")) {
                    edt_ProjectName.setError(getString(R.string.error_not_null));
                } else {
                    edt_ProjectName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
        //TODO 设置包名
        edt_Project.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                clear.setEnabled(true);
                packageFullName.setText(getString(R.string.package_name) + edt_Project.getText().toString() + "." + edt_ProjectName.getText().toString().toLowerCase());
                a = edt_Project.getText().toString().matches("^[a-z]{1,}+\\.[a-z]{1,}+\\.[a-z]{1,}$");
                if (!a) {
                    edt_Project.setError(getString(R.string.error_bad_format));
                } else {
                    NameList = edt_Project.getText().toString().split("\\.");
                    edt_Project.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
    }

    @Override
    public void onClick(View p1) {
        switch (p1.getId()) {
            case R.id.next: {
                if ((a) && !edt_ProjectName.getText().toString().equals("")) {
                    PackageName p = new PackageName(NameList[0], NameList[1], NameList[2],
                            edt_ProjectName.getText().toString());
                    Bundle packageData = new Bundle();
                    packageData.putSerializable("packagename", p);
                    Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                    intent.putExtras(packageData);
                    startActivity(intent);
                } else {
                    Snackbar.make(coordinatorLayout, R.string.error_info, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
            break;
            case R.id.clear: {
                edt_ProjectName.setText("");
                edt_Project.setText("");
                packageFullName.setText(getString(R.string.package_name));
                p1.setEnabled(false);
            }
            break;
            case R.id.fab:
                PgyUpdateManager.register(MainActivity.this,
                        new UpdateManagerListener() {
                            @Override
                            public void onUpdateAvailable(final String result) {
                                final AppBean appBean = getAppBeanFromString(result);
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(getString(R.string.update))
                                        .setMessage("")
                                        .setNegativeButton(
                                                getString(R.string.yes),
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which) {
                                                        startDownloadTask(
                                                                MainActivity.this,
                                                                appBean.getDownloadURL());
                                                    }
                                                }).show();
                            }

                            @Override
                            public void onNoUpdateAvailable() {
                                Snackbar.make(coordinatorLayout, R.string.version_info,
                                        Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        });
                break;
            default:
        }
    }

    //TODO 初始化->复制模板文件
    private void copyTemplateFile2Local() {
        configDialog(true);
        new Thread() {
            public void run() {
                try {
                    copyFilesFromAssets(MainActivity.this, "templet_AS2Aide",
                            Environment.getExternalStorageDirectory().getPath() + "/templet_AS2Aide");
                    configDialog(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    configDialog(false);
                }
            }
        }.start();
    }

    private void configDialog(boolean needShow) {
        if (needShow) {
            if (dialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.wait));
                builder.setMessage(getString(R.string.wait_info));
                builder.setCancelable(false);
                dialog = builder.create();
            }
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    //TODO 菜单栏设置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.menu_crash:   //TODO PgyCrash
                PgyerDialog.setDialogTitleBackgroundColor("#303F9F");
                PgyerDialog.setDialogTitleTextColor("#ffffff");
                PgyFeedback.getInstance().showDialog(MainActivity.this);
                break;
            case R.id.menu_esc: //TODO 退出
                finish();
                break;
            case R.id.menu_about://TODO 关于
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO 从assets文件夹中copy文件夹至本地文件夹中
    private void copyFilesFromAssets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);
            //获取assets目录下的所有文件及目
            if (fileNames.length > 0) {
                //如果是目录
                File file = new File(newPath);
                file.mkdirs();
                //如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                //如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {
                    //循环从输入流读取buffer字节
                    fos.write(buffer, 0, byteCount);
                    //将读取的输入流写入到输出流
                }
                fos.flush();
                //刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generatedcatch
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        PgyFeedbackShakeManager.setShakingThreshold(950);
        PgyFeedbackShakeManager.register(this, true);
    }

    //TODO 活动暂停时中止pgy
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        PgyFeedbackShakeManager.unregister();
    }
}

