import java.util.concurrent.atomic.AtomicInteger;

class Producer implements Runnable {
    private final int totalItems; // Загальна кількість елементів, які потрібно створити
    private final Manager manager;
    private static AtomicInteger itemCounter = new AtomicInteger(0); // Загальний лічильник для всіх виробників

    public Producer(int totalItems, Manager manager) {
        this.totalItems = totalItems;
        this.manager = manager;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (itemCounter.get() < totalItems) { // Перевіряємо, чи не досягли ми загальної кількості елементів
            try {
                manager.empty.acquire(); // Чекаємо, доки є місце в сховищі
                manager.access.acquire(); // Захоплюємо доступ до сховища

                // Перевіряємо, чи не досягли ми загальної кількості елементів
                if (itemCounter.get() < totalItems) {
                    // Створюємо елемент на основі загального лічильника
                    String item = "елемент " + itemCounter.getAndIncrement();
                    manager.storage.add(item);
                    System.out.println("Додано " + item + " від " + Thread.currentThread().getName());
                    System.out.println("Кількість елементів зараз: " + manager.storage.size());
                    System.out.println("-------------------------");
                }

                manager.access.release(); // Звільняємо доступ до сховища
                manager.full.release(); // Звільняємо семафор, тепер сховище не пусте
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
