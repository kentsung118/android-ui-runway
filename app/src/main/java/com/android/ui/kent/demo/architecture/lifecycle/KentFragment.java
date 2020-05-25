package com.android.ui.kent.demo.architecture.lifecycle;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ui.kent.R;

import timber.log.Timber;

/**
 * Created by Kent Song on 2018/12/30.
 */
public class KentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_lifecycle, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTextView ftv = new FragmentTextView(getContext());
        ftv.setText("我是 Fragment 內部 TextView，請看 log 生命週期情況");

        Timber.d(">> KentFragment onViewCreated");
        ConstraintLayout rootLayout = view.findViewById(R.id.root_layout);
        rootLayout.addView(ftv);

        getLifecycle().addObserver(ftv);
    }

    @Override
    public void onStart() {
        super.onStart();
//        Timber.d(">> KentFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Timber.d(">> KentFragment onResume");

    }
}
