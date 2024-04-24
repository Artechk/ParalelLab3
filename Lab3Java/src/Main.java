
public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        int storageSize = 3;  // Розмiр сховища
        int itemNumbers = 10; // Товари
        int numProducers = 3; // Кількість виробників
        int numConsumers = 3; // Кількість споживачів
        main.starter(storageSize, itemNumbers, numProducers, numConsumers);
    }

    private void starter(int storageSize, int itemNumbers, int numProducers, int numConsumers) {
        Manager manager = new Manager(storageSize);

        for (int i = 0; i < numProducers; i++) {
            new Producer(itemNumbers, manager);
        }

        for (int i = 0; i < numConsumers; i++) {
            new Consumer(itemNumbers, manager);
        }
    }
}