import java.util.*;

public class RoomAllocationService {


    private Set<String> allocatedRoomIds;


    private Map<String, Set<String>> assignedRoomsByType;


    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }


    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        allocateCore(reservation, inventory);
    }

    public void allocateRoom(Reservation reservation,
                             RoomInventory inventory,
                             BookingHistory history) {

        boolean success = allocateCore(reservation, inventory);


        if (success) {
            history.addBooking(reservation);
        }
    }


    private boolean allocateCore(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();


        if (availability.get(roomType) == null || availability.get(roomType) <= 0) {
            System.out.println("❌ No " + roomType + " rooms available for " + reservation.getGuestName());
            return false;
        }


        String roomId = generateRoomId(roomType);


        allocatedRoomIds.add(roomId);


        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);


        availability.put(roomType, availability.get(roomType) - 1);

        // Confirm reservation
        System.out.println("✅ Room Allocated:");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println("---------------------------");

        return true;
    }


    private String generateRoomId(String roomType) {

        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}