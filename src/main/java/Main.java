import java.util.*;


public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        int maxValue = 0;
        int keyMaxValue = 0;
        final int numberOfThreads = 1000;
        for (int i = 0; i < numberOfThreads; i++) {
            Runnable logic = () -> {
                synchronized (sizeToFreq) {
                    int countTurnRight = 0;
                    int countRepeat = 0;
                    char searchChar = 'R';
                    String way = generateRoute("RLRFR", 100);
                    for (int j = 0; j < way.length(); j++) {
                        if (way.charAt(j) == searchChar) {
                            countTurnRight++;
                        }
                    }
                    if (!sizeToFreq.containsKey(countTurnRight)) {
                        sizeToFreq.put(countTurnRight, ++countRepeat);
                    } else {
                        countRepeat = sizeToFreq.get(countTurnRight);
                        countRepeat++;
                        sizeToFreq.put(countTurnRight, countRepeat);
                    }
                }
            };
            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }

        synchronized (sizeToFreq){
            //Поиск максимального значения
            for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                    keyMaxValue = entry.getKey();
                }
            }
            System.out.println("Самое частое количество повторений " + keyMaxValue + " (встретилось " + maxValue + " раз) "
                    + "\nДругие размеры: ");

            //Вывод всех элементов мапы
            for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                System.out.println(entry.getKey() + " (" + entry.getValue() + " раз) ");

            }
            System.out.println();
        }




    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}

