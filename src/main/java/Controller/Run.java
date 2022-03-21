package Controller;

import com.pi4j.io.gpio.*;

public class Run {
    public static void main(String[] args) throws InterruptedException {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);

        while(true){
            System.out.println("Toggling LED");
            pin.toggle();
            Thread.sleep(1000);
        }
    }
}
