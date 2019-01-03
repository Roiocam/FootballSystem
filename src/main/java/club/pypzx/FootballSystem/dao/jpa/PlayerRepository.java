package club.pypzx.FootballSystem.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import club.pypzx.FootballSystem.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, String> {



}
