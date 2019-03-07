package aide.xd.com.buildaide.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.jkb.fragment.rigger.annotation.Animator;
import com.jkb.fragment.rigger.annotation.Puppet;

import java.util.Objects;

import aide.xd.com.buildaide.MainActivity;
import aide.xd.com.buildaide.PackageName;
import aide.xd.com.buildaide.R;
import aide.xd.com.buildaide.adapter.HorizontalPagerAdapter;
@Animator(enter = R.anim.push_left_in_no_alpha, exit = R.anim.push_right_out_no_alpha,
        popEnter = R.anim.push_right_in_no_alpha, popExit = R.anim.push_left_out_no_alpha)
@Puppet
public class FragmentTwo extends Fragment {
    private int zhi = 0;
    public static FragmentTwo newInstance(Bundle bundle) {
        FragmentTwo fragment = new FragmentTwo();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_two, container, false);
        v.findViewById(R.id.selectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                Bundle bundle = new Bundle();
                PackageName p = (PackageName) Objects.requireNonNull(getArguments()).getSerializable("packagename");
                bundle.putSerializable("packagename", p);
                bundle.putInt("image", zhi);
                ((MainActivity) Objects.requireNonNull(getActivity())).startThree(bundle);
            }
        });
        HorizontalInfiniteCycleViewPager infiniteCycleViewPager = v.findViewById(R.id.viewpager);
        HorizontalPagerAdapter n = new HorizontalPagerAdapter(getContext());
        infiniteCycleViewPager.setAdapter(n);
        infiniteCycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                zhi = Math.abs(position % 8);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return v;
    }
}
