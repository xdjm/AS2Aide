package aide.xd.com.buildaide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pgyersdk.feedback.PgyFeedbackShakeManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import aide.xd.com.buildaide.adapter.FragmentAdapter;
import aide.xd.com.buildaide.fragment.FragmentAbout;
import aide.xd.com.buildaide.fragment.FragmentHead;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks {
    private EditText edtProjectname;
    private EditText edtProject;
    private TextView packageFullName;
    private Button clear;
    private AlertDialog dialog;
    private String[] namelist = {"mayapp", "maycompany", "com"};
    private boolean a = true;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initOnClickEvent();
    }
    private void init() {
        setContentView(R.layout.activity_main);
        ViewPager pager = findViewById(R.id.viewpager);
        FragmentHead fragment1 = new FragmentHead();
        FragmentAbout fragment2 = new FragmentAbout();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        pager.setAdapter(fragmentAdapter);
        request_for_access();
        edtProjectname = findViewById(R.id.edt_application_name);
        edtProject = findViewById(R.id.edt_project_name);
        packageFullName = findViewById(R.id.package_FullName);
        packageFullName.setText(getString(R.string.package_name) + "com.mycompany.myapp.myapplication");
        findViewById(R.id.next).setOnClickListener(this);
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(this);
    }

    private void initOnClickEvent() {
        //TODO 设置工程名
        edtProjectname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                clear.setEnabled(true);
                packageFullName.setText(getString(R.string.package_name) + edtProject.getText().toString() + "."
                        + edtProjectname.getText().toString().toLowerCase());
                if ("".equals(edtProjectname.getText().toString())) {
                    edtProjectname.setError(getString(R.string.error_not_null));
                } else {
                    edtProjectname.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
        //TODO 设置包名
        edtProject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                clear.setEnabled(true);
                packageFullName.setText(getString(R.string.package_name) + edtProject.getText().toString() + "." + edtProjectname.getText().toString().toLowerCase());
                a = edtProject.getText().toString().matches("^[a-z]{1,}+\\.[a-z]{1,}+\\.[a-z]{1,}$");
                if (!a) {
                    edtProject.setError(getString(R.string.error_bad_format));
                } else {
                    namelist = edtProject.getText().toString().split("\\.");
                    edtProject.setError(null);
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
                if ((a) && !"".equals(edtProjectname.getText().toString())) {
                    PackageName p = new PackageName(namelist[0], namelist[1], namelist[2],
                            edtProjectname.getText().toString());
                    Bundle packageData = new Bundle();
                    packageData.putSerializable("packagename", p);
                    Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                    intent.putExtras(packageData);
                    startActivity(intent);
                } else {
                    Snackbar.make(edtProject, R.string.error_info, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
            break;
            case R.id.clear: {
                edtProjectname.setText("");
                edtProject.setText("");
                packageFullName.setText(getString(R.string.package_name));
                p1.setEnabled(false);
            }
            break;
            default:
        }
    }

    private void copyTemplateFile2Local() {
        configDialog(true);
        new Thread() {
            @Override
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

    private void copyFilesFromAssets(Context context, String oldPath, String newPath) {
        try {
            String[] fileNames = context.getAssets().list(oldPath);
            //获取assets目录下的所有文件及目
            assert fileNames != null;
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
                int byteCount;
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
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
        }
    }

    @AfterPermissionGranted(0)
    public void request_for_access() {
        if (hasWRITE_EXTERNAL_STORAGEPermission()) {
                if(!new File(Environment.getExternalStorageDirectory().getPath() + "/templet_AS2Aide").exists())
                copyTemplateFile2Local();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask),
                    0,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
    private boolean hasWRITE_EXTERNAL_STORAGEPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        PgyFeedbackShakeManager.unregister();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}

