package Controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

import java.io.IOException;
import java.util.Arrays;

public class Run {
    public static void main(String[] args) throws IOException {
        System.out.println("Running");
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput CEpin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "CE", PinState.LOW);
        GpioPinDigitalInput Clock = gpio.provisionDigitalInputPin(RaspiPin.GPIO_14);
        GpioPinDigitalInput RX = gpio.provisionDigitalInputPin(RaspiPin.GPIO_13);



        byte PRIM_RX = (byte) 0x00;
        byte Acknowledgement = (byte) 0x01;
        byte AddressWidth = (byte) 0x03;
        byte frequencyChannel = (byte) 0x05;

//        Creates a spi object on channel cs0 (One connected on raspberry pi)
        SpiDevice receiver = SpiFactory.getInstance(SpiChannel.CS0, // default spi speed 1 MHz
                SpiDevice.DEFAULT_SPI_SPEED, // default spi mode 0
                SpiDevice.DEFAULT_SPI_MODE);

//        Set PRIM_RX bit to 1
        System.out.println(Arrays.toString(receiver.write(PRIM_RX, (byte) 0x0D)));

//        Set Acknowledgement to 0
        System.out.println(Arrays.toString(receiver.write(Acknowledgement, (byte) 0x00)));

//        Use same address width
        System.out.println(Arrays.toString(receiver.write(AddressWidth, (byte) 0x03)));

//        Use same frequency channel
        System.out.println(Arrays.toString(receiver.write(frequencyChannel, (byte) 0x01)));

//        Set PWR_UP and CE to high
        System.out.println(Arrays.toString(receiver.write(PRIM_RX, (byte) 0x0F)));
        CEpin.toggle();

        System.out.println(Arrays.toString(receiver.write((byte) 0x61)));
        System.out.println("Ended");
//      System.out.println("Clock: " + Clock.getState() + ", Data: " + RX.getState());

    }
}
