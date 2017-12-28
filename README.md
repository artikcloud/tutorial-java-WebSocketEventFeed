# Tutorial Java event feed WebSocket

This is a starter code to connect to ARTIK Cloud [event feed WebSocket (/events)](https://developer.artik.cloud/documentation/data-management/rest-and-websockets.html#event-feed-websocket) endpoint using ARTIK Cloud Java SDK. By running this sample, you will learn to:

- Connect to the ARTIK Cloud Event feed WebSocket url.
- Monitor realtime events sent to ARTIK Cloud by the devices.

**Consult the documentation for the [supported events](https://developer.artik.cloud/documentation/data-management/rest-and-websockets.html#event-feed-websocket).**

## Requirements

- Java 7 or higher 
- [Apache Maven](https://maven.apache.org/download.cgi)

## Setup

### Setup at ARTIK Cloud

1. Login to [ARTIK Cloud API Console](https://developer.artik.cloud/api-console/).
2. Retrieve your user ID and a [user token](https://developer.artik.cloud/documentation/tools/api-console.html#find-your-user-token) using our api-console, or via oauth2 authentication with your own application. 

### Setup Java Project

1. Clone this repository if you haven't already done so.

2. At the root directory and run the command:

   ```
   mvn clean package
   ```

   The executable `websocket-event-monitor-x.x.jar` is generated under the target directory.

3. Run the command at the target directory to learn the usage:

   ```
   java -jar websocket-event-monitor-x.x.jar
   ```

## Demo:

 1. Start and run the following command in the target directory.

```
java -jar websocket-monitor-x.x.jar -uid YOUR_USER_ID -t YOUR_USER_TOKEN
```

Now you can monitor all types of events triggered by the devices on your account.

 2. If connecting a device to ARTIK cloud on device channel WebSocket (e.g. [running this sample](https://github.com/artikcloud/tutorial-java-WebSocketDeviceChannel), you should see the following output

```
Received event:[class EventFeedData {
    event: device.status.online
    ts: 1514400645581
    data: {uid=77edb, did=81788}
}]
```

Stop WebSocket device channel on the device. You should see the new event 'device.status.online' in the output

 3. At My ARTIK Cloud, connect a new device and then delete it. You should see the output like the following:

```
Received event:[class EventFeedData {
    event: device.new
    ts: 1514425057542
    data: {uid=77edbc617b32, aid=d18f11ef, did=c19dc9e, dtid=dt013005}
}]
Received event:[class EventFeedData {
    event: device.connected
    ts: 1514425057542
    data: {uid=77edbc617b32, aid=d18f11ef, did=c19dc9e, dtid=dt013005}
}]
Received ping with ts:[1514425069806]
Received event:[class EventFeedData {
    event: device.disconnected
    ts: 1514425078317
    data: {uid=77edbc617b32, aid=d18f11ef, did=c19dc9e, dtid=dt013005}
}]
Received event:[class EventFeedData {
    event: device.deleted
    ts: 1514425078317
    data: {uid=77edbc617b32, aid=d18f11ef, did=c19dc9e, dtid=dt013005}
```

4. You can try [other supported events](https://developer.artik.cloud/documentation/data-management/rest-and-websockets.html#event-feed-websocket).

## More about ARTIK Cloud

If you are not familiar with ARTIK Cloud, we have extensive documentation at <https://developer.artik.cloud/documentation>

The full ARTIK Cloud API specification can be found at <https://developer.artik.cloud/documentation/api-reference/>

Peek into advanced sample applications at [https://developer.artik.cloud/documentation/tutorials/code-samples](https://developer.artik.cloud/documentation/tutorials/code-samples/)

To create and manage your services and devices on ARTIK Cloud, visit the Developer Dashboard at [https://developer.artik.cloud](https://developer.artik.cloud/)

## License and Copyright

Licensed under the Apache License. See [LICENSE](./LICENSE).

Copyright (c) 2017 Samsung Electronics Co., Ltd.
