package hifly.ac.kr.attention_mobile.data;

import java.io.Serializable;
import java.util.ArrayList;

import hifly.ac.kr.attention_mobile.R;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User implements Serializable {

    private int icon;
    private String uuid;//유저간 고유 UUID
    private String name;
    private String tel;
    private String stateMessage;
    private String P2PChatUUID;//유저간 고유 채팅 룸 UUID
    public User(int icon, String name, String stateMessage) {
        if (icon == 0)
            this.icon = R.drawable.main_friend_basic_icon;
        else
            this.icon = icon;
        this.name = name;
        this.stateMessage = stateMessage;
    }
    public User(int icon, String name, String stateMessage, String uuid) {
        if (icon == 0)
            this.icon = R.drawable.main_friend_basic_icon;
        else
            this.icon = icon;
        this.uuid = uuid;
        this.name = name;
        this.stateMessage = stateMessage;
    }

    public User(String uuid, String name, String tel, String stateMessage) {
        this.uuid = uuid;
        this.name = name;
        this.tel = tel;
        this.stateMessage = stateMessage;
    }

    public User(String uuid, String name, String tel) {
        this.uuid = uuid;
        this.name = name;
        this.tel = tel;
    }

    public User(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public User(String uuid, String name, String tel, String stateMessage, String p2PChatUUID) {
        this.uuid = uuid;
        this.name = name;
        this.tel = tel;
        this.stateMessage = stateMessage;
        P2PChatUUID = p2PChatUUID;
    }
}
