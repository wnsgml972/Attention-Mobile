package hifly.ac.kr.attention_mobile.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.main.MainActivity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User implements Serializable {
    private String uuid;//유저간 고유 UUID
    private String name;
    private String tel;
    private String stateMessage;
    private String P2PChatUUID;//유저간 고유 채팅 룸 UUID
    private byte[] profileData;

    public User(String uuid, String name, String tel, String stateMessage) {
        this.uuid = uuid;
        this.name = name;
        this.tel = tel;
        this.stateMessage = stateMessage;
    }

    public User(String uuid, String name, String tel, String stateMessage, String p2PChatUUID) {
        this.uuid = uuid;
        this.name = name;
        this.tel = tel;
        this.stateMessage = stateMessage;
        P2PChatUUID = p2PChatUUID;
    }

    public User(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public User(String name, String stateMessage, String myUUID) {
        this.name = name;
        this.stateMessage = stateMessage;
        this.uuid = myUUID;
    }
}
