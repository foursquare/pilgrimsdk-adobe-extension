# Pilgrim SDK extension for Adobe Launch.

If you want to publish an update to the extension you will need a private key and a public cert in order to upload.

```
openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -keyout private.key -out adobe-io-public.crt
```

Once you have those, you will have to login to console.adobe.io and add your public key to the `Pilgrim SDK Launch` integration.

There are 3 pieces to this repo. The `web`, `ios` and `android`. The `web` directory contains all of the code and configuration 
that partners will see when they go to add the extension to their app in the Adobe Launch console. We define the UI for the settings 
configurations and the data types that are available on an event. To test changes, modify the files, cd into the web directory and run 
`npx @adobe/reactor-sandbox`. That will startup a localhost where you can view and validate your changes. When you are pleased the next 
step is to run `npx @adobe/reactor-packager`. That will spit out a zip. Then finally you run `npx @adobe/reactor-uploader`. Select the `production` 
option for the first question, then the `Adobe I/O` option for the section question. For the last part you will need to be logged into 
console.adobe.io and on the `Pilgrim SDK Launch` page to get the values to enter. Once thats done you've successfully uploaded a new version 
of the Extension to Launch.


