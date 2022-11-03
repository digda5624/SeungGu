package digda.lab.lab.lock.repository;

import digda.lab.lab.lock.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
