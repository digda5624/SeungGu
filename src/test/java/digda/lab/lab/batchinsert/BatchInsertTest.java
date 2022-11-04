package digda.lab.lab.batchinsert;

import digda.lab.lab.batchinsert.domain.Member;
import digda.lab.lab.batchinsert.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BatchInsertTest {
    
    @Autowired
    MemberService memberService;
    
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    @DisplayName("[success] batch_insert_TEST")
    public void batch_insert_TEST(){
        // given
        List<Member> memberList = new ArrayList<>();

        for(int i = 0; i < 100000; i++){
            memberList.add(new Member("test" + i));
        }

        long l = System.currentTimeMillis();
        memberService.jpaSaveAll(memberList);
        System.out.println("소요시간 : " + (System.currentTimeMillis() - l));
    }

    @Test
    @DisplayName("[success] batch_insert_TEST")
    public void batch_insert_TEST_with_JDBCTemplate(){
        // given
        List<Member> memberList = new ArrayList<>();

        for(int i = 0; i < 100000; i++){
            memberList.add(new Member("test" + i));
        }

        long l = System.currentTimeMillis();
        memberService.bulkMemberInsert(memberList);
        System.out.println("소요시간 : " + (System.currentTimeMillis() - l));
    }

}
