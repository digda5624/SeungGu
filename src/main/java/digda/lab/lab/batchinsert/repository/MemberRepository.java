package digda.lab.lab.batchinsert.repository;


import digda.lab.lab.batchinsert.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
