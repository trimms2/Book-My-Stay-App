import java.util.HashMap;
import java.util.Map;

public class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();

        availability.put("Single", 3);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availability;
    }
}