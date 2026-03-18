import java.util.*;

public class BookingReportService {


    public void displayAllBookings(BookingHistory history) {

        System.out.println("\n📋 Booking History:");

        List<Reservation> bookings = history.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : bookings) {
            System.out.println(
                    "Guest: " + r.getGuestName() +
                            ", Room Type: " + r.getRoomType()
            );
        }
    }


    public void generateSummary(BookingHistory history) {

        System.out.println("\n📊 Booking Summary:");

        Map<String, Integer> countByType = new HashMap<>();

        for (Reservation r : history.getAllBookings()) {
            countByType.put(
                    r.getRoomType(),
                    countByType.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (String type : countByType.keySet()) {
            System.out.println(type + " Rooms Booked: " + countByType.get(type));
        }
    }
}