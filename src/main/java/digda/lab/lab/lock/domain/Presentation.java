package digda.lab.lab.lock.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Presentation {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer maxPerson;
    private Integer participantCnt;
    private Integer waitingCnt;

    @Version
    private Long version;

    @OneToMany(mappedBy = "presentation")
    private List<Waiting> waitingList = new ArrayList<>();
    @OneToMany(mappedBy = "presentation")
    private List<Participation> participationList = new ArrayList<>();

    public Presentation(String name, Integer maxPerson, Integer participantCnt, Integer waitingCnt) {
        this.name = name;
        this.maxPerson = maxPerson;
        this.participantCnt = participantCnt;
        this.waitingCnt = waitingCnt;
    }

    public static Presentation createPresentation(String name, Integer maxPerson){
        return new Presentation(name, maxPerson, 0, 0);
    }

    public void plusWaitingCnt() {
        waitingCnt += 1;
    }

    public void plusParticipantCnt() {
        participantCnt += 1;
    }
}
