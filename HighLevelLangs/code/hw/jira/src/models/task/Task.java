package highlevellangs.code.hw.jira.src.models.task;

import java.util.List;

public class Task {
    private int duration;

    private List<Integer> neededResources;
    private List<Integer> followers;

    public Task(int duration, List<Integer> neededResources, List<Integer> followers) {
        this.duration = duration;

        this.neededResources = neededResources;
        this.followers = followers;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public List<Integer> getNeededResources() {
        return neededResources;
    }

    public int getDuration() {
        return duration;
    }

}