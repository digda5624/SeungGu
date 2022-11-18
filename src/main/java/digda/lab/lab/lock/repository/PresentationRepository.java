package digda.lab.lab.lock.repository;

import digda.lab.lab.lock.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {

    @Query("select p from Presentation p where p.id = :presentationId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Presentation findByIdForPessimisticLock(@Param("presentationId") Long presentationId);

    @Query("select p from Presentation p where p.id = :presentationId")
    @Lock(LockModeType.OPTIMISTIC)
    Presentation findByIdForOptimisticLock(@Param("presentationId") Long presentationId);

}
