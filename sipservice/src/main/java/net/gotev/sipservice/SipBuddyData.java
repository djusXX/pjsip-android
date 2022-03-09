package net.gotev.sipservice;

import android.os.Parcel;
import android.os.Parcelable;

import org.pjsip.pjsua2.BuddyConfig;

import java.util.Objects;

/**
 * Contains the buddy's configuration data.
 */
@SuppressWarnings("unused")
public class SipBuddyData implements Parcelable {

    private String displayName;
    private String sipUri;
    private boolean subscribe = false;

    public SipBuddyData() { }


    /*****          Parcelable overrides        ******/
    public static final Creator<SipBuddyData> CREATOR = new Creator<SipBuddyData>() {
        @Override
        public SipBuddyData createFromParcel(Parcel in) {
            return new SipBuddyData(in);
        }

        @Override
        public SipBuddyData[] newArray(int size) {
            return new SipBuddyData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(sipUri);
        dest.writeByte((byte) (subscribe ? 1 : 0));
    }

    protected SipBuddyData(Parcel in) {
        displayName = in.readString();
        sipUri = in.readString();
        subscribe = (in.readByte() == 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    /*          Parcelable overrides end        */


    /*****          Getters and Setters        ******/
    public String getDisplayName() {
        return displayName;
    }

    public SipBuddyData setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getBuddyUri() {
        return sipUri;
    }

    public SipBuddyData setSipUri(String sipUri) {
        this.sipUri = sipUri;
        return this;
    }
    /*          Getters and Setters end        */


    /*****          Utilities        ******/
    BuddyConfig getBuddyConfig() {
        BuddyConfig buddyConfig = new BuddyConfig();
        buddyConfig.setUri(sipUri);
        buddyConfig.setSubscribe(subscribe);
        return buddyConfig;
    }
    /*          Utilities end           */


    /*****          Object overrides        ******/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SipBuddyData that = (SipBuddyData) o;
        if (!Objects.equals(displayName, that.displayName)) return false;
        if (!Objects.equals(sipUri, that.sipUri)) return false;
        if (subscribe != that.subscribe) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = displayName.hashCode();
        result = 31 * result + sipUri.hashCode();
        result = 31 * result + (subscribe ? 1 : 0);
        return result;
    }

    SipBuddyData getDeepCopy() {
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        SipBuddyData temp = SipBuddyData.CREATOR.createFromParcel(parcel);
        parcel.recycle();
        return temp;
    }
    /*          Object overrides end        */
    
}


