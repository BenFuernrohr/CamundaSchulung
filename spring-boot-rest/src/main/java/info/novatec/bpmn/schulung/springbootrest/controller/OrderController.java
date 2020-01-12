package info.novatec.bpmn.schulung.springbootrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.bpmn.schulung.springbootrest.requests.PlaceOrderRequest;
import info.novatec.bpmn.schulung.springbootrest.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("v1/placeorder")
    public void checkout(@RequestBody PlaceOrderRequest placeOrder) {
        orderService.placeOrder(placeOrder.getBusinessKey());
    }
}
