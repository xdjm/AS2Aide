package aide.xd.com.buildaide.fragment;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jkb.fragment.rigger.annotation.Animator;
import com.jkb.fragment.rigger.annotation.Puppet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import aide.xd.com.buildaide.PackageName;
import aide.xd.com.buildaide.R;
import base.Tools;
import freemarker.template.Configuration;
import freemarker.template.Template;

import static android.app.Activity.RESULT_OK;
@Animator(enter = R.anim.push_left_in_no_alpha, exit = R.anim.push_right_out_no_alpha,
        popEnter = R.anim.push_right_in_no_alpha, popExit = R.anim.push_left_out_no_alpha)
@Puppet
public class FragmentThree extends Fragment {
    private EditText activityName;
    private EditText layoutName;
    private Button btn;
    private Button btnPath;
    private AlertDialog dialog;
    private android.support.v7.app.AlertDialog.Builder build;
    private PackageName p;
    private int a;
    private String sdCardHome;
    private String path;
    private boolean aa = true;
    private Map<String, Object> map = new HashMap<>();
    private int[] imageIds = new int[]{
            R.drawable.blank_activity,
            R.drawable.basic_activity,
            R.drawable.blank_activity_drawer,
            R.drawable.blank_activity_tabs,
            R.drawable.fullscreen_activity,
            R.drawable.login_activity,
            R.drawable.scroll_activity,
            R.drawable.settings_activity
    };
    public static FragmentThree newInstance(Bundle bundle) {
        FragmentThree fragment = new FragmentThree();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,container,false);
        init(view);
        initOnClickEvent();
        return view;
    }
    private void init(View v) {
        Resources res = getResources();
        String[] description = res.getStringArray(R.array.descriptions);
        layoutName = v.findViewById(R.id.layout_name);
        activityName = v.findViewById(R.id.activity_name);
        Bundle data = getArguments();
        assert data != null;
        a = data.getInt("image");
        p = (PackageName) data.getSerializable("packagename");
        path = Environment.getExternalStorageDirectory().getPath();
        String sdCard = Environment.getExternalStorageDirectory().getPath() + "/AppProjects/";
        makeRootDirectory(sdCard);
        TextView tvDescription = v.findViewById(R.id.TextView1);
        tvDescription.setText(description[a]);
        sdCardHome = Objects.requireNonNull(Tools.getParam(Objects.requireNonNull(getContext()), "String", Environment.getExternalStorageDirectory().getPath() + "/AppProjects/")).toString();
        ImageView image = v.findViewById(R.id.completeImageView);
        image.setImageResource(imageIds[a]);
        build = new android.support.v7.app.AlertDialog.Builder(getContext());
        btn = v.findViewById(R.id.completeButton);
        btnPath = v.findViewById(R.id.filepathButton);
    }

    private void initOnClickEvent() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    call();
                }

            }
        });
        btnPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent filePickerActivity = new Intent(getActivity(), FilePickerActivity.class);
                    filePickerActivity.putExtra(FilePickerActivity.SCOPE, Scope.ALL);
                    filePickerActivity.putExtra(FilePickerActivity.REQUEST, Request.DIRECTORY);
                    filePickerActivity.putExtra(FilePickerActivity.INTENT_EXTRA_FAB_COLOR_ID, android.R.color.holo_green_dark);
                    startActivityForResult(filePickerActivity, 11);
                }
            }
        });
        activityName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                aa = activityName.getText().toString().matches("^[A-Z][A-z]+$") || activityName.getText().toString().matches("^[A-Z]$");
                if (!aa) {
                    activityName.setError("首字母必须是大写");
                } else {
                    activityName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
        layoutName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                if (layoutName.getText().toString().equals("")) {
                    layoutName.setError("不能为空");
                } else {
                    layoutName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
    }

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
        } catch (Exception ignored) {
        }

    }

    private void makeRootDirectory(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception ignored) {
        }

    }

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

    private void configDialog(boolean needShow) {
        if (needShow) {
            if (dialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
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
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 11) && (resultCode == RESULT_OK)) {
            sdCardHome = data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH) + "/";
            Tools.setParam(Objects.requireNonNull(getContext()), "String", sdCardHome);
        } else if ((requestCode == 10) && (resultCode == RESULT_OK)) {
            Toast.makeText(getContext(), "File Selected: " + data
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

    private void freemarker_make_gradle(String templateName_FromPath, String templateName_OutPath, Map<String, Object> map) {
        try {

            File dir = new File(path + "/templet_AS2Aide/" + templateName_FromPath + "/app_/");
            File outFile = new File(templateName_OutPath + "build.gradle");
            //TODO 修改为模板所在文件件的路径
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(dir);
            Template t = configuration.getTemplate("build.gradle" + ".ftl");
            t.process(map, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void call() {
        File file = new File(sdCardHome + p.getDName());
        String layoutname = layoutName.getText().toString();
        String activityname = activityName.getText().toString();
        map.put("packagefullname", p.getFullName());
        map.put("activityname", activityname);
        map.put("layoutname", layoutname);
        map.put("app_name", p.getDName());
        if (!"".equals(layoutname) && aa && !file.exists()) {
            switch (a) {
                default:
                    //EmptyActivity
                case 0: {
                    configDialog(true);
                    makeRootDirectory(sdCardHome);
                    makeRootDirectory(sdCardHome + p.getDName());
                    copyFolder(path + "/templet_AS2Aide/EmptyActivity/app", sdCardHome + p.getDName() + "/app");
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                    freemarker_make_gradle("EmptyActivity", sdCardHome + p.getDName() + "/app/", map);
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
                //BasicActivity
                case 1: {
                    configDialog(true);
                    makeRootDirectory(sdCardHome);
                    makeRootDirectory(sdCardHome + p.getDName());
                    copyFolder(path + "/templet_AS2Aide/BasicActivity/app", sdCardHome + p.getDName() + "/app");
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                    freemarker_make_gradle("BasicActivity", sdCardHome + p.getDName() + "/app/", map);
                    makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/res/layout");
                    makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java");
                    makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp());
                    makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany());
                    makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom());
                    makeRootDirectory(sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName());
                    freemarker_make_xml("BasicActivity", "AndroidManifest", sdCardHome + p.getDName() + "/app/src/main/AndroidManifest", map);
                    freemarker_make_java("BasicActivity", "MainActivity", sdCardHome + p.getDName() + "/app/src/main/java/" + p.getMyApp() + "/" + p.getMyCompany() + "/" + p.getCom() + "/" + p.getName() + "/" + activityname, map);
                    freemarker_make_xml("BasicActivity", "activity_main", sdCardHome + p.getDName() + "/app/src/main/res/layout/" + layoutname, map);
                    freemarker_make_xml("BasicActivity", "fragment_one", sdCardHome + p.getDName() + "/app/src/main/res/layout/fragment_one", map);
                    freemarker_make_xml("BasicActivity", "strings", sdCardHome + p.getDName() + "/app/src/main/res/values/strings", map);
                    configDialog(false);
                    break;
                }
                case 2: {
                    configDialog(true);
                    makeRootDirectory(sdCardHome);
                    makeRootDirectory(sdCardHome + p.getDName());
                    copyFolder(path + "/templet_AS2Aide/NavigationDrawerActivity/app", sdCardHome + p.getName() + "/app");
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/.gitignore", getResources().getString(R.string.git_ignore));
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/build.gradle", getResources().getString(R.string.build));
                    writeFileSdcardFile(sdCardHome + p.getDName() + "/settings.gradle", "include':app'");
                    freemarker_make_gradle("NavigationDrawerActivity", sdCardHome + p.getName() + "/app/", map);
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
                    freemarker_make_gradle("TabbedActivity", sdCardHome + p.getDName() + "/app/", map);
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
                    freemarker_make_gradle("FullscreenActivity", sdCardHome + p.getDName() + "/app/", map);
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
                    freemarker_make_gradle("LoginActivity", sdCardHome + p.getDName() + "/app/", map);
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
                    freemarker_make_gradle("ScrollActivity", sdCardHome + p.getDName() + "/app/", map);
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
                    freemarker_make_gradle("SettingsActivity", sdCardHome + p.getDName() + "/app/", map);
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
            build.setTitle(p.getDName() + " 项目已生成!");
            build.setMessage("打开Aide，或继续你的创建");
            build.setPositiveButton("打开"
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO 【已修复】打开Aide主程序的代码
                            try {
                                Intent intent = new Intent();
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.setComponent(new ComponentName("com.aide.ui","com.aide.ui.MainActivity"));
                                startActivity(intent);
                            } catch (Exception e) {
                                Snackbar.make(activityName, R.string.error_null_aide, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });
            build.setNegativeButton("继续", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            build.create().show();
        } else {
            if (file.exists()) {
                Snackbar.make(activityName, p.getDName() + getString(R.string.error_exist), Snackbar.LENGTH_LONG).show();
            }
            if (activityName.getText().toString().equals("")) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.myanim);
                activityName.startAnimation(anim);
            }
            if (layoutName.getText().toString().equals("")) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.myanim);
                layoutName.startAnimation(anim);
            }
        }
    }
}
