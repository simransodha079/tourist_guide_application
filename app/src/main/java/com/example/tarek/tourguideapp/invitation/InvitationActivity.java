package com.example.tarek.tourguideapp.invitation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarek.tourguideapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitationActivity extends AppCompatActivity {


    @BindView(R.id.text_invitation)
    TextView textInvitation;
    @BindView(R.id.image_invitation)
    ImageView imageInvitation;

    private InvitationData data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        ButterKnife.bind(this);
        data = new InvitationData(this);
        textInvitation.setText(data.getInvitationMsg());
        imageInvitation.setImageResource(data.getImageInvitationResourceID());
    }

    @OnClick(R.id.facebook_icon)
    public void onClickFacebookIcon() {
        toastInvitationMsg(data.getShareViaFacebook());
    }

    @OnClick(R.id.whatsapp_icon)
    public void onClickWhatsappIcon() {
        toastInvitationMsg(data.getShareViaWhatsapp());
    }

    @OnClick(R.id.gmail_icon)
    public void onClickGmailIcon() {
        toastInvitationMsg(data.getShareViaGmail());
    }

    private void toastInvitationMsg(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
