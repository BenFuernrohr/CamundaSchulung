package info.novatec.bpmn.schulung.testing.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class InjectableDelegate implements JavaDelegate {

    private Expression text;

    @Override
    public void execute(DelegateExecution execution) {
        String expressionValue = (String) text.getValue(execution);
        System.out.println("Injectable Delegate executed with argument " + expressionValue);
    }

}
