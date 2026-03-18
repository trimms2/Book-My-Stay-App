import java.util.*;

public class CancellationService {

    // Stack for rollback (LIFO)
    private Stack<String> releasedRoomIds = new Stack<>();

    public void cancelBooking(Reservation reservation,
                              RoomInventory inventory,
                              BookingHistory history,
                              RoomAllocationService allocationService) {

        // Validate booking exists
        if (!history.hasBooking(reservation)) {
            System.out.println("❌ Cancellation Failed: Booking does not exist");
            return;
        }

        String roomType = reservation.getRoomType();

        // Simulate getting allocated room ID
        String roomId = allocationService.releaseRoom(roomType);

        if (roomId == null) {
            System.out.println("❌ Cancellation Failed: No room found to release");
            return;
        }

        // Push into rollback stack
        releasedRoomIds.push(roomId);

        // Restore inventory
        Map<String, Integer> availability = inventory.getRoomAvailability();
        availability.put(roomType, availability.get(roomType) + 1);

        // Remove from booking history
        history.removeBooking(reservation);

        System.out.println("🔁 Booking Cancelled Successfully:");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Released Room ID: " + roomId);
        System.out.println("---------------------------");
    }
}