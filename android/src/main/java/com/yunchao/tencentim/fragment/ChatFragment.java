package com.yunchao.tencentim.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.base.BaseFragment;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.yunchao.tencentim.R;
import com.yunchao.tencentim.common.Constants;


public class ChatFragment extends BaseFragment {

    private View mBaseView;
    public static ChatLayout mChatLayout;
    private TitleBarLayout mTitleBar;
    private InputLayout mInputLayout;
    private ChatInfo mChatInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.chat_fragment, container, false);
        return mBaseView;
    }

    private void initView() {
        //从布局文件中获取聊天面板组件
        mChatLayout = mBaseView.findViewById(R.id.chat_layout);
        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();

        /*
         * 需要聊天的基本信息
         */
        mChatLayout.setChatInfo(mChatInfo);


        //获取单聊面板的标题栏
        mTitleBar = mChatLayout.getTitleBar();
        //隐藏右侧title显示按钮
        mTitleBar.getRightGroup().setVisibility(View.INVISIBLE);
        mTitleBar.setBackground(new ColorDrawable(Color.parseColor("#FFFFFF")));

        // 从 ChatLayout 里获取 InputLayout
        mInputLayout = mChatLayout.getInputLayout();
        mInputLayout.setBackground(new ColorDrawable(Color.parseColor("#FFFFFF")));


        // 从ChatLayout 里获取 MessageLayout
        MessageLayout messageLayout = mChatLayout.getMessageLayout();
        // 设置头像圆角，不设置则默认不做圆角处理
        messageLayout.setAvatarRadius(50);
        // 设置聊天背景
//        messageLayout.setBackground(new ColorDrawable(Color.parseColor("#F7F9F8")));
        // 设置自己聊天气泡的背景
//        messageLayout.setRightBubble(getResources().getDrawable(R.drawable.chat_opposite_bg));
        // 设置朋友聊天气泡的背景
//        messageLayout.setLeftBubble(getResources().getDrawable(R.drawable.chat_self_bg));

        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (mChatInfo.getType() == TIMConversationType.C2C.value()) {
            mTitleBar.setOnRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(MyApplication.instance(), FriendProfileActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(TUIKitConstants.ProfileType.CONTENT, mChatInfo);
//                    MyApplication.instance().startActivity(intent);
                }
            });
        }
        mChatLayout.getMessageLayout().setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //因为adapter中第一条为加载条目，位置需减1
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }

            @Override
            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {
                if (null == messageInfo) {
                    return;
                }
//                ChatInfo info = new ChatInfo();
//                info.setId(messageInfo.getFromUser());
//                Intent intent = new Intent(MyApplication.instance(), FriendProfileActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(TUIKitConstants.ProfileType.CONTENT, info);
//                MyApplication.instance().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        mChatInfo = (ChatInfo) bundle.getSerializable(Constants.CHAT_INFO);
        if (mChatInfo == null) {
            return;
        }
        initView();

    }

    @Override
    public void onPause() {
        super.onPause();
        AudioPlayer.getInstance().stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatLayout != null) {
            mChatLayout.exitChat();
        }
    }
}
