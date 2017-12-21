package hifly.ac.kr.attention_mobile.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import hifly.ac.kr.attention_mobile.adapter_item.ChatActivity_RecyclerView_Item;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CYSN on 2017-12-20.
 */

@Setter
@Getter
public class Room implements Serializable{
    private String roomUUID;
    private ArrayList<String> userUUIDs;
    private Vector<ChatActivity_RecyclerView_Item> items;
    private boolean isRoomVisible = false;
    public Room(String roomUUID){
        userUUIDs = new ArrayList<>();
        items = new Vector<ChatActivity_RecyclerView_Item>();
        this.roomUUID = roomUUID;
    }
    public void addUser(String userUUID){
        userUUIDs.add(userUUID);
    }
    public void addItem(ChatActivity_RecyclerView_Item item){
        items.add(item);
    }
}
