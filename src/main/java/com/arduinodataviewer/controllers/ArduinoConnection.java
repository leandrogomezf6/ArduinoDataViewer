
package com.arduinodataviewer.controllers;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author Leandro Gómez
 * @version 0.0.1
 */
public class ArduinoConnection{
    
    private static final int BAUD_RATE = 9600;
    private static final int DATA_BITS = 8;
    private static final int STOP_BITS = 1;
    private static final int PARITY = 0;
    
    private String data = "";
    private int bytesUsed = 0;

    private SerialPort serialPort;
    
    /**
    * Constructor de la clase.
    * @param portName Nombre del puerto donde esta conectado el Arduino.
    */
    public ArduinoConnection(String portName) {
        serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(BAUD_RATE, DATA_BITS, STOP_BITS, PARITY);
            Thread.sleep(1000);
            
            serialPort.addEventListener(listener);
            clearSerialPort();
        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private SerialPortEventListener listener = new SerialPortEventListener() {

        @Override
        public void serialEvent(SerialPortEvent spe) {
            if (spe.isRXCHAR()) {
                try {
                    bytesUsed = spe.getEventValue();
                    data = serialPort.readString(bytesUsed);
                    System.out.print(data);
                    clearSerialPort();
                } catch (SerialPortException ex) {
                    ex.printStackTrace();
                }    
            }
        }
    };
    
    /**
    * 
    * Devuelve los bytes usados en la transferencia de datos.
    */
    public int getbytesUsed(){
        return bytesUsed;
    }
    
    /**
    * 
    * Devuelve los datos optenidos del Arduino, si no se encuentran devuelve -1.
    */
    public String getStringData(){
        return (data.isEmpty()) ? "-1" : data;
    }
    
    /**
    * 
    * Para la conexión con Arduino despues de esperar 500 ms.
    */
    public void stopConection(){
        try {
            Thread.sleep(500);
            serialPort.closePort();
        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
    * 
    * Limpia tanto el TX como RX para una optención de datos más uniforme.
    */
    public void clearSerialPort(){
        try {
            serialPort.purgePort(SerialPort.PURGE_RXCLEAR);
            serialPort.purgePort(SerialPort.PURGE_TXCLEAR);
        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }
    }
    
  
    public static void main(String[] args) throws SerialPortException {
        new ArduinoConnection("/dev/ttyACM0");
    }
}
