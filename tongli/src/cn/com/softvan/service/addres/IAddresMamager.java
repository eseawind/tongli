
/*	
 * 课程地址信息表   业务处理接口类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.27      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.service.addres;
import java.util.List;
import cn.com.softvan.bean.addres.TcAddresBean;
/**
 * <p>课程地址信息表   业务处理接口类。</p>	
 * <ol>[功能概要] 
 * <div>编辑(新增or修改)。</div> 
 * <div>详情检索。</div> 
 * <div>分页检索。</div> 
 * <div>列表检索。</div> 
 * <div>逻辑删除。</div> 
 * <div>物理删除。</div> 
 * <div>恢复逻辑删除。</div> 
 *</ol> 
 * @author wuxiaogang
 */
public interface IAddresMamager{

	/**	
	 * <p>信息编辑。</p>	
	 * <ol>[功能概要] 	
	 * <div>新增信息。</div>	
	 * <div>修改信息。</div>	
	 * </ol>	
	 * @return 处理结果	
	 */	
	public String saveOrUpdateData(TcAddresBean bean) throws Exception;	
	/**	
	 * <p>信息编辑。</p>	
	 * <ol>[功能概要] 	
	 * <div>物理删除。</div>	
	 * </ol>	
	 * @return 处理结果	
	 */	
	public String deleteData(TcAddresBean bean) throws Exception;	
	/**	
	 * <p>信息 单条。</p>	
	 * <ol>[功能概要] 	
	 * <div>恢复逻辑删除的数据。</div>	
	 * </ol>	
	 * @return 处理结果	
	 */	
	public String recoveryDataById(TcAddresBean bean) throws Exception;	
	/**	
	 * <p>信息 单条。</p>	
	 * <ol>[功能概要] 	
	 * <div>逻辑删除。</div>	
	 * </ol>	
	 * @return 处理结果	
	 */	
	public String deleteDataById(TcAddresBean bean) throws Exception;	
	/**	
	 * <p>信息列表 分页。</p>	
	 * <ol>[功能概要] 	
	 * <div>信息检索。</div>	
	 * <div>分页。</div>	
	 * </ol>	
	 * @return 处理结果	
	 */	
	public List<TcAddresBean> findDataIsPage(TcAddresBean bean);	
	/**	
	 * <p>信息列表。</p>	
	 * <ol>[功能概要] 	
	 * <div>信息检索。</div>	
	 * <div>列表。</div>	
	 * </ol>	
	 * @return 处理结果	
	 */	
	public List<TcAddresBean> findDataIsList(TcAddresBean bean);	
	/**	
	 * <p>信息详情。</p>	
	 * <ol>[功能概要] 	
	 * <div>信息检索。</div>	
	 * <div>详情。</div>	
	 * </ol>	
	 * @return 处理结果	
	 */	
	public TcAddresBean findDataById(TcAddresBean bean);	
}