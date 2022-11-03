package digda.lab.lab.lock.service;

import digda.lab.lab.lock.domain.Presentation;
import digda.lab.lab.lock.repository.PresentationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PresentationServiceTest {

    @Autowired
    PresentationRepository presentationRepository;
    @Autowired
    PresentationService presentationService;

    @Test
    @DisplayName("[success] 설명회_신청_락없이")
    public void 설명회_신청_락없이() throws InterruptedException {
        // given

        Presentation presentation = Presentation.createPresentation("락없는 설명회", 10);
        presentationRepository.save(presentation);

        for(int i = 0; i < 200; i++) {
            new Thread(() -> {
                    presentationService.registerWithNoLock(presentation.getId());
            }).start();
        }
        Thread.sleep(6000);
    }

    @Test
    @DisplayName("[success] 설명회_신청_비관락")
    public void 설명회_신청_비관락() throws InterruptedException {
        // given

        Presentation presentation = Presentation.createPresentation("비관적락 설명회", 10);
        presentationRepository.save(presentation);

        for(int i = 0; i < 200; i++) {
            new Thread(() -> {
                presentationService.registerWithPessimisticLock(presentation.getId());
            }).start();
        }
        Thread.sleep(6000);
    }

    @Test
    @DisplayName("[success] 설명회_신청_낙관락")
    public void 설명회_신청_낙관락() throws InterruptedException {
        // given

        Presentation presentation = Presentation.createPresentation("락없는 설명회", 10);
        presentationRepository.save(presentation);

        for(int i = 0; i < 200; i++) {
            new Thread(() -> {
                presentationService.registerWithOptimisticLock(presentation.getId());
            }).start();
        }
        Thread.sleep(6000);
    }


}