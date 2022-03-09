package net.gotev.sipservice;

import org.pjsip.pjsua2.BuddyInfo;
import org.pjsip.pjsua2.PresenceStatus;

public class SipContactInfo extends BuddyInfo {
    protected String uri;
    protected String name;
    protected boolean subEnabled;
    protected boolean subState;
    protected String subStateName;
    protected int subTermCode;
    protected String subTermReason;
    protected PresenceStatus status;

    public SipContactInfo() {
        super();
    }

    // TODO: start getters/setters

}
