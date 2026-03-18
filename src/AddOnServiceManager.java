import java.util.*;

public class AddOnServiceManager {


    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }


    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Service added: " + service.getServiceName() +
                " for Reservation ID: " + reservationId);
    }


    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }


    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) {
            System.out.println("No services added.");
            return;
        }

        System.out.println("Services for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " (₹" + s.getPrice() + ")");
        }
    }
}