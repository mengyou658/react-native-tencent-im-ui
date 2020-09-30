package com.yunchao.tencentim.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.yunchao.tencentim.R;
import com.yunchao.tencentim.activity.ChatActivity;
import com.yunchao.tencentim.common.Constants;

public class TencentIMConversationModel extends SimpleViewManager<View> {

    private ConversationLayout mConversationLayout;

    @Override
    public String getName() {
        return "TencentIMConversationModel";
    }

    @Override
    protected View createViewInstance(ThemedReactContext reactContext) {
        return createConversationView(reactContext);
    }

    private View createConversationView(ThemedReactContext reactContext) {
        View conversationView = LayoutInflater.from(reactContext).inflate(R.layout.conversation, null);
        initConversationView(conversationView, reactContext);
        return conversationView;
    }

    private View createSimpleView(ThemedReactContext reactContext) {
        View simpleView = LayoutInflater.from(reactContext).inflate(R.layout.simple_view, null);
        return simpleView;
    }

    private void initConversationView(View conversationView, final Context context) {
        // 从布局文件中获取会话列表面板
        mConversationLayout = conversationView.findViewById(R.id.conversation_layout);
        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
        mConversationLayout.getTitleBar().setVisibility(View.INVISIBLE);
        //隐藏titleBar
        mConversationLayout.setBackground(new ColorDrawable(Color.parseColor("#F7F9F8")));
        // 设置adapter item头像圆角大小
        mConversationLayout.getConversationList().setItemAvatarRadius(100);
        mConversationLayout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ConversationInfo conversationInfo) {
                //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
                startChatView(conversationInfo, context);
            }
        });
    }

    private void startChatView(ConversationInfo conversationInfo, Context context) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(conversationInfo.isGroup() ? TIMConversationType.Group.value(): TIMConversationType.C2C.value());
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
