import java.io.*;

public class PersistenceService {

    public void saveState(RoomInventory inventory, BookingHistory history) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("hotel_data.ser"))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("✅ Data saved successfully");

        } catch (IOException e) {
            System.out.println("❌ Error saving data");
        }
    }

    public Object[] loadState() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("hotel_data.ser"))) {

            RoomInventory inventory = (RoomInventory) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();

            System.out.println("✅ Data loaded successfully");

            return new Object[]{inventory, history};

        } catch (Exception e) {
            System.out.println("⚠ No previous data found. Starting fresh.");
            return null;
        }
    }
}