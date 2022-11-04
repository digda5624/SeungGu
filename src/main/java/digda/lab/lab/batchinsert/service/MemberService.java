package digda.lab.lab.batchinsert.service;

import digda.lab.lab.batchinsert.domain.Member;
import digda.lab.lab.batchinsert.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JdbcTemplate jdbcTemplate;

    public void jpaSaveAll(List<Member> memberList){
        memberRepository.saveAll(memberList);
    }

    public void bulkMemberInsert(List<Member> memberList){
        jdbcTemplate.batchUpdate(
                "insert into Member (NAME) VALUES (?)",
                memberList,
                1000,
                (PreparedStatement ps, Member member) -> {
                    ps.setString(1, member.getName());
                });
    }

}
