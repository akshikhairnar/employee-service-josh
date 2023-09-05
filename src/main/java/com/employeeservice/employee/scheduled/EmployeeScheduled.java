package com.employeeservice.employee.scheduled;

import com.employeeservice.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

@Component
public class EmployeeScheduled {

    @Autowired
    private EmployeeRepository employeeRepository;
    private static final String MY_LOCK_KEY = "someLockKey";
    private final LockRegistry lockRegistry;

    public EmployeeScheduled(JdbcLockRegistry jdbcLockRegistry) {
        this.lockRegistry = jdbcLockRegistry;
    }

    private static final Logger log = LoggerFactory.getLogger(EmployeeScheduled.class);


    @Scheduled(fixedRate = 7000)
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
            if (lock.tryLock()) {
                log.info("jdbc lock successful with thread :{}", uuid);
                log.info("Employee details without project: {}", employeeRepository.employeeWithNoProject());
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
