package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.mybatis.GroupMapper;
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.service.GroupService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupMapper mapper;

	@Override
	public BaseExcution<Group> add(Group obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.insert(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> edit(Group obj) throws Exception {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.update(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> findById(String objId) {
		Group group = new Group(objId);
		Group selectByPrimaryKey = mapper.selectPrimary(group);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Group> findByCondition(Group obj) {
		int selectCount = mapper.selectCount(obj);
		List<Group> selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		if (selectRowBounds != null && selectRowBounds.size() >= -1) {
			return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Group> findAll(int pageIndex, int pageSize) {
		List<Group> selectAll = mapper.selectRowBounds(new Group(), Page.getInstance(pageIndex, pageSize));
		if (selectAll != null && selectAll.size() > -1) {
			int selectCount = mapper.selectCount(new Group());
			return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectAll, selectCount);
		}
		return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);

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
		List<GroupVo> queryTeamByGroup = mapper.queryTeamByGroup(cupId);
		if (queryTeamByGroup != null && queryTeamByGroup.size() > -1) {
			return new BaseExcution<GroupVo>(BaseStateEnum.SUCCESS, queryTeamByGroup, queryTeamByGroup.size());
		}
		return new BaseExcution<GroupVo>(BaseStateEnum.QUERY_ERROR);
	}

}
