package digda.lab.lab.batchinsert;

import digda.lab.lab.batchinsert.domain.Member;
import digda.lab.lab.batchinsert.repository.MemberRepository;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PersistTest.MemberRepo.class)
public class PersistTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberRepo memberRepo;

    @Test
    @DisplayName("[fail] 멤버 저장 키생성 전략 없음")
    public void Member_persist(){
        // given
        Member member = new Member();
        // when
        Assertions.assertThrows(JpaSystemException.class, () -> {
            memberRepository.save(member);
        });
        // then
    }

    @Test
    @DisplayName("[success] 멤버_저장_성공_키생성X")
    public void 멤버_저장_성공_키생성X(){
        // given
        Member member = new Member(1L, "test");

        // when
        Member save = memberRepository.save(member);
    }

    @Test
    @DisplayName("[success] 엔티티매니저_사용")
    public void 엔티티매니저_사용(){
        // given
        Member member = new Member(1L, "test");
        // when
        memberRepo.save(member);
        // then
    }



    @Test
    @DisplayName("[success] 멤버_저장_성공_키생성")
    @Transactional
    public void 멤버_저장_성공_키생성(){
        // given
        Member member = new Member("test");
        Member member1 = new Member("test");

        // when
        memberRepository.save(member);
        System.out.println("===== 지연 되는 지?");
        memberRepository.save(member1);
    }


    @Component
    public static class MemberRepo {

        @Autowired
        EntityManager em;

        @Transactional
        public Member save(Member member) {
            em.merge(member);
            return member;
        }
    }
}
