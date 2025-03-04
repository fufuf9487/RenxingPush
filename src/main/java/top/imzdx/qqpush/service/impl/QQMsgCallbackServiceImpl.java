package top.imzdx.qqpush.service.impl;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.qqpush.model.po.MessageCallback;
import top.imzdx.qqpush.model.po.MessageCallbackLog;
import top.imzdx.qqpush.repository.MessageCallbackDao;
import top.imzdx.qqpush.repository.MessageCallbackLogDao;
import top.imzdx.qqpush.service.MsgCallbackService;

import static top.imzdx.qqpush.model.po.MessageCallbackLog.TYPE_QQ;

@Service
public class QQMsgCallbackServiceImpl extends MsgCallbackService {
    @Autowired
    public QQMsgCallbackServiceImpl(MessageCallbackDao messageCallbackDao, MessageCallbackLogDao messageCallbackLogDao) {

        super(messageCallbackDao, messageCallbackLogDao);
        Listener<MessageEvent> listener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {
            MessageChain chain = event.getMessage();
            MessageCallback messageCallback = new MessageCallback()
                    .setAppType(TYPE_QQ)
                    .setSender(String.valueOf(event.getSender().getId()))
                    .setKeyword(chain.contentToString());
            if (event instanceof GroupMessageEvent groupMessageEvent) {
                messageCallback.setGroup(String.valueOf(groupMessageEvent.getGroup().getId()));
            }
            messageCallback = findMessageCallback(messageCallback);
            if (messageCallback != null) {
                MessageCallbackLog messageCallbackLog = new MessageCallbackLog()
                        .setAppType(TYPE_QQ)
                        .setContent(chain.contentToString())
                        .setUid(messageCallback.getUid());
                String feedback = callback(messageCallback);
                messageCallbackLog.setFeedback(feedback);
                if (feedback != null) {
                    if (event instanceof FriendMessageEvent friendMessageEvent) {
                        if (messageCallback.getReply())
                            friendMessageEvent.getSender().sendMessage(feedback);
                    } else if (event instanceof GroupMessageEvent groupMessageEvent) {
                        if (messageCallback.getReply())
                            groupMessageEvent.getGroup().sendMessage(feedback);
                    }
                    saveMessageCallbackLog(messageCallbackLog);
                } else {
                    messageCallbackLog.fail();
                    event.getSender().sendMessage("回调失败");
                }
            }
        });
    }

}
