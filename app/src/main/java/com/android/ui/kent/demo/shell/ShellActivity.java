package com.android.ui.kent.demo.shell;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songzhukai on 2019-12-24.
 */
public class ShellActivity extends BaseActivity {
    private final String TAG = ShellActivity.class.getSimpleName();
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @BindView(R.id.edit_input)
    EditText mEditShell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        ButterKnife.bind(this);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postShell(v);
            }
        });

        mEditShell.setText("cat /proc/meminfo");
        //TODO 更完善的作法请参考 https://www.jianshu.com/p/af4b3264bc5d

        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("ShellActivity");
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ShellActivity.class);
        activity.startActivity(intent);
    }


    public void postShell(View v) {
        String cmd = mEditShell.getText().toString();
        if (StringUtils.isBlank(cmd)) {
            return;
        }
        long start = System.currentTimeMillis();
        String result = playRunTime(cmd);
        long end = System.currentTimeMillis();
        Log.i(TAG, "spend time=" + (end - start));
        mTvResult.setText(result);

    }

    //执行
    private String playRunTime(String cmd) {
        StringBuilder sb = new StringBuilder();
        try {
            Log.i(TAG, "kent flag1");
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            p.waitFor();
            is.close();
            reader.close();
            p.destroy();
        } catch (Exception e) {
            Log.i(TAG, "kent playRunTime catch exception" + e.getMessage());
            e.printStackTrace();

        } finally {
            Log.i(TAG, "kent playRunTime =" + sb.toString());
            return sb.toString();
        }
    }

}
