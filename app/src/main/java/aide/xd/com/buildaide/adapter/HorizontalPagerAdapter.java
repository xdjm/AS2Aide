package aide.xd.com.buildaide.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aide.xd.com.buildaide.R;
import aide.xd.com.buildaide.Utils;

import static aide.xd.com.buildaide.Utils.setupItem;

/**
 * Created by yjm on 2017/1/14.
 */

public class HorizontalPagerAdapter extends PagerAdapter {
    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.blank_activity
            ),
            new Utils.LibraryObject(
                    R.drawable.basic_activity
            ),
            new Utils.LibraryObject(
                    R.drawable.blank_activity_drawer
            ),
            new Utils.LibraryObject(
                    R.drawable.blank_activity_tabs
            ),

            new Utils.LibraryObject(
                    R.drawable.fullscreen_activity
                    )
            ,
            new Utils.LibraryObject(
                    R.drawable.login_activity

            ),

            new Utils.LibraryObject(
                    R.drawable.scroll_activity

            ),
            new Utils.LibraryObject(
                    R.drawable.settings_activity

            )
    };

    private LayoutInflater mLayoutInflater;



    public HorizontalPagerAdapter(final Context context)
    {
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount()
    {
        return  8;
    }

    @Override
    public int getItemPosition(@NonNull final Object object)
    {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position)
    {
        final View view;

        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, LIBRARIES[position]);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull final View view, @NonNull final Object object)
    {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull final ViewGroup container, final int position, @NonNull final Object object)
    {
        container.removeView((View) object);
    }
}
