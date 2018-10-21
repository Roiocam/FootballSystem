package club.pypzx.FootballSystem.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.enums.GroupEnum;

/**
 * 测试生成4位随机数方法最优性能
 * 
 * @author Roiocam
 * @date 2018年9月11日 下午3:32:53
 */
public class RandomTest {
	public static void methodA() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			int intFlag = (int) (Math.random() * 1000);

			String flag = String.valueOf(intFlag);
			if (flag.length() == 4 && flag.substring(0, 1).equals("9")) {
//				System.out.println(intFlag);
			} else {
				intFlag = intFlag + 1000;
//				System.out.println(intFlag);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("方法A用时:" + (end - start));

	}

	public static void methodB() {
		long start = System.currentTimeMillis();

		for (int j = 0; j < 10000000; j++) {
			int a = (int) ((Math.random() * 9 + 1) * 1000);
			System.out.println(a);
		}

		long end = System.currentTimeMillis();
		System.out.println("方法B用时:" + (end - start));
	}

	public static void methodC() {
		long start = System.currentTimeMillis();
		Random random = new Random();
		for (int j = 0; j < 10000000; j++) {
			String result = "";
			for (int i = 0; i < 4; i++) {
				result += random.nextInt(10);
			}
			System.out.println(result);
		}

		long end = System.currentTimeMillis();
		System.out.println("方法C用时:" + (end - start));
	}

	public static void methodD() {
		long start = System.currentTimeMillis();

		for (int j = 0; j < 10000000; j++) {
			int flag = new Random().nextInt(9999);
			if (flag < 1000) {
				flag += 1000;
			}

		}
		long end = System.currentTimeMillis();
		System.out.println("方法D用时:" + (end - start));
	}

	public static void methodE() {
		long start = System.currentTimeMillis();
		Random rand = new Random();

		for (int i = 0; i < 10000000; i++) {
			String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
			StringBuffer flag = new StringBuffer();

			for (int j = 0; j < 4; j++) {
				flag.append(sources.charAt(rand.nextInt(9)) + "");
			}

		}
		long end = System.currentTimeMillis();
		System.out.println("方法E用时:" + (end - start));
	}

//	public static void main(String[] args) {
//		List<Integer> list = new ArrayList();
//		for (int i = 0; i < 9; i++) {
//			list.add(i);
//		}
//		for (int i = 0; i < 20; i++) {
//			randomListA(list);
//
//		}
//		for (int i = 0; i < 20; i++) {
//			randomListB(list);
//
//		}
//		
//	}

	public static void randomListA(List<Integer> list) {
		long start = System.currentTimeMillis();
		Collections.shuffle(list);
		for (int x = 0; x < 9000000; x++) {
			Iterator iterator = list.iterator();
			int count = 1;
			int index = 1;
			GroupEnum stringOf = GroupEnum.stringOf(index);
			while (iterator.hasNext()) {
				if (count % 3 == 0) {
					stringOf = GroupEnum.stringOf(index++);
				}
				int next = (int) iterator.next();
				count++;
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("方法A用时:" + (end - start));
	}

	public static void randomListB(List<Integer> list) {
		long start = System.currentTimeMillis();
		Collections.shuffle(list);
		for (int x = 0; x < 9000000; x++) {
			int count = 0;
			// 遍历9次,读出
			for (int i = 1; i < 4; i++) {
				for (int j = 1; j < 4; j++) {
					GroupEnum stringOf = GroupEnum.stringOf(j);
					Integer next = list.get(count);
					
					count++;
				}
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("方法B用时:" + (end - start));
	}

//	public static void main(String[] args) {
//		methodA();
//		methodB();
//		methodC();
//		methodD();
//		methodE();
//		for(int i=0;i<100;i++) {
//			System.out.println(IDUtils.getUUID());
//		}
//		System.out.println(IDUtils.getUUID());
//	}
//	public static void main(String[] args) {
//		final String[] str = { "a-b 1:1", "a-c 2:1", "b-c 0:1" };
//
//		final Map<String, Integer> map = new HashMap<String, Integer>();
//		for (String s : str) {
//			final String[] temp = s.split(" ");
//			final String[] names = temp[0].split("-");
//			final String[] counts = temp[1].split(":");
//			for (int i = 0; i < 2; i++) {
//				String name = names[i];
//				int count = Integer.parseInt(counts[i]);
//				if (map.containsKey(name)) {
//					map.put(name, map.get(name) + count);
//				} else {
//					map.put(name, count);
//				}
//			}
//		}
//
//		final List<Team> teams = new LinkedList<Team>();
//		for (String name : map.keySet()) {
//			teams.add(new Team(name, map.get(name)));
//		}
//		Collections.sort(teams);
//		System.out.println("队伍 : 分");
//		for (Team t : teams) {
//			System.out.println(t.getName() + "   : " + t.getCount());
//		}
//	}
}

class Team implements Comparable<Team> {
	private String name;
	private int count;

	public Team(String name, int count) {
		this.name = name;
		this.count = count;
	}

	public void addCount(int count) {
		this.count = this.count + count;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public int compareTo(Team o) {
		return o.getCount() - this.count;
	}
}