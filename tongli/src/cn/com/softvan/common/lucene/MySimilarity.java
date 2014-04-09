package cn.com.softvan.common.lucene;

import org.apache.lucene.search.Similarity;
/**
 * 
 * @author wuxiaogang 
 *
 */
public class MySimilarity extends Similarity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3294012549969510258L;

	/**
	 * 当查找词组时，命中率高的权重高 
	 * 如：调拨　功能　　　同时出现的会更高
	 */
	@Override
	public float coord(int overlap, int maxOverlap) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	/**
	 * 在创建索引时，
	 * 如Term="调拨" 他在多个文档中，按理讲，应该跨越多文档权重越低
	 * 
	 */
	@Override
	public float idf(int docFreq, int numDocs) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	/**
	 * 这是对文档长度设置权重，长度越短，权重越高
	 * 
	 */
	@Override
	public float lengthNorm(String fieldName, int numTokens) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	/**
	 * 这是跟查询有关系的
	 */
	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	/**
	 * 这是在查找时，
	 * 如调拨功能 ＂调拨功能＂比＂调拨我们的功能＂　越近的分数越高
	 * 
	 */
	@Override
	public float sloppyFreq(int distance) {
		// TODO Auto-generated method stub
		return 1.0f / (distance + 1);
	}

	/**
	 * 在一篇文档中出现的term越多权重发挥高
	 */
	@Override
	public float tf(float freq) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

}
