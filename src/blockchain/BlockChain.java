/**
	 * 存储课程简介的区块链系统搭建
	 * 组长：查伦
	 * 组员：程启伦，谭振豪，尚可珂
	 */
package blockchain;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class BlockChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();// 创建区块链动态列表
	public static ArrayList<MerkleTrees> merkleTrees = new ArrayList<MerkleTrees>();// 创建默克尔树动态列表
	public static int difficulty = 2;// 定义静态变量挖矿难度

	/**
	 * 区块链添加成链主程序
	 */
	public static void main(String[] args) {
//第一个块
		ArrayList<String> CourseList1 = new ArrayList<String>();// 新建动态列表存储课程简介
		CourseList1.add("新概念英语");
		CourseList1.add("提升学生词汇量，打好语法基础，掌握简单句、从句等基础语法知识，能听懂熟悉的日常对话");
		CourseList1.add("雅思");
		CourseList1.add("国际英语语言测试系统，由British Council建立的用于出国留学或者移民的英语能力测试");
		MerkleTrees merkleTrees1 = new MerkleTrees(CourseList1, 1);// 将存储了数据的列表和序号初始化给默克尔树
		merkleTrees1.merkle_tree();// 默克尔树构造
		merkleTrees.add(merkleTrees1);// 将构造好的默克尔树添加到默克尔树列表
		blockchain.add(new Block(merkleTrees1.getRoot(), "0", blockchain.size() + 1));// 将默克尔树树根的哈希值，前一个区块的哈希值，本区块的序号等初始化成块
		System.out.println("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);// 进行挖矿
//第二个块
		ArrayList<String> CourseList2 = new ArrayList<String>();
		CourseList2.add("GRE");
		CourseList2.add("美国研究生入学考试，由美国教育考试服务处主办");
		CourseList2.add("GMAT");
		CourseList2.add("经企管理研究生入学考试，由美国教务考试服务处主办");
		MerkleTrees merkleTrees2 = new MerkleTrees(CourseList2, 2);
		merkleTrees2.merkle_tree();
		merkleTrees.add(merkleTrees2);
		blockchain.add(
				new Block(merkleTrees2.getRoot(), blockchain.get(blockchain.size() - 1).hash, blockchain.size() + 1));
		System.out.println("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);
//第三个块
		ArrayList<String> CourseList3 = new ArrayList<String>();
		CourseList3.add("日语入门");
		CourseList3.add("日语基础词汇，语法，造句");
		CourseList3.add("韩语入门");
		CourseList3.add("韩语基础词汇，语法，造句");
		MerkleTrees merkleTrees3 = new MerkleTrees(CourseList3, 3);
		merkleTrees3.merkle_tree();
		merkleTrees.add(merkleTrees3);
		blockchain.add(
				new Block(merkleTrees3.getRoot(), blockchain.get(blockchain.size() - 1).hash, blockchain.size() + 1));
		System.out.println("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);
//第四个块
		ArrayList<String> CourseList4 = new ArrayList<String>();
		CourseList4.add("ACCA");
		CourseList4.add("特许注册会计师联盟，培养会计金融方向综合型人才");
		CourseList4.add("CFA");
		CourseList4.add("注册金融分析师，培养金融方向专业分析员");
		MerkleTrees merkleTrees4 = new MerkleTrees(CourseList4, 4);
		merkleTrees4.merkle_tree();
		merkleTrees.add(merkleTrees4);
		blockchain.add(
				new Block(merkleTrees4.getRoot(), blockchain.get(blockchain.size() - 1).hash, blockchain.size() + 1));
		System.out.println("Trying to Mine block 4... ");
		blockchain.get(3).mineBlock(difficulty);
//第五个块
		ArrayList<String> CourseList5 = new ArrayList<String>();
		CourseList5.add("FRM");
		CourseList5.add("金融风险管理，培养金融风控人才");
		CourseList5.add("CMA");
		CourseList5.add("注册管理会计师，培养职业管理会计");
		MerkleTrees merkleTrees5 = new MerkleTrees(CourseList5, 5);
		merkleTrees5.merkle_tree();
		merkleTrees.add(merkleTrees5);
		blockchain.add(
				new Block(merkleTrees5.getRoot(), blockchain.get(blockchain.size() - 1).hash, blockchain.size() + 1));
		System.out.println("Trying to Mine block 5... ");
		blockchain.get(4).mineBlock(difficulty);
//准备输出
		System.out.println("\nBlockchain is Valid: " + isChainValid());// 输出区块链是否有效

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);// 依次输出链中区块

		String merkletreeJson = new GsonBuilder().setPrettyPrinting().create().toJson(merkleTrees);
		System.out.println("\nThe MerkleTree: ");
		System.out.println(merkletreeJson);// 依次输出区块中默克尔树种存储的课程简介及树根哈希值
	}

	/**
	 * 检验区块及区块链是否有效
	 */
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// 检验本区块哈希值是否:
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// 检验上一个区块的哈希值是否等于本区块中存储的上一个区块哈希值（previousHash）
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// 检验是否完成工作量证明
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			// 检验数据是否被篡改
			if (!merkleTrees.get(i).getRoot().contentEquals(currentBlock.merkletreeroot)) {
				System.out.println("The block has been changed");
				return false;
			}
		}
		return true;
	}
}