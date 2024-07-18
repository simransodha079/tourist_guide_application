package com.example.tarek.tourguideapp.invitation;

import android.content.Context;

import com.example.tarek.tourguideapp.R;


public class InvitationData {
    private Context context;
    private String invitationMsg;
    private String shareViaFacebook;
    private String shareViaWhatsapp;
    private String shareViaGmail;
    private int imageInvitationResourceID;


    public InvitationData(Context context) {
        this.context = context;
        setInvitationMsg();
        setShareViaFacebook();
        setShareViaWhatsapp();
        setShareViaGmail();
        setImageInvitationResourceID();
    }

    public void setImageInvitationResourceID() {
        this.imageInvitationResourceID = R.drawable.icons8_people_528;
    }

    public void setInvitationMsg() {
        this.invitationMsg = context.getString(R.string.text_invitation);
    }

    public void setShareViaFacebook() {
        this.shareViaFacebook = context.getString(R.string.share_via_facebook);
    }

    public void setShareViaGmail() {
        this.shareViaGmail = context.getString(R.string.share_via_gmail);
    }

    public void setShareViaWhatsapp() {
        this.shareViaWhatsapp = context.getString(R.string.share_via_whatsapp);
    }

    public String getShareViaWhatsapp() {
        return shareViaWhatsapp;
    }

    public String getShareViaGmail() {
        return shareViaGmail;
    }

    public String getShareViaFacebook() {
        return shareViaFacebook;
    }

    public String getInvitationMsg() {
        return invitationMsg;
    }

    public int getImageInvitationResourceID() {
        return imageInvitationResourceID;
    }
}
