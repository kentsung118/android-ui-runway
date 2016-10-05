package com.android.ui.kent.demo.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.application.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Kent on 2016/10/4.
 */

public class TabFragment extends Fragment {

    private View rootView;
    private String content;
    private String imgUrl;

    public static TabFragment newInstance(ItemVO itemVO) {
        TabFragment tabFragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString("content", itemVO.content);
        args.putString("imgUrl", itemVO.imgUrl);
        tabFragment.setArguments(args);

        return tabFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        content = getArguments().getString("content");
        imgUrl = getArguments().getString("imgUrl");
        Log.d("tester", "TabFragment onCreate");
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgView = (ImageView) rootView.findViewById(R.id.imageView);
        TextView text = (TextView) rootView.findViewById(R.id.textView);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(imgUrl, imgView, MyApplication.ImageLoaderOptions);
        text.setText(content);

    }


}
