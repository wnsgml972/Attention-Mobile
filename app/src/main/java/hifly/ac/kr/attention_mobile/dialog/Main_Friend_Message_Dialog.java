package hifly.ac.kr.attention_mobile.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter.Main_Friend_Message_Dialog_Recycler_Adapter;
import hifly.ac.kr.attention_mobile.adapter_item.Main_Friend_Message_Dialog_Recycler_Item;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.main.MainActivity;
import hifly.ac.kr.attention_mobile.messageCore.FileRequestSocketThread;
import hifly.ac.kr.attention_mobile.messageCore.MessageService;
import hifly.ac.kr.attention_mobile.value.Values;


/**
 * Created by hscom-018 on 2017-12-12.
 */

public class Main_Friend_Message_Dialog extends AppCompatActivity {

    private RecyclerView main_Friend_Message_RecyclerView_Dialog;
    private Main_Friend_Message_Dialog_Recycler_Adapter main_friend_message_dialog_recycler_adapter;
    private List<Main_Friend_Message_Dialog_Recycler_Item> main_friend_message_dialog_recycler_items;

    private ImageButton imageButton;
    private Messenger messenger;
    private ServiceConnection connection = new ServiceConnection() {            //@@
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            messenger = new Messenger(service);
            if (messenger != null) {
                // mainFragment2.setMessenger(messenger);
                Message message = new Message();
                message.what = Values.SERVICE_HANDLER_ENROLL;
                message.obj = new Messenger(new RemoteHandler());
                try {
                    messenger.send(message);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messenger = null;
        }
    };

    private class RemoteHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Values.USER_FRIENDS_RESPONSE:

                    break;
            }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);   //타이틀바 없애는코드
        setContentView(R.layout.main_friend_message_recycler_dialog);
        setFinishOnTouchOutside(false);         //다이얼로그 테마로 다이얼로그를 띄울때 다른곳을 터치할시에 꺼지는것을 방지
        main_Friend_Message_RecyclerView_Dialog = (RecyclerView) findViewById(R.id.main_friend_message_recycler);
        imageButton = (ImageButton) findViewById(R.id.main_friend_message_dialog_exit_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Main_Friend_Message_Dialog_Recycler_Item> items = main_friend_message_dialog_recycler_adapter.getSelectedItem();
                ArrayList<String> userUUIDs = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                String chatRoomUUID = UUID.randomUUID().toString().replace("-","");
                builder.append(Values.ROOM_IN_PROTOCOL).append(Values.SPLIT_MESSAGE).
                        append(chatRoomUUID).append(Values.SPLIT_MESSAGE).
                        append(items.size()+1).append(Values.SPLIT_MESSAGE).
                        append(Values.myUUID).append(Values.SPLIT_MESSAGE);
                for(Main_Friend_Message_Dialog_Recycler_Item item : items){
                    userUUIDs.add(item.getUuid());
                    builder.append(item.getUuid()).append(Values.SPLIT_MESSAGE);
                }

                Message message = new Message();
                message.what = Values.CREATE_ROOM;
                message.obj = builder.toString();

                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();

                intent.putExtra("userUUIDs", userUUIDs);
                intent.putExtra("roomUUID", chatRoomUUID);

                setResult(RESULT_OK, intent);

                finish();
            }
        });

        setRecyclerView();
        Intent intent = new Intent(getApplicationContext(), MessageService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

    private void setRecyclerView() {
        main_friend_message_dialog_recycler_items = new ArrayList<Main_Friend_Message_Dialog_Recycler_Item>();
        int i=0;
        for(String userUUID : MainActivity.users.keySet()) {
            if(Values.myUUID.equals(MainActivity.users.get(userUUID).getUuid()))
                continue;
            i++;
            main_friend_message_dialog_recycler_items.add(new Main_Friend_Message_Dialog_Recycler_Item(Integer.toString(i), MainActivity.users.get(userUUID).getName(),
                    userUUID,false));
        }

        main_friend_message_dialog_recycler_adapter = new Main_Friend_Message_Dialog_Recycler_Adapter(getApplicationContext(), main_friend_message_dialog_recycler_items);
        main_Friend_Message_RecyclerView_Dialog.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        main_Friend_Message_RecyclerView_Dialog.setAdapter(main_friend_message_dialog_recycler_adapter);
    }
}













