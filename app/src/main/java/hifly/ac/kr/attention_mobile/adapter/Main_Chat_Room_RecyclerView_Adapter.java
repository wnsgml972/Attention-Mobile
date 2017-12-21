package hifly.ac.kr.attention_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter_item.ChatActivity_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.data.Room;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.interfaces.TestCallback;
import hifly.ac.kr.attention_mobile.main.MainActivity;
import hifly.ac.kr.attention_mobile.main.Main_Friend_Message_Activity;



public class Main_Chat_Room_RecyclerView_Adapter extends RecyclerView.Adapter<Main_Chat_Room_RecyclerView_Adapter.ViewHolder> {

    private Context context;
    private List<Room> chattingRooms;
    private RecyclerView second_RecyclerView;


    public Main_Chat_Room_RecyclerView_Adapter(Context context, ArrayList<Room> chattingRooms){
        this.context = context;
        this.chattingRooms = chattingRooms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_chat_room_recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Room room = chattingRooms.get(position);
        StringBuilder builder = new StringBuilder();
        for(String userUUID : room.getUserUUIDs()) {
            User user = MainActivity.users.get(userUUID);
            if(user != null) {
                builder.append(user.getName()).append(", ");//방 이름만드는부분
            }
        }
        if(builder.length()>=2)
            builder.delete(builder.length()-2,builder.length());
        holder.name.setText(builder.toString());//만들어서 넣기
        //방에서 채팅내용 가져오고 시간도 가져오기
        if(room.getItems().size()==0){
            holder.content.setText("비어있음");
            holder.time.setText("비어있음");
        }
        else{
            ChatActivity_RecyclerView_Item chatActivity_recyclerView_item= room.getItems().get(room.getItems().size()-1);
            holder.content.setText(chatActivity_recyclerView_item.getChat_content());
            holder.time.setText(chatActivity_recyclerView_item.getTime());
        }


    }

    @Override
    public int getItemCount() {
        return chattingRooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView content;
        public TextView time;
        public LinearLayout linearLayout;
        private User user;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.second_RecyclerView_Item_Name);
            content = itemView.findViewById(R.id.second_RecyclerView_Item_Content);
            time = itemView.findViewById(R.id.second_RecyclerView_Item_Time);
            linearLayout = itemView.findViewById(R.id.main_chat_room_recyclerview_item_king);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.main_chat_room_recyclerview_item_king:
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        int position = getAdapterPosition();
                        Intent intent = new Intent(view.getContext(), Main_Friend_Message_Activity.class);
                        //intent.putExtra("object", chattingRooms.get(position).getUserUUIDs());
                        intent.putExtra("room", chattingRooms.get(position).getRoomUUID());
                        view.getContext().startActivity(intent);
                        break;
                    }
            }
        }
    }
}

