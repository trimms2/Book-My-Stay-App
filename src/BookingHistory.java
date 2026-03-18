import java.util.*;
import java.io.Serializable;

public class BookingHistory implements Serializable {

    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    public boolean hasBooking(Reservation reservation) {
        return confirmedBookings.contains(reservation);
    }

    public void removeBooking(Reservation reservation) {
        confirmedBookings.remove(reservation);
    }

    public List<Reservation> getAllBookings() {
        return confirmedBookings;
    }
}