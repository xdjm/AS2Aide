package aide.xd.com.buildaide.adapter;

import android.view.View;
import android.widget.ImageView;
import aide.xd.com.buildaide.R;

/**
 * Created by yjm on 2017/1/14.
 */

public class Utils {
    protected static void setupItem(final View view, final LibraryObject libraryObject) {
        final ImageView img = view.findViewById(R.id.img_item);
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
