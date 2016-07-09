package ua.dp.vedanta.ticketchecker.ui.history;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ua.dp.vedanta.ticketchecker.R;
import ua.dp.vedanta.ticketchecker.db.TicketsProvider;



public class HistoryFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    HistoryRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
             recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        }


        getLoaderManager().initLoader(0,null,this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(context.getString(R.string.history));

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    // Init loader

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new DataLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter=new HistoryRecyclerViewAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public static class DataLoader extends CursorLoader{

        public DataLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            return  getContext().getContentResolver().query(TicketsProvider.CONTENT_URI,null,null,null,"activation_date DESC");
        }
    }

}
