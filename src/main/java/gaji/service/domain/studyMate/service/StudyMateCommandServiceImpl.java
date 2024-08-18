package gaji.service.domain.studyMate.service;

import gaji.service.domain.studyMate.entity.StudyMate;
import gaji.service.domain.studyMate.repository.StudyMateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyMateCommandServiceImpl implements StudyMateCommandService{
    private final StudyMateRepository studyMateRepository;

    public void saveStudyMate(StudyMate studyMate) {
        studyMateRepository.save(studyMate);
    }
}
