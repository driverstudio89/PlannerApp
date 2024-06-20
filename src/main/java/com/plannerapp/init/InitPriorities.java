package com.plannerapp.init;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.enums.PriorityName;
import com.plannerapp.repo.PriorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;



@Component
public class InitPriorities implements CommandLineRunner {

    private Map<PriorityName, String> priorityDescriptions = Map.of(
            PriorityName.URGENT, "An urgent problem that blocks the system use until the issue is resolved.",
            PriorityName.IMPORTANT, "A core functionality that your product is explicitly supposed to perform is compromised.",
            PriorityName.LOW, "Should be fixed if time permits but can be postponed.");

    private final PriorityRepository priorityRepository;

    public InitPriorities(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (priorityRepository.count() > 0) {
            return;
        }

        for (PriorityName priorityName : priorityDescriptions.keySet()) {
            Priority priority = new Priority(priorityName, priorityDescriptions.get(priorityName));
            priorityRepository.save(priority);
        }
    }
}
