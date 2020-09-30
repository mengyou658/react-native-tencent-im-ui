package com.yunchao.tencentim.activity;

import android.content.Intent;
import android.os.Bundle;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.yunchao.tencentim.R;
import com.yunchao.tencentim.common.Constants;
import com.yunchao.tencentim.fragment.ChatFragment;
import com.yunchao.tencentim.utils.IMLog;

public class ChatActivity extends com.yunchao.tencentim.activity.BaseActivity {

    private static final String TAG = ChatActivity.class.getSimpleName();

    private ChatFragment mChatFragment;
    public static ChatInfo mChatInfo;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        chat(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        IMLog.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
        chat(intent);
    }

    @Override
    protected void onResume() {
        IMLog.i(TAG, "onResume");
        super.onResume();
    }

    private void chat(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        } else {
            mChatInfo = (ChatInfo) bundle.getSerializable(Constants.CHAT_INFO);
            if (mChatInfo == null) {
                return;
            }
            mChatFragment = new ChatFragment();
            mChatFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.empty_view, mChatFragment).commitAllowingStateLoss();
        }
    }


}
