package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.jpa.GroupRepository;
import club.pypzx.FootballSystem.dao.mybatis.GroupMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.GroupVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.service.GroupService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupMapper mapper;
	@Autowired
	private GroupRepository repository;


	@Override
	public BaseExcution<Group> add(Group obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				return new BaseExcution<>(BaseStateEnum.FAIL);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> edit(Group obj) throws Exception {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				return new BaseExcution<>(BaseStateEnum.FAIL);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> findById(String objId) {
		Group group = new Group(objId);
		Group selectByPrimaryKey = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(group);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).orElse(null);
		}
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Group> findByCondition(Group obj) {
		List<Group> selectRowBounds = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int selectCount = mapper.selectCount(obj);
			selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectRowBounds = repository.findAll(Example.of(obj));
		}
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Group> findAll(int pageIndex, int pageSize) {
		List<Group> selectAll = null;
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectAll = mapper.selectRowBounds(new Group(), Page.getInstance(pageIndex, pageSize));
			selectCount = mapper.selectCount(new Group());
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			org.springframework.data.domain.Page<Group> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			selectAll = findAll.getContent();
			selectCount = (int) repository.count();
		}
		if (selectAll == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectAll, selectCount);

	}

	@Override
	@Deprecated
	public BaseExcution<Group> removeById(String objId) throws Exception {
		return null;
	}

	@Override
	@Deprecated
	public BaseExcution<Group> removeByIdList(List<String> list) throws Exception {
		return null;
	}

	@Override
	public BaseExcution<GroupVo> queryTeamByGroup(String cupId) {
		List<GroupVo> queryTeamByGroup = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			queryTeamByGroup = mapper.queryTeamByGroup(cupId);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			queryTeamByGroup = repository.queryTeamByGroup(cupId);
		}
		if (queryTeamByGroup != null && queryTeamByGroup.size() > -1) {
			return new BaseExcution<GroupVo>(BaseStateEnum.SUCCESS, queryTeamByGroup, queryTeamByGroup.size());
		}
		return new BaseExcution<GroupVo>(BaseStateEnum.QUERY_ERROR);
	}

}
