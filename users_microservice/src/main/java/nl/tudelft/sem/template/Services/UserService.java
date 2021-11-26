package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {



    @Override
    public List<User> findAll() {
        UserRepository repository = new UserRepository() {
            @Override
            public <S extends User> S save(S entity) {
                return null;
            }

            @Override
            public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<User> findById(String s) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(String s) {
                return false;
            }

            @Override
            public Iterable<User> findAll() {
                return null;
            }

            @Override
            public Iterable<User> findAllById(Iterable<String> strings) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(String s) {

            }

            @Override
            public void delete(User entity) {

            }

            @Override
            public void deleteAll(Iterable<? extends User> entities) {

            }

            @Override
            public void deleteAll() {

            }
        };
        var users = (List<User>) repository.findAll();
        return users;
    }

    @Override
    public void save(User user) {
        UserRepository repository = new UserRepository() {
            @Override
            public <S extends User> S save(S entity) {
                return null;
            }

            @Override
            public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<User> findById(String s) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(String s) {
                return false;
            }

            @Override
            public Iterable<User> findAll() {
                return null;
            }

            @Override
            public Iterable<User> findAllById(Iterable<String> strings) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(String s) {

            }

            @Override
            public void delete(User entity) {

            }

            @Override
            public void deleteAll(Iterable<? extends User> entities) {

            }

            @Override
            public void deleteAll() {

            }
        };
        repository.save(user);
    }
}
