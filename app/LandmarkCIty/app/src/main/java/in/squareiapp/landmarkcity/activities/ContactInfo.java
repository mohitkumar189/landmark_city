package in.squareiapp.landmarkcity.activities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohit kumar on 7/18/2017.
 */

public class ContactInfo implements Parcelable {

    private String name;
    private String surname;
    private int idx;

    // get and set method

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeInt(idx);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };

    // "De-parcel object
    public ContactInfo(Parcel in) {
        name = in.readString();
        surname = in.readString();
        idx = in.readInt();
    }
}