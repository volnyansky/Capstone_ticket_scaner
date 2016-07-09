package ua.dp.vedanta.ticketchecker.ui.history;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.dp.vedanta.ticketchecker.R;

import ua.dp.vedanta.ticketchecker.databinding.HistoryItemBinding;
import ua.dp.vedanta.ticketchecker.db.Ticket;
import ua.dp.vedanta.ticketchecker.ui.TicketDetailsActivity;
import ua.dp.vedanta.ticketchecker.ui.base.CursorRecyclerViewAdapter;


public class HistoryRecyclerViewAdapter extends CursorRecyclerViewAdapter<HistoryRecyclerViewAdapter.ViewHolder> {



    public HistoryRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final Ticket ticket=new Ticket(cursor);
        viewHolder.getBinding().setTicket(ticket);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private  HistoryItemBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding=DataBindingUtil.bind(view);
            mView = view;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(), TicketDetailsActivity.class);
                    intent.putExtra("ticket",binding.getTicket());
                    intent.putExtra("hide_scan",true);
                    v.getContext().startActivity(intent);
                }
            });
        }
        public HistoryItemBinding getBinding(){
            return binding;
        }

    }
}
