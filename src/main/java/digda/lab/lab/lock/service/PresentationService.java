package digda.lab.lab.lock.service;

import digda.lab.lab.lock.domain.Participation;
import digda.lab.lab.lock.domain.Presentation;
import digda.lab.lab.lock.domain.Waiting;
import digda.lab.lab.lock.repository.ParticipationRepository;
import digda.lab.lab.lock.repository.PresentationRepository;
import digda.lab.lab.lock.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PresentationService {

     private final ParticipationRepository participationRepository;
     private final PresentationRepository presentationRepository;
     private final WaitingRepository waitingRepository;
     public static int i = 0;

     @PostConstruct
     public void init(){
         Presentation presentation = Presentation.createPresentation("설명회", 10);
         presentationRepository.save(presentation);
     }

     @Transactional
     public void registerWithNoLock(Long presentationId) {

         Presentation presentation = presentationRepository.findById(presentationId).get();
         log.info("presentation 상태 : {}/{} (신청/최대) | {} (대기)",
                 presentation.getParticipantCnt(), presentation.getMaxPerson(), presentation.getWaitingCnt());

         if (canParticipate(presentation)){
             participate(presentation);
         } else {
             wait(presentation);
         }
     }

     @Transactional
     public void registerWithPessimisticLock(Long presentationId){
         Presentation presentation = presentationRepository.findByIdForPessimisticLock(presentationId);
         i++;
         log.info("presentation 상태 : {}/{} (신청/최대) | {} (대기)",
                 presentation.getParticipantCnt(), presentation.getMaxPerson(), presentation.getWaitingCnt());

         if (canParticipate(presentation)){
             participate(presentation);
         } else {
             Waiting wait = wait(presentation);
         }
     }

    @Transactional
    public void registerWithOptimisticLock(Long presentationId){
        Presentation presentation = presentationRepository.findByIdForOptimisticLock(presentationId);
        log.info("presentation 상태 : {}/{} (신청/최대) | {} (대기)",
                presentation.getParticipantCnt(), presentation.getMaxPerson(), presentation.getWaitingCnt());

        if (canParticipate(presentation)){
            participate(presentation);
        } else {
            wait(presentation);
        }
    }

    private boolean canParticipate(Presentation presentation){
         // 설명회에서 수용가능한 인원
         Integer maxPerson = presentation.getMaxPerson();
         // 신청 인원
         Integer participantCnt = presentation.getParticipantCnt();
         return maxPerson > participantCnt;
     }

     private Participation participate(Presentation presentation){
         // 설명회 인원수 증가
         presentation.plusParticipantCnt();

         Participation participation = Participation.createParticipation(presentation);
         return participationRepository.save(participation);
     }

     private Waiting wait(Presentation presentation){
         // 대기 인원 수 증가
         presentation.plusWaitingCnt();
         // 대기 번호 부여
         Integer waitingCnt = presentation.getWaitingCnt();

         Waiting waiting = Waiting.createWaiting(waitingCnt, presentation);
         return waitingRepository.save(waiting);
     }
}
