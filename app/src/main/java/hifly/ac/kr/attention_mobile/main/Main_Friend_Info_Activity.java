package hifly.ac.kr.attention_mobile.main;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.data.User;
import hifly.ac.kr.attention_mobile.value.Values;

public class Main_Friend_Info_Activity extends AppCompatActivity {
    private ImageView profileView;
    private TextView nameTextView;
    private TextView stateTextView;
    private FloatingActionButton callFab;
    private FloatingActionButton messageFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_friend_info);
        nameTextView = (TextView) findViewById(R.id.main_friend_info_name_textView);
        stateTextView = (TextView) findViewById(R.id.main_friend_info_state_textView);
        profileView = (ImageView) findViewById(R.id.activity_main_friend_profile);

        callFab = (FloatingActionButton)findViewById(R.id.main_friend_info_call_fab);
        messageFab = (FloatingActionButton) findViewById(R.id.main_friend_info_message_fab);
        User user = (User)getIntent().getSerializableExtra("object");
        if(user != null){
            nameTextView.setText(user.getName());
            stateTextView.setText(user.getStateMessage());
        }
        else{
            Glide.with(this).load(R.drawable.main_friend_basic_icon)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into((profileView));
        }
    }

    public void call(View v){
      /*  Intent intent = new Intent(getApplicationContext(), Main_Friend_Call_Activity.class);
        intent.putExtra("object",(User)getIntent().getSerializableExtra("object"));
        startActivity(intent);*/
    }

    public void message(View v){
        Intent intent = new Intent(getApplicationContext(), Main_Friend_Message_Activity.class);
        intent.putExtra("object",(User)getIntent().getSerializableExtra("object"));
        startActivity(intent);
    }

}
