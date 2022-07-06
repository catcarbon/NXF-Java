package io.wtmsb.nxf.gateway;

import io.wtmsb.nxf.manager.ControllerManager;
import io.wtmsb.nxf.manager.TrackManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NxfGateway {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TrackManager trackManager;

	@Autowired
	private ControllerManager controllerManager;


}
