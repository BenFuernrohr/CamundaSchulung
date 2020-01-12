package info.novatec.bpmn.schulung.springbootrest.service;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private static final String PLACE_ORDER_PROC_DEF_KEY = "PlaceOrder";

    @Autowired
    private RuntimeService runtimeService;

    @Transactional
    public void placeOrder(String businessKey) {
        runtimeService.startProcessInstanceByKey(PLACE_ORDER_PROC_DEF_KEY, businessKey);
    }

}
