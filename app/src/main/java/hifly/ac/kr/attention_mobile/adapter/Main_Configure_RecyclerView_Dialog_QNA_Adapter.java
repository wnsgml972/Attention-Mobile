package hifly.ac.kr.attention_mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hifly.ac.kr.attention_mobile.R;
import hifly.ac.kr.attention_mobile.adapter_item.Main_Configure_RecyclerView_Dialog_QNA_Adapter_Item;

/**
 * Created by hscom-018 on 2017-12-07.
 */

public class Main_Configure_RecyclerView_Dialog_QNA_Adapter extends RecyclerView.Adapter<Main_Configure_RecyclerView_Dialog_QNA_Adapter.ViewHolder> {
    private Context context;
    private List<Main_Configure_RecyclerView_Dialog_QNA_Adapter_Item> main_configure_recyclerView_dialog_qna_adapter_items;

    public Main_Configure_RecyclerView_Dialog_QNA_Adapter(Context context, List<Main_Configure_RecyclerView_Dialog_QNA_Adapter_Item> main_configure_recyclerView_dialog_qna_adapter_items) {
        this.context = context;
        this.main_configure_recyclerView_dialog_qna_adapter_items = main_configure_recyclerView_dialog_qna_adapter_items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_configure_recycler_item_qna_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position.setText(main_configure_recyclerView_dialog_qna_adapter_items.get(position).getPosition());
        holder.content.setText(main_configure_recyclerView_dialog_qna_adapter_items.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return main_configure_recyclerView_dialog_qna_adapter_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView position;
        public TextView content;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            position = (TextView) itemView.findViewById(R.id.main_Configure_RecyclerView_Item_QNA_Dialog_position);
            content = (TextView) itemView.findViewById(R.id.main_Configure_RecyclerView_Item_QNA_Dialog_content);
        }
    }
}
