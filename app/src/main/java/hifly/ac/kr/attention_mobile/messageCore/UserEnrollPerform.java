package hifly.ac.kr.attention_mobile.messageCore;

import android.os.Message;

import hifly.ac.kr.attention_mobile.value.Values;

/**
 * Created by CYSN on 2017-12-19.
 */

public class UserEnrollPerform implements SignalPerform {
    @Override
    public void performAction(SignalKey signalKey, MessageService messageService) {
        Message msg = new Message();
        if (signalKey.getBodyData().equals(Values.USER_ENROLL_SUCCESS_PROTOCOL)) {
            msg.what = Values.SERVICE_MESSAGE;
            msg.obj = Values.USER_ENROLL_SUCCESS_PROTOCOL;
            messageService.remoteSendMessage(msg);
        } else {
            msg.what = Values.SERVICE_MESSAGE;
            msg.obj = Values.USER_ENROLL_FAIL_PROTOCOL;
            messageService.remoteSendMessage(msg);
        }
    }
}
