package eventbus;

import model.LocationSignal;

/**
 * Created by Victor on 05-Jul-15.
 */
public class DestinationEvent{

    private LocationSignal destination;

    public DestinationEvent(LocationSignal destination) {
        this.destination = destination;
    }

    public LocationSignal getDestination() {
        return destination;
    }

    public void setDestination(LocationSignal destination) {
        this.destination = destination;
    }
}
