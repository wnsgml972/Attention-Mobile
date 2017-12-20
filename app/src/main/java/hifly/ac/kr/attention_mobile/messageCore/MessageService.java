package hifly.ac.kr.attention_mobile.messageCore;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter_item.ChatActivity_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.adapter_item.Main_Chat_Room_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.data.Call;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.main.Main_Friend_Message_Activity;
import hifly.ac.kr.attention_mobile.value.Values;
import hifly.ac.kr.attention_mobile.voiceCore.Call_Receive_Thread;
import hifly.ac.kr.attention_mobile.voiceCore.Call_Thread;

public class MessageService extends Service {

    private MessageThread messageThread;


    private Messenger mRemote;
    private boolean isCalling = false;
    private static PowerManager.WakeLock sCpuWakeLock;
    private String myUUID;
    private String myName;
    private MediaPlayer mp;

    //private Main_Chat_Room_RecyclerView_Adapter main_chat_room_recyclerView_adapter;
    private List<Main_Chat_Room_RecyclerView_Item> main_chat_room_recyclerView_items;
    //private ChatRoomWrapper chatRoomWrapper;

    private Call_Thread call_thread;
    private Call_Receive_Thread call_receive_thread;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StringBuilder shared_chat_room_name;
    private SoundPool soundPool;
    private int soundID;

    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(new RemoteHandler()).getBinder();
    }

    // Send message to activity
    public void remoteSendMessage(Message message) {
        if (mRemote != null) {

            try {
                mRemote.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void screenOn() {

        if (sCpuWakeLock != null) {
            return;
        }
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        sCpuWakeLock = pm.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "hi");

        sCpuWakeLock.acquire();

        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }

    }

    public void enroll_User(User user) {
        messageThread.sendMessage(Values.USER_ENROLL_PROTOCOL + Values.SPLIT_MESSAGE + user.getName() + Values.SPLIT_MESSAGE + user.getUuid() + Values.SPLIT_MESSAGE + user.getStateMessage() + Values.SPLIT_MESSAGE + user.getTel());
    }

    public void request_Friends(ArrayList myFriends) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Values.USER_FRIENDS_REQUEST_PROTOCOL).append(Values.SPLIT_MESSAGE);
        for (int i = 0; i < myFriends.size(); i++) {
            stringBuilder.append(((User) (myFriends).get(i)).getTel()).append(Values.SPLIT_MESSAGE);
        }
        messageThread.sendMessage(stringBuilder.toString());
    }
    public void send_Message_Item(ChatActivity_RecyclerView_Item item){
        StringBuilder builder = new StringBuilder();
        builder.append(Values.CHATTING_MESSAGE_PROTOCOL).append(Values.SPLIT_MESSAGE).
                append(item.getSender_name()).append(Values.SPLIT_MESSAGE).
                append(item.getChat_content()).append(Values.SPLIT_MESSAGE).
                append(item.getTime()).append(Values.SPLIT_MESSAGE).
                append(item.getSender_uuid()).append(Values.SPLIT_MESSAGE).
                append(item.getRoomUUID()).append(Values.SPLIT_MESSAGE);


        messageThread.sendMessage(builder.toString());
    }
    public void myNotify(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentText("메세지가 도착했습니다.");
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, Main_Friend_Message_Activity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(Main_Friend_Message_Activity.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pIntent = taskStackBuilder.getPendingIntent(Values.MESSAGE_NOTIFICATION,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(Values.MESSAGE_NOTIFICATION, builder.build());
        }

    }
    // Service handler
    private class RemoteHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String message = null;

            switch (msg.what) {
                case Values.SERVICE_HANDLER_ENROLL:
                    // Register activity hander
                    mRemote = (Messenger) msg.obj;
                    break;
                case Values.USER_ENROLL:
                    enroll_User((User) msg.obj);
                    break;
                case Values.USER_FRIENDS_REQUEST:
                    request_Friends((ArrayList) msg.obj);
                    break;
                case Values.CHATTING_MESSAGE_SEND:
                    send_Message_Item((ChatActivity_RecyclerView_Item) msg.obj);
                    break;
                case Values.START_CALL:
                    Log.i(Values.TAG, "전화 들옴~~~~~~~~~~~~~~~~~~");
                    try {
                        mp.seekTo(0);
                        mp.start();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    break;
                case Values.REFUSE_CALL:
                    Log.i(Values.TAG, message + Values.SPLIT_MESSAGE);
                    /*databaseReference.child(Values.USER).child(myUUID).child(Values.VOICE).child(Values.VOICE).setValue("null");
                    databaseReference.child(Values.USER).child(myUUID).child(Values.VOICE).child(Values.VOICE_CALLER).setValue("null");
                    databaseReference.child(Values.VOICE).child(voiceRoomName).child(myUUID).child(Values.VOICE_CALL_STATE).setValue(Values.REFUSE);*/
                    message = (String) msg.obj;
                    isCalling = false;
                    break;
                case Values.RECEIVE_CALL:
                    message = (String) msg.obj;//ip
                    String messages[] = message.split(Values.SPLIT_MESSAGE);
                    String userIP = messages[0];
                    final int userPort = Integer.parseInt(messages[1]);
                    final String userState = messages[2];
                    if (!isCalling) {
                        isCalling = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Socket socket = new Socket("www.google.com", 80);
                                    String ipAddress = socket.getLocalAddress().toString();
                                    ipAddress = ipAddress.substring(1);
                                    Call call = null;
                                    if (userState.equals(Values.VOICE_CALLER)) {
                                        call = new Call(myUUID, Values.RECEIVE, ipAddress, userPort);
                                    } else {
                                        call = new Call(myUUID, Values.RECEIVE, ipAddress, userPort + 1);
                                    }
                                    //databaseReference.child(Values.VOICE).child(voiceRoomName).child(myUUID).setValue(call);
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }
                            }
                        }).start();
                        mp.release();
                        screenOn();
                        if (userState.equals(Values.VOICE_CALLER)) {
                            call_thread = new Call_Thread(userIP, userPort - 1);
                            call_receive_thread = new Call_Receive_Thread(userIP, userPort);
                            call_thread.start();
                            call_receive_thread.start();
                        } else {
                            call_thread = new Call_Thread(userIP, userPort + 1);
                            call_receive_thread = new Call_Receive_Thread(userIP, userPort);
                            call_thread.start();
                            call_receive_thread.start();
                        }

                    }
                    break;
                case Values.END_CALL:
                    Log.i(Values.TAG, message + " ");
                    isCalling = false;
                  /*  if (voiceRoomName != null && !voiceRoomName.equals("null")) {
                        *//*databaseReference.child(Values.USER).child(myUUID).child(Values.VOICE).removeValue();
                        databaseReference.child(Values.VOICE).child(voiceRoomName).child(myUUID).removeValue();*//*
                    }*/
                    if (call_thread != null) {
                        call_thread.interrupt();
                    }
                    if (call_receive_thread != null) {
                        call_receive_thread.interrupt();
                    }
                    break;
                case Values.CHAT_ROOM:       // 채팅창 방 업데이트
                   /* chatRoomWrapper = (ChatRoomWrapper) msg.obj;
                    main_chat_room_recyclerView_adapter = chatRoomWrapper.getAdapter();
                    main_chat_room_recyclerView_items = chatRoomWrapper.getItems();

                    *//* shared init *//*
                    sharedPreferences = getSharedPreferences(Values.shared_name, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    shared_chat_room_name = new StringBuilder(sharedPreferences.getString(Values.shared_chat_room_name,""));*/
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        Log.i(Values.TAG, "Service onCreate!");
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(Values.TAG, "onStartCommand!");
        Values.myUUID = getSharedPreferences(Values.userInfo, Context.MODE_PRIVATE).getString(Values.userUUID, null);
        Values.myName = getSharedPreferences(Values.userInfo, Context.MODE_PRIVATE).getString(Values.userName, "null");
        Values.myTel = getSharedPreferences(Values.userInfo, Context.MODE_PRIVATE).getString(Values.userTel, "01037125066");
        if (messageThread == null) {
            messageThread = new MessageThread(this);
            messageThread.start();
            Log.i(Values.TAG, "ReceiveThread Start!!");
        }
        Log.i(Values.TAG, "MessageService Start!!");
        mp = MediaPlayer.create(this, R.raw.ioi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(8).build();
        } else {
            soundPool = new SoundPool(8, AudioManager.STREAM_NOTIFICATION, 0);
        }
        soundID = soundPool.load(this, R.raw.click, 1);
        startForeground(1, new Notification());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messageThread != null)
            messageThread.interrupt();
    }

    private class SendfeedbackJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            return "some message";
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
        }
    }


}
