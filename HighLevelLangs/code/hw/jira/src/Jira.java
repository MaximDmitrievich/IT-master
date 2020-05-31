package highlevellangs.code.hw.jira.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import highlevellangs.code.hw.jira.src.models.customfilereader.CustomFileReader;
import highlevellangs.code.hw.jira.src.models.task.Task;
import highlevellangs.code.hw.jira.src.models.task.TaskReader;
import highlevellangs.code.hw.jira.src.models.resolver.Resolver;

public class Jira {
    public static final int ITERATIONS = 100;
    public static final String FILE_NAME = "tasks.txt";
    public static void main(final String[] args) {
        new Jira().run();
    }

    private void run(){
        final CustomFileReader fileReader = new TaskReader();
        taskList = fileReader.read(FILE_NAME);
        //mock case
        task_resources = fileReader.getLastResources();
        for (int i=0; i<ITERATIONS; i++){
            new ResolverRunner();
        }
    }

    private List<Task> taskList;

    private List<Integer> task_resources;

    class ResolverRunner implements Runnable {
        private final Thread thread;
        ResolverRunner() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            final Random random = new Random(System.currentTimeMillis());
            final HashSet<Integer> vals = new HashSet();
            vals.add(0);
            for (int j = 0; j < 10 + random.nextInt(taskList.size() - 10); j++){
                vals.add(Math.abs(random.nextInt() % taskList.size() ));
            }
            new Resolver(taskList, new ArrayList(vals), task_resources).init();
        }
    }
}