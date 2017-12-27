package cloud.artik.example;

import cloud.artik.model.Acknowledgement;
import cloud.artik.model.ActionOut;
import cloud.artik.model.EventFeedData;
import cloud.artik.model.MessageOut;
import cloud.artik.model.WebSocketError;
import cloud.artik.websocket.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


/**
* WebSocket starter code to connect to ARTIK Cloud Event feed(/events) WebSocket endpoint.
* 
* ARTIK Cloud Java SDK:
* https://github.com/artikcloud/artikcloud-java
* 
* Additional information available in our documentation:
* https://developer.artik.cloud/documentation/data-management/rest-and-websockets.html#event-feed-websocket
* 
*/


public class WebSocketEventFeed {
	
	private static final int MIN_EXPECTED_ARGUMENT_NUMBER = 3;
	
	static ArrayList<String> queryParams = new ArrayList<String>();

	// here we are using the `user token`
	static String accessToken = null;
		
	// user ID associated with the access token.
	static String uid = null;	
	
	// single `device ID` of interest
	static String deviceId =  null;
	
	// comma delimited string of `device ID` of interest
	static String sdids = null;     

	// parameters below not used in this sample and set to null
	// for use when monitoring by device type
	static String sdtids = null;    //comma delimited string of `device type ID` of interest
	
	
	public static void main(String args[]) throws URISyntaxException, IOException {
		
		if (!succeedParseCommand(args)) {
            printUsage();
             return;
        }
		
		// Use wild card to receive any events described in
		// https://developer.artik.cloud/documentation/data-management/rest-and-websockets.html#event-feed-websocket
		String events = "*";
		
		EventFeedWebSocketCallback eventFeedCallback = new EventFeedWebSocketCallback() {
			@Override
			public void onEvent(EventFeedData event) {
				System.out.println(String.format("Received event:[%s]", event));
				
			}
		};
		
		ArtikCloudWebSocketCallback generalCallback = new ArtikCloudWebSocketCallback() {
			@Override
			public void onOpen(int code, String message) {
				System.out.println(String.format("Connection successful with code:[%d]", code));
			}
			
			@Override
			public void onClose(int arg0, String arg1, boolean arg2) {
				System.out.println(String.format("Connction closed with [%d][%s][%b]", arg0, arg1, arg2));
			}
		
			@Override
			public void onMessage(MessageOut message) {
				System.out.println(String.format("Received message:[%s]", message));
			}
			
			@Override
			public void onAction(ActionOut actionOut) {
				System.out.println(String.format("Received action:[%s]", actionOut));
			}
			
			@Override
			public void onPing(long ts) {
				System.out.println(String.format("Received ping with ts:[%d]", ts));
			}
			
			@Override
			public void onError(WebSocketError error) {
				System.out.println(String.format("Received error:[%s]", error));
			}
			
			@Override
			public void onAck(Acknowledgement acknowledgement) {
				System.out.println(String.format("Received Ack [%s]", acknowledgement));
			}
		};
		
		// sets the parameters and callback function for the WebSocket connection
		EventFeedWebSocket ws = 
				new EventFeedWebSocket(accessToken, deviceId, sdids, uid, events, generalCallback, eventFeedCallback);

		System.out.println(String.format("Connecting to: wss://api.artik.cloud/v1.1/events?authorization=bearer+%s", accessToken));
		
		System.out.println("With query parameters:");
		
		for(String params: queryParams ) {
			 System.out.println("  "  + params);
		}
		
		// fires the request to make WebSocket connection
		ws.connect();
				
		System.out.println("Status: " + ws.getConnectionStatus());
		
		
	}
	
	// Usage: event-monitor -token xxx <== token can be user token
	private static boolean succeedParseCommand(String args[]) {
	       if (args.length < MIN_EXPECTED_ARGUMENT_NUMBER) {
	           return false; 
	       }
	       
	       int index = 0;
	       while (index < args.length) {
	           String arg = args[index];
	           
	           if ("-uid".equals(arg)) {
	        	   ++index; // Move to the next argument, value for parameter 
	        	   uid = args[index];
	           } else if ("-t".equals(arg)) {
	               ++index; // Move to the next argument, value for parameter
	               accessToken = args[index];
	           } else {  	        	   
	        	   return false;
	           }
	           
	           //store as query param format for printing without the '-' flag prefix
	           queryParams.add(arg.substring(1) + "=" + args[index]);
	           
	           ++index;
	       }
	       
	       // must provide a token
	       if (accessToken == null ) {
	    	   System.out.println("access token null");
	           return false;
	       }
	       
	       // one of device (device id), sdids (list of device ids), uid (user id) must be set
	       if (deviceId == null && sdids == null && uid == null) {
	    	   
	    	   System.out.println("device id or sdids null");
	    	   return false;
	       }
	       
	       return true;
	   }
	
	private static void printUsage() {
	       System.out.println("Usage: java -jar websocket-event-monitor-x.x.jar" + " -uid YOUR_USER_ID -t YOUR_USER_TOKEN");
	      
	       System.out.println("\n       monitor all events of the devices owned by this user");
	       System.out.println("       consult the following link for supported events");
	       System.out.println("      https://developer.artik.cloud/documentation/data-management/rest-and-websockets.html#event-feed-websocket");
	       
	}

}

