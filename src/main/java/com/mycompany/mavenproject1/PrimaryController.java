package com.mycompany.mavenproject1;

import com.fazecast.jSerialComm.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    private Label lab;

    @FXML
    private void switchToSecondary() throws IOException {
        SerialPort[] ports = SerialPort.getCommPorts();

        //lab.setText("Available: ");
        SerialPortDataListener serialp = new SerialPortDataListener() {
            public boolean b;

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                SerialPort comPort = event.getSerialPort();
                System.out.println(+comPort.bytesAvailable() + " bytes.");
                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes.");
                b = false;
                v(new String(newData));
            }
        };
        ports[1].addDataListener(serialp);
        ports[1].openPort();
        ports[1].writeBytes("huiyt".getBytes(), 5);
        //App.setRoot("secondary");
    }

    public void v(String s) {
        lab.setText(s);
    }
}
