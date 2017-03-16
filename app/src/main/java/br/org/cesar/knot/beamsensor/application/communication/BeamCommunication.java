package br.org.cesar.knot.beamsensor.application.communication;


import java.util.List;

import br.org.cesar.knot.beamsensor.application.model.BeamSensor;
import br.org.cesar.knot.beamsensor.application.model.BeamSensorFilter;

/**
 * Created by carlos on 09/03/17.
 */

public interface BeamCommunication {

    List<BeamSensor> getSensors(BeamSensorFilter filter);
}
