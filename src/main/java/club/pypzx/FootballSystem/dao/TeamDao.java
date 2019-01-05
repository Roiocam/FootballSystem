package club.pypzx.FootballSystem.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.TeamRepository;
import club.pypzx.FootballSystem.dao.jpa.TeamVoRepository;
import club.pypzx.FootballSystem.dao.mybatis.TeamMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamVo;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class TeamDao implements BaseDao<Team> {
	@Autowired
	private TeamMapper mapper;
	@Autowired
	private TeamRepository repository;
	@Autowired
	private TeamVoRepository voRepository;

	public static BaseDao<Team> instance() {
		return EntityFactroy.getBean(TeamDao.class);
	}

	@Override
	public void add(Team obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增球队失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(Team obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新球队失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) {
		Team bean = EntityFactroy.getBean(Team.class);
		bean.setTeamId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.delete(bean)) {
				throw new RuntimeException("删除球队失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.delete(bean);
		}

	}

	@Override
	public Team findById(String objId) {
		Team selectByPrimaryKey = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectByPrimary(objId);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).orElse(null);
		}
		return selectByPrimaryKey;

	}

	@Override
	public Team findByCondition(Team obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<Team> findAllCondition(Team obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int selectCount = mapper.selectCount(obj);
			return mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		} else {
			return repository.findAll(Example.of(obj));
		}
	}

	@Override
	public int count() {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(EntityFactroy.getBean(Team.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(Team obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<Team> findAll(int pageIndex, int pageSize) {
		Team bean = EntityFactroy.getBean(Team.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<Team> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

	public TeamVo findIdMore(String objId) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectMorePrimary(objId);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return voRepository.findById(objId).orElse(null);
		} else {
			return null;
		}
	}

	public List<TeamVo> findAllMore(Team example, int pageIndex, int pageSize) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectMoreRowBounds(example, Page.getInstance(pageIndex, pageSize));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			TeamVo bean = EntityFactroy.getBean(TeamVo.class);
			Cup cup = EntityFactroy.getBean(Cup.class);
			cup.setCupId(example.getCupId());
			bean.setCup(cup);
			org.springframework.data.domain.Page<TeamVo> findAll = voRepository.findAll(Example.of(bean),
					PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}
		return null;
	}

	public void editTeamLeader(String teamId, String leaderId) {
		Team team = EntityFactroy.getBean(Team.class);
		team.setTeamId(teamId);
		team.setLeaderId(leaderId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int updateByPrimaryKey = mapper.updateLeader(team);
			if (updateByPrimaryKey != 1) {
				throw new RuntimeException("更新队长失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			Optional<Team> findById = repository.findById(teamId);
			if (findById.isPresent()) {
				Team temp = findById.get();
				temp.setLeaderId(leaderId);
				repository.save(temp);
			} else {
				throw new RuntimeException("更新队长失败");
			}
		}

	}

}
