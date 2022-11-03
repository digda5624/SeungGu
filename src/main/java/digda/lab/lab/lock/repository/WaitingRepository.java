package digda.lab.lab.lock.repository;

import digda.lab.lab.lock.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {

}
