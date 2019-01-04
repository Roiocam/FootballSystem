package club.pypzx.FootballSystem.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.GroupVo;

public interface GroupRepository extends JpaRepository<Group, String> {
	@Query("SELECT new club.pypzx.FootballSystem.entity.GroupVo(g.teamId,t.teamName,g.teamGroup) FROM Group g  LEFT JOIN "
			+ "Team t ON g.teamId=t.teamId  WHERE g.cupId=:cupId")
	public List<GroupVo> queryTeamByGroup(@Param("cupId") String cupId);
}
