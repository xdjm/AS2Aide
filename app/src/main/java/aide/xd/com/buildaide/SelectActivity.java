package aide.xd.com.buildaide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import aide.xd.com.buildaide.adapter.HorizontalPagerAdapter;

/**
 * @author Administrator
 */
public class SelectActivity extends AppCompatActivity {
    private HorizontalInfiniteCycleViewPager infiniteCycleViewPager;
    private Toolbar toolbar;
    private PackageName p;
    private Button selectButton;
    private int zhi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initOnClickEvent();
    }

    private void init() {
        setContentView(R.layout.select_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.step_2);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        p = (PackageName) data.getSerializable("packagename");
        selectButton = (Button) findViewById(R.id.selectButton);
        infiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.viewpager);
        HorizontalPagerAdapter n = new HorizontalPagerAdapter(this);
        infiniteCycleViewPager.setAdapter(n);
    }

    private void initOnClickEvent() {
        selectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                Bundle dataNext = new Bundle();
                dataNext.putSerializable("packagename", p);
                dataNext.putInt("image", zhi);
                Intent intent = new Intent(SelectActivity.this, CompleteActivity.class);
                intent.putExtras(dataNext);
                startActivity(intent);
            }
        });

        infiniteCycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                zhi = Math.abs(position % 8);
                //Toast.makeText(getApplicationContext(), "选中了" + Math.abs(i % 8), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        PgyFeedbackShakeManager.unregister();
    }
}
