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
import gaji.service.domain.user.service.UserCommandService;
import gaji.service.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
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
    private final UserCommandService userCommandService;

    private final EventMapper eventMapper;


    @Override
    @Transactional(readOnly = true) //날짜에 맞는 나의 이벤트 리스트 조회
    public EventInfoListResponse getEventList(LocalDate date, Long userId) {

        //userId와 date에 맞는 Event을 찾음 (response 값으로 변환)
        List<Event> events = findAllEventByUserIdAndDate(date, userId);

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
    @Transactional  //날짜에 맞는 나의 이벤트 추가
    public Long putEvent(LocalDate date, Long userId, Long myId , EventInfoRequest request) {

        // 나의 userId인지 확인하기
        if(!userId.equals(myId)){
            throw new RestApiException(EventErrorStatus._EVENT_NOT_MY_EVENT); // 본인의 이벤트가 아님
        }

        // 스터디 일정 제외하고 나의 일정이 20개 이상인지 확인하기 (20개 이상이면 에러)
        if( findAllEventByUserIdAndDate(date, userId).size()>20){
            throw new RestApiException(EventErrorStatus._EVENT_OVER_LIMIT); //이벤트가 20개 이상이면 에러
        }

        //반복 이벤트인 경우 RecurringEvent 생성
        if(request.isRecurringStatus()){
            return recurringEventRepository.save(
                    new RecurringEvent(userCommandService.findById(userId), request.getContent(), request.getStartTime(), request.getEndTime())
            ).getId();
        }

        //Event 생성
        return eventRepository.save(
                Event.builder()
                        .content(request.getContent())
                        .writer(userCommandService.findById(userId))
                        .startDateTime(request.getStartTime())
                        .endDateTime(request.getEndTime())
                        .isRecurring(request.isRecurringStatus())
                        .build()
        ).getId();
    }


    @Override
    @Transactional
    public Long patchEvent(Long eventId, Long userId, EventInfoRequest request) {

        //EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        //Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

        // Event 수정
        if(request.isRecurringStatus()){
            //todo: 반복 이벤트로 수정


            //RecurringEvent 생성
            return recurringEventRepository.save(
                    new RecurringEvent(userCommandService.findById(userId), request.getContent(), request.getStartTime(), request.getEndTime())
            ).getId();
        }

        //반복 이벤트 수정 시
        if(event.isRecurring()){
            return recurringEventRepository.findById(eventId).get().updateRecurringEvent(request).getId();
        }
        event.updateEvent(request);

        return event.getId();
    }

    @Override
    @Transactional
    public Long deleteEvent(Long eventId, Long userId) {

        // EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        // Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

        if(event.isRecurring()){
            //반복 이벤트인 경우 RecurringEndDate 설정
            return recurringEventRepository.findById(eventId).get().setRecurrenceEndDate(LocalDateTime.now()).getId();
        }

        // Event 삭제
        eventRepository.delete(event);

        return event.getId();
    }

    @Override
    @Transactional
    public Long putEventComplete(Long eventId, Long userId) {

        // EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        // Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

        // Event가 이미 완료된 상태인지 확인하기
        if(event.getIsCompleted()){
            throw new RestApiException(EventErrorStatus._EVENT_ALREADY_COMPLETED);
        }

        // Event 완료 처리
        return event.setIsCompleted(true).getId();
    }

    @Override
    @Transactional
    public Long deleteEventComplete(Long eventId, Long userId) {

        // EventId에 맞는 Event 찾기
        Event event = findEventById(eventId);

        // Event가 내 것인지 확인하기
        checkMyEvent(event, userId);

        // Event가 완료되지 않은 상태인지 확인하기
        if(!event.getIsCompleted()){
            throw new RestApiException(EventErrorStatus._EVENT_NOT_COMPLETED);
        }

        // 이벤트 완료 취소 처리
        return event.setIsCompleted(false).getId();
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

    //유저가 직접 생성한 Event 리스트 찾기
    private List<Event> findAllEventByUserIdAndDate(LocalDate date, Long userId ){
        //userId와 date에 맞는 Event을 찾음 (response 값으로 변환)
        List<Event> events = eventRepository.findEventsByDateAndUserId(date, userId);

        //userId와 date에 맞는 요일을 가진 RecurringEvent를 맞음 (response 값으로 변환)
        List<RecurringEvent> recurringEvents = recurringEventRepository.findByDayOfWeekAndDate(date, userId);
        events.addAll(new ArrayList<>(recurringEvents)); //타입 캐스팅해서 추가

        return events;
    }

}
