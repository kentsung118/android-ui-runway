package com.android.ui.kent.demo.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.android.ui.kent.R;

/**
 * Created by Kent on 2017/5/10.
 */

public class DialogHelper {


    public static void showAlert(final Context context, String msg) {
        showAlert(context, null, msg);
    }


    public static void showAlert(Context context, String title, String msg) {
        AlertDialog dialog = getCommonAlert(context, title, msg);
        dialog.show();
    }

    private static AlertDialog getCommonAlert(Context context, @Nullable String title, @Nullable String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(title)
                .setPositiveButton(R.string.all_dialog_confirm, null)
                .setMessage(msg)
                .create();
    }


    public static AlertDialog getCommonYesNoDialog(Context context, String title, String msg, DialogInterface.OnClickListener onPositive){
        String positiveStr = context.getString(R.string.all_dialog_confirm);
        String negativeStr = context.getString(R.string.all_dialog_cancel);

        return getCommonDialog(context, title, msg, positiveStr, onPositive, negativeStr, null);
    }


    public static AlertDialog getCommonYesNoDialog(
            Context context,@Nullable String title, @Nullable String msg,
            DialogInterface.OnClickListener onPositive, DialogInterface.OnClickListener onNegative){

        String positiveStr = context.getString(R.string.all_dialog_confirm);
        String negativeStr = context.getString(R.string.all_dialog_cancel);

        return getCommonDialog(context, title, msg, positiveStr, onPositive, negativeStr, onNegative);
    }


    public static AlertDialog getCommonDialog(
            Context context,@Nullable String title, @Nullable String msg,
            @Nullable String positiveBtnStr, DialogInterface.OnClickListener onPositive,
            @Nullable String negativeBtnStr, DialogInterface.OnClickListener onNegative ){

        return getCommonDialog(context, title, msg, positiveBtnStr, onPositive, negativeBtnStr, onNegative, null, null);
    }


    public static AlertDialog getCommonDialog(
            Context context,@Nullable String title, @Nullable String msg,
            @Nullable String positiveBtnStr, DialogInterface.OnClickListener onPositive,
            @Nullable String negativeBtnStr, DialogInterface.OnClickListener onNegative,
            @Nullable String neutralBtnStr, DialogInterface.OnClickListener onNeutral){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if(title != null){
            builder.setTitle(title);
        }

        if(msg != null){
            builder.setMessage(msg);
        }

        if(positiveBtnStr != null){
            builder.setPositiveButton(positiveBtnStr, onPositive);
        }

        if(negativeBtnStr != null){
            builder.setNegativeButton(negativeBtnStr, onNegative);
        }

        if(neutralBtnStr != null){
            builder.setNeutralButton(neutralBtnStr, onNeutral);
        }


        return builder.create();
    }

}
