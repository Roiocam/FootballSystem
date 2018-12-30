package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.mybatis.GroupMapper;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.service.GroupService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupMapper mapper;

	@Override
	public BaseExcution<Group> insertObj(Group obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.insert(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> updateObjByPrimaryKey(Group obj) throws Exception {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.updateByPrimaryKey(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Group> queryObjOneByPrimaryKey(String objId) {
		Group selectByPrimaryKey = mapper.selectByPrimaryKey(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Group> queryObjOne(Group obj) {
		Group selectOne = mapper.selectOne(obj);
		if (selectOne == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectOne);
	}

	@Override
	public BaseExcution<Group> queryAll(int pageIndex, int pageSize) {
		List<Group> selectAll = mapper.selectByRowBounds(null, new RowBounds((pageIndex - 1) * pageSize, pageSize));
		if (selectAll == null) {
			return new BaseExcution<Group>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(null);
		return new BaseExcution<Group>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	@Deprecated
	public BaseExcution<Group> deleteObjByPrimaryKey(String objId) throws Exception {
		return null;
	}

	@Override
	@Deprecated
	public BaseExcution<Group> deleteObjectList(List<String> list) throws Exception {
		return null;
	}

}
