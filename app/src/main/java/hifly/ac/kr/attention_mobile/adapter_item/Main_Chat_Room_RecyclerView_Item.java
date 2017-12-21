package hifly.ac.kr.attention_mobile.adapter_item;


import hifly.ac.kr.attention_mobile.data.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main_Chat_Room_RecyclerView_Item {
    private String roomUUID;
    private String last_content;
    private String time;
    private String chat_room_name;
    private Room room;
}
