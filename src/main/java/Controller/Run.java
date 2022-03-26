package Controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.spi.SpiChannel;

import java.io.IOException;

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
        SpiClass receiver = new SpiClass(SpiChannel.CS0);

//        Set PRIM_RX bit to 1
        receiver.spi.write(PRIM_RX, (byte) 0x0D);

//        Set Acknowledgement to 0
        receiver.spi.write(Acknowledgement, (byte) 0x00);

//        Use same address width
        receiver.spi.write(AddressWidth, (byte) 0x03);

//        Use same frequency channel
        receiver.spi.write(frequencyChannel, (byte) 0x01);

//        Set PWR_UP and CE to high
        receiver.spi.write(PRIM_RX, (byte) 0x0F);
        CEpin.toggle();

        while (true){
            System.out.println("Clock: " + Clock.getState() + ", Data: " + RX.getState());
        }

    }
}
