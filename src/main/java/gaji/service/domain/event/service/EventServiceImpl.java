package gaji.service.domain.event.service;

import gaji.service.domain.event.code.EventErrorStatus;
import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import gaji.service.domain.event.repository.EventRepository;
import gaji.service.domain.event.repository.RecurringEventRepository;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.repository.UserRepository;
import gaji.service.domain.user.service.UserCommandServiceImpl;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private  final RecurringEventRepository recurringEventRepository;

    private final UserCommandServiceImpl userCommandService;

    private final

    private final int MAX_EVENT_COUNT = 20;

    @Override
    @Transactional(readOnly = true)
    public EventInfoListResponse getEventList(DateTime date, Long userId) {

        User user = userCommandService.findById(userId);

        //userId에 맞는 Event을 찾음 (response 값으로 변환)
        List<Event> events = eventRepository.findEventsByWriter(user);

        //userId에 맞는 RecurringEvent를 맞음 (response 값으로 변환)
        List<Event> recurringEvents = recurringEventRepository.findRecurringEventsByWriter(user);

        //스터디 관련 일정들 조회 (response 값으로 변환)
        //시작 시간을 기준으로 정렬

        //response 값 반환
        return null;
    }

    @Override
    @Transactional
    public Long putEvent(DateTime date, Long userId, Long myId ,EventInfoRequest request) {

        // 나의 userId인지 확인하기
        if(!userId.equals(myId)){
            throw new RestApiException(EventErrorStatus._EVENT_NOT_MY_EVENT); // 본인의 이벤트가 아님
        }

        // 스터디 일정 제외하고 나의 일정이 20개 이상인지 확인하기 (20개 이상이면 에러)

        //Event 생성
        //Event 생성 후 EventId 반환
        return null;
    }

    @Override
    @Transactional
    public Long patchEvent(Long eventId, Long userId, EventInfoRequest request) {

        //EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        //Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

        // Event 수정
        // EventId 반환

        return null;
    }

    @Override
    @Transactional
    public Long deleteEvent(Long eventId, Long userId) {

        // EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        // Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

        // Event 삭제
        // EventId 반환

        return null;
    }

    @Override
    @Transactional
    public Long putEventComplete(Long eventId, Long userId) {

        // EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        // Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

        // Event가 이미 완료된 상태인지 확인하기

        // Event 완료 처리
        // EventId 반환

        return null;
    }

    @Override
    @Transactional
    public Long deleteEventComplete(Long eventId, Long userId) {

        // EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        // Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

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
    public void checkMyEvent(Event event, Long userId) {
        if (!event.getWriter().getId().equals(userId)) {
            throw new RestApiException(EventErrorStatus._EVENT_NOT_MY_EVENT);
        }
    }

}
