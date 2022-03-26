package Controller;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

import java.awt.*;
import java.io.IOException;

public class SpiClass extends Component {

    protected final SpiDevice spi;

    public SpiClass(SpiChannel channel) throws IOException {
        this.spi = SpiFactory.getInstance(channel);
    }

    private void execute(byte command, byte data) throws IOException {
        spi.write(command, data);
    }

}
