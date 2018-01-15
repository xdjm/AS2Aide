package aide.xd.com.buildaide.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aide.xd.com.buildaide.R;

import static aide.xd.com.buildaide.adapter.Utils.setupItem;

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
    public int getItemPosition(final Object object)
    {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position)
    {
        final View view;

        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, LIBRARIES[position]);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object)
    {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object)
    {
        container.removeView((View) object);
    }
}
