package club.pypzx.FootballSystem.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import club.pypzx.FootballSystem.dto.GameVo;

public interface GameVoRepository extends JpaRepository<GameVo, String> {


}
