package dev.cah1r.geminiservice.transit.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
  boolean existsByRegistrationOrIdNumber(String registration, Integer idNumber);
}
