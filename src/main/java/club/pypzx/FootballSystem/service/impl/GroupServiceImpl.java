package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.GroupDao;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.GroupVo;
import club.pypzx.FootballSystem.service.GroupService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class GroupServiceImpl implements GroupService {
	private final GroupDao dao;

	@Autowired
	public GroupServiceImpl(GroupDao dao) {
		this.dao = dao;
	}

	@Override
	public BaseExcution<Group> add(Group obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.add(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> edit(Group obj) throws Exception {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.edit(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Deprecated
	public BaseExcution<Group> findById(String objId) {
		Group selectByPrimaryKey = dao.findById(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Group> findByCondition(Group obj) {
		List<Group> selectRowBounds = dao.findAllCondition(obj);
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Group> findAll(int pageIndex, int pageSize) {
		List<Group> selectAll = dao.findAll(pageIndex, pageSize);
		int selectCount = dao.count();
		if (selectAll == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectAll, selectCount);

	}

	@Override
	@Transactional
	public BaseExcution<Group> removeById(String objId) throws Exception {
		dao.remove(objId);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> removeByIdList(List<String> list) throws Exception {
		return null;
	}

	@Override
	public BaseExcution<GroupVo> queryTeamByGroup(String cupId) {
		List<GroupVo> queryTeamByGroup = dao.queryTeamGroup(cupId);
		if (queryTeamByGroup != null && queryTeamByGroup.size() > -1) {
			return new BaseExcution<GroupVo>(BaseStateEnum.SUCCESS, queryTeamByGroup, queryTeamByGroup.size());
		}
		return new BaseExcution<GroupVo>(BaseStateEnum.QUERY_ERROR);
	}

}
