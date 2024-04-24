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
        full = new Semaphore(0); // Заполняем пустое хранилище семафором full
        empty = new Semaphore(storageSize); // Начинаем с полным хранилищем
    }
}
