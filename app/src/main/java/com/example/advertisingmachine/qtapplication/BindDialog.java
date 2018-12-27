package com.example.advertisingmachine.qtapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.qtapplication.R;

public class BindDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private EditText editText;
    private Button btnBind;
    private Button btnCancle;
    public LeaveLisenner lisenner;

    public interface LeaveLisenner{
        public void onClick(View v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bind_request_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        editText = (EditText) findViewById(R.id.et_mac);
        btnBind = (Button) findViewById(R.id.btn_bind);
        btnCancle = (Button) findViewById(R.id.btn_cancel);

        editText.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        btnCancle.setOnClickListener(this);


    }

    public BindDialog(Context context, LeaveLisenner lisenner) {
        super(context,R.style.CustomDialog);
        this.mContext = context;
        this.lisenner = lisenner;
    }

    public BindDialog(Context context) {
        super(context);
        this.mContext = context;

    }


    private void requestFocus() {
        editText.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void clearEditText() {
        editText.setText("");
        requestFocus();
    }



    @Override
    public void onClick(View v) {
        lisenner.onClick(v);
    }

}
