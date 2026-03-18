import java.util.*;

public class RoomAllocationService {

    /* Stores all allocated room IDs to prevent duplicates */
    private Set<String> allocatedRoomIds;

    /* Stores assigned room IDs by room type */
    private Map<String, Set<String>> assignedRoomsByType;

    /* Constructor */
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /* Allocate room for reservation */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Check availability
        if (availability.get(roomType) == null || availability.get(roomType) <= 0) {
            System.out.println("❌ No " + roomType + " rooms available for " + reservation.getGuestName());
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Store globally
        allocatedRoomIds.add(roomId);

        // Store by type
        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        // Update inventory immediately
        availability.put(roomType, availability.get(roomType) - 1);

        // Confirm reservation
        System.out.println("✅ Room Allocated:");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println("---------------------------");
    }

    /* Generate unique room ID */
    private String generateRoomId(String roomType) {

        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}