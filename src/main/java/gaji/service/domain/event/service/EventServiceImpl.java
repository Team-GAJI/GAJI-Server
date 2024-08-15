package gaji.service.domain.event.service;

import gaji.service.domain.event.code.EventErrorStatus;
import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.domain.RecurringEvent;
import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import gaji.service.domain.event.mapper.EventMapper;
import gaji.service.domain.event.repository.event.EventRepository;
import gaji.service.domain.event.repository.RecurringEvent.RecurringEventRepository;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final RecurringEventRepository recurringEventRepository;

    private final RoomCommandService roomCommandService;

    private final EventMapper eventMapper;


    @Override
    @Transactional(readOnly = true)
    public EventInfoListResponse getEventList(LocalDate date, Long userId) {

        //userId와 date에 맞는 Event을 찾음 (response 값으로 변환)
        List<Event> events = eventRepository.findEventsByDateAndUserId(date, userId);

        //userId와 date에 맞는 요일을 가진 RecurringEvent를 맞음 (response 값으로 변환)
        List<RecurringEvent> recurringEvents = recurringEventRepository.findByDayOfWeekAndDate(date, userId);
        events.addAll(new ArrayList<>(recurringEvents)); //타입 캐스팅해서 추가

        List<EventInfoListResponse.EventInfo> eventInfoList
                = eventMapper.toEventInfoList( //DTO로 변환
                events.stream()
                        //시작시간 기준으로 정렬
                        .sorted(Comparator.comparing(Event::getStartDateTime))
                        .collect(Collectors.toList())
        );

        //Room 리스트 조회
        List<Room> rooms = roomCommandService.findRoomsByUserId(userId);

        //Room과 Date로 UserAssignment 조회
        List<EventInfoListResponse.StudyEventInfo> studyEventInfoList = new ArrayList<>();
        rooms.forEach(room -> {
            roomCommandService.findRoomEventByRoomAndDate(room, date).forEach(
                    roomEvent->{
                        roomEvent.getAssignmentList().forEach(
                                assignment -> {
                                    studyEventInfoList.add( // studyEventInfoList에 추가
                                            eventMapper.toStudyEventInfo(
                                                    roomEvent,room,assignment,roomCommandService.findUserAssignmentByAssignmentAndUserId(assignment, userId)
                                                    )
                                    );
                                }
                        );
                    }
            );

        });

        //response 값 반환
        return EventInfoListResponse.builder()
                .studyEventInfoList(studyEventInfoList)
                .eventInfoList(eventInfoList)
                .build();
    }

    @Override
    @Transactional
    public Long putEvent(LocalDateTime date, Long userId, Long myId , EventInfoRequest request) {

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
