package aide.xd.com.buildaide;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.rigger.Rigger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.io.File;
import java.util.List;

import aide.xd.com.buildaide.fragment.FragmentOne;
import aide.xd.com.buildaide.fragment.FragmentThree;
import aide.xd.com.buildaide.fragment.FragmentTwo;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author Administrator
 */

@Puppet(containerViewId = R.id.include,stickyStack = true)
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private AlertDialog dialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                Intent intent = new Intent();
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bugly.init(this, "01c2fd1183", false);
        Beta.autoCheckUpgrade = true;
        Rigger.getRigger(this).startFragment(FragmentOne.newInstance());
        request_for_access();
    }

    public void startTwo(Bundle bundle) {
        Rigger.getRigger(this).startFragment(FragmentTwo.newInstance(bundle));
    }
    public void startThree(Bundle bundle) {
        Rigger.getRigger(this).startFragment(FragmentThree.newInstance(bundle));
    }

    private void copyTemplateFile2Local() {
        configDialog(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    Utils.copyFilesFromAssets(MainActivity.this, "templet_AS2Aide",
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}

