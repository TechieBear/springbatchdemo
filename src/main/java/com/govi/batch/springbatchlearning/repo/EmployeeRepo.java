
package com.govi.batch.springbatchlearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.govi.batch.springbatchlearning.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {

}
