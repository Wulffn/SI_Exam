import time
import json
from camunda.external_task.external_task import ExternalTask, TaskResult
from camunda.external_task.external_task_worker import ExternalTaskWorker

# configuration for the Client
default_config = {
    "maxTasks": 1,
    "lockDuration": 10000,
    "asyncResponseTimeout": 5000,
    "retries": 3,
    "retryTimeout": 5000,
    "sleepSeconds": 30,
}


def handle_task(task: ExternalTask) -> TaskResult:
    """
    This task handler you need to implement with your business logic.
    After completion of business logic call either task.complete() or task.failure() or task.bpmn_error() 
    to report status of task to Camunda
    """
    # add your business logic here
    # ...
    print('Service invoked')
    cars = task.get_variable("cars")
    car_dict = json.loads(cars)
    de = []
    da = []
    for key in car_dict:
        if car_dict.get(key).get('country') == 'DE':
            de.append(car_dict.get(key).get('price'))
        if car_dict.get(key).get('country') == 'DK':
            da.append(car_dict.get(key).get('price'))

    duty = (sum(da)/len(da))-(sum(de)/len(de))
    # mark task either complete/failure/bpmnError based on outcome of your business logic

    # if failure:
    #     # this marks task as failed in Camunda
    #     return task.failure(error_message="task failed",  error_details="failed task details",
    #                         max_retries=3, retry_timeout=5000)
    # elif bpmn_error:
    #     return task.bpmn_error(error_code="BPMN_ERROR_CODE", error_message="BPMN Error occurred",
    #                             variables={"var1": "value1", "success": False})

    # pass any output variables you may want to send to Camunda as dictionary to complete()
    return task.complete({"duty": duty})

if __name__ == '__main__':
    print('worker started')
    ExternalTaskWorker(worker_id="1", base_url="http://localhost:8082/engine-rest", config=default_config).subscribe("dutycal", handle_task)