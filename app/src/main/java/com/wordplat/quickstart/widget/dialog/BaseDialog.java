package com.wordplat.quickstart.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.utils.AppUtils;

/**
 * Created by afon on 2016/9/3.
 */
public class BaseDialog extends Dialog {

    private final static String TAG = "BaseDialog";

    protected TextView Dialog_Text;
    protected TextView Dialog_Title;
    protected Button Dialog_Action_But;
    protected Button Dialog_Cancel_But;
    protected Context context;
    protected View.OnClickListener onActionClickListener;
    protected View.OnClickListener onCancelClickListener;
    protected View.OnClickListener myActionClickListener;
    protected View.OnClickListener myCancelClickListener;

    protected String dialogText; // 对话框主要内容 文本
    protected String dialogTitle; // 对话框主要标题 文本
    protected String actionText; // 对话框动作按钮 文本
    protected String cancelText; // 对话框取消按钮 文本

    public BaseDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dialog_Title = (TextView)findViewById(R.id.Dialog_Title);
        Dialog_Text = (TextView) findViewById(R.id.Dialog_Text);
        Dialog_Action_But = (Button) findViewById(R.id.Dialog_Action_But);
        Dialog_Cancel_But = (Button) findViewById(R.id.Dialog_Cancel_But);

        if(Dialog_Text != null && !TextUtils.isEmpty(dialogText)) {
            Dialog_Text.setText(dialogText);
        }
        if(Dialog_Title != null && !TextUtils.isEmpty(dialogTitle)){
            Dialog_Title.setText(dialogTitle);
        }
        if(Dialog_Action_But != null && !TextUtils.isEmpty(actionText)) {
            Dialog_Action_But.setText(actionText);
        }
        if(Dialog_Cancel_But != null && !TextUtils.isEmpty(cancelText)) {
            Dialog_Cancel_But.setText(cancelText);
        }

        if(onActionClickListener == null) {
            onActionClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myActionClickListener != null) {
                        myActionClickListener.onClick(v);
                        dismiss();
                    } else {
                        dismiss();
                    }
                }
            };
        }

        if(onCancelClickListener == null) {
            onCancelClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myCancelClickListener != null) {
                        myCancelClickListener.onClick(v);
                        dismiss();
                    } else {
                        dismiss();
                    }
                }
            };
        }

        if(Dialog_Action_But != null) {
            Dialog_Action_But.setOnClickListener(onActionClickListener);
        }

        if(Dialog_Cancel_But != null) {
            Dialog_Cancel_But.setOnClickListener(onCancelClickListener);
        }
    }

    public BaseDialog withText(String text) {
        dialogText = text;
        return this;
    }

    public BaseDialog withTitle(String text) {
        dialogTitle = text;
        return this;
    }

    public BaseDialog withAction(String text) {
        actionText = text;
        return this;
    }

    public BaseDialog withCancel(String text) {
        cancelText = text;
        return this;
    }

    public BaseDialog onActionClick(View.OnClickListener onClickListener) {
        myActionClickListener = onClickListener;
        return this;
    }

    public BaseDialog onCancelClick(View.OnClickListener onClickListener) {
        myCancelClickListener = onClickListener;
        return this;
    }

    protected int getStandardWith() {
        return AppUtils.dpTopx(context, 250);
    }
}