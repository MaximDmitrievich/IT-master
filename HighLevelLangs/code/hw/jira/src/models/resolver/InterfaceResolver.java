package highlevellangs.code.hw.jira.src.models.resolver;

import java.io.BufferedReader;
import java.io.FileReader;

public interface InterfaceResolver {
    boolean readTasks(String file_name);
    boolean readTasks(FileReader reader);
    boolean readTasks(BufferedReader reader);
}