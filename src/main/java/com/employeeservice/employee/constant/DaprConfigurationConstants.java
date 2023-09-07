package com.employeeservice.employee.constant;

import org.springframework.context.annotation.Configuration;

@Configuration
public interface DaprConfigurationConstants {

    String SCHEDULER_NOTIFICATION_TOPIC="schedulertopic";

    String EMPLOYEE_ADD_NOTIFICATION_TOPIC="testdapr";

    String PUB_SUB="employee-pubsub";

    String EMPLOYEE_NOTIFICATION_ROUTE="/add-employee-notification";

    String SCHEDULER_NOTIFICATION_ROUTE="/scheduler-notification";

}
