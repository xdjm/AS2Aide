package aide.xd.com.buildaide.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aide.xd.com.buildaide.R;

/**
 * FileName: FragmentHead
 * Author: Administrator
 * Date: 2019/2/21 21:14
 */
public class FragmentHead extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_head,container,false);
    }
}
