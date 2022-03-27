package Controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

import java.io.IOException;
import java.util.Arrays;

public class Run {

//    static GpioController gpio = GpioFactory.getInstance();
//    static GpioPinDigitalOutput CEpin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "CE", PinState.LOW);
////    static GpioPinDigitalOutput CSNpin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "CSN", PinState.LOW);
//    static GpioPinDigitalInput Clock = gpio.provisionDigitalInputPin(RaspiPin.GPIO_14);
//    static GpioPinDigitalInput RX = gpio.provisionDigitalInputPin(RaspiPin.GPIO_13);

    // Creates a spi object on channel cs0 (One connected on raspberry pi)
    // default spi speed 1 MHz
    public static SpiDevice receiver;

    static {
        try {
//          receiver = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED);
            receiver = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED, SpiDevice.DEFAULT_SPI_MODE);
        } catch (IOException ignored) {}
    }

//    MemoryMap
    static byte CONFIG = (byte) 0b00000000; // 0
    static byte EN_AA = (byte) 0b00010000; // 1
    static byte EN_RXADDR = (byte) 0b00001000; // 2
    static byte SETUP_AW = (byte) 0b00011000; // 3
    static byte SETUP_RETR = (byte) 0b00000100; // 4
    static byte RF_CH = (byte) 0b00010100; // 5
    static byte RF_SETUP = (byte) 0b00001100; // 6
    static byte STATUS = (byte) 0b00011100; // 7
    static byte OBSERVE_TX = (byte) 0b00000010; // 8
    static byte CD = (byte) 0b00010010; // 9
    static byte TX_ADDR = (byte) 0b00001010; // 10
    static byte RX_PW_P0 = (byte) 0b00011010; // 11
//  ...

    static byte R_REGISTER = 0b00000000; //
    static byte W_REGISTER = 0b00100000; //

    public static String toBinary( byte byteBoi ){
        return String.format("%8s", Integer.toBinaryString(byteBoi & 0xFF)).replace(' ', '0');
    }

    public static void printBinary(byte[] bytes){
        System.out.println(toBinary(bytes[0]) + " | " + toBinary(bytes[1]));
    }

    public static void printPacket(byte packet){
        System.out.println(toBinary(packet));
    }

    public static byte[] read(byte MemoryMap) throws IOException {
        byte packet = (byte) (MemoryMap | R_REGISTER);   // address byte

        printPacket(packet);
        byte[] result = receiver.write(packet);
        return result;
    }

    public static byte[] write(byte MemoryMap, byte data) throws IOException {
        byte[] packet = new byte[2];
        packet[0] = (byte) (MemoryMap | W_REGISTER);   // address byte
        packet[1] = data;                         // data byte

        printBinary(packet);
        byte[] result = receiver.write(packet);
        return result;
    }

//    public byte[] bashWrite(byte MemoryMap, byte data){
//
//    }

    public static byte[] read_payload() throws IOException {
        return read((byte) 0b01100001);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Running");

//        Set PRIM_RX bit to 1 and do CRC config
        System.out.println("\nPrim bit and CRC config:");
        printBinary(write(CONFIG, (byte) 0b10110000));

//        Set Acknowledgement to 0
        System.out.println("\nAcknowledgement:");
        printBinary(write(EN_AA, (byte) 0b00000000));

//        Use same address width UNSURE
        System.out.println("\nAddress width:");
        printBinary(write(SETUP_AW, (byte) 0b11000000));

//        Use same frequency channel
        System.out.println("\nFrequency Channel:");
        printBinary(write(RF_CH, (byte) 0b10000000));

//        Set PWR_UP and CE to high
        System.out.println("\nPWR_UP:");
        printBinary(write(CONFIG, (byte) 0b11110000));

        System.out.println("\nRead Config:");
        System.out.println(Arrays.toString(read((byte) CONFIG)));

        byte[] result = receiver.write("Hello World".getBytes("US-ASCII"));
        System.out.println(result);
        
        System.out.println("Ended");
//      System.out.println("Clock: " + Clock.getState() + ", Data: " + RX.getState());

    }
}
