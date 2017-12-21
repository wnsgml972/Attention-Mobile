package hifly.ac.kr.attention_mobile.main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter.Main_Configuration_RecyclerView_Adapter;
import hifly.ac.kr.attention_mobile.adapter_item.Main_Configuration_RecyclerView_Item;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.value.Values;

public class Main_Configuration_Fragment extends Fragment {

    private RecyclerView fourth_RecyclerView;
    private Main_Configuration_RecyclerView_Adapter main_configuration_recyclerView_adapter;
    private List<Main_Configuration_RecyclerView_Item> main_Configuration_RecyclerView_Items;
    private ImageView fourth_item_image;
    private ImageView fourth_fragment_profile_Item_Image;
    private int REQEUST_OK = 102;
    private View view;

    /////////////////////////
    private TextView nameTextView;
    private TextView telTextView;

    public RequestManager mGlideRequestManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlideRequestManager = Glide.with(this);
    }

    //////////////////////////////////////// 프사 바꾸기
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = (LinearLayout) inflater.inflate(R.layout.fragment_fourth,container,false);
        view = (LinearLayout) inflater.inflate(R.layout.main_configuration_fragment, container, false);

        fourth_item_image = (ImageView) view.findViewById(R.id.fourth_RecyclerView_Item_Image);
        fourth_RecyclerView = (RecyclerView) view.findViewById(R.id.fourth_RecyclerView);

        fourth_fragment_profile_Item_Image = (ImageView) view.findViewById(R.id.fourth_fragment_profile_Item_Image);
        /////////////어플 켜자마자 자기자신 설정 탭 에서 프사 바꿔져있기

        /////////////////
        fourth_fragment_profile_Item_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQEUST_OK);
            }
        });

        setRecyclerView();
        nameTextView = (TextView) view.findViewById(R.id.main_chat_room_recyclerview_user_name);
        telTextView = (TextView) view.findViewById(R.id.main_chat_room_recyclerview_user_tel);
        nameTextView.setText(Values.myName);
        String tel = Values.myTel.substring(0,3);
        tel += "-";
        tel += Values.myTel.substring(3,7);
        tel += "-";
        tel += Values.myTel.substring(7);
        telTextView.setText(tel);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {


            mGlideRequestManager.load(data.getData()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(fourth_fragment_profile_Item_Image);

            //프로필사진 올리기


            fourth_fragment_profile_Item_Image.setDrawingCacheEnabled(true);
            fourth_fragment_profile_Item_Image.buildDrawingCache();

            Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image_bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);

            byte[] profile_data = baos.toByteArray();
            Handler handler = new MainActivity.MyHandler((MainActivity) getActivity());
            Message message = new Message();
            MainActivity.users.get(Values.myUUID).setProfileData(profile_data);

            Bundle bundle = new Bundle();
            bundle.putInt("message",Values.PROFILE_INSERT);
            message.setData(bundle);
            message.obj = profile_data;
            MainActivity.isDataChanged = true;
            handler.sendMessage(message);

        } catch (Exception e) {
            Log.e("test", e.getMessage());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User user = MainActivity.users.get(Values.myUUID);
        if(user != null && user.getProfileData() != null)
            mGlideRequestManager.load(user.getProfileData()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(fourth_fragment_profile_Item_Image);

    }

    private void setRecyclerView() {

        main_Configuration_RecyclerView_Items = new ArrayList<Main_Configuration_RecyclerView_Item>();
        main_Configuration_RecyclerView_Items.add(new Main_Configuration_RecyclerView_Item("공지사항", R.drawable.main_configuration_item_speaker));
        main_Configuration_RecyclerView_Items.add(new Main_Configuration_RecyclerView_Item("버전정보", R.drawable.main_configuration_item_i));
        main_Configuration_RecyclerView_Items.add(new Main_Configuration_RecyclerView_Item("도움말", R.drawable.main_configuration_item_qna));
        main_Configuration_RecyclerView_Items.add(new Main_Configuration_RecyclerView_Item("개발자", R.drawable.main_configuration_item_developer));
        main_Configuration_RecyclerView_Items.add(new Main_Configuration_RecyclerView_Item("동기화", R.drawable.main_configuration_item_refresh));

        main_configuration_recyclerView_adapter = new Main_Configuration_RecyclerView_Adapter(getContext(), main_Configuration_RecyclerView_Items);
        fourth_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fourth_RecyclerView.setAdapter(main_configuration_recyclerView_adapter);
    }

}

