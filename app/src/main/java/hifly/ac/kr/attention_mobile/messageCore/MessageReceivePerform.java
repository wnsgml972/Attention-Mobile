package hifly.ac.kr.attention_mobile.messageCore;

import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import hifly.ac.kr.attention_mobile.adapter_item.ChatActivity_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.data.Room;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.main.MainActivity;
import hifly.ac.kr.attention_mobile.main.Main_Friend_Message_Activity;
import hifly.ac.kr.attention_mobile.value.Values;

/**
 * Created by CYSN on 2017-12-19.
 */

public class MessageReceivePerform implements SignalPerform {
    @Override
    public void performAction(SignalKey signalKey, MessageService messageService) {
        Message msg = new Message();
        String splitMyFriends[] = signalKey.getBodyData().split(Values.SPLIT_MESSAGE);
        ChatActivity_RecyclerView_Item item = new ChatActivity_RecyclerView_Item(splitMyFriends[0],splitMyFriends[1],splitMyFriends[2],splitMyFriends[3],splitMyFriends[4]);

        Log.i(Values.TAG, splitMyFriends[0] + " " + splitMyFriends[1]+ " " +splitMyFriends[2]+ " " + splitMyFriends[3]+ " " +splitMyFriends[4]);
        if (item.getSender_uuid().equals(Values.myUUID)) {
            item.setItemViewType(1);//1이 오른쪽(내거)
        } else {
            item.setItemViewType(0);
        }
        for(Room room : MainActivity.rooms){
            if(room.getRoomUUID().equals(splitMyFriends[4])){
                if(room.isRoomVisible()){
                    Message message = new Message();
                    message.what = Values.CHATTING_MESSAGE_RECEIVE;
                    message.obj = item;
                    messageService.remoteSendMessage(message);
                    break;
                }
                else{
                    Log.i(Values.TAG,"MESSAGE_RECEIVE_PERFORM : MESSAGE ACTIVITY GONE");
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(new File(messageService.getFilesDir(),splitMyFriends[4])));
                        Room thisRoom = (Room) objectInputStream.readObject();
                        thisRoom.addItem(item);
                        ObjectOutputStream objectOutputStream  = new ObjectOutputStream(new FileOutputStream(new File(messageService.getFilesDir(),splitMyFriends[4])));
                        objectOutputStream.writeObject(thisRoom);
                        objectOutputStream.flush();
                        objectOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
     /*   if(Main_Friend_Message_Activity.isMessageActivityRunning){
            Log.i(Values.TAG,"MESSAGE_RECEIVE_PERFORM : MESSAGE ACTIVITY RUNNING");
            for(Room room : MainActivity.rooms){
                if(room.getRoomUUID().equals(splitMyFriends[4])){
                    room.addItem(item);
                    Message message = new Message();
                    message.what = Values.CHATTING_MESSAGE_RECEIVE;
                    messageService.remoteSendMessage(message);
                    break;
                }
            }
        }else{

        }*//*
        for(Room room : MainActivity.rooms){
            if(room.getRoomUUID().equals(splitMyFriends[4])){
                room.addItem(item);
                break;
            }
        }*/


    }
}
