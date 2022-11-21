package digda.lab.lab.lock.domain;

import digda.lab.lab.lock.service.PresentationService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
public class Participation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime time;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Presentation presentation;
    private int checksum;

    public Participation(Presentation presentation) {
        this.time = LocalTime.now();
        this.presentation = presentation;
        this.checksum = PresentationService.i;
    }

    public static Participation createParticipation(Presentation presentation) {
        return new Participation(presentation);
    }
}
