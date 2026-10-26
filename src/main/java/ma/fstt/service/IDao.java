package ma.fstt.service;

import java.util.List;

public interface IDao<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(Long id);
    T findById(Long id);
    List<T> findAll();
}