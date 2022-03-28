package net.gotev.sipservice;

import org.pjsip.pjsua2.Buddy;
import org.pjsip.pjsua2.BuddyInfo;
import org.pjsip.pjsua2.OnBuddyEvSubStateParam;
import org.pjsip.pjsua2.SipEvent;
import org.pjsip.pjsua2.SipEventBody;
import org.pjsip.pjsua2.pjsua_buddy_status;

public class SipBuddy extends Buddy {

    private static final String LOG_TAG = SipBuddy.class.getName();

    private final SipService service;
    private final SipBuddyData data;
    private static String ownerSipUri;


    protected SipBuddy(SipService service, SipBuddyData data) {
        super();
        this.service = service;
        this.data = data;
    }

    public SipService getService() { return service; }

    public SipBuddyData getData() { return data; }

    public void create(SipAccount sipAccount) throws Exception {
        ownerSipUri = sipAccount.getData().getIdUri();
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
        try {
            BuddyInfo buddyInfo = this.getInfo();
            Logger.debug(LOG_TAG, "=================================================================================");
            Logger.debug(LOG_TAG, "Buddy presence status:");
            Logger.debug(LOG_TAG, "     status type: " + buddyInfo.getPresStatus().getStatus() + "|");
            Logger.debug(LOG_TAG, "     status text: " + buddyInfo.getPresStatus().getStatusText() + "|");
            Logger.debug(LOG_TAG, "     status activity: " + buddyInfo.getPresStatus().getActivity() + "|");
            Logger.debug(LOG_TAG, "     status note: " + buddyInfo.getPresStatus().getNote() + "|");
            Logger.debug(LOG_TAG, "     status rpid: " + buddyInfo.getPresStatus().getRpidId() + "|");

            Logger.debug(LOG_TAG, "Buddy subscriptionState: " + buddyInfo.getSubState() + "|");
            Logger.debug(LOG_TAG, "Buddy contact: " + buddyInfo.getContact() + "|");
            Logger.debug(LOG_TAG, "Buddy id: " + this.getId() + "|");
            Logger.debug(LOG_TAG, "=================================================================================");

            String statusStr = "UNKNOWN";
            int statusInt = buddyInfo.getPresStatus().getStatus();
            if (pjsua_buddy_status.PJSUA_BUDDY_STATUS_ONLINE == statusInt) statusStr = "ONLINE";
            if (pjsua_buddy_status.PJSUA_BUDDY_STATUS_OFFLINE == statusInt) statusStr = "OFFLINE";


            service.getBroadcastEmitter().buddyState(ownerSipUri, data.getBuddyUri(), statusStr, buddyInfo.getPresStatus().getStatusText());
        } catch (Exception e) {
            Logger.error(LOG_TAG, "Error while getting buddyInfo: " + e);
        }

    }

    @Override
    public void onBuddyEvSubState(OnBuddyEvSubStateParam prm) {
        Logger.debug(LOG_TAG, "called onBuddyEvSubState() of " + data.getBuddyUri() + ", event type: " + prm.getE().getType());
        Logger.debug(LOG_TAG, "     tsxState.tsx.state: " + prm.getE().getBody().getTsxState().getTsx().getState());
        Logger.debug(LOG_TAG, "     tsxState.tsx.statusText: " + prm.getE().getBody().getTsxState().getTsx().getStatusText());
        Logger.debug(LOG_TAG, "     tsxState.src.status: " + prm.getE().getBody().getTsxState().getSrc().getStatus());

//        service.getBroadcastEmitter().buddySubscriptionState(this, prm);
    }

}
