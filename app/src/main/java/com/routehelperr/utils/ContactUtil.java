package com.routehelperr.utils;

import android.content.Context;

public class ContactUtil {

    private Context mcontext;
    private String TAG = this.getClass().getSimpleName();

    public ContactUtil(Context mcontext) {
        this.mcontext = mcontext;
    }

//    public void makeCall() {
//        String number = ("tel:" + "0512345678");
//        Intent i = new Intent(Intent.ACTION_CALL);
//        i.setData(Uri.parse("tel:0612312312"));
//    /*
//        Intent i = new Intent(Intent.ACTION_DIAL);
//        i.setData(Uri.parse("tel:0612312312"));
//        if (i.resolveActivity(getPackageManager()) != null) {
//      startActivity(i);
//        }*/
//        if (ContextCompat.checkSelfPermission(mcontext, CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            mcontext.startActivity(i);
//        } else {
//            mcontext.requestPermissions(new String[]{CALL_PHONE}, 1);
//        }
//    }



}
