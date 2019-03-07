package aide.xd.com.buildaide.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jkb.fragment.rigger.annotation.Animator;
import com.jkb.fragment.rigger.annotation.Puppet;

import java.util.Objects;

import aide.xd.com.buildaide.MainActivity;
import aide.xd.com.buildaide.PackageName;
import aide.xd.com.buildaide.R;
@Animator( enter = R.anim.push_left_in_no_alpha, exit = R.anim.push_right_out_no_alpha,
        popEnter = R.anim.push_right_in_no_alpha, popExit = R.anim.push_left_out_no_alpha)
@Puppet
public class FragmentOne extends Fragment {
    private EditText applicationName;
    private EditText projectName;
    private TextView packageFullName;
    private String[] nameList = {"mayapp", "maycompany", "com"};
    private boolean a = true;

    public static FragmentOne newInstance() {
        Bundle args = new Bundle();
        FragmentOne fragment = new FragmentOne();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one,container,false);
        final Button clear = v.findViewById(R.id.clear);
        applicationName = v.findViewById(R.id.edt_application_name);
        applicationName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                clear.setEnabled(true);
                packageFullName.setText(String.format("%s%s.%s", getString(R.string.package_name), projectName.getText().toString(), applicationName.getText().toString().toLowerCase()));
                if ("".equals(applicationName.getText().toString())) {
                    applicationName.setError(getString(R.string.error_not_null));
                } else {
                    applicationName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
        projectName = v.findViewById(R.id.edt_project_name);
        projectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
                clear.setEnabled(true);
                packageFullName.setText(String.format("%s%s.%s", getString(R.string.package_name), projectName.getText().toString(), applicationName.getText().toString().toLowerCase()));
                a = projectName.getText().toString().matches("^[a-z]++\\.[a-z]++\\.[a-z]+$");
                if (!a) {
                    projectName.setError(getString(R.string.error_bad_format));
                } else {
                    nameList = projectName.getText().toString().split("\\.");
                    projectName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable p1) {
            }
        });
        packageFullName = v.findViewById(R.id.package_FullName);
        packageFullName.setText(String.format("%scom.mycompany.myapp.myapplication", getString(R.string.package_name)));
        v.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((a) && !"".equals(applicationName.getText().toString())) {
                    PackageName p = new PackageName(nameList[0], nameList[1], nameList[2],
                            applicationName.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("packagename", p);
                    ((MainActivity) Objects.requireNonNull(getActivity())).startTwo(bundle);
                } else {
                    Snackbar.make(projectName, R.string.error_info, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
        v.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationName.setText("");
                projectName.setText("");
                packageFullName.setText(getString(R.string.package_name));
                v.setEnabled(false);
            }
        });
        return v;
    }
}
