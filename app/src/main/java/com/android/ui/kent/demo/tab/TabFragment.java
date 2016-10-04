package com.android.ui.kent.demo.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ui.kent.R;

/**
 * Created by Kent on 2016/10/4.
 */

public class TabFragment extends Fragment {

    private View rootView;
    private int imgRes;
    private String content;

    public static TabFragment newInstance(ItemVO itemVO) {
        TabFragment tabFragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt("imgRes", itemVO.imgRes);
        args.putString("content", itemVO.content);
        tabFragment.setArguments(args);

        return tabFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        content = getArguments().getString("content");
        imgRes = getArguments().getInt("imgRes");

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
        ImageView img = (ImageView) rootView.findViewById(R.id.imageView);
        TextView text = (TextView) rootView.findViewById(R.id.textView);

        img.setImageResource(imgRes);
        text.setText(content);

    }


}
