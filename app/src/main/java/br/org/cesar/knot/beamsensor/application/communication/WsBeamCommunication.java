package br.org.cesar.knot.beamsensor.application.communication;


import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.application.model.BeamSensor;
import br.org.cesar.knot.beamsensor.application.model.BeamSensorFilter;
import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.event.Event;
import br.org.cesar.knot.lib.exception.SocketNotConnected;
import br.org.cesar.knot.lib.model.KnotList;

/**
 * Created by carlos on 14/03/17.
 */

public class WsBeamCommunication implements BeamCommunication {

    private static FacadeConnection connection;
    private static final String ENDPOINT = "";
    private static final String UUID_OWNER = "";
    private static final String TOKEN_OWNER = "";

    public WsBeamCommunication() {
        if (connection == null)
            connection = FacadeConnection.getInstance();

    }

    public void initialise(){
        // Configuring the API
        try {
            connection.setupSocketIO(ENDPOINT, UUID_OWNER, TOKEN_OWNER);
            connection.connectSocket(ENDPOINT);
        } catch (SocketNotConnected socketNotConnected) {
            socketNotConnected.printStackTrace();
        }
    }

    public void close(){
        connection.disconnectSocket();
    }

    @Override
    public List<BeamSensor> getSensors(BeamSensorFilter filter) {
        final List<BeamSensor> result = new ArrayList<>();
        try{
            connection.socketIOGetDeviceList(new KnotList<>(BeamSensor.class), filter.getQuery(), new Event<List<BeamSensor>>() {
                @Override
                public void onEventFinish(List<BeamSensor> beamSensors) {
                    for (BeamSensor b :
                            beamSensors) {
                        result.add(b);
                    }
                }

                @Override
                public void onEventError(Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }
}
