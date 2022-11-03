package digda.lab.lab.lock.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
public class Waiting {

    @Id @GeneratedValue
    private Long id;
    private Integer orders;
    private LocalTime localTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Presentation presentation;

    public Waiting(Integer orders, Presentation presentation) {
        this.orders = orders;
        this.localTime = LocalTime.now();
        this.presentation = presentation;
    }

    public static Waiting createWaiting(Integer orders, Presentation presentation) {
        return new Waiting(orders, presentation);
    }

}