package info.novatec.bpmn.schulung.testing.process;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.camunda.bpm.extension.mockito.CamundaMockito.registerMockInstance;
import static org.camunda.bpm.extension.mockito.CamundaMockito.verifyExecutionListenerMock;
import static org.camunda.bpm.extension.mockito.CamundaMockito.verifyJavaDelegateMock;
import static org.camunda.bpm.extension.mockito.DelegateExpressions.registerExecutionListenerMock;
import static org.camunda.bpm.extension.mockito.DelegateExpressions.registerJavaDelegateMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import info.novatec.bpmn.schulung.testing.delegates.InjectableDelegate;
import info.novatec.bpmn.schulung.testing.delegates.MyDelegate;
import info.novatec.bpmn.schulung.testing.delegates.MyListener;

@Deployment(resources = "process.bpmn")
public class ProcessTest {

    private static final String PROCESS_DEFINITION_KEY = "test-process";

    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    private RuntimeService runtimeService;

    private InjectableDelegate injectableDelegateMock;

    @Before
    public void setup() {
        runtimeService = rule.getRuntimeService();

        registerExecutionListenerMock(MyListener.class);
        registerJavaDelegateMock(MyDelegate.class);

        injectableDelegateMock = registerMockInstance(InjectableDelegate.class);
    }

    @Test
    public void testHappyPath() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
        assertThat(processInstance).isActive();

        assertThat(processInstance).isWaitingAtExactly("userTask1");
        complete(task(), withVariables("check", true));

        assertThat(processInstance).isEnded();

        verifyExecutionListenerMock(MyListener.class).executed();
        verifyJavaDelegateMock(MyDelegate.class).executed();
    }

    @Test
    public void testUnHappyPath() {
        ProcessInstance processInstance = runtimeService
            .createProcessInstanceByKey(PROCESS_DEFINITION_KEY)
            .startBeforeActivity("userTask1")
            .execute();
        assertThat(processInstance).isActive();

        assertThat(processInstance).isWaitingAtExactly("userTask1");
        complete(task(), withVariables("check", false));

        assertThat(processInstance).isEnded();

        verifyExecutionListenerMock(MyListener.class).executed();
        verify(injectableDelegateMock).execute(any(DelegateExecution.class));
    }
}
