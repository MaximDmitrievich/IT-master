package highlevellangs.code.hw.jira.src.models.resolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import highlevellangs.code.hw.jira.src.models.task.Task;

public class Resolver implements InterfaceResolver {
    private volatile List<Task> taskList;
    private List<Integer> tasks;
    private List<Integer> task_resources;

    //Map<Day, ResourcesUsed>
    private Map<Integer, int[]> mapResources = new HashMap<Integer, int[]>();

    //Map<task, day>
    private Map<Integer, Integer> mapDayStart = new HashMap<Integer, Integer>();

    public Resolver(List<Task> taskList, List<Integer> tasks, List<Integer> task_resources) {
        this.taskList = taskList;
        this.tasks = tasks;
        this.task_resources = task_resources;
    }

    public void init(){


            try {
                for (int i: tasks) {
                    arrayWork(i);
                }

                System.out.println(mapResources.size() + " : " + tasks.toString());

            } catch (NothingToFollowFromException e) {
                System.out.println(e.getMessage());
                //break;
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    private void arrayWork(int currentIndex) throws Exception {

        if (currentIndex != 0){
            int followFrom = -1;
            for (int nodeIndex: tasks){
                if (taskList.get(nodeIndex).getFollowers().contains(currentIndex)){
                    followFrom = nodeIndex;
                }
                if (followFrom != -1) break;
            }
            if (followFrom == -1) throw new NothingToFollowFromException("Haven't task to follow for task " + currentIndex);

            if (mapDayStart.get(followFrom) == null){
                checkAndInsert( taskList.get(followFrom).getDuration(), taskList.get(currentIndex));
            }else {
                checkAndInsert(mapDayStart.get(followFrom) + taskList.get(followFrom).getDuration(), taskList.get(currentIndex));
            }
        }else{
            checkAndInsert(0, taskList.get(currentIndex));
        }
    }



    private void createNewValue(int fromDay){

        int[] data = new int[task_resources.size()];
        Arrays.fill(data, 0);
        mapResources.put(fromDay, data);

    }

    private void checkAndInsert(int fromDay, Task task){
        for (int day = fromDay; day<fromDay + task.getDuration(); day++){
            boolean okFlag = true;
            if (mapResources.get(fromDay) != null){
                for (int j=0; j<task_resources.size(); j++){
                    if (mapResources.get(day) == null) createNewValue(day);
                    if ( mapResources.get(day)[j]
                            + task.getNeededResources().get(j) >
                            task_resources.get(j) ){
                        okFlag = false;
                        break;
                    }
                }
            }else{
                createNewValue(day);
            }
            if (!okFlag){
                checkAndInsert(++day, task);
                return;
            }
        }
        fillValue(fromDay, task);
    }

    private void fillValue(int fromDay, Task task){
        for (int day = fromDay; day<fromDay + task.getDuration();day++){
            for (int j = 0; j < task_resources.size(); j++){
                mapResources.get(day)[j] += task.getNeededResources().get(j);
            }
        }
        mapDayStart.put(taskList.indexOf(task), fromDay);
    }



}