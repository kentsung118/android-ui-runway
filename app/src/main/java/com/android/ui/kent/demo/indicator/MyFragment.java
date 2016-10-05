package com.android.ui.kent.demo.indicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ui.kent.R;

/**
 * Created by Kent on 2016/10/4.
 */

public class MyFragment extends Fragment{

    private View rootView;
    private int imgRes;
    private String content;

    public static MyFragment newInstance(ItemVO itemVO) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("imgRes", itemVO.imgRes);
        args.putString("content", itemVO.content);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        content = getArguments().getString("content");
        imgRes = getArguments().getInt("imgRes");
        Log.d("tester", "onCreate");
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_indicator, container, false);
        Log.d("tester", "onCreateView");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img = (ImageView) rootView.findViewById(R.id.imageView);
        TextView text = (TextView) rootView.findViewById(R.id.textView);

        Log.d("tester", "img = " + img);
        Log.d("tester", "imgRes = " + imgRes);
        img.setImageResource(imgRes);
        text.setText(content);

    }
}
