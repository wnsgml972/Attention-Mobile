package hifly.ac.kr.attention_mobile.value;

import android.media.AudioFormat;

import hifly.ac.kr.attention_mobile.R;


/**
 * Created by CYSN on 2017-11-22.
 */

// 정수 interface
public class Values {
    public static final String TAG = "KKKK";
    public static final String SPLIT_MESSAGE = "@SEGMENT@";

    /*SERVICE VALUES*/
    public static final int SERVICE_HANDLER_ENROLL = -1994;
    public static final int USER_ENROLL = 10000;
    public static final int SERVICE_MESSAGE = 10;

    /*SERVER CONNECTION*/
    public static final String SERVER_IP = "223.194.156.150";
    public static final int FILE_SERVER_PORT = 10035;
    public static final int MESSAGE_SERVER_PORT = 10036;

    /*SERVER MESSAGE PROTOCOL*/
    public static final String USER_ENROLL_PROTOCOL = "USER_ENROLL_PROTOCOL";
    public static final String USER_EXIST_CHECK_PROTOCOL = "USER_EXIST_CHECK_PROTOCOL";
    public static final String USER_ENROLL_SUCCESS_PROTOCOL = "USER_ENROLL_SUCCESS_PROTOCOL";
    public static final String USER_ENROLL_FAIL_PROTOCOL = "USER_ENROLL_FAIL_PROTOCOL";
    public static final String USER_FRIENDS_REQUEST_PROTOCOL = "USER_FRIENDS_REQUEST_PROTOCOL";
    public static final String USER_FRIENDS_RESPONSE_PROTOCOL = "USER_FRIENDS_RESPONSE_PROTOCOL";
    public static final String ROOM_MESSAGE_PROTOCOL = "ROOM_MESSAGE_PROTOCOL";
    public static final String ROOM_IN_FIRST_PROTOCOL = "ROOM_IN_FIRST_PROTOCOL";
    public static final String ROOM_OUT_PROTOCOL = "ROOM_OUT_PROTOCOL";
    public static final String BROADCAST_PROTOCOL = "BROADCAST_PROTOCOL";
    public static final String CALLING_PROTOCOL = "CALLING_PROTOCOL";
    public static final String COME_AGAIN_PROTOCOL = "COME_AGAIN_PROTOCOL";
    public static final String CHATTING_MESSAGE_PROTOCOL = "CHATTING_MESSAGE_PROTOCOL";

    //준희추가 프로토콜
    public static final String PROFILE_INSERT_PROTOCOL = "PROFILE_INSERT_PROTOCOL";
    public static final String PROFILE_GET_PROTOCOL = "PROFILE_GET_PROTOCOL";
    public static final String ROOM_IN_PROTOCOL = "ROOM_IN_PROTOCOL";
    public static final String USER_REMOVE_PROTOCOL = "USER_REMOVE_PROTOCOL";
    public static final String ROOM_REMOVE_PROTOCOL = "ROOM_REMOVE_PROTOCOL";
    public static final String ROOM_FAIL_PROTOCOL = "ROOM_FAIL_PROTOCOL";
    public static final String ROOM_FILE_PROTOCOL = "ROOM_FILE_PROTOCOL";
    public static final String PROFILE_INSERT_PERFORM = "PROFILE_INSERT_PERFORM";
    public static final String PROFILE_GO = "PROFILE_GO";
    public static final int PROFILE_INSERT = 40;
    public static final int CREATE_ROOM = 43;
    public static final int WRITE_OBJECT = 41;




    public static final int USER_ENROLL_CHECK = 1000;
    public static final int USER_FRIENDS_REQUEST = 1001;
    public static final int USER_FRIENDS_RESPONSE = 1002;

    public static final int MESSAGE_NOTIFICATION = 1502;

    public static final String userInfo = "userInfo";
    public static final String userUUID = "uuid";
    public static final String userName = "name";
    public static final String userTel = "tel";
    public static final String userProfile = "userProfile";

    public static final String USER = "user";
    public static final String VOICE = "voice";
    public static final String VOICE_ROOM = "voice_room";
    public static final String VOICE_ROOM_FIRST = "voice_";
    public static final String VOICE_CALLER = "voice_caller";
    public static final String VOICE_USER_IP = "voice_user_ip";
    public static final String VOICE_USER_PORT = "voice_user_port";

    public static final String VOICE_CALL_STATE = "call_state";


    public static final int CALLER_SEND_PORT = 10035;
    public static int SERVER_UDP_PORT = 10036;
    public static final int RECORDING_RATE = 22050;
    //private final int RECORDING_RATE = 8000;
    public static final int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    public static final int AUDIO_OUT_CHANNEL = AudioFormat.CHANNEL_OUT_MONO;
    //private final int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_STEREO;
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    //private final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_8BIT;

    public static final int USER_VIEW = 0;
    public static final int NORMAL_VIEW = 1;

    public static final int REFUSE_CALL = 1;
    public static final String REFUSE = "refuse";
    public static final int RECEIVE_CALL = 2;
    public static final String RECEIVE = "receive";
    public static final int END_CALL = 3;
    public static final int START_CALL = 4;
    public static final String END = "end";
    public static final String CALLING = "calling";
    public static final int CHAT_ROOM = 5;

    public static String myUUID;
    public static String myName;
    public static String myTel;
    public static String SetProfilePhoto = "profile_photo";

    public static String imageIconsName[] = {"(웃음)", "(키스)", "(쿠우)", "(흑흑)", "(우웅)", "(우이씨)"};
    public static int imageIconsDrawable[] = {R.drawable.main_friend_message_emoticon_happy, R.drawable.main_friend_message_emoticon_kiss, R.drawable.main_friend_message_emoticon_sleepy, R.drawable.main_friend_message_emoticon_sad, R.drawable.main_friend_message_emoticon_sick, R.drawable.main_friend_message_emoticon_angry};

    public static final int CHATTING_MESSAGE_RECEIVE = 77;
    public static final int CHATTING_MESSAGE_SEND= 78;
}


