package aide.xd.com.buildaide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.model.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.model.MaterialAboutTitleItem;


/**
 * @author yjm
 * @date 2017/1/16
 */

public class AboutActivity extends MaterialAboutActivity {
    @Override
    protected MaterialAboutList getMaterialAboutList(Context context) {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();
        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .icon(R.mipmap.ic_launcher)
                .build());
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.version)
                .subText("1.4")
                .icon(R.drawable.about_version)
                .build());
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.changelog)
                .icon(R.drawable.about_changelog)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                        builder.setTitle(R.string.changelog);
                        builder.setMessage(R.string.change_info);
                        builder.show();
                    }
                })
                .build());
        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title(R.string.more_info);
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("春和景明").subText("QQ:727787745")
                .icon(R.drawable.about_auther)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                        builder.setTitle(R.string.contact_author);
                        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=727787745";
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                } catch (Exception e) {
                                    Toast.makeText(AboutActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.show();
                    }
                })
                .build());
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("工具【AS2Aide】发布").subText("@C春和景明C")
                .icon(R.drawable.about_tieba)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                        builder.setTitle(R.string.go_to_look);
                        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String url = "http://tieba.baidu.com/p/4935297747";
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                } catch (Exception e) {
                                    Toast.makeText(AboutActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.show();
                    }
                })
                .build());
        MaterialAboutCard.Builder licenseCardBuilder = new MaterialAboutCard.Builder();
        licenseCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.license_name)
                .icon(R.drawable.about_license)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                        builder.setTitle(R.string.license_name);
                        builder.setMessage(R.string.license);
                        builder.show();
                    }
                })
                .build());
        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), licenseCardBuilder.build());
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.mal_title_about);
    }
}

