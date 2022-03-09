package net.gotev.sipservice;

import org.pjsip.pjsua2.SWIGTYPE_p_void;
import org.pjsip.pjsua2.SendInstantMessageParam;
import org.pjsip.pjsua2.SipTxOption;

public class SipMessage extends SendInstantMessageParam {
    protected String type;
    protected String content;
    protected SipTxOption txOption;
    protected SWIGTYPE_p_void userData;

    public SipMessage() {
        super();
    }

    public SipMessage(String type, String content, SipTxOption txOption, SWIGTYPE_p_void userData) {
        super();
        this.type = type;
        this.content = content;
        this.txOption = txOption;
        this.userData = userData;
        updateMembers();
    }

    public void updateMembers() {
        setContentType(type);
        setContent(content);
        setTxOption(txOption);
        setUserData(userData);
    }

    @Override
    public String getContentType() { return type; }

    @Override
    public String getContent() { return content; }

    @Override
    public SipTxOption getTxOption() { return txOption; }

    @Override
    public SWIGTYPE_p_void getUserData() { return userData; }
}
