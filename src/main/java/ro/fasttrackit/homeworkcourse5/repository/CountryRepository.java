package ro.fasttrackit.homeworkcourse5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homeworkcourse5.model.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
}
