package hifly.ac.kr.attention_mobile.main;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.Vector;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter.ChatActivity_RecyclerView_Adapter;
import hifly.ac.kr.attention_mobile.adapter_item.ChatActivity_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.data.Room;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.dialog.Main_Friend_Message_Dialog;
import hifly.ac.kr.attention_mobile.messageCore.MessageService;
import hifly.ac.kr.attention_mobile.value.Values;
import hifly.ac.kr.attention_mobile.voiceCore.Call_Receive_Thread;
import hifly.ac.kr.attention_mobile.voiceCore.Call_Thread;

public class Main_Friend_Message_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private Button messageSendbtn;
    private RecyclerView chatActivity_recyclerView;
    private ChatActivity_RecyclerView_Adapter chatActivity_recyclerView_adapter;
    private Vector<ChatActivity_RecyclerView_Item> chatActivity_recyclerView_items;

    private String senderName = Values.myName;

    private HashMap<Integer, String> imoticonContents;
    private int imageIcons[] = {R.id.main_friend_message_emoticon1, R.id.main_friend_message_emoticon2, R.id.main_friend_message_emoticon3, R.id.main_friend_message_emoticon4, R.id.main_friend_message_emoticon5, R.id.main_friend_message_emoticon6};
    private Messenger messenger;
    private SimpleDateFormat simpleDataFormat;
    private Date date;
    private String currentTime;
    private String P2PChatUUID;
    private Room room;
    private ImageView imageView;
    private boolean isCalling = false;
    Call_Receive_Thread call_receive_thread;
    Call_Thread call_thread;
    private ServiceConnection connection = new ServiceConnection() {            //@@
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            messenger = new Messenger(service);
            if (messenger != null) {
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
                case Values.CHATTING_MESSAGE_RECEIVE:

                    ChatActivity_RecyclerView_Item item = (ChatActivity_RecyclerView_Item)msg.obj;

                        User user = MainActivity.users.get(item.getSender_uuid());
                        if(user != null){
                            item.setSender_name(user.getName());
                        }


                    Log.i(Values.TAG,"MESSAGE ITEM SIZE : " + room.getItems().size());
                    //chatActivity_recyclerView_items.add(item);
                    room.addItem(item);
                    Log.i(Values.TAG,"MESSAGE ITEM SIZE : " + room.getItems().size());
                    chatActivity_recyclerView_adapter.notifyItemInserted(chatActivity_recyclerView_adapter.getItemCount()-1);  // @@
                    chatActivity_recyclerView.scrollToPosition(chatActivity_recyclerView_adapter.getItemCount()-1);
                    Log.i(Values.TAG,"MESSAGE ITEM SIZE : " + room.getItems().size());
                    break;
            }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_friend_message);

        initEmoticon();
        init();

        for (int imageIcon : imageIcons) {
            ((ImageView) findViewById(imageIcon)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    updateCurrentTime();
                    ChatActivity_RecyclerView_Item item = new ChatActivity_RecyclerView_Item(senderName, imoticonContents.get(view.getId()), currentTime, 1, Values.myUUID);
                    item.setRoomUUID(P2PChatUUID);
                    Message message = new Message();
                    message.what = Values.CHATTING_MESSAGE_SEND;
                    message.obj = item;
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        Intent intent = new Intent(getApplicationContext(), MessageService.class);
        bindService(intent,connection,Context.BIND_AUTO_CREATE);

    }
    public void initEmoticon(){
        imoticonContents = new HashMap<Integer, String>();
        for(int i=0; i<imageIcons.length; i++){
            imoticonContents.put(imageIcons[i],Values.imageIconsName[i]);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        room.setRoomVisible(false);
        unbindService(connection);

        try {
            ObjectOutputStream objectOutputStream  = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),P2PChatUUID)));
            objectOutputStream.writeObject(room);
            objectOutputStream.flush();
            objectOutputStream.close();
            Toast.makeText(getApplicationContext(), "object write success!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.messageSendbtn:
                LinearLayout chat_activity_RecyclerView0 = (LinearLayout)findViewById(R.id.main_chat_activity_RecyclerView);
                LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams) chat_activity_RecyclerView0.getLayoutParams();

                LinearLayout main_friend_message_new_place0 = (LinearLayout)findViewById(R.id.main_friend_message_new_place);
                main_friend_message_new_place0.setVisibility(View.GONE);

                LinearLayout main_friend_message_new_place_emoticon0 = (LinearLayout)findViewById(R.id.main_friend_message_new_place_emoticon);
                main_friend_message_new_place_emoticon0.setVisibility(View.GONE);

                // 7, 9
                params0.weight = 9;
                chat_activity_RecyclerView0.setLayoutParams(params0);
                if(editText.getText().toString().equals(""))
                    return;
                updateCurrentTime();
                ChatActivity_RecyclerView_Item item = new ChatActivity_RecyclerView_Item(senderName, editText.getText().toString(), currentTime, 1, Values.myUUID);
                item.setRoomUUID(P2PChatUUID);
                editText.setText("");

                Message message = new Message();
                message.what = Values.CHATTING_MESSAGE_SEND;
                message.obj = item;

                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                /*chatActivity_recyclerView_adapter.notifyItemInserted(chatActivity_recyclerView_items.size() - 1);
                chatActivity_recyclerView.getLayoutManager().scrollToPosition(chatActivity_recyclerView.getAdapter().getItemCount() - 1);  //@@*/
                break;

            case R.id.main_friend_message_plus_btn:
                LinearLayout chat_activity_RecyclerView = (LinearLayout)findViewById(R.id.main_chat_activity_RecyclerView);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) chat_activity_RecyclerView.getLayoutParams();

                LinearLayout main_friend_message_new_place = (LinearLayout)findViewById(R.id.main_friend_message_new_place);
                main_friend_message_new_place.setVisibility(View.VISIBLE);

                LinearLayout main_friend_message_new_place_emoticon = (LinearLayout)findViewById(R.id.main_friend_message_new_place_emoticon);
                main_friend_message_new_place_emoticon.setVisibility(View.GONE);

                // 7, 9
                params.weight = 7;
                chat_activity_RecyclerView.setLayoutParams(params);
                break;
            case R.id.myItemKing:
            case R.id.yourItemKing:
                LinearLayout chat_activity_RecyclerView1 = (LinearLayout)findViewById(R.id.main_chat_activity_RecyclerView);
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) chat_activity_RecyclerView1.getLayoutParams();

                LinearLayout main_friend_message_new_place1 = (LinearLayout)findViewById(R.id.main_friend_message_new_place);
                main_friend_message_new_place1.setVisibility(View.GONE);

                LinearLayout main_friend_message_new_place_emoticon1 = (LinearLayout)findViewById(R.id.main_friend_message_new_place_emoticon);
                main_friend_message_new_place_emoticon1.setVisibility(View.GONE);

                // 7, 9
                params1.weight = 9;
                chat_activity_RecyclerView1.setLayoutParams(params1);
                break;
            case R.id.main_friend_message_emoticon:
                LinearLayout chat_activity_RecyclerView2 = (LinearLayout)findViewById(R.id.main_chat_activity_RecyclerView);
                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) chat_activity_RecyclerView2.getLayoutParams();

                LinearLayout main_friend_message_new_place2 = (LinearLayout)findViewById(R.id.main_friend_message_new_place);
                main_friend_message_new_place2.setVisibility(View.GONE);

                LinearLayout main_friend_message_new_place_emoticon2 = (LinearLayout)findViewById(R.id.main_friend_message_new_place_emoticon);
                main_friend_message_new_place_emoticon2.setVisibility(View.VISIBLE);
                // 7, 9
                params2.weight = 7;
                chat_activity_RecyclerView2.setLayoutParams(params2);
                break;
            case R.id.main_friend_message_invite_friend:
                Intent intent = new Intent(view.getContext(), Main_Friend_Message_Dialog.class);
                startActivityForResult(intent,Values.CREATE_ROOM);
                break;
            case R.id.main_friend_message_voice_chat:
                if(!isCalling) {
                    isCalling=true;
                String ip = "223.194.156.145";//용석ip 준희한테 깔거
                int port = 10075;
                Call_Receive_Thread call_receive_thread = new Call_Receive_Thread(ip,port);//10075
                Call_Thread call_thread = new Call_Thread(ip,port+1);//10076
                    /*String ip = "223.194.153.149";
                    int port = 10076;
                    call_receive_thread = new Call_Receive_Thread(ip, port);//10076
                    call_thread = new Call_Thread(ip, port - 1);//10075*/
                    call_receive_thread.start();
                    call_thread.start();
                    Toast.makeText(getApplicationContext(),"Voice Chat Start!",Toast.LENGTH_SHORT).show();
                /*Intent mintent = new Intent(getApplicationContext(), Main_Friend_Call_Activity.class);
                mintent.putExtra("object",(User)getIntent().getSerializableExtra("object"));
                startActivity(mintent);*/
                }
                else{
                    call_thread.isrunning = false;
                    call_receive_thread.isrunning = false;
                    isCalling=false;
                    Toast.makeText(getApplicationContext(),"Voice Chat End!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == Values.CREATE_ROOM){
                ArrayList<String> userUUIDs = (ArrayList<String>)data.getSerializableExtra("userUUIDs");
                boolean isRoomExist = false;
                String chatRoomUUID = data.getStringExtra("roomUUID");
                for(Room room : MainActivity.rooms){
                    if(room.getRoomUUID().equals(chatRoomUUID)){
                        for (String userUUID : userUUIDs)
                            room.addUser(userUUID);
                        isRoomExist = true;
                    }
                }
                if(!isRoomExist) {
                    Room makeRoom = new Room(chatRoomUUID);
                    for (String userUUID : userUUIDs)
                        makeRoom.addUser(userUUID);
                    MainActivity.rooms.add(makeRoom);
                }
                finish();
            }
        }
    }

    public void updateCurrentTime(){
        long now = System.currentTimeMillis();
        date.setTime(now);
        currentTime = simpleDataFormat.format(date);
    }
    @Override
    public void onBackPressed() {
        LinearLayout chat_activity_RecyclerView = (LinearLayout)findViewById(R.id.main_chat_activity_RecyclerView);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) chat_activity_RecyclerView.getLayoutParams();
        LinearLayout main_friend_message_new_place = (LinearLayout)findViewById(R.id.main_friend_message_new_place);
        LinearLayout main_friend_message_new_place_emoticon = (LinearLayout)findViewById(R.id.main_friend_message_new_place_emoticon);

        if(main_friend_message_new_place.getVisibility() == View.VISIBLE){
            main_friend_message_new_place.setVisibility(View.GONE);
            params.weight = 9;
            chat_activity_RecyclerView.setLayoutParams(params);
        }
        else if(main_friend_message_new_place_emoticon.getVisibility() == View.VISIBLE){
            main_friend_message_new_place_emoticon.setVisibility(View.GONE);
            params.weight = 9;
            chat_activity_RecyclerView.setLayoutParams(params);
        }
        else {
            super.onBackPressed();
        }
    }

    private void init() {
        //User user = (User)getIntent().getSerializableExtra("object");
        //ArrayList<String> userUUIDs = (ArrayList<String>)getIntent().getSerializableExtra("object");
        //P2PChatUUID = user.getP2PChatUUID();
        //P2PChatUUID = getIntent().getStringExtra("room");
        /*if(userUUIDs.size() ==1){
            P2PChatUUID = MainActivity.users.get(userUUIDs.get(0)).getP2PChatUUID();
        }*/
        ArrayList<String> userUUIDs = null;
        P2PChatUUID = getIntent().getStringExtra("room");
        boolean isRoomExist = false;
        for(Room mroom : MainActivity.rooms) {
            if (mroom.getRoomUUID().equals(P2PChatUUID)){
                room = mroom;
                userUUIDs = room.getUserUUIDs();
                isRoomExist = true;
                break;
            }
        }

        date = new Date();
        simpleDataFormat = new SimpleDateFormat("HH:mm",Locale.KOREA);
        chatActivity_recyclerView = (RecyclerView) findViewById(R.id.chat_activity_RecyclerView);
        editText = (EditText) findViewById(R.id.editText);
        messageSendbtn = (Button) findViewById(R.id.messageSendbtn);

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File(getFilesDir(),P2PChatUUID)));
            room= (Room) objectInputStream.readObject();

            if (room == null) {

                if(userUUIDs.size()==1) {
                    room = new Room(P2PChatUUID);
                    Toast.makeText(getApplicationContext(), "new p2p room!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    room = new Room(P2PChatUUID);
                    Toast.makeText(getApplicationContext(), "new multiple room!!", Toast.LENGTH_SHORT).show();
                }
                for(String uuid : userUUIDs)
                    room.addUser(uuid);
                MainActivity.rooms.add(room);


            } else {
                Toast.makeText(getApplicationContext(), "read room success!!", Toast.LENGTH_SHORT).show();
                Log.i(Values.TAG,"ROOM UUID : " + room.getRoomUUID() + "  THISROOM.GETUSERUUID.GET(0) : " + room.getUserUUIDs().get(0));


                for(int i=0; i<MainActivity.rooms.size(); i++){
                    Room mRoom = MainActivity.rooms.get(i);
                    if(mRoom.getRoomUUID().equals(P2PChatUUID)){
                        MainActivity.rooms.set(i, room);
                        break;
                    }
                }
                if(!isRoomExist){
                    MainActivity.rooms.add(room);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
           /* if(userUUIDs.size()==1) {
                room = new Room(P2PChatUUID);
                Toast.makeText(getApplicationContext(), "new p2p room!!", Toast.LENGTH_SHORT).show();
            }
            else{
                room = new Room(P2PChatUUID);
                Toast.makeText(getApplicationContext(), "new multiple room!!", Toast.LENGTH_SHORT).show();
            }
            for(String uuid : userUUIDs)
                room.addUser(uuid);
            MainActivity.rooms.add(room);
            Toast.makeText(getApplicationContext(), "room Error!!", Toast.LENGTH_SHORT).show();*/
        }

        room.setRoomVisible(true);
        chatActivity_recyclerView_items = room.getItems();

        chatActivity_recyclerView_adapter = new ChatActivity_RecyclerView_Adapter(getApplicationContext(), chatActivity_recyclerView_items);
        chatActivity_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        chatActivity_recyclerView.setAdapter(chatActivity_recyclerView_adapter);
        chatActivity_recyclerView_adapter.notifyDataSetChanged();
        //스크롤
        chatActivity_recyclerView.scrollToPosition(chatActivity_recyclerView_adapter.getItemCount() - 1);

        messageSendbtn.setOnClickListener(this);

    }
}
