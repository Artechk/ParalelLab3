class Consumer implements Runnable {
    private final int totalItems; // Общее количество элементов, которые нужно забрать
    private final Manager manager;

    public Consumer(int totalItems, Manager manager) {
        this.totalItems = totalItems;
        this.manager = manager;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (manager.storage.size() < totalItems) { // Проверяем, не забрали ли уже все элементы
            try {
                manager.full.acquire(); // Ожидаем пока в хранилище есть элементы
                manager.access.acquire(); // Захватываем доступ к хранилищу

                // Проверяем, не забрали ли уже все элементы
                if (manager.storage.size() < totalItems) {
                    // Забираем элемент из хранилища
                    if (!manager.storage.isEmpty()) { // Проверяем, есть ли элементы в хранилище
                        String item = manager.storage.remove(0);
                        System.out.println("Took " + item + " by " + Thread.currentThread().getName());
                        System.out.println("Items number now is:" + manager.storage.size());
                        System.out.println("-------------------------");
                    }
                }

                manager.access.release(); // Освобождаем доступ к хранилищу
                manager.empty.release(); // Освобождаем семафор, теперь в хранилище есть место
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
