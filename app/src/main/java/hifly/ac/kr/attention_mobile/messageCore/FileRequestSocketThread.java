package hifly.ac.kr.attention_mobile.messageCore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.logging.Handler;

import hifly.ac.kr.attention_mobile.main.MainActivity;
import hifly.ac.kr.attention_mobile.value.Values;

/**
 * Created by CYSN on 2017-12-20.
 */

public class FileRequestSocketThread extends Thread {
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private  MainActivity.MyHandler myHandler;
    public FileRequestSocketThread(MainActivity.MyHandler mHandler) {
        this.myHandler = mHandler;
    }

    public void initSocket() {
        try {
            socket = new Socket(Values.SERVER_IP, Values.FILE_SERVER_PORT);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            Log.i(Values.TAG, "FileRequest 생성!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeSocket(){
        if(socket != null &&  socket.isConnected()) {
            try {
                dis.close();
                dos.close();
                socket.close();
                dos = null;
                dis = null;
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void run() {

        try {
            Log.i(Values.TAG, "FileRequest 시작!");
            for (String userUUID : MainActivity.users.keySet()) {
                initSocket();
                dos.writeUTF(Values.PROFILE_GET_PROTOCOL + Values.SPLIT_MESSAGE + userUUID);
                String request = dis.readUTF();
                Log.i(Values.TAG, "FileRequest " + request + "  들어옴!");
                String split[] = request.split(Values.SPLIT_MESSAGE);
                String protocol = split[0];
                int fileSize = Integer.parseInt(split[1]);
                String uuid = split[2];
                if(fileSize == 0 || uuid.equals("null"))
                    continue;
                if(protocol.equals(Values.PROFILE_GET_PROTOCOL)) {
                    byte[] image = new byte[fileSize];
                    dis.readFully(image, 0, fileSize);
                    MainActivity.users.get(uuid).setProfileData(image);
                    Log.i(Values.TAG, "FileRequest " + userUUID + " " + image.length);
                }
                closeSocket();
            }
            MainActivity.isDataChanged = true;
            Log.i(Values.TAG, "FileRequest 성공!");
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("message",Values.WRITE_OBJECT);
            message.setData(bundle);
            myHandler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
