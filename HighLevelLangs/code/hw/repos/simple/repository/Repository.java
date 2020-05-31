package highlevellangs.code.hw.repos.simple.repository;

public interface Repository<T>{
    Iterable<T> findAll();
    void save(T entity);
    T findById(int id);
    void delete(T entity);
}