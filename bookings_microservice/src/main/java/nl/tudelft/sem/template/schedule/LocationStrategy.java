package nl.tudelft.sem.template.schedule;

import java.util.Comparator;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;

public class LocationStrategy implements SortStrategy {

    @Override
    public List<Booking> sortBookings(List<Booking> bookings) {
        bookings.sort(new LocationComparator());
        return bookings;
    }

    protected class LocationComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            Booking b1 = (Booking) o1;
            Booking b2 = (Booking) o2;

            int building1 = b1.getBuilding();
            int building2 = b2.getBuilding();

            if (building1 < building2) {
                return -1;
            }
            if (building2 < building1) {
                return 1;
            }
            if (building1 == building2) {
                int room1 = b1.getRoom();
                int room2 = b2.getRoom();

                if (room1 < room2) {
                    return -1;
                }
                if (room2 < room1) {
                    return 1;
                }
            }
            return 0;
        }
    }
}
