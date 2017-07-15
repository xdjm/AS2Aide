package com.xd.aide.buildaide.adapter;

import android.view.View;
import android.widget.ImageView;

import com.xd.aide.buildaide.R;

/**
 * Created by yjm on 2017/1/14.
 */

public class Utils {
    protected static void setupItem(final View view, final LibraryObject libraryObject) {
        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());
    }

    protected static class LibraryObject {
        private int mRes;
        protected LibraryObject(final int res) {
            mRes = res;
        }
        public int getRes() {
            return mRes;
        }
        public void setRes(final int res) {
            mRes = res;
        }
    }
}
