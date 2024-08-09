package gaji.service.domain.event.service;

import gaji.service.domain.event.code.EventErrorStatus;
import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import gaji.service.domain.event.repository.EventRepository;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    private final int MAX_EVENT_COUNT = 20;

    @Override
    @Transactional(readOnly = true)
    public EventInfoListResponse getEventList(DateTime date, Long userId) {
        //userId에 맞는 일정들을 찾음 (response 값으로 변환)
        //스터디 관련 일정들 조회 (response 값으로 변환)
        //시작 시간을 기준으로 정렬

        //response 값 반환
        return null;
    }

    @Override
    @Transactional
    public Long putEvent(DateTime date, Long userId ,EventInfoRequest request) {

        // 나의 userId인지 확인하기
        // 스터디 일정 제외하고 나의 일정이 20개 이상인지 확인하기 (20개 이상이면 에러)

        //Event 생성
        //Event 생성 후 EventId 반환
        return null;
    }

    @Override
    @Transactional
    public Long patchEvent(Long eventId, EventInfoRequest request) {

        //EventId에 맞는 Event 찾기

        //Event가 내 것인지 확인하기

        // Event 수정
        // EventId 반환

        return null;
    }

    @Override
    @Transactional
    public Long deleteEvent(Long eventId) {

        // EventId에 맞는 Event 찾기

        // Event가 내 것인지 확인하기

        // Event 삭제
        // EventId 반환

        return null;
    }

    @Override
    @Transactional
    public Long putEventComplete(Long eventId) {

        // EventId에 맞는 Event 찾기

        // Event가 내 것인지 확인하기
        // Event가 이미 완료된 상태인지 확인하기

        // Event 완료 처리
        // EventId 반환

        return null;
    }

    @Override
    @Transactional
    public Long deleteEventComplete(Long eventId) {

        // EventId에 맞는 Event 찾기

        // Event가 내 것인지 확인하기
        // Event가 완료되지 않은 상태인지 확인하기

        // Event 완료 취소 처리
        // EventId 반환

        return null;
    }

    // EventId에 맞는 Event 찾기
    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus._EVENT_NOT_FOUND));
    }

    // Event가 내 것인지 확인하기
    @Override
    public void checkMyEvent(Long eventId, Long userId) {
        Event event = findEventById(eventId);
        if (!event.getWriter().getId().equals(userId)) {
            throw new RestApiException(EventErrorStatus._EVENT_NOT_MY_EVENT);
        }
    }

}
