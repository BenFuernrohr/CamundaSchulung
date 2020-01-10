
package info.novatec.bpmn.schulung.worker;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class WorkerMainClass {

    private final static Logger LOGGER = Logger.getLogger(WorkerMainClass.class.getName());

    private ExternalTaskClient paymentWorker;

    public static void main(String[] args) {
        WorkerMainClass workerMainClass = new WorkerMainClass();
        workerMainClass.init();
    }

    private void init() {
        initPaymentWorker();
    }

    private void initPaymentWorker() {
        paymentWorker = ExternalTaskClient.create()
            .baseUrl("http://localhost:8080/engine-rest")
            .asyncResponseTimeout(10000)
            .build();

        paymentWorker.subscribe("payment")
            .lockDuration(1000)
            .handler((externalTask, externalTaskService) -> {
                LOGGER.info("Charging customer account!");

                externalTaskService.complete(externalTask);
            })
            .open();
    }
}