package com.ck.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ck.bean.CreditHistory;
import com.ck.listener.MyItemClickListener;
import com.ck.qianqian.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cnbs5 on 2017/7/14.
 */

public class CreditHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CreditHistory> data;
    private MyItemClickListener myItemClickListener;

    public CreditHistoryAdapter(List<CreditHistory> data, MyItemClickListener myItemClickListener) {
        this.data = data;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_credit_history, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myItemClickListener.onItemClick(v);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            CreditHistory creditHistory = data.get(position);
            ((ViewHolder) holder).name.setText(creditHistory.getLoanTypeName());
            ((ViewHolder) holder).number.setText(creditHistory.getLoanNumber());
            ((ViewHolder) holder).amount.setText(creditHistory.getAmountMoney());
            ((ViewHolder) holder).time.setText(creditHistory.getDay()+"天");
            int state = creditHistory.getLoanState();
            if(state == 0){
                ((ViewHolder) holder).state.setText("无贷款记录 ");
            }else if(state == 1){//0：无贷款记录 1：申请贷款 2：取消放款 3：已放款 4：已还款 5：已逾期 6：已续期
                ((ViewHolder) holder).state.setText("申请贷款中");
            }else if(state == 2){
                ((ViewHolder) holder).state.setText("取消放款");
            }else if(state == 3){
                ((ViewHolder) holder).state.setText("已放款");
            }else if(state == 4){
                ((ViewHolder) holder).state.setText("已还款");
            }else if(state == 5){
                ((ViewHolder) holder).state.setText("已逾期");
            }else if(state == 6){
                ((ViewHolder) holder).state.setText("已续期");
            }else if(state == 7){
                ((ViewHolder) holder).state.setText("取消贷款");
            }else if(state == 8){
                ((ViewHolder) holder).state.setText("申请还款中");
            }else{
                ((ViewHolder) holder).state.setText("未知状态");
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.state)
        TextView state;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
