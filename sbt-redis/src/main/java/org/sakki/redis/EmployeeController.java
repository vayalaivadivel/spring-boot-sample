package org.sakki.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/emp")
public class EmployeeController {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeRepo repo;

	@Cacheable(value = "employee", key = "employees", unless = "#result.followers < 12000")
	@GetMapping
	public List<Employee> getAllEmployees() {
		LOG.info("Getting employee details");
		return repo.findAll();
	}

	@PostMapping
	public Long save(Employee emp) {
		return repo.save(emp).getId();
	}

	@Cacheable(value = "employee", key = "#id", unless = "#result.followers < 12000")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Employee getUser(@PathVariable String id) {
		LOG.info("Getting user with ID {}.", id);
		return repo.findById(Long.valueOf(id)).get();
	}

	@CachePut(value = "employee", key = "#employee.id")
	@PutMapping
	public Employee updatePersonByID(@RequestBody Employee user) {
		repo.save(user);
		return user;
	}

	@CacheEvict(value = "employee", allEntries = true)
	@DeleteMapping
	public void deleteUserByID(@PathVariable Long id) {
		LOG.info("deleting person with id {}", id);
		repo.deleteById(id);
	}
}
