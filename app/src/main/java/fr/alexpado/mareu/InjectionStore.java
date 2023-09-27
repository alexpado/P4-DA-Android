package fr.alexpado.mareu;

import fr.alexpado.mareu.interfaces.repositories.MeetingRepository;
import fr.alexpado.mareu.interfaces.repositories.RoomRepository;
import fr.alexpado.mareu.interfaces.repositories.UserRepository;
import fr.alexpado.mareu.repositories.FakeMeetingRepository;
import fr.alexpado.mareu.repositories.FakeRoomRepository;
import fr.alexpado.mareu.repositories.FakeUserRepository;
import fr.alexpado.mareu.services.MeetingService;
import fr.alexpado.mareu.services.RoomService;
import fr.alexpado.mareu.services.UserService;

/**
 * Class handling everything related to dependency injection using singletons.
 */
public final class InjectionStore {

    private static UserRepository    userRepository;
    private static RoomRepository    roomRepository;
    private static MeetingRepository meetingRepository;

    private static UserService    userService;
    private static RoomService    roomService;
    private static MeetingService meetingService;

    private InjectionStore() {}

    public static UserRepository userRepository() {

        if (userRepository == null) {
            userRepository = new FakeUserRepository();
        }
        return userRepository;
    }

    public static RoomRepository roomRepository() {

        if (roomRepository == null) {
            roomRepository = new FakeRoomRepository();
        }
        return roomRepository;
    }

    public static MeetingRepository meetingRepository() {

        if (meetingRepository == null) {
            meetingRepository = new FakeMeetingRepository();
        }
        return meetingRepository;
    }

    public static UserService userService() {

        if (userService == null) {
            userService = new UserService(userRepository());
        }
        return userService;
    }

    public static RoomService roomService() {

        if (roomService == null) {
            roomService = new RoomService(roomRepository());
        }
        return roomService;
    }

    public static MeetingService meetingService() {

        if (meetingService == null) {
            meetingService = new MeetingService(meetingRepository());
        }
        return meetingService;
    }

}
