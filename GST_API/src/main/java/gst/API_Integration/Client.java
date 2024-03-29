package gst.API_Integration;

import java.util.ArrayList;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

/**
This is the API integration to get the GST Details by GST Number

USE: Copy console Details and paste in the Excel then split on the basis of '@' and here are our requirement

 **/
public class Client
{
    public static void main( String[] args )
    {
        System.out.println( "Inside Client, Main" );
        System.out.println( "**************************" );
        Client client = new Client();
        ArrayList<String> gstDetails = client.getGst( "27AAICA3918J1CT" );
        //        ArrayList<String> gstDetails = client.getGstInBatch( null );
        System.out.println( "GST Responses: " + gstDetails );
        if ( client.isValid( gstDetails ) && !gstDetails.isEmpty() )
        {
            System.out.println( "**************************" );
            System.out.println( "GSTNumber@Tradename@LegalName@Address@City@State@PinCode" );
            for ( String response : gstDetails )
            {
                JSONObject obj = null;
                JSONObject obj2 = null;
                JSONObject obj3 = null;
                JSONObject obj4 = null;
                try
                {
                    obj = new JSONObject( response );
                    if ( client.isValid( obj.tryGetString( "message" ) )
                            && obj.tryGetString( "message" ).equalsIgnoreCase( "GSTIN  found." ) )
                    {
                        obj2 = new JSONObject( obj.tryGetString( "data" ) );
                        obj3 = new JSONObject( obj2.tryGetString( "pradr" ) );
                        obj4 = new JSONObject( obj3.tryGetString( "addr" ) );
                        System.out.println( obj2.tryGetString( "gstin" ) + "@" + obj2.tryGetString( "tradeNam" ) + "@"
                                + obj2.tryGetString( "lgnm" ) + "@" + obj3.tryGetString( "adr" ) + "@"
                                + obj4.tryGetString( "dst" ) + "@" + obj4.tryGetString( "stcd" ) + "@"
                                + obj4.tryGetString( "pncd" ) );
                    }
                    else
                    {
                        System.err.println( "Invalid Response" );
                    }
                }
                catch ( JSONException e )
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            System.out.println( "Invalid List of GST Details" );
        }
    }

    public ArrayList<String> getGst( String gstNumber )
    {
        System.out.println( "Inside Client, getGst" );
        Helper helper = new Helper();
        String response = null;
        ArrayList<String> lst = new ArrayList<String>();
        String[] gstArray = gstNumber.split( "," );
        for ( String gst : gstArray )
        {
            response = helper.getGstDetails( gst );
            if ( isValid( response ) )
            {
                lst.add( response );
            }
        }
        if ( isValid( lst ) && !lst.isEmpty() )
        {
            return lst;
        }
        return null;
    }

    public ArrayList<String> getGstInBatch( ArrayList<String> gstNumber )
    {
        System.out.println( "Inside Client, getGstInBatch" );
        Helper helper = new Helper();
        ArrayList<String> lst = new ArrayList<String>();
        String response = null;
        for ( String gst : gstNumber )
        {
            response = helper.getGstDetails( gst );
            if ( isValid( response ) )
            {
                lst.add( response );
            }
        }
        if ( isValid( lst ) && !lst.isEmpty() )
        {
            return lst;
        }
        return null;
    }

    private boolean isValid( Object inObj )
    {
        if ( ( null == inObj ) )
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
