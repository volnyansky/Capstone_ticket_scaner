package ua.dp.vedanta.ticketchecker.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;

import ua.dp.vedanta.ticketchecker.BR;
import ua.dp.vedanta.ticketchecker.R;
import ua.dp.vedanta.ticketchecker.api.TicketJson;
import ua.dp.vedanta.ticketchecker.databinding.TicketDetailsActivityBinding;
import ua.dp.vedanta.ticketchecker.db.Ticket;

public class TicketDetailsActivity extends AppCompatActivity {
    Ticket ticket;
    public  final ObservableField<Boolean> hideScan=new ObservableField<>(false);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TicketDetailsActivityBinding binding= DataBindingUtil.setContentView(this,R.layout.ticket_details_activity);
        Gson gson=new Gson();
        if (getIntent()!=null && getIntent().hasExtra("ticket")) {
            ticket=getIntent().getParcelableExtra("ticket");
            hideScan.set(getIntent().getBooleanExtra("hide_scan",false));
        }
        if (savedInstanceState!=null){
            ticket=savedInstanceState.getParcelable("ticket");
            hideScan.set(savedInstanceState.getBoolean("hide_scan",false));
        }
        if (ticket ==null) {
            finish();
            return;
        }
        binding.setTicket(ticket);
        binding.setActivity(this);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ticket",ticket);
        outState.putBoolean("hide_scan",hideScan.get());
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return true;
    }
}
