package highlevellangs.code.hw.jira.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Jira {
    private static final String FILE_NAME = "X14_6.RCP";
    public static void main(String[] args)
    {
        try {
           
            int task, count_res, res1, res2, res3, res4, task_time;
            int counter = 0; // счётчик переносов, чтобы не было перегрузки
            int days_project = 0; // длительность проекта
            int time1 = 0; // остаток для 1 ресурса
            int time2 = 0; // остаток для 2 ресурса
            int time3 = 0; // остаток для 3 ресурса
            int time4 = 0; // остаток для 4 ресурса
            Queue<Integer> queue = new LinkedList<>(); // очередь для составления цепочки
            Queue<Integer> tasks = new LinkedList<>(); // собсна, сама цепочка
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String test_string = br.readLine(); // переводим считанную строку с файла в "рабочую"
            Scanner in = new Scanner(test_string); // Для чтения строки использую сканер
            task = in.nextInt(); // считал первое число, которое содержит количество задач
            int[][] test_array = new int[task][]; // создаю специально двумерный массив, который будет в дальнейшем рабочим, до построения карты
                                                // также стоит обратить внимание, что количество строк определяется количеством задач
            count_res = in.nextInt(); // считываю количество рабочих ресурсов, где применять полученное - не сообразил ещё
            test_string = br.readLine(); // читаю следующую строку
            in = new Scanner(test_string); // снова в работу строку берёт сканер
            res1 = in.nextInt(); // максимум первого ресурса
            res2 = in.nextInt(); // максимум второго ресурса
            res3 = in.nextInt(); // третьего ресурса
            res4 = in.nextInt(); // четвёртого ресурса
            // а вот тут цикл начинает дальше читать столько строк, сколько задач
            for (int i = 0; i < task; i++) {
                int count = 0;
                test_string = br.readLine();
                in = new Scanner(test_string);
                while (in.hasNextInt()) { // здесь он считает количество символов в строке, лучше не придумал
                    if (in.nextInt() > -1) {
                        count++;
                    }
                }
                int[] arr = new int[count]; // соответственно затем будет строка переводиться в массив целочисленный
                in = new Scanner(test_string); // перезапускаем сканер строки, да, он тупой и не поймёт, что надо читать заново, а не дальше
                for (int j = 0; j < arr.length; j++) {
                    if (in.hasNextInt()) { // постоянная проверка, а дальше есть число?
                        arr[j] = in.nextInt(); // вот тут уже идёт заполнение
                    }
                }
                test_array[i] = arr; // каждой строке соответствует свой массив столбцов
            }
            in.close(); // просто освобождаем за ненадобностью
            br.close(); // также освобождаем от файла, всё же закончили с ним работать
            int adders = test_array[0][5]; // 6 столбец содержит последователей, на 1 строчке больше 1
            // вот дальше цикл заполняет первую строку очереди, которая дальше будет идти в работу, как и должно быть
            for (int i = 0; i < adders; i++) {
                queue.add(test_array[0][6 + i]); // заполняется затычкой, мол 7+0, затем 7+1, потом 7+2 и всё, из этого будет извлекаться
                tasks.add(test_array[0][6 + i]); // заполняется затычкой, мол 7+0, затем 7+1, потом 7+2 и всё, эта будет заполняться
            }
            while (!queue.isEmpty()) { // пока очередь не будет пустой, т.е. формирующая цепочку в конце опустошится, вообще ничего не будет
                                    // нужная рабочая будет сформирована как надо
                int removed = queue.remove(); // взяли первый из очереди, и выкинули его же
                removed--; // поскольку массивы с 0, отсюда уменьшаем на 1
                adders = test_array[removed][5]; // получаем соответствующее числу строку, но массива, также 6 столбец-число
                for (int i = 0; i < adders; i++) { // тоже самое, что и было раньше
                    if (!tasks.contains(test_array[removed][6 + i])) { // а вот тут уже идёт проверка на повторяемость
                                                                    // поскольку может быть уже в цепочке добавляемая строка для чтения
                                                                    // она не будет добавлена
                        queue.add(test_array[removed][6 + i]);         // тут идёт она в очередь
                        tasks.add(test_array[removed][6 + i]);         // а тут в рабочую цепочку
                    }
                }
            }
            Map<Integer, int[]> map = new HashMap<>(); // делаю тут карту, чтобы было удобно работать со списком без танцев с бубном
            for (int i = 1; i < test_array.length + 1; i++) {
                map.put(i, test_array[i - 1]); // заполняю эту самую карту методом Ключ, значение - где ключ идёт с 1, а значения - это ранее составленные массивчики
            }
            int[] arr = map.get(tasks.remove()); // получаем из цепочки рабочий массив
            task_time = arr[0]; // получаем длительность задачи
            time1 = res1 - arr[1]; // вычисляем остаток 1 ресурса после задачи
            time2 = res2 - arr[2]; // вычисляем остаток 2 ресурса после задачи
            time3 = res3 - arr[3]; // вычисляем остаток 3 ресурса после задачи
            time4 = res4 - arr[4]; // вычисляем остаток 4 ресурса после задачи
            days_project += task_time; // добавляем в длительность задачи длительность задачи
            int prev_time = task_time;
            while (!tasks.isEmpty()){
                arr = map.get(tasks.remove());
                task_time = arr[0];
                time1 -= arr[1];
                time2 -= arr[2];
                time3 -= arr[3];
                time4 -= arr[4];
                if (task_time - prev_time > 0) {
                    days_project += (task_time - prev_time);
                    prev_time = task_time;
                }
                if (time1 <= 0 || time2 <= 0 || time3 <= 0 || time4 <= 0) {
                    System.out.println("Перегрузка, не хватает ресурсов. Переносим...");
                    counter++;
                    days_project += task_time;
                    time1 = res1 - arr[1];
                    time2 = res2 - arr[2];
                    time3 = res3 - arr[3];
                    time4 = res4 - arr[4];
                    prev_time = task_time;
                }
            }
            System.out.println();
            System.out.println("Количество задач: " + task + "\nКоличество ресурсов: " + count_res);
            System.out.println("Длительность проекта составляет: " + days_project + " дней.");
            System.out.println("Количество переносов за время составления проекта: " + counter);
            
        } catch (IOException exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}