package highlevellangs.code.hw.repos.simple.service;

import java.util.ArrayList;
import java.util.List;

import highlevellangs.code.hw.repos.simple.entity.Project;   
import highlevellangs.code.hw.repos.simple.repository.ProjectPostgresRepository;
import highlevellangs.code.hw.repos.simple.repository.Repository;

public class ProjectService {
    private Repository<Project> repository = new ProjectPostgresRepository();

    public List<Project> getAllProjects(){
        List<Project> projects = new ArrayList<>();
        repository.findAll().forEach(projects::add);
        return projects;
    }

    public Project getProject(int id) { return repository.findById(id);}

    public void save(Project projects) { repository.save(projects);}

    public void delete(Project projects) { repository.delete(projects);}
}