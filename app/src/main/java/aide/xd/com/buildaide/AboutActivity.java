package aide.xd.com.buildaide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;

public class AboutActivity extends MaterialAboutActivity {

    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull final Context c) {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();
        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .desc("1.8")
                .icon(R.mipmap.ic_launcher)
                .build());
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.changelog)
                .icon(R.drawable.about_changelog)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                        builder.setTitle(R.string.changelog);
                        builder.setMessage(R.string.change_info);
                        builder.show();
                    }
                })
                .build());
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                        .text(R.string.license_name)
                        .icon(R.drawable.about_license)
                        .setOnClickAction(new MaterialAboutItemOnClickAction() {
                            @Override
                            public void onClick() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                                builder.setTitle(R.string.license_name);
                                builder.setMessage(R.string.license);
                                builder.show();
                            }
                        })
                        .build());
        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title(R.string.more_info);
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("xdjm").subText("https://github.com/xdjm")
                .icon(R.drawable.about_auther)
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(c, Uri.parse("https://github.com/xdjm")))

//                                try {
//                                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=727787745";
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                                } catch (Exception e) {
//                                    Toast.makeText(AboutActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//                                }

                .build());
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("C春和景明C").subText("工具【AS2Aide】发布")
                .icon(R.drawable.about_tieba)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        String url = "http://tieba.baidu.com/p/4935297747";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                })
                .build());
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("评分")
                .icon(R.drawable.ic_looks_5_black_24dp)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        Utils.goToMarket(getApplicationContext(),"com.coolapk.market");
                    }
                })
                .build());
        return new MaterialAboutList(appCardBuilder.build(),authorCardBuilder.build());
    }

    @Nullable
    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.about);
    }
}
