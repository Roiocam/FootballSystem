package club.pypzx.FootballSystem.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import club.pypzx.FootballSystem.entity.KickDay;

public interface KickDayRepository extends JpaRepository<KickDay, String> {

	@Query("select new club.pypzx.FootballSystem.entity.KickDay(COALESCE(date,curdate())AS date,COALESCE(num,0)AS num) from KickDay where date=curdate()")
	public KickDay queryKickToday();

}
