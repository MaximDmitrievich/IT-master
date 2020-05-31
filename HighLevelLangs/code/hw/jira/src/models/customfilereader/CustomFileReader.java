package highlevellangs.code.hw.jira.src.models.customfilereader;

import java.util.List;

import highlevellangs.code.hw.jira.src.models.task.Task;

public interface CustomFileReader {
    List<Task> read(String filename);
    List<Integer> getLastResources();
}