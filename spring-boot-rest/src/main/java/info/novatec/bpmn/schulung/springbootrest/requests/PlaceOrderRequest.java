package info.novatec.bpmn.schulung.springbootrest.requests;

public class PlaceOrderRequest {

    private String businessKey;

    public PlaceOrderRequest() {

    }

    public PlaceOrderRequest(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;

    }
}
