package br.org.cesar.knot.beamsensor.application.controller;


import java.util.List;

import br.org.cesar.knot.beamsensor.application.communication.BeamCommunication;
import br.org.cesar.knot.beamsensor.application.communication.BeamCommunicationFactory;
import br.org.cesar.knot.beamsensor.application.communication.BeamCommunicationFactory.BeamCommunicationProtocol;
import br.org.cesar.knot.beamsensor.application.model.BeamSensor;
import br.org.cesar.knot.beamsensor.application.model.BeamSensorFilter;

/**
 * Created by carlos on 14/03/17.
 */

public class BeamController {
    private BeamCommunication communication;

    public BeamController(){
        try {
            communication = BeamCommunicationFactory.getBeamCommunication(BeamCommunicationProtocol.WSS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BeamSensor> getSensor(BeamSensorFilter filter){
       return communication.getSensors(filter);
    }
}
