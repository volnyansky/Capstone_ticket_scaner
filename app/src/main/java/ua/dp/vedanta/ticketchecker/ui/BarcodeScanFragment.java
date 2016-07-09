package ua.dp.vedanta.ticketchecker.ui;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import com.google.gson.Gson;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import ua.dp.vedanta.ticketchecker.R;
import ua.dp.vedanta.ticketchecker.ThisApplication;
import ua.dp.vedanta.ticketchecker.api.ApiClient;
import ua.dp.vedanta.ticketchecker.api.TicketJson;

import ua.dp.vedanta.ticketchecker.api.ValidationResult;
import ua.dp.vedanta.ticketchecker.databinding.BarcodeScanFragmentBinding;
import ua.dp.vedanta.ticketchecker.db.Ticket;
import ua.dp.vedanta.ticketchecker.db.TicketsProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarcodeScanFragment extends Fragment {


    private View rootView;
    private BarcodeScanFragmentBinding binding;
    private Tracker mTracker;

    public BarcodeScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTracker = ThisApplication.getTracker();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.barcode_scan_fragment, container, false);
        rootView = binding.getRoot();
        inflater.inflate(R.layout.barcode_scan_fragment, container, false);
        barcodeView = binding.barcodeScanner;
        barcodeView.decodeContinuous(callback);

        return rootView;
    }


    private static final String TAG = BarcodeScanFragment.class.getSimpleName();
    private DecoratedBarcodeView barcodeView;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                ThisApplication.getTracker().send(new HitBuilders.EventBuilder()
                        .setCategory("Event")
                        .setAction("Scan succesfull")
                        .build());
                barcodeView.setStatusText(result.getText());
                barcodeView.pause();
                ValidateTicket task = new ValidateTicket();
                task.execute(result.getText());

                //Added preview of scanned barcode
                ImageView imageView = (ImageView) rootView.findViewById(R.id.barcodePreview);
                imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));

            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Scan");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        barcodeView.resume();
        barcodeView.setStatusText("");
    }

    @Override
    public void onPause() {
        super.onPause();

        barcodeView.pause();
    }


    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }


    private class ValidateTicket extends AsyncTask<String, Void, Boolean> {
        String error;
        private Ticket ticket;

        @Override
        protected void onPostExecute(Boolean ticketFound) {
            if (ticketFound) {
                Intent intent = new Intent(getContext(), TicketDetailsActivity.class);
                intent.putExtra("ticket", ticket);
                startActivity(intent);
            } else if (error != null) {

                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.error)
                        .setMessage(error)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                barcodeView.resume();
                            }
                        })
                        .show();
            } else {
                new AlertDialog.Builder(getContext())
                        .setMessage(R.string.ticket_not_found)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                barcodeView.resume();
                            }
                        })
                        .show();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                long barcode = Long.parseLong(params[0]);
                Uri uri = ContentUris.withAppendedId(TicketsProvider.CONTENT_URI, barcode);
                Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
                Boolean ticketFound = cursor.getCount() > 0;
                long activationDate = 0;
                if (ticketFound) {
                    ticket = new Ticket(cursor);
                    cursor.moveToFirst();
                    activationDate = cursor.getInt(cursor.getColumnIndex("activation_date"));
                }
                cursor.close();

                RequestBody param = RequestBody.create(MediaType.parse("text/plain"), params[0]);

                if (ticketFound) {
                    if (activationDate == 0) {

                        ApiClient.getService().validate(params[0], param).execute();

                        ContentValues values = new ContentValues();
                        values.put("activation_date", System.currentTimeMillis());
                        getContext().getContentResolver().update(uri, values, null, null);
                        ticket.setActivated(new Date(values.getAsLong("activation_date")));
                    }
                    return Boolean.TRUE;
                } else {

                    Response<TicketJson> response = ApiClient.getService().getTicket(params[0]).execute();
                    TicketJson ticketJson = response.body();
                    ContentValues values = new ContentValues();
                    values.put("_ID", barcode);
                    values.put("event", ticketJson.getProduct().getTitle());
                    values.put("price", ticketJson.getLineItem().getUnitPrice());
                    values.put("user_name", ticketJson.getOrder().getOwner().getName());
                    values.put("email", ticketJson.getOrder().getOwner().getMail());
                    values.put("event_date", ticketJson.getProduct().getDateStart() * 1000);
                    values.put("phone", ticketJson.getOrder().getOwner().getPhone());
                    getContext().getContentResolver().insert(TicketsProvider.CONTENT_URI, values);
                    ticket = new Ticket(values);


                    if (!ticketJson.getUsed()) {
                        ApiClient.getService().validate(params[0], param).execute();
                        values = new ContentValues();
                        values.put("activation_date", System.currentTimeMillis());
                        getContext().getContentResolver().update(uri, values, null, null);
                    } else {
                        values = new ContentValues();
                        values.put("activation_date", ticketJson.getChanged() * 1000);
                        getContext().getContentResolver().update(uri, values, null, null);
                    }
                    ticket.setActivated(new Date(values.getAsLong("activation_date")));


                    return Boolean.TRUE;
                }
            } catch (IOException e) {
                e.printStackTrace();
                error = e.getLocalizedMessage();
                return Boolean.FALSE;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(context.getString(R.string.scan));
    }
}
