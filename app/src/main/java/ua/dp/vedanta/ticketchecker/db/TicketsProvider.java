package ua.dp.vedanta.ticketchecker.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Stanislav Volnyansky on 27.06.16.
 */
public class TicketsProvider extends ContentProvider {
    // Creates a UriMatcher object.
    static final String PROVIDER_NAME = "ua.dp.vedanta.ticketchecker.db.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/tickets";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "name";

    private static final String TICKETS_TABLE_NAME ="tickets" ;



    static final int TICKETS = 1;
    static final int TICKET_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "tickets", TICKETS);
        uriMatcher.addURI(PROVIDER_NAME, "tickets/#", TICKET_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        LocalDatabaseHelper dbHelper = new LocalDatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TICKETS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {

            case TICKET_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;
            case TICKETS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }


        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            /**
             * Get all tickets records
             */
            case TICKETS:
                return "vnd.android.cursor.dir/vnd.ua.dp.vedanta.students";

            /**
             * Get a particular ticket
             */
            case TICKET_ID:
                return "vnd.android.cursor.item/vnd.ua.dp.vedanta.students";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(	TICKETS_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case TICKETS:
                count = db.delete(TICKETS_TABLE_NAME, selection, selectionArgs);
                break;

            case TICKET_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( TICKETS_TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case TICKETS:
                count = db.update(TICKETS_TABLE_NAME, values, selection, selectionArgs);
                break;

            case TICKET_ID:
                count = db.update(TICKETS_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    // A string that defines the SQL statement for creating a table
    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            "tickets " +                       // Table's name
            "(" +                           // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            " event TEXT,"+
            " event_date INT,"+
            " price REAL,"+
            " activation_date INTEGER,"+
            " user_name TEXT,"+
            " email TEXT,"+
            " phone TEXT)";

    /**
     * Helper class that actually creates and manages the provider's underlying data repository.
     */
    protected static final class LocalDatabaseHelper extends SQLiteOpenHelper {

        private static final String DBNAME ="local_db" ;

        /*
                 * Instantiates an open helper for the provider's SQLite data repository
                 * Do not do database creation and upgrade here.
                 */
        public LocalDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        /*
         * Creates the data repository. This is called when the provider attempts to open the
         * repository and SQLite reports that it doesn't exist.
         */
        public void onCreate(SQLiteDatabase db) {

            // Creates the main table
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    }



}
