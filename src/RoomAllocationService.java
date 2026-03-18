import java.util.*;

public class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    private BookingValidator validator = new BookingValidator();

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    // UC6
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        allocateCore(reservation, inventory);
    }

    // UC8
    public void allocateRoom(Reservation reservation,
                             RoomInventory inventory,
                             BookingHistory history) {

        boolean success = allocateCore(reservation, inventory);

        if (success) {
            history.addBooking(reservation);
        }
    }

    // CORE METHOD (UC6 + UC9 VALIDATION)
    private boolean allocateCore(Reservation reservation, RoomInventory inventory) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        // ✅ VALIDATION (UC9)
        try {
            validator.validate(reservation, availability);
        } catch (InvalidBookingException e) {
            System.out.println("❌ Booking Failed: " + e.getMessage());
            return false;
        }

        String roomType = reservation.getRoomType();

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Store globally
        allocatedRoomIds.add(roomId);

        // Store by type
        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        // Update inventory
        availability.put(roomType, availability.get(roomType) - 1);

        // Confirm booking
        System.out.println("✅ Room Allocated:");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println("---------------------------");

        return true;
    }

    // Generate unique ID
    private String generateRoomId(String roomType) {

        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}