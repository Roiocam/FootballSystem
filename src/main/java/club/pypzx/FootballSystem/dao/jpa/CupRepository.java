package club.pypzx.FootballSystem.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import club.pypzx.FootballSystem.entity.Cup;

public interface CupRepository extends JpaRepository<Cup, String> {
	Cup findCupByCupId(String cupId);

	List<Cup> findCup();

}
