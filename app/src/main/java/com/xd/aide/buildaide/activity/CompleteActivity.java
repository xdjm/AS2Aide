package com.xd.aide.buildaide.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.xd.aide.buildaide.bean.PackageName;
import com.xd.aide.buildaide.R;
import com.xd.aide.buildaide.base.Tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Created by yjm on 2017/1/14.
 */
public class CompleteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tv_path;
    private EditText edt_ActivityName;
    private EditText edt_LayoutName;
    private CoordinatorLayout c;
    private Button btn;
    private Button btn_path;
    private AlertDialog dialog;
    private android.support.v7.app.AlertDialog.Builder build;
    private PackageName p;
    private int a;
    private String sdCardHome;
    private String path;
    private boolean aa = true;
    private Map<String, Object> map = new HashMap<>();
    private File file;
    private int[] imageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initOnClickEvent();
    }

    private void init() {
        setContentView(R.layout.complete_main);
        c = (CoordinatorLayout) findViewById(R.id.main_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.step_3);
        Resources res = getResources();
        String[] description = res.getStringArray(R.array.descriptions);
        edt_LayoutName = (EditText) findViewById(R.id.layout_name);
        edt_ActivityName = (EditText) findViewById(R.id.activity_name);
        //获取传递的参数
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        a = data.getInt("image");
        p = (PackageName) data.getSerializable("packagename");
        path = Environment.getExternalStorageDirectory().getPath();
        String sdCard = Environment.getExternalStorageDirectory().getPath() + "/AppProjects/";
        makeRootDirectory(sdCard);
        TextView tv_description = (TextView) findViewById(R.id.TextView1);
        tv_description.setText(description[a]);
        tv_path = (TextView) findViewById(R.id.completeTextView);
        tv_path.setText(Tools.getParam(this, "String", Environment.getExternalStorageDirectory().getPath() + "/AppProjects/").toString());
        sdCardHome = Tools.getParam(this, "String", Environment.getExternalStorageDirectory().getPath() + "/AppProjects/").toString();
        ImageView image = (ImageView) findViewById(R.id.completeImageView);
        image.setImageResource(imageIds[a]);
        build = new android.support.v7.app.AlertDialog.Builder(this);
        btn = (Button) findViewById(R.id.completeButton);
        btn_path = (Button) findViewById(R.id.filepathButton);
        imageIds = new int[]{
                R.drawable.blank_activity,
                R.drawable.basic_activity,
                R.drawable.blank_activity_drawer,
                R.drawable.blank_activity_tabs,
                R.drawable.fullscreen_activity,
                R.drawable.login_activity,
                R.drawable.scroll_activity,
                R.drawable.settings_activity
        };
    }

    private void initOnClickEvent() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                file = new File(sdCardHome + p.getDName());
                String layoutname = edt_LayoutName.getText().toString();
                String activityname = edt_ActivityName.getText().toString();
                map.put("packagefullname", p.getFullName());
                map.put("activityname", activityname);
                map.put("layoutname", layoutname);
                map.put("app_name", p.getDName());
                if (!layoutname.equals("") && aa && !file.exists()) {
                    switch (a) {
                        default:
                        case 0://TODO EmptyActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/EmptyActivity/app", sdCardHome + p.getDName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("EmptyActivity", "build.gradle", sdCardHome + p.getDName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/res/layout");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("EmptyActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_java("EmptyActivity", "MainActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("EmptyActivity", "activity_main", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                            freemarker_make_xml("EmptyActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                        case 1://TODO BasicActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/BasicActivity/app", sdCardHome + p.getDName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("BasicActivity", "build.gradle", sdCardHome + p.getDName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/res/layout");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("BasicActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_java("BasicActivity", "MainActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("BasicActivity", "activity_main", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                            freemarker_make_xml("BasicActivity", "content_main", sdCardHome + p.getDName() + "/app/src/main/res/layout/content_main", map);
                            freemarker_make_xml("BasicActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                        case 2://TODO NavigationDrawerActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/NavigationDrawerActivity/app", sdCardHome + p.getName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("NavigationDrawerActivity", "build.gradle", sdCardHome + p.getName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("NavigationDrawerActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_java("NavigationDrawerActivity", "MainActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("NavigationDrawerActivity", "activity_main", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                            freemarker_make_xml("NavigationDrawerActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                        case 3://TODO TabbedActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/TabbedActivity/app", sdCardHome + p.getDName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("TabbedActivity", "build.gradle", sdCardHome + p.getDName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("TabbedActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_java("TabbedActivity", "MainActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("TabbedActivity", "activity_main", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                            freemarker_make_xml("TabbedActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                        case 4://TODO FullscreenActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/FullscreenActivity/app", sdCardHome + p.getDName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("FullscreenActivity", "build.gradle", sdCardHome + p.getDName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/res/layout");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("FullscreenActivity", "AndroidManifest", sdCardHome + p.getName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_java("FullscreenActivity", "FullscreenActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("FullscreenActivity", "activity_fullscreen", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                            freemarker_make_xml("FullscreenActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                        case 5://TODO LoginActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/LoginActivity/app", sdCardHome + p.getDName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("LoginActivity", "build.gradle", sdCardHome + p.getDName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/res/layout");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("LoginActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_java("LoginActivity", "LoginActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("LoginActivity", "activity_login", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                            freemarker_make_xml("LoginActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                        case 6://TODO ScrollActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/ScrollActivity/app", sdCardHome + p.getDName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("ScrollActivity", "build.gradle", sdCardHome + p.getDName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/res/layout");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("ScrollActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_java("ScrollActivity", "ScrollingActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("ScrollActivity", "activity_scrolling", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                            freemarker_make_xml("ScrollActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                        case 7://TODO SettingsActivity
                        {
                            configDialog(true);
                            makeRootDirectory(sdCardHome);
                            makeRootDirectory(sdCardHome + p.getDName());
                            copyFolder(path + "/templet_AS2Aide/SettingsActivity/app", sdCardHome + p.getDName() + "/app");
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                            writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                            freemarker_make_gradle("SettingsActivity", "build.gradle", sdCardHome + p.getDName() + "/app/", map);
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                            makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                            freemarker_make_xml("SettingsActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                            freemarker_make_xml("SettingsActivity", "pref_headers", sdCardHome + p.getDName() + "/app/src/main/res/xml/pref_headers", map);
                            freemarker_make_java("SettingsActivity", "AppCompatPreferenceActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/AppCompatPreferenceActivity", map);
                            freemarker_make_java("SettingsActivity", "SettingsActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                            freemarker_make_xml("SettingsActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                            configDialog(false);
                            break;
                        }
                    }
                    build.setIcon(R.mipmap.ic_launcher);
                    build.setTitle(p.getDName() + " Project has been generated!");
                    build.setMessage("Open the Aide or continue your show");
                    build.setPositiveButton("Open"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /**打开Aide主程序的代码【1.2版开始抛弃该方法】
                                     * Intent intent = new Intent();
                                     * intent.setComponent(new ComponentName("com.aide.ui","com.aide.ui.MainActivity"));
                                     * intent.setAction(Intent.ACTION_VIEW);
                                     * startActivity(intent);
                                     **/
                                    try {
                                        openJavaFile("file://" + sdCardHome + p.getName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + edt_ActivityName.getText().toString() + ".java");
                                    } catch (Exception e) {
                                        Snackbar.make(c, R.string.error_null_aide, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }
                            });
                    build.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    build.create().show();
                } else {
                    if (file.exists()) {
                        Snackbar.make(c, p.getDName() + getString(R.string.error_exist), Snackbar.LENGTH_LONG).show();
                    }
                    if (edt_ActivityName.getText().toString().equals("")) {
                        Animation anim = AnimationUtils.loadAnimation(CompleteActivity.this, R.anim.myanim);
                        edt_ActivityName.startAnimation(anim);
                    }
                    if (edt_LayoutName.getText().toString().equals("")) {
                        Animation anim = AnimationUtils.loadAnimation(CompleteActivity.this, R.anim.myanim);
                        edt_LayoutName.startAnimation(anim);
                    }
                }
            }
        });
        btn_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                Intent filePickerActivity = new Intent(CompleteActivity.this, FilePickerActivity.class);
                filePickerActivity.putExtra(FilePickerActivity.SCOPE, Scope.ALL);
                filePickerActivity.putExtra(FilePickerActivity.REQUEST, Request.DIRECTORY);
                filePickerActivity.putExtra(FilePickerActivity.INTENT_EXTRA_FAB_COLOR_ID, android.R.color.holo_green_dark);
                startActivityForResult(filePickerActivity, 11);
            }
        });
        //TODO 监听格式
        edt_ActivityName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                aa = edt_ActivityName.getText().toString().matches("^[A-Z]{1}+[A-z]{1,}$") || edt_ActivityName.getText().toString().matches("^[A-Z]{1}$");
                if (!aa)
                    edt_ActivityName.setError("The first letter must be capitalized");
                else {
                    edt_ActivityName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
        edt_LayoutName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                if (edt_LayoutName.getText().toString().equals(""))
                    edt_LayoutName.setError("Not Null");
                else {
                    edt_LayoutName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                finish();
            }
        });
    }

    //TODO 复制文件夹
    private void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp;
            for (String aFile : file) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + aFile);
                } else {
                    temp = new File(oldPath + File.separator + aFile);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()));
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + aFile, newPath + "/" + aFile);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    //TODO 创建文件夹
    private void makeRootDirectory(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
            //Toast.makeText(getApplicationContext(), "已存在", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.i("error:", e + "");
        }

    }

    //TODO 写文件
    private void writeFileSdcardFile(String fileName, String write_str) {
        try {
            FileOutputStream foul = new FileOutputStream(fileName);
            byte[] bytes = write_str.getBytes();
            foul.write(bytes);
            foul.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO 提示
    private void configDialog(boolean needShow) {
        if (needShow) {
            if (dialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请稍候");
                builder.setMessage("工程正在创建...");
                builder.setCancelable(false);
                dialog = builder.create();
            }
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 11) && (resultCode == RESULT_OK)) {
            tv_path.setText(data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH) + "/");
            sdCardHome = tv_path.getText().toString();
            Tools.setParam(this, "String", sdCardHome);
        } else if ((requestCode == 10) && (resultCode == RESULT_OK)) {
            Toast.makeText(this, "File Selected: " + data
                            .getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void freemarker_make_java(String templateName_FromPath, String templateName, String templateName_OutPath, Map<String, Object> map) {
        try {

            File dir = new File(path + "/templet_AS2Aide/" + templateName_FromPath + "/app_/");
            File outFile = new File(templateName_OutPath + ".java");
            //TODO 修改为模板所在文件件的路径
            //						StringWriter writer = new StringWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(dir);
            Template t = configuration.getTemplate(templateName + ".ftl");
            t.process(map, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarker_make_xml(String templateName_FromPath, String templateName, String templateName_OutPath, Map<String, Object> map) {
        try {

            File dir = new File(path + "/templet_AS2Aide/" + templateName_FromPath + "/app_/");
            File outFile = new File(templateName_OutPath + ".xml");
            //TODO 修改为模板所在文件件的路径
            //						StringWriter writer = new StringWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(dir);
            Template t = configuration.getTemplate(templateName + ".ftl");
            t.process(map, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarker_make_gradle(String templateName_FromPath, String templateName, String templateName_OutPath, Map<String, Object> map) {
        try {

            File dir = new File(path + "/templet_AS2Aide/" + templateName_FromPath + "/app_/");
            File outFile = new File(templateName_OutPath + templateName);
            //TODO 修改为模板所在文件件的路径
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(dir);
            Template t = configuration.getTemplate(templateName + ".ftl");
            t.process(map, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO 跳转至AIDE
    private void openJavaFile(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setDataAndType(Uri.parse(filePath), "*/.java");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        PgyFeedbackShakeManager.setShakingThreshold(950);
        PgyFeedbackShakeManager.register(this, true);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        PgyFeedbackShakeManager.unregister();
    }
}