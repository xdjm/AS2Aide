package aide.xd.com.buildaide.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import aide.xd.com.buildaide.AboutActivity;
import aide.xd.com.buildaide.MainActivity;
import aide.xd.com.buildaide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbout extends Fragment {


    public FragmentAbout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_about, container, false);
        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PgyFeedback.getInstance().showDialog(getContext());
            }
        });
        view.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PgyUpdateManager.register(getActivity(),
                        new UpdateManagerListener() {
                            @Override
                            public void onUpdateAvailable(final String result) {
                                final AppBean appBean = getAppBeanFromString(result);
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(getString(R.string.update))
                                        .setMessage("")
                                        .setNegativeButton(
                                                getString(R.string.yes),
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which) {
                                                        startDownloadTask(
                                                                getActivity(),
                                                                appBean.getDownloadURL());
                                                    }
                                                }).show();
                            }

                            @Override
                            public void onNoUpdateAvailable() {
                                Snackbar.make(view, R.string.version_info,
                                        Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        });
            }
        });
        view.findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).request_for_access();
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        PgyFeedbackShakeManager.unregister();
    }
}
