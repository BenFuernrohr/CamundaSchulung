package info.novatec.bpmn.schulung.testing.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class MyListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        System.out.println("Listener was notified!");
    }
}
