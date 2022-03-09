package net.gotev.sipservice;

import org.pjsip.pjsua2.Buddy;
import org.pjsip.pjsua2.OnBuddyEvSubStateParam;

public class SipBuddy extends Buddy {

    private static final String LOG_TAG = SipBuddy.class.getSimpleName();

    private final SipService service;
    private final SipBuddyData data;


    protected SipBuddy(SipService service, SipBuddyData data) {
        super();
        this.service = service;
        this.data = data;
    }

    public SipService getService() { return service; }

    public SipBuddyData getData() { return data; }

    public void create(SipAccount sipAccount) throws Exception {
        create(sipAccount, data.getBuddyConfig());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SipBuddy that = (SipBuddy) o;
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public void onBuddyState() {
        Logger.debug(LOG_TAG, "called onBuddyState() of " + data.getBuddyUri());
        service.getBroadcastEmitter().buddyState(this);
    }

    @Override
    public void onBuddyEvSubState(OnBuddyEvSubStateParam prm) {
        Logger.debug(LOG_TAG, "called onBuddyEvSubState() of " + data.getBuddyUri() + ", event type: " + prm.getE().getType());
        Logger.debug(LOG_TAG, "whole message: " + prm.getE().getBody().getTxMsg().getTdata().getWholeMsg());

        service.getBroadcastEmitter().buddySubscriptionState(this, prm);
    }
}
