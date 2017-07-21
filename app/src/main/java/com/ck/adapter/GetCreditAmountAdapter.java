package com.ck.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ck.bean.credit.GetCreditAmount;
import com.ck.listener.MyItemClickListener;
import com.ck.qianqian.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cnbs5 on 2017/7/20.
 */

public class GetCreditAmountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<GetCreditAmount> data;
    private MyItemClickListener myItemClickListener;
    private int select = -1;

    public GetCreditAmountAdapter(ArrayList<GetCreditAmount> data, MyItemClickListener myItemClickListener) {
        this.data = data;
        this.myItemClickListener = myItemClickListener;
    }

    public void changeSelect(int position) {
        this.select = position;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_get_credit_list, null);
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
            if (position == select) {
                ((ViewHolder) holder).text.setBackgroundResource(R.drawable.shape_blue_round);
            } else {
                ((ViewHolder) holder).text.setBackgroundResource(R.drawable.shape_gray_round);
            }
            ((ViewHolder) holder).text.setText(data.get(position).getAmount()+"元");
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
