package gst.API_Integration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

public class Helper
{
    private final String GET_GST_URL = "http://sheet.gstincheck.co.in/check/";
    private final String API_KEY     = "613f0f945ed21761e4281983d53d0346";
    public String getGstDetails( String gstNumber )
    {
        try
        {
            System.out.println( "Inside Helper, getGstDetails" );
            URL obj = new URL( GET_GST_URL + API_KEY + "/" + gstNumber );
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod( "GET" );
            con.setRequestProperty( "Accept", "application/json" );
            con.setRequestProperty( "Content-Type", "application/json" );
            int responseCode = con.getResponseCode();
            BufferedReader in;
            if ( responseCode != HttpStatus.SC_OK )
            {
                in = new BufferedReader( new InputStreamReader( con.getErrorStream() ) );
            }
            else
            {
                in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ( ( inputLine = in.readLine() ) != null )
            {
                response.append( inputLine );
            }
            in.close();
            return response.toString();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            System.out.println( "Exception while getting GST Details  for GST Number: " + gstNumber );
            return null;
        }
    }
}
