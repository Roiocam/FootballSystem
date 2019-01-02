package club.pypzx.FootballSystem.service.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.mybatis.CupMapper;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.enums.DecideEnum;
import club.pypzx.FootballSystem.enums.GroupEnum;
import club.pypzx.FootballSystem.service.CupService;
import club.pypzx.FootballSystem.service.GroupService;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.IDUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Service
public class CupServiceImpl implements CupService {

	@Autowired
	private CupMapper mapper;
	@Autowired
	private TeamService teamSerivce;
	@Autowired
	private GroupService groupService;

	public Cup packageCup(String name) {
		Cup obj = new Cup();
		obj.setCupId(IDUtils.getUUID());
		obj.setCupName(name);
		return obj;
	}

	public Cup packageCup(String id, String name) {
		Cup obj = new Cup();
		obj.setCupId(id);
		obj.setCupName(name);
		return obj;
	}

	@Override
	@Transactional
	public BaseExcution<Cup> removeById(String objId) throws Exception {
		// 先查询是否存在赛程分配
		BaseExcution<Group> exist = groupService.findByCondition(new Group(objId));
		if (ResultUtil.failListResult(exist)) {
			return new BaseExcution<>(BaseStateEnum.NEED_DELETE_GROUP);
		}

		// 先删除比赛记录,再删除球员,再删除球队,再删除赛程安排表
		// --查询赛事下是否有球队
		Team t = new Team();
		t.setCupId(objId);
		BaseExcution<Team> findByCondition = teamSerivce.findByCondition(t);
		if (!ResultUtil.failListResult(findByCondition)) {
			List<Team> objList = findByCondition.getObjList();
			// 遍历赛事下球队，删除球队下球员，
			Iterator<Team> iterator = objList.iterator();
			while (iterator.hasNext()) {
				teamSerivce.removeById(iterator.next().getTeamId());
			}
		}
		if (1 != mapper.delete(new Cup(objId))) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}

		return new BaseExcution<>(BaseStateEnum.SUCCESS);

	}

	@Override
	public BaseExcution<Cup> add(Cup obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		obj.setIsGroup(DecideEnum.FALSE.getState());
		if (1 != mapper.insert(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Cup> edit(Cup obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.update(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Cup> findById(String objId) {
		Cup selectByPrimaryKey = mapper.selectByPrimary(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Cup>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Cup>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Cup> findByCondition(Cup obj) {
		int selectCount = mapper.selectCount(obj);
		List<Cup> selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Cup>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Cup>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Cup> findAll(int pageIndex, int pageSize) {
		List<Cup> selectAll = mapper.selectRowBounds(new Cup(), Page.getInstance(pageIndex, pageSize));
		if (selectAll == null) {
			return new BaseExcution<Cup>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(new Cup());
		return new BaseExcution<Cup>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	@Transactional
	public BaseExcution<Cup> removeByIdList(List<String> list) throws Exception {
		Iterator<?> iterator = list.iterator();
		if (iterator == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		while (iterator.hasNext()) {
			removeById((String) iterator.next());
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Transactional
	public BaseExcution<Cup> randomTeamToGroup(String cupId) throws Exception {
		Team t = new Team();
		t.setCupId(cupId);
		BaseExcution<TeamVo> queryAllByPage = teamSerivce.findAllMore(t, 1, 9);
		if (ResultUtil.failListResult(queryAllByPage)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		List<TeamVo> objList = queryAllByPage.getObjList();
		if (objList.size() != 9) {
			return new BaseExcution<>(BaseStateEnum.WRONG_TEAM_COUNT);
		}
		// 打乱list
		Collections.shuffle(objList);
		int count = 0;
		// 遍历9次,读出
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				GroupEnum stringOf = GroupEnum.stringOf(j);
				TeamVo teamVo = objList.get(count);
				Group g = new Group();
				g.setCupId(cupId);
				g.setTeam_id(teamVo.getTeamId());
				g.setTeamGroup(stringOf.getGroup_string());
				BaseExcution<Group> insertObj = groupService.add(g);
				if (ResultUtil.failResult(insertObj)) {
					throw new Exception("分组失败");
				}
				count++;
			}
		}
		Cup cup = new Cup();
		cup.setCupId(cupId);
		cup.setIsGroup(1);
		if (1 != mapper.update(cup)) {
			throw new Exception("更新赛事分组状态失败");
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

}
