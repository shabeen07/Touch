package com.devcods.touch.dialogs;

import android.app.Activity;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import com.devcods.touch.R;
public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;


    public LoadingDialog(Activity myActivity) {
        this.activity=myActivity;
    }

     public void showDialog(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(activity, R.style.ThemeDialogCustom);
        LayoutInflater inflater=activity.getLayoutInflater();
//        mBuilder.setView(inflater.inflate(R.layout.loading_view,null));
        mBuilder.setCancelable(false);

        dialog=mBuilder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}
