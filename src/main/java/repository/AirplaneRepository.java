package repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import entity.Airplane;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
}
