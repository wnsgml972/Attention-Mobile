package hifly.ac.kr.attention_mobile.main;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter.Main_Friend_Recycler_Adapter;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.value.Values;

/**
 * Created by CYSN on 2017-11-09.
 */

public class Main_Friend_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private Main_Friend_Recycler_Adapter adapter;
    public Main_Friend_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_friend, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_friend_recyclerview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }
    public void refresh(){
        Handler handler = new MainActivity.MyHandler((MainActivity) getActivity());
        adapter = new Main_Friend_Recycler_Adapter(getContext(), handler);
        adapter.addUser(null);
        //adapter.addUser(new User(Values.myName, "좋은 하루~", Values.myUUID));
        adapter.addUser(MainActivity.users.get(Values.myUUID));
        adapter.addUser(null);
        for (String userUUID : MainActivity.users.keySet()) {
            if(!userUUID.equals(Values.myUUID))
                adapter.addUser(MainActivity.users.get(userUUID));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    public void notifyAdapterChanged(){
        adapter.notifyDataSetChanged();
    }


}
