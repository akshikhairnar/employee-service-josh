package com.employeeservice.employee.scheduler;

import com.employeeservice.employee.constant.DaprConfigurationConstants;
import com.employeeservice.employee.entity.Employee;
import com.employeeservice.employee.externalservices.EmployeeDaprComponent;
import com.employeeservice.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

@Component
public class EmployeeScheduler implements DaprConfigurationConstants {

    @Autowired
    private EmployeeDaprComponent daprComponent;
    @Autowired
    private EmployeeRepository employeeRepository;
    private static final String MY_LOCK_KEY = "someLockKey";
    private final LockRegistry lockRegistry;

    public EmployeeScheduler(JdbcLockRegistry jdbcLockRegistry) {
        this.lockRegistry = jdbcLockRegistry;
    }

    private static final Logger log = LoggerFactory.getLogger(EmployeeScheduler.class);


    @Scheduled(initialDelay = 5000,fixedRate = 20000)
    public void taskExecute() {
        log.info("TaskExecute Method Call");
        UUID uuid = UUID.randomUUID();
        Lock lock = null;
        try {
            lock = lockRegistry.obtain(MY_LOCK_KEY);

        } catch (Exception e) {
            log.error("Unable to obtain lock: {}", MY_LOCK_KEY);
        }

        boolean locked = false;

        try {

            log.info("Attempting to lock with thread: {}", uuid);
            //assert lock != null;
            if (lock.tryLock()) {
                log.info("jdbc lock successful with thread :{}", uuid);
                List<Employee> employeeList=employeeRepository.employeeWithNoProject();
                log.info("Employee details without project: {}",employeeList);
                daprComponent.notifyServices(PUB_SUB,SCHEDULER_NOTIFICATION_TOPIC,employeeList.toString());

                Thread.sleep(5000);
                locked = true;
            } else {
                log.info("jdbc lock unsuccessful with thread :{}", uuid);
            }
        } catch (Exception e) {
            log.error("Resource is locked so system throwing: {}:", e);
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
                log.info("jdbc lock released with thread :{}", uuid);
            }
        }

    }

}
