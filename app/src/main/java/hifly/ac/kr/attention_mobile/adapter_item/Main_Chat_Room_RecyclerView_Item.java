package hifly.ac.kr.attention_mobile.adapter_item;


import hifly.ac.kr.attention_mobile.data.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main_Chat_Room_RecyclerView_Item {
    private String name;
    private String last_content;
    private String time;
    private String chat_room_name;
    private User user;

    public Main_Chat_Room_RecyclerView_Item(String name, String last_content, String time, String chat_room_name) {
        this.name = name;
        this.last_content = last_content;
        this.time = time;
        this.chat_room_name = chat_room_name;
    }

}
