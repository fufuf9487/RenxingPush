package top.imzdx.qqpush.service;


import top.imzdx.qqpush.model.dto.Msg;
import top.imzdx.qqpush.model.po.MessageLog;
import top.imzdx.qqpush.model.po.User;

/**
 * @author Renxing
 */
public interface MsgService {
    void sendMsg(User user, Msg msg);

    MessageLog saveMsgToDB(Msg msg, long uid);
}
