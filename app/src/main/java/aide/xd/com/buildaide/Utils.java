package aide.xd.com.buildaide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjm on 2017/1/14.
 */

public class Utils {

    public static void setupItem(final View view, final LibraryObject libraryObject) {
        final ImageView img = view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());
    }

    public static class LibraryObject {
        private int mRes;
        public LibraryObject(final int res) {
            mRes = res;
        }
        public int getRes() {
            return mRes;
        }
        public void setRes(final int res) {
            mRes = res;
        }
    }
    static void copyFilesFromAssets(Context context, String oldPath, String newPath) {
        try {
            String[] fileNames = context.getAssets().list(oldPath);
            assert fileNames != null;
            if (fileNames.length > 0) {
                File file = new File(newPath);
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
        } catch (Exception ignored) {
        }
    }


    static void goToMarket(Context context,String marketPkg){
        String appPkg = context.getPackageName();
// 扫描已经安装的市场包名
        ArrayList<String> marketPkgs = MarketUtils.queryInstalledMarketPkgs(context);
        Uri uri = Uri.parse("market://details?id=" + appPkg);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
// 指定市场
        if (!TextUtils.isEmpty(marketPkg) && marketPkgs.contains(marketPkg)) {
            intent.setPackage(marketPkg);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
