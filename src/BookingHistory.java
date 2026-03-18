import java.util.*;

public class BookingHistory {


    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }


    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }


    public List<Reservation> getAllBookings() {
        return confirmedBookings;
    }
}