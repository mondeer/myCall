package ke.co.afrobay.mycall;

import java.sql.Date;
import java.text.SimpleDateFormat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
public class MainActivity extends Activity {
    //initialize the objects we will use
    private ImageView dad_img;
    private ImageView mum_img;
    String dad_phone_number = "+254724871111";
    String mum_phone_number = "+254704298857";
    String third_phone_number = "0715605503";
    String fourth_phone_number = "0724392535";
    String emergency_phone_number = "911";
    int sequence1Counter = 555;
    //will hold the return value from callSequenceRecursive() signifying if the first sequence went unanswered
    int sequence1;
    boolean mum_first = false;
    boolean dad_first = false;

    // add PhoneStateListener for monitoring

    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PhoneStateListener phoneListener = new PhoneStateListener();
        final TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instantiate the objects
        dad_img = (ImageView) findViewById(R.id.dad);
        mum_img = (ImageView) findViewById(R.id.mum);
        //call dad if dad's image is clicked
        dad_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //set the dad_flag
                dad_first = true;
                mum_first = false;
                // call makecall function
                makeCall(dad_phone_number);
                //callSequence3();
                //get the duration of the last call and convert it into an int
                int duration = Integer.parseInt(getLastOutgoingCallDuration());
                //check to see if the call did not connect by seeing if the duration is zero


                public void on
                   if ((duration == 0) && (TelephonyManager.CALL_STATE_IDLE == 0)) {
                       sequence1 = callSequenceRecursive(2);
                       if (sequence1 == 0) {
                           Toast.makeText(getApplicationContext(), "first sequence DONE ...",
                                   Toast.LENGTH_LONG).show();
                           //callSequence2();
                           //if callSequenceRecursive returns 0 then no body picked the call throughout the sequence
                           //call the  sendBluetoothSignal()
                       }

               }
            }
        });
        //call mum if mum's picture is clicked
        mum_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //set the mum_flag to 1
                mum_first = true;
                dad_first = false;
                // call makecall function
                makeCall(mum_phone_number);
                //callSequence3();
                //get the duration of the last call and convert it into an int
                int duration = Integer.parseInt(getLastOutgoingCallDuration());
                //check to see if the call did not connect by seeing if the duration is zero
                if (duration == 0) {
                    sequence1 = callSequenceRecursive(2);
                    if (sequence1 == 0) {
                        Toast.makeText(getApplicationContext(), "first sequence DONE ...",
                                Toast.LENGTH_LONG).show();
                        //callSequence2();
                        //if callSequenceRecursive returns 0 then no body picked the call throughout the sequence
                        //call the  sendBluetoothSignal()
                    }
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }//end onCreate method
    /*
    *Method to make a call
    *The parameter is a string containing the phone number to be called
    * does not return anything
     */
    public void makeCall(String phoneNumber) {
        try {
            // set the data
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + (phoneNumber)));
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
            startActivity(callIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Your call has failed...",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public int callSequenceRecursive(int counter) {
        sequence1Counter = counter;
        if (counter == 0) {
            return sequence1Counter;
        } else {
            if (dad_first) {
                //display a message on the screen
                Toast.makeText(getApplicationContext(), "can't reach dad trying MUM instead...",
                        Toast.LENGTH_LONG).show();
                //call mum
                makeCall(mum_phone_number);
                dad_first = false;
                mum_first = true;
            } else if (mum_first) {
                //display a message on the screen
                Toast.makeText(getApplicationContext(), "can't reach mum trying DAD instead...",
                        Toast.LENGTH_LONG).show();
                //call dad
                makeCall(dad_phone_number);
                mum_first = false;
                dad_first = true;
            }
        }
        callSequenceRecursive(--sequence1Counter);
        return sequence1Counter;
    }//end callsequence recursive
    /*
    *method to send a bluetooth signal
    */
    public void sendBluetoothSignal1() {
        //send out a bluetooth signal to sound a horn for a certain period of time
    }//end of method to sendBluetoothSignal
    /*
     * sequence 2 method
     * takes no arguments
     * is called after the first sequence and bluetooth signall methods have been called
     */
    public void callSequence2() {
        // call makecall function
        makeCall(dad_phone_number);
        //get the duration of the last call and convert it into an int
        int duration = Integer.parseInt(getLastOutgoingCallDuration());
        //check to see if the call did not connect by seeing if the duration is zero
        if (duration == 0) {
            Toast.makeText(getApplicationContext(), "can't reach dad trying MUM instead...",
                    Toast.LENGTH_LONG).show();
            makeCall(mum_phone_number);
            int duration1 = Integer.parseInt(getLastOutgoingCallDuration());
            if (duration1 == 0) {
                Toast.makeText(getApplicationContext(), "can't reach mum trying uncle instead...",
                        Toast.LENGTH_LONG).show();
                //call the third phone number
                makeCall(third_phone_number);
                int duration2 = Integer.parseInt(getLastOutgoingCallDuration());
                if (duration2 == 0) {
                    Toast.makeText(getApplicationContext(), "sorry can't reach any of them am gonna flash the lights..",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }//end of callsequence2
    /*
    * sends out the second bluetooth signal to flash lights
     */
    public void sendBluetoothSignal2() {
        //send out a bluetooth signal to flash lights for a certain period of time
    }//end of method to sendBluetoothSignal2
    /*
 * sequence 2 method
 * takes no arguments
 * is called after the first sequence and bluetooth signall methods have been called
 */
    public void callSequence3() {
        // call makecall function
        makeCall(dad_phone_number);
        //get the duration of the last call and convert it into an int
        int duration = Integer.parseInt(getLastOutgoingCallDuration());
        //check to see if the call did not connect by seeing if the duration is zero
        if (duration == 0) {
            Toast.makeText(getApplicationContext(), "can't reach dad trying MUM instead...",
                    Toast.LENGTH_LONG).show();
            makeCall(mum_phone_number);
            int duration1 = Integer.parseInt(getLastOutgoingCallDuration());
            if (duration1 == 0) {
                Toast.makeText(getApplicationContext(), "can't reach mum trying Uncle instead...",
                        Toast.LENGTH_LONG).show();
                //call the third phone number
                makeCall(third_phone_number);
                int duration2 = Integer.parseInt(getLastOutgoingCallDuration());
                if (duration2 == 0) {
                    Toast.makeText(getApplicationContext(), "can't reach Uncle trying Auntie instead...",
                            Toast.LENGTH_LONG).show();
                    //call the third phone number
                    makeCall(fourth_phone_number);
                    int duration3 = Integer.parseInt(getLastOutgoingCallDuration());
                    if (duration3 == 0) {
                        Toast.makeText(getApplicationContext(), "CALLING THE EMERGENCY CONTACT...",
                                Toast.LENGTH_LONG).show();
                        makeCall(emergency_phone_number);
                    }
                }
            }
        }
    }//end of callsequence3
    /*
     *method to get duration of last outgoing call
     */
    public String getLastOutgoingCallDuration() {
        String output = null;
        final Uri callog = CallLog.Calls.CONTENT_URI;
        Cursor cursor = null;
        try {
            // Query all the columns of the records that matches "type=2"
            // (outgoing) and orders the results by "date"
            cursor = getContentResolver().query(callog, null,
                    CallLog.Calls.TYPE + "=" + CallLog.Calls.OUTGOING_TYPE,
                    null, CallLog.Calls.DATE);
            final int durationCol = cursor
                    .getColumnIndex(CallLog.Calls.DURATION);
            // Retrieve only the last record to get the last outgoing call
            if (cursor.moveToLast()) {
                // Retrieve only the duration column
                output = cursor.getString(durationCol);
            }
        } finally {
            // Close the resources
            if (cursor != null) {
                cursor.close();
            }
        }
        return output;
    }//end of method to get last outgoing call
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class TeleListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // CALL_STATE_IDLE;
                    Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE",
                            Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // CALL_STATE_OFFHOOK;
                    Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",
                            Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // CALL_STATE_RINGING
                    Toast.makeText(getApplicationContext(), incomingNumber,
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "CALL_STATE_RINGING",
                            Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }
}//end MainActivity Class
