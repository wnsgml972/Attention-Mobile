package hifly.ac.kr.attention_mobile.messageCore;

import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.value.Values;

/**
 * Created by CYSN on 2017-12-19.
 */

public class FriendsEnrollPerform implements SignalPerform {
    @Override
    public void performAction(SignalKey signalKey, MessageService messageService) {
        Message msg = new Message();
        String splitMyFriends[] = signalKey.getBodyData().split(Values.SPLIT_MESSAGE);
        HashMap<String, User> myFriends = new HashMap<>();//uuid name tel
        for (int i = 0; i < splitMyFriends.length; i++) {
            myFriends.put(splitMyFriends[i],new User(splitMyFriends[i++], splitMyFriends[i++], splitMyFriends[i++],splitMyFriends[i++],splitMyFriends[i]));
            //Log.i(Values.TAG,myFriends.get(i).toString());
        }

        msg.what = Values.USER_FRIENDS_RESPONSE;
        msg.obj = myFriends;
        messageService.remoteSendMessage(msg);
    }
}
