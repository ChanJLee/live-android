package com.wenyu.ylive.biz.live.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.EditText;

import com.wenyu.ylive.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wenyu.ylive.common.api.service.YLiveApiService.CATEGORY_CODE;

/**
 * Created by chan on 17/5/10.
 */

public class RoomConfigDialog extends Dialog {

    private Callback mCallback;

    @Bind(R.id.spinner)
    AppCompatSpinner mAppCompatSpinner;

    @Bind(R.id.title)
    EditText mTitle;

    public RoomConfigDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog_video_room_config);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.cancel)
    void onCancelClicked() {
        dismiss();
    }

    @OnClick(R.id.accept)
    void onAcceptClicked() {
        if (mCallback != null) {
            int position = mAppCompatSpinner.getSelectedItemPosition();
            mCallback.onAcceptClicked(mTitle.getText().toString(), CATEGORY_CODE[position]);
        }
        dismiss();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onAcceptClicked(String title, int category);
    }
}
