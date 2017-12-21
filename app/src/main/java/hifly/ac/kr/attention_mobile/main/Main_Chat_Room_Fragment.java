package hifly.ac.kr.attention_mobile.main;

import android.os.Bundle;
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
import hifly.ac.kr.attention_mobile.data.Room;
import hifly.ac.kr.attention_mobile.value.Values;

public class Main_Chat_Room_Fragment extends Fragment implements View.OnClickListener {

    private TextView null_second;
    private RecyclerView second_RecyclerView;
    private Main_Chat_Room_RecyclerView_Adapter second_recyclerView_adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_chat_room_fragment, container, false);
        second_RecyclerView = (RecyclerView) view.findViewById(R.id.chat_Room_RecyclerView);
        null_second = (TextView) view.findViewById(R.id.null_second_item);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRecyclerView();

        if (MainActivity.rooms.size() == 0) {
            null_second.setVisibility(View.VISIBLE);
        } else {
            null_second.setVisibility(View.INVISIBLE);
        }
    }

    private void setRecyclerView() {
        second_recyclerView_adapter = new Main_Chat_Room_RecyclerView_Adapter(getContext(), MainActivity.rooms);
        second_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        second_RecyclerView.setAdapter(second_recyclerView_adapter);
        second_recyclerView_adapter.notifyDataSetChanged();
    }
    public void notifiChanged(){
        if (MainActivity.rooms.size() == 0) {
            null_second.setVisibility(View.VISIBLE);
        } else {
            null_second.setVisibility(View.INVISIBLE);
        }
        second_recyclerView_adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        Log.i(Values.TAG, "룸 번호 클릭 리스너");
    }

    /*private TestCallback testCallback = new TestCallback() {
        @Override
        public void test(ChatActivity_RecyclerView_Item item, int position) {
            second_recyclerView_items.get(position).setLast_content(item.getChat_content());
            second_recyclerView_items.get(position).setTime(item.getTime());
            Log.e("time", item.getTime());
            second_recyclerView_adapter.notifyDataSetChanged();
        }
    };*/
}
