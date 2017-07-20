package com.ck.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ck.bean.credit.Credit;
import com.ck.listener.MyItemClickListener;
import com.ck.qianqian.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cnbs5 on 2017/7/19.
 */

public class CreditListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Credit> data;
    private MyItemClickListener myItemClickListener;

    private enum ITEM_TYPE {BLUE, RED}

    public CreditListAdapter(List<Credit> data, MyItemClickListener myItemClickListener) {
        this.data = data;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getValid() == 0 ? ITEM_TYPE.RED.ordinal() : ITEM_TYPE.BLUE.ordinal();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.RED.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_list2, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myItemClickListener.onItemClick(v);
                }
            });
            //使用了这个那么item布局的margin会不起作用
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new RedViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_list, parent, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myItemClickListener.onItemClick(v);
                }
            });
            return new BlueViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RedViewHolder) {
            ((RedViewHolder) holder).name.setText(data.get(position).getStateDec());
            ((RedViewHolder) holder).credit_amount.setText(data.get(position).getLoanMoney());
            int state = data.get(position).getLoanState();
            String tmp = "";
            switch (state) {
                case 0:
                    tmp = "无贷款记录";
                    break;
                case 1:
                    tmp = "申请贷款";
                    break;
                case 2:
                    tmp = "取消放款";
                    break;
                case 3:
                    tmp = "已放款";
                    break;
                case 4:
                    tmp = "已还款";
                    break;
                case 5:
                    tmp = "已逾期";
                    break;
                case 6:
                    tmp = "已续期";
                    break;
            }
            ((RedViewHolder) holder).credit_state.setText(tmp);
        } else if (holder instanceof BlueViewHolder) {
            ((BlueViewHolder) holder).name.setText(data.get(position).getStateDec());
            ((BlueViewHolder) holder).credit_amount.setText(data.get(position).getLoanMoney());
            int state = data.get(position).getLoanState();
            String tmp = "";
            switch (state) {
                case 0:
                    tmp = "无贷款记录";
                    break;
                case 1:
                    tmp = "申请贷款";
                    break;
                case 2:
                    tmp = "取消放款";
                    break;
                case 3:
                    tmp = "已放款";
                    break;
                case 4:
                    tmp = "已还款";
                    break;
                case 5:
                    tmp = "已逾期";
                    break;
                case 6:
                    tmp = "已续期";
                    break;
            }
            ((BlueViewHolder) holder).credit_state.setText(tmp);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
//        return 10;
    }

    static class RedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.credit_amount)
        TextView credit_amount;
        @BindView(R.id.credit_state)
        TextView credit_state;

        RedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class BlueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.credit_amount)
        TextView credit_amount;
        @BindView(R.id.credit_state)
        TextView credit_state;

        BlueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
