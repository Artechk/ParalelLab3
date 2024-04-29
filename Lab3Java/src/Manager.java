import java.util.ArrayList;
import java.util.concurrent.Semaphore;

class Manager {

    public Semaphore access;
    public Semaphore full;
    public Semaphore empty;
    public int itemcount;

    public ArrayList<String> storage = new ArrayList<>();

    public Manager(int storageSize) {
        access = new Semaphore(1);
        full = new Semaphore(0); // Заповнюємо порожнє сховище семафором full
        empty = new Semaphore(storageSize); // Починаємо з повним сховищем
    }
}
