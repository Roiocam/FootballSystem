package club.pypzx.FootballSystem.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import club.pypzx.FootballSystem.entity.Team;

public interface TeamRepository extends JpaRepository<Team, String> {



}
