package hifly.ac.kr.attention_mobile.adapter_item;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatActivity_RecyclerView_Item implements Serializable{
    private String sender_name;
    private String chat_content;
    private String time;
    private int itemViewType;
    private String sender_uuid;
    private String roomUUID;

    public ChatActivity_RecyclerView_Item() {

    }

    public ChatActivity_RecyclerView_Item(String sender_name, String chat_content, String time, int itemViewType, String sender_uuid) {
        this.sender_name = sender_name;
        this.chat_content = chat_content;
        this.time = time;
        this.itemViewType = itemViewType;
        this.sender_uuid = sender_uuid;
    }

    public ChatActivity_RecyclerView_Item(String sender_name, String chat_content, String time, String sender_uuid, String roomUUID) {
        this.sender_name = sender_name;
        this.chat_content = chat_content;
        this.time = time;
        this.sender_uuid = sender_uuid;
        this.roomUUID = roomUUID;
    }
}
