package hifly.ac.kr.attention_mobile.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter.Main_Configure_RecyclerView_Dialog_Speaker_Adapter;
import hifly.ac.kr.attention_mobile.adapter_item.Main_Configure_RecyclerView_Dialog_Speaker_Item;


/**
 * Created by hscom-018 on 2017-12-06.
 */


public class Main_Configure_Item_Speaker_Dialog extends AppCompatActivity {

    private RecyclerView main_Configure_RecyclerView_Dialog;
    private Main_Configure_RecyclerView_Dialog_Speaker_Adapter main_configure_recyclerView_dialog_speaker_adapter;
    private List<Main_Configure_RecyclerView_Dialog_Speaker_Item> main_configure_recyclerView_dialog_speaker_items;

    private ImageButton imageButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);   //타이틀바 없애는코드
        setContentView(R.layout.main_configure_recycler_speaker_dialog);
        setFinishOnTouchOutside(false);         //다이얼로그 테마로 다이얼로그를 띄울때 다른곳을 터치할시에 꺼지는것을 방지
        main_Configure_RecyclerView_Dialog = (RecyclerView) findViewById(R.id.main_Configure_RecyclerView_Dialog_speaker);
        imageButton = (ImageButton) findViewById(R.id.main_Configure_speaker_dialog_exit_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Main_Configure_Item_Speaker_Dialog.this.finish();
            }
        });

        setRecyclerView();

    }

    private void setRecyclerView() {


        main_configure_recyclerView_dialog_speaker_items = new ArrayList<Main_Configure_RecyclerView_Dialog_Speaker_Item>();
        main_configure_recyclerView_dialog_speaker_items.add(new Main_Configure_RecyclerView_Dialog_Speaker_Item("1", "새로 출시된 회의 어플 1.0.0 버전 업데이트"));
        main_configure_recyclerView_dialog_speaker_items.add(new Main_Configure_RecyclerView_Dialog_Speaker_Item("2", "키워드 알림이 추가된 1.0.1 버전 업데이트"));
        main_configure_recyclerView_dialog_speaker_items.add(new Main_Configure_RecyclerView_Dialog_Speaker_Item("3", "새 옷을 입은 1.0.1 버전"));
        main_configure_recyclerView_dialog_speaker_items.add(new Main_Configure_RecyclerView_Dialog_Speaker_Item("4", "보기 편해진 채팅목록, 1.0.1 버전"));
        main_configure_recyclerView_dialog_speaker_items.add(new Main_Configure_RecyclerView_Dialog_Speaker_Item("5", "보안 정책 강화 및 1.0.1 버전 업데이트"));
        main_configure_recyclerView_dialog_speaker_items.add(new Main_Configure_RecyclerView_Dialog_Speaker_Item("6", "이모티콘 관리가 더욱 쉬워진 1.0.2 버전 준비중"));

        main_configure_recyclerView_dialog_speaker_adapter = new Main_Configure_RecyclerView_Dialog_Speaker_Adapter(getApplicationContext(), main_configure_recyclerView_dialog_speaker_items);
        main_Configure_RecyclerView_Dialog.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        main_Configure_RecyclerView_Dialog.setAdapter(main_configure_recyclerView_dialog_speaker_adapter);

    }
}













