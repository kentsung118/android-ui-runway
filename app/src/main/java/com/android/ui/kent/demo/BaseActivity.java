package com.android.ui.kent.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import com.android.ui.kent.R;

import butterknife.BindView;

/**
 * Created by Kent on 2016/9/27.
 */

public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private MenuOptionItemListener optionItemListener;

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

    }

    protected void setToolbarTitle(String text) {
        if (toolbar != null) {
            ActionBar actionbar = getSupportActionBar();
            actionbar.setTitle(text);
        }
    }

    protected void enableBackButton(){
        if (toolbar != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                if (optionItemListener != null) {
                    optionItemListener.onMenuOptionItemSelected(item);
                }
                return super.onOptionsItemSelected(item);
        }
    }

    public void setOptionItemListener(MenuOptionItemListener listener) {
        optionItemListener = listener;
    }

    public interface MenuOptionItemListener {
        void onMenuOptionItemSelected(MenuItem item);
    }
}
