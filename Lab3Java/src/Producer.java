import java.util.concurrent.atomic.AtomicInteger;

class Producer implements Runnable {
    private final int totalItems; // Общее количество элементов, которые нужно создать
    private final Manager manager;
    private static AtomicInteger itemCounter = new AtomicInteger(0); // Общий счетчик для всех производителей

    public Producer(int totalItems, Manager manager) {
        this.totalItems = totalItems;
        this.manager = manager;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (itemCounter.get() < totalItems) { // Проверяем, не достигли ли общего количества элементов
            try {
                manager.empty.acquire(); // Ожидаем пока есть место в хранилище
                manager.access.acquire(); // Захватываем доступ к хранилищу

                // Проверяем, не достигли ли общего количества элементов
                if (itemCounter.get() < totalItems) {
                    // Создаем элемент на основе общего счетчика
                    String item = "item " + itemCounter.getAndIncrement();
                    manager.storage.add(item);
                    System.out.println("Added " + item + " by " + Thread.currentThread().getName());
                    System.out.println("Items number now is:" + manager.storage.size());
                    System.out.println("-------------------------");
                }

                manager.access.release(); // Освобождаем доступ к хранилищу
                manager.full.release(); // Освобождаем семафор, теперь хранилище не пустое
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
