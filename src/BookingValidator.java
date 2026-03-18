import java.util.Map;

public class BookingValidator {

    public void validate(Reservation reservation,
                         Map<String, Integer> availability)
            throws InvalidBookingException {

        if (reservation == null) {
            throw new InvalidBookingException("Reservation cannot be null");
        }

        if (reservation.getGuestName() == null ||
                reservation.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name is invalid");
        }


        String roomType = reservation.getRoomType();

        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }


        if (availability.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }
}