package dev.cah1r.geminiservice.transit.driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface DriverRepository extends JpaRepository<Driver, UUID> {

  Optional<Driver> findDriverByPhoneNumber(String phone);
}
