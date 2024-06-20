package com.plannerapp.repo;

import com.plannerapp.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByUserIsNull();


    Task findById(long taskId);

    void deleteById(long id);
}
