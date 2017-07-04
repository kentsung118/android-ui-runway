package com.android.ui.kent.demo.widget.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kent on 2017/5/10.
 */

public class DialogActivity extends BaseActivity {

    @BindView(R.id.btnAlert) Button btnAlert;
    @BindView(R.id.btnAlert2) Button btnAlert2;
    @BindView(R.id.btnCommonDialog) Button btnCommonDialog;
    @BindView(R.id.btnItems) Button btnItems;
    @BindView(R.id.btnSingleChoice) Button btnSigleChoice;
    @BindView(R.id.btnMultiChoice) Button btnMultiChoice;
    @BindView(R.id.fullScreen) Button fullScreen;
    @BindView(R.id.verifyDismissButton) Button verifyDismiss;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("Dialog Sample");
    }

    @OnClick({
            R.id.btnAlert, R.id.btnAlert2, R.id.btnCommonDialog, R.id.btnItems, R.id.btnSingleChoice, R.id.btnMultiChoice,
            R.id.fullScreen, R.id.verifyDismissButton
    }) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAlert:
                DialogHelper.showAlert(this, "Alert 只有title");
                break;
            case R.id.btnAlert2:
                DialogHelper.showAlert(this, "Alert 有title", "也有 message");
                break;
            case R.id.btnCommonDialog:
                showCommonDialog();
                break;
            case R.id.btnItems:
                showListItemDialog();
                break;
            case R.id.btnSingleChoice:
                showSingleChoiceDialog();
                break;
            case R.id.btnMultiChoice:
                showMultiChoiceDialog();
                break;
            case R.id.fullScreen:
                showFullScreenDialog();
                break;
            case R.id.verifyDismissButton:
                showVerifyDismissButtonDialog();
                break;
        }
    }

    private void showCommonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sample Title")
                .setMessage("msg")
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "click confirm", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "click cancel", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .setNeutralButton("thirdBtn", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "click thirdBtn", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showListItemDialog(){
        final String[] strArray = new String[]{"Taiwan","US","India"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請選擇國家")
                .setItems(strArray, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "選擇了["+ strArray[which] +"]", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showSingleChoiceDialog(){
        AlertDialog dialog;

        final String[] strArray = new String[]{"Taiwan","US","India"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請選擇國家")
                .setSingleChoiceItems(strArray, 0, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "選擇了["+ strArray[which] +"]", Toast.LENGTH_SHORT)
                                .show();
                        dialog.dismiss();
                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    private void showMultiChoiceDialog(){

        final List<String> box = new ArrayList<>();
        final String[] strArray = new String[]{"Taiwan","US","India"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請選擇國家")
                .setMultiChoiceItems(strArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            box.add(strArray[which]);
                            Toast.makeText(DialogActivity.this, "選擇了["+ strArray[which] +"]", Toast.LENGTH_SHORT)
                                    .show();

                        } else{
                            box.remove(strArray[which]);
                            Toast.makeText(DialogActivity.this, "取消了["+ strArray[which] +"]", Toast.LENGTH_SHORT)
                                    .show();
                        }


                    }
                })
        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, "共選了["+ new Gson().toJson(box)+"]", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showFullScreenDialog(){
        final String[] strArray = new String[]{"Taiwan","US","India"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.DeviceDefault_Light_ButtonBar);
        builder.setTitle("請選擇國家")
                .setItems(strArray, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "選擇了["+ strArray[which] +"]", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showVerifyDismissButtonDialog(){
        final AlertDialog alertDialog;

        final String[] strArray = new String[]{"Taiwan","US","India"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請選擇國家")
                .setSingleChoiceItems(strArray, 0, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "選擇了["+ strArray[which] +"]", Toast.LENGTH_SHORT)
                                .show();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("確定", null);

        alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Boolean wantToCloseDialog = false;
                        //Do stuff, possibly set wantToCloseDialog to true then...
                        if(wantToCloseDialog) {
                            alertDialog.dismiss();
                        } else{
                            Toast.makeText(DialogActivity.this, "檢核不成功，無法退出", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        alertDialog.show();



    }



    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, DialogActivity.class);
        activity.startActivity(intent);
    }
}
