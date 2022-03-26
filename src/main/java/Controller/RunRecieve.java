package Controller;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Spi;

//
// Power up prim rx have to set to high (bits in register)
// CE = Antenna mode (high = recieve) pin: 25
// SCK = Clock (Ignore? cause sinking clock)
// MISO = Data output (from antenna to pi) pin:
// MOSI = Data input (from pi to antenna)
// pins 19 21 23 = mosi miso sclk
// CSN chip select (Need to be set in order to use chip (Could be hi or lo))



public class RunRecieve {
    public static void main(String[] args) throws InterruptedException {
        GpioController gpio = GpioFactory.getInstance();

        // defines the CE pin
        GpioPinDigitalOutput CEpin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "MyLED", PinState.HIGH);
        GpioPinDigitalInput MISOPin = gpio.
        GpioPinDigitalOutput MOSIPin = gpio.



//        while(true){
//            System.out.println("Toggling LED");
//            pin.toggle();
//            Thread.sleep(1000);
//        }
    }
}
