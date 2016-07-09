package ua.dp.vedanta.ticketchecker.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

import ua.dp.vedanta.ticketchecker.api.TicketJson;

/**
 * Created by Stanislav Volnyansky on 02.07.16.
 * class
 */
public class Ticket implements Parcelable {
    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };
    private String barcode;
    private String event;
    private String userName;
    private String email;
    private String phone;
    private Date activated;
    private Date eventDate;
    private float price;

    public Ticket(Cursor cursor) {
        if (cursor.moveToFirst()) {
            setBarcode(cursor.getLong(cursor.getColumnIndex("_ID")));
            setEvent(cursor.getString(cursor.getColumnIndex("event")));
            setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
            Date dt = new Date(cursor.getLong(cursor.getColumnIndex("activation_date")));
            setActivated(dt);
            dt = new Date(cursor.getLong(cursor.getColumnIndex("event_date")));
            setEventDate(dt);
            setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            setEmail(cursor.getString(cursor.getColumnIndex("email")));
            setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        }
    }


    public Ticket(ContentValues values) {
        setBarcode(values.getAsLong("_ID"));
        setEvent(values.getAsString("event"));
        setPrice(values.getAsFloat("price"));
        if (values.containsKey("activation_date")){
            Date dt = new Date(values.getAsLong("activation_date"));
            setActivated(dt);
        }
        Date dt = new Date(values.getAsLong("event_date"));
        setEventDate(dt);

        setUserName(values.getAsString("user_name"));
        setEmail(values.getAsString("email"));
        setPhone(values.getAsString("phone"));
    }

    protected Ticket(Parcel in) {
        barcode = in.readString();
        event = in.readString();
        userName = in.readString();
        email = in.readString();
        phone = in.readString();
        price = in.readFloat();
        long dt=in.readLong();
        activated=new Date(dt);
        dt=in.readLong();
        eventDate=new Date(dt);
    }

    public Date getActivated() {
        return activated;
    }

    public Ticket setActivated(Date activated) {
        this.activated = activated;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public Ticket setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public Ticket setBarcode(long barcode) {
        this.barcode = String.valueOf(barcode);
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Ticket setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEvent() {
        return event;
    }

    public Ticket setEvent(String event) {
        this.event = event;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Ticket setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Ticket setPrice(float price) {
        this.price = price;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Ticket setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPriceFormatted() {
        return String.format("%.2f UAH", price);
    }

    public String getActivatedFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        if (activated != null) {
            return format.format(activated);
        }
        return "";
    }
    public String getEventDateFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        if (eventDate != null) {
            return format.format(eventDate);
        }
        return "";
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getBarcode());
        dest.writeString(getEvent());
        dest.writeString(getUserName());
        dest.writeString(getEmail());
        dest.writeString(getPhone());
        dest.writeFloat(getPrice());
        dest.writeLong(getActivated().getTime());
        dest.writeLong(getEventDate().getTime());
    }

    public boolean isValid() {
        if (activated == null) return false;
        return (System.currentTimeMillis() - activated.getTime()) < 5000;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public Ticket setEventDate(Date eventDate) {
        this.eventDate = eventDate;
        return this;
    }
}
