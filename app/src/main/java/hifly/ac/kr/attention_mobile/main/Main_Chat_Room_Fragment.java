/*
package hifly.ac.kr.attention_mobile.main;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.List;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter.Main_Chat_Room_RecyclerView_Adapter;
import hifly.ac.kr.attention_mobile.adapter_item.ChatActivity_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.adapter_item.Main_Chat_Room_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.interfaces.TestCallback;
import hifly.ac.kr.attention_mobile.value.Values;

public class Main_Chat_Room_Fragment extends Fragment implements View.OnClickListener {

    private TextView null_second;
    private Main_Chat_Room_RecyclerView_Adapter second_recyclerView_adapter;
    private List<Main_Chat_Room_RecyclerView_Item> second_recyclerView_items;
    private RecyclerView second_RecyclerView;

    private Messenger messenger;
    private ChatRoomWrapper chatRoomWrapper;
    private String myUUID;
    private String value;
    private String innerValue;
    private String value_chat_room_name;
    private StringBuilder real_chat_room_name;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StringBuilder shared_chat_room_name;
    int shared_index = 2;

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
        Log.i("setmessage", "불림" + messenger.toString());
        Message message = new Message();
        chatRoomWrapper = new ChatRoomWrapper(second_recyclerView_adapter, second_recyclerView_items, null_second);

        message.what = Values.CHAT_ROOM;
        message.obj = chatRoomWrapper;

        try {
            messenger.send(message);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    private class RemoteHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_chat_room_fragment, container, false);
        second_RecyclerView = (RecyclerView) view.findViewById(R.id.chat_Room_RecyclerView);

        */
/* shared init *//*

        sharedPreferences = getActivity().getSharedPreferences(Values.shared_name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        shared_chat_room_name = new StringBuilder("");

        setRecyclerView();

        null_second = (TextView) view.findViewById(R.id.null_second_item);
        if (second_recyclerView_items.size() == 0) {
            null_second.setVisibility(view.VISIBLE);
        } else {
            null_second.setVisibility(view.INVISIBLE);
        }

        // 채팅창 방 업데이트
        if (second_recyclerView_items.isEmpty()) {
            myUUID = getActivity().getSharedPreferences(Values.userInfo, Context.MODE_PRIVATE).getString(Values.userUUID, "null");
        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setRecyclerView() {
        second_recyclerView_items = new ArrayList<Main_Chat_Room_RecyclerView_Item>();

        second_recyclerView_adapter = new Main_Chat_Room_RecyclerView_Adapter(getContext(), second_recyclerView_items, testCallback);
        second_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        second_RecyclerView.setAdapter(second_recyclerView_adapter);


    }

    @Override
    public void onClick(View view) {
        Log.i(Values.TAG, "룸 번호 클릭 리스너");
    }

    private TestCallback testCallback = new TestCallback() {
        @Override
        public void test(ChatActivity_RecyclerView_Item item, int position) {
            second_recyclerView_items.get(position).setLast_content(item.getChat_content());
            second_recyclerView_items.get(position).setTime(item.getTime());
            Log.e("time", item.getTime());
            second_recyclerView_adapter.notifyDataSetChanged();
        }
    };
}*/
