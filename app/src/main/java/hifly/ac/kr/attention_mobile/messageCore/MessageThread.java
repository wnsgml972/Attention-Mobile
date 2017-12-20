package hifly.ac.kr.attention_mobile.messageCore;

import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.value.Values;

class MessageThread extends Thread {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;
        private User user;
        private HashMap<String, SignalPerform> signalPerformHashMap;
        private MessageService messageService;
        private SignalKey signalKey;
        private String headerProtocol;
        private String bodyData;
        private Message msg;
        public MessageThread(MessageService messageService){
            this.messageService = messageService;
            msg = new Message();
            signalPerformHashMap = new HashMap<>();
            signalPerformHashMap.put(Values.USER_EXIST_CHECK_PROTOCOL, new UserEnrollPerform());
            signalPerformHashMap.put(Values.USER_FRIENDS_RESPONSE_PROTOCOL, new FriendsEnrollPerform());
            signalPerformHashMap.put(Values.CHATTING_MESSAGE_PROTOCOL, new MessageReceivePerform());
        }
        public void sendMessage(String message) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                dos.writeUTF(message);
            } catch (IOException e) {
                Toast.makeText(messageService.getApplicationContext(),"서버와의 연결이 끊어졌습니다.",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        public void init() {
            try {
                socket = new Socket(Values.SERVER_IP, Values.MESSAGE_SERVER_PORT);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                if(Values.myUUID != null){
                    sendMessage(Values.COME_AGAIN_PROTOCOL + Values.SPLIT_MESSAGE + Values.myUUID);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            init();
            while (true) {
                try {
                    String message = dis.readUTF();
                    Log.i(Values.TAG,"MESSAGE THREAD READ_UTF() : " + message + " 들어옴!");
                    if (message == null) {
                        Log.i(Values.TAG,"서버와 연결 끊김");
                        Toast.makeText(messageService.getApplicationContext(), "서버와의 연결이 끊겼습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }


                    signalKey = new SignalKey();
                    headerProtocol = message.split(Values.SPLIT_MESSAGE)[0];
                    bodyData = message.substring(headerProtocol.length() + Values.SPLIT_MESSAGE.length());
                    signalKey.setHeaderProtocol(headerProtocol);
                    signalKey.setBodyData(bodyData);

                    SignalPerform performClass = signalPerformHashMap.get(headerProtocol);
                    performClass.performAction(signalKey, messageService);




                    Log.i(Values.TAG, message + " -MessageThread-");
                   /* if (message.startsWith(Values.USER_EXIST_CHECK_PROTOCOL)) {

                    } else if (message.startsWith(Values.USER_FRIENDS_RESPONSE_PROTOCOL)) {

                    }
                    if (message.startsWith("callToMe")) {
                        String messages[] = message.split(Values.SPLIT_MESSAGE);//1 : name 2: ip
                      *//*  Intent intent = new Intent(getApplicationContext(), Main_Friend_Call_Receive_Activity.class);
                        intent.putExtra("name", messages[1]);
                        intent.putExtra("ip", messages[2]);
                        Log.i(Values.TAG, "name : " + messages[1] + " ip : " + messages[2]);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);*//*

                    }*/
                } catch (Exception e) {

                }
            }
        }
    }