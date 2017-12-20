package hifly.ac.kr.attention_mobile.main;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.messageCore.MessageService;
import hifly.ac.kr.attention_mobile.value.Values;

/**
 * Created by CYSN on 2017-11-30.
 */

public class SettingActivity extends AppCompatActivity {
    private EditText editText;
    private Intent serviceIntent;
    private Messenger messenger;
    private boolean isEnrollSuccess = false;
    private boolean isEnrollBtnClicked = false;
    private SharedPreferences sharedPreferences;
    private String uuid, myNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.WAKE_LOCK, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }
        editText = (EditText) findViewById(R.id.setting_activity_editText);
        serviceIntent = new Intent(this, MessageService.class);
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {            //@@
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            messenger = new Messenger(service);
            Message message = new Message();
            message.what = Values.SERVICE_HANDLER_ENROLL;
            message.obj = new Messenger(new RemoteHandler());
            try {
                messenger.send(message);
            } catch (Exception e) {
                e.getStackTrace();
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
            Log.i(Values.TAG, msg.what + " @@@@");
            Log.i(Values.TAG, (String)(msg.obj) + " @@@@");
            switch (msg.what) {
                case Values.SERVICE_MESSAGE:
                    if (((String) msg.obj).equals(Values.USER_ENROLL_SUCCESS_PROTOCOL)) {
                        isEnrollSuccess = true;
                        if (isEnrollBtnClicked) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Values.userName, editText.getText().toString());
                            editor.putString(Values.userUUID, uuid);
                            editor.putString(Values.userTel, myNumber);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            unbindService(connection);
                            Toast.makeText(getApplicationContext(), "Enroll Success!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }
                    break;
            }
        }
    }

    public void setUUID() {
        sharedPreferences = getSharedPreferences(Values.userInfo, Context.MODE_PRIVATE);
        uuid = sharedPreferences.getString(Values.userUUID, null);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString().replace("-", "");


            myNumber = null;
            TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                myNumber = mgr.getLine1Number();
                myNumber = myNumber.replace("+82", "0").replace("-", "");
            } catch (Exception e) {
                e.getStackTrace();
            }


            try {
                if(editText.getText().toString().length() > 1) {
                    Message message = new Message();
                    message.what = Values.USER_ENROLL;
                    User user = new User(uuid, editText.getText().toString(), myNumber, "만나서 반갑습니다. " + editText.getText().toString() + "입니다.");
                    message.obj = user;
                    messenger.send(message);
                }
                else{
                    isEnrollBtnClicked = false;
                    Toast.makeText(getApplicationContext(),"이름이 너무 짧습니다.",Toast.LENGTH_SHORT).show();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveMainActivity(View v) {
        isEnrollBtnClicked = true;
        setUUID();

    }
}
