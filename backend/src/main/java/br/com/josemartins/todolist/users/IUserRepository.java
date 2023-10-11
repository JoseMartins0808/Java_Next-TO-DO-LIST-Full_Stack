package br.com.josemartins.todolist.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;



public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    public boolean existsByEmail(final String email);

    public boolean existsByUsername(final String username);

    public UserModel findByUsername(final String username);
}
