package club.pypzx.FootballSystem.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import club.pypzx.FootballSystem.entity.Game;

public interface GameRepository extends JpaRepository<Game, String> {


}
