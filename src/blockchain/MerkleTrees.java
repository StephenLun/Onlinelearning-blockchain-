package blockchain;

import java.util.ArrayList;
import java.util.List;

public class MerkleTrees {

	List<String> CourseList;// 需要存储的课程简介构成的列表
	String root;// 以哈希值形式呈现的默克尔树根
	int index;// 默克尔树在链中的序号

	/**
	 * 初始化默克尔树
	 */
	public MerkleTrees(List<String> CourseList, int index) {
		this.CourseList = CourseList;
		root = "";
		this.index = index;
	}

	/**
	 * 生成默克尔树的迭代函数
	 */
	private List<String> getNewCourseList(List<String> tempCourseList) {

		List<String> newCourseList = new ArrayList<String>();
		int index = 0;
		while (index < tempCourseList.size()) {
			// 左子树赋值
			String left = tempCourseList.get(index);
			index++;
			// 右字数赋值
			String right = "";//如若遇到奇数个节点，用空值进行补足
			if (index != tempCourseList.size()) {
				right = tempCourseList.get(index);
			}
			// 左右字数合并取哈希值
			String sha2HexValue = getShavalue.applySha256(left + right);
			newCourseList.add(sha2HexValue);
			index++;

		}

		return newCourseList;
	}

	/**
	 * 迭代构造默克尔树
	 */
	public void merkle_tree() {

		List<String> tempCourseList = new ArrayList<String>();

		for (int i = 0; i < this.CourseList.size(); i++) {
			tempCourseList.add(this.CourseList.get(i));
		}
		
		List<String> hashList = new ArrayList<String>();
		for (int x = 0; x < tempCourseList.size();x = x + 1);{
			int xx = 0;
			String shavalue = getShavalue.applySha256(tempCourseList.get(xx));
			hashList.add(shavalue);
			xx = xx + 1;
		}
		
		List<String> newCourseList = getNewCourseList(hashList);

		while (newCourseList.size() != 1) {
			newCourseList = getNewCourseList(newCourseList);
		} // 迭代合并直到列表中值得数量为1

		this.root = newCourseList.get(0);// 将哈希值赋值给树根
	}

	public String getRoot() {
		return this.root;
	}

}