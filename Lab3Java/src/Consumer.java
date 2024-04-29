class Consumer implements Runnable {
    private final int totalItems; // Загальна кількість елементів, які потрібно забрати
    private final Manager manager;

    public Consumer(int totalItems, Manager manager) {
        this.totalItems = totalItems;
        this.manager = manager;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (manager.storage.size() < totalItems) { // Перевіряємо, чи не забрали вже всі елементи
            try {
                manager.full.acquire(); // Чекаємо, доки в сховищі є елементи
                manager.access.acquire(); // Захоплюємо доступ до сховища

                // Перевіряємо, чи не забрали вже всі елементи
                if (manager.storage.size() < totalItems) {
                    // Забираємо елемент із сховища
                    if (!manager.storage.isEmpty()) { // Перевіряємо, чи є елементи в сховищі
                        String item = manager.storage.remove(0);
                        System.out.println("Взято " + item + " від " + Thread.currentThread().getName());
                        System.out.println("Кількість елементів зараз: " + manager.storage.size());
                        System.out.println("-------------------------");
                    }
                }

                manager.access.release(); // Звільняємо доступ до сховища
                manager.empty.release(); // Звільняємо семафор, тепер в сховищі є місце
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
