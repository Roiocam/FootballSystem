package club.pypzx.FootballSystem.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import club.pypzx.FootballSystem.entity.User;

public interface UserRepository extends JpaRepository<User, String> {



}
