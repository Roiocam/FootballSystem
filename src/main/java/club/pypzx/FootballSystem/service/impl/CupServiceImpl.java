package club.pypzx.FootballSystem.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.CupDao;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamVo;
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
    private final CupDao cupDao;
    private final TeamService teamSerivce;
    private final GroupService groupService;

    @Autowired
    public CupServiceImpl(CupDao cupDao, TeamService teamSerivce, GroupService groupService) {
        this.cupDao = cupDao;
        this.teamSerivce = teamSerivce;
        this.groupService = groupService;
    }

	public Cup packageCup(String name) {
		Cup obj = EntityFactroy.getBean(Cup.class);
		obj.setCupId(IDUtils.getUUID());
		obj.setCupName(name);
		return obj;
	}

	public Cup packageCup(String id, String name) {
		Cup obj = EntityFactroy.getBean(Cup.class);
		obj.setCupId(id);
		obj.setCupName(name);
		return obj;
	}

	@Override
	@Transactional
	public BaseExcution<Cup> removeById(String objId) throws Exception {
		// 先查询是否存在赛程分配
		BaseExcution<Group> exist = groupService.findByCondition(new Group(objId));
		if (exist.getState() == BaseStateEnum.SUCCESS.getState() && exist.getObjList() != null
				&& exist.getObjList().size() > 0) {
			return new BaseExcution<>(BaseStateEnum.NEED_DELETE_GROUP);
		}
		// 先删除比赛记录,再删除球员,再删除球队,再删除赛程安排表
		// --查询赛事下是否有球队
		Team t = EntityFactroy.getBean(Team.class);
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
		cupDao.remove(objId);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);

	}

	@Override
	public BaseExcution<Cup> add(Cup obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		obj.setIsGroup(DecideEnum.FALSE.getState());
		Cup temp = EntityFactroy.getBean(Cup.class);
		temp.setCupName(obj.getCupName());
		Cup findByCondition = cupDao.findByCondition(temp);
		if (findByCondition != null) {
			return new BaseExcution<>(BaseStateEnum.SAME_CUPNAME);
		}
		try {
			cupDao.add(obj);
		} catch (Exception e) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Cup> edit(Cup obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		cupDao.edit(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Cup> findById(String objId) {
		Cup selectByPrimaryKey = cupDao.findById(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Cup>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Cup>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Cup> findByCondition(Cup obj) {
		List<Cup> selectRowBounds = cupDao.findAllCondition(obj);
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Cup>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Cup>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Cup> findAll(int pageIndex, int pageSize) {
		List<Cup> selectAll = cupDao.findAll(pageIndex, pageSize);
		int selectCount = cupDao.count();
		if (selectAll == null) {
			return new BaseExcution<Cup>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Cup>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	@Transactional
	public BaseExcution<Cup> removeByIdList(List<String> list) throws Exception {
		for(String is:list){
			removeById(is);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Transactional
	public BaseExcution<Cup> randomTeamToGroup(String cupId) throws Exception {
		Team t = EntityFactroy.getBean(Team.class);
		t.setCupId(cupId);
		BaseExcution<TeamVo> queryAllByPage = teamSerivce.findAllMore(t, 1, 9);
		if (ResultUtil.failListResult(queryAllByPage)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		List<TeamVo> objList = queryAllByPage.getObjList();
		if (objList == null || objList.size() != 9) {
			return new BaseExcution<>(BaseStateEnum.WRONG_TEAM_COUNT);
		}
		// 转为可写数组
		List<TeamVo> list = new ArrayList<>(objList);
		// 打乱list
		Collections.shuffle(list);
		int count = 0;
		// 遍历9次,读出
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				GroupEnum stringOf = GroupEnum.stringOf(j);
				TeamVo teamVo = list.get(count);
				Group g = EntityFactroy.getBean(Group.class);
				g.setCupId(cupId);
				g.setTeamId(teamVo.getTeamId());
				g.setTeamGroup(stringOf.getGroup_string());
				BaseExcution<Group> insertObj = groupService.add(g);
				if (ResultUtil.failResult(insertObj)) {
					throw new RuntimeException("分组失败");
				}
				count++;
			}
		}
		Cup findById = cupDao.findById(cupId);
		if (findById == null) {
			throw new Exception("更新赛事分组状态失败");
		}
		findById.setIsGroup(1);
		cupDao.edit(findById);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

}
