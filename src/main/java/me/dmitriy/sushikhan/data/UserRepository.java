package me.dmitriy.sushikhan.data;

import me.dmitriy.sushikhan.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
