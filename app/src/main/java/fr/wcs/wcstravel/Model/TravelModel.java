package fr.wcs.wcstravel.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apprenti on 24/01/18.
 */

public class TravelModel implements Parcelable{
    private String airline;
    private String travel;
    private String departure_date;
    private String return_date;
    private String price;

    public TravelModel(){
    }

    public TravelModel(String airline, String travel, String departure_date, String return_date, String price) {
        this.airline = airline;
        this.travel = travel;
        this.departure_date = departure_date;
        this.return_date = return_date;
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static final Creator<TravelModel> CREATOR = new Creator<TravelModel>() {
        @Override
        public TravelModel createFromParcel(Parcel in) {
            return new TravelModel(in);
        }

        @Override
        public TravelModel[] newArray(int size) {
            return new TravelModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    protected TravelModel(Parcel in) {
        airline = in.readString();
        travel = in.readString();
        departure_date = in.readString();
        return_date = in.readString();
        price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(airline);
        parcel.writeString(travel);
        parcel.writeString(departure_date);
        parcel.writeString(return_date);
        parcel.writeString(price);
    }
}
