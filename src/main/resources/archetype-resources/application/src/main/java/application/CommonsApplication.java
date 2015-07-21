#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.Entity;
import ${package}.domain.AbstractCommonEntity;
import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.domain.InternalOrganization;

/**
 * 应用层门面接口。封装对领域层的访问。
 * 
 * @author yyang
 * 
 */
public interface CommonsApplication {

	/**
	 * 保存实体到仓储中
	 * 
	 * @param entity
	 */
	<T extends AbstractEntity> T saveEntity(T entity);

	/**
	 * 从仓储中删除实体
	 * 
	 * @param entity
	 */
	void removeEntity(AbstractEntity entity);

	/**
	 * 获取ID为指定值的实体
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	<T extends Entity> T getEntity(Class<T> entityClass, Long id);

	/**
	 * 获取指定类型的所有实体
	 * 
	 * @param entityClass
	 * @return
	 */
	<T extends Entity> List<T> findAllEntities(Class<T> entityClass);

	/**
	 * 创建内部机构的子机构
	 * 
	 * @param internalOrganization
	 * @param parent
	 */
	void createChildInternalOrganization(InternalOrganization internalOrganization, InternalOrganization parent);

	/**
	 * 隐藏文件
	 * 
	 * @param document
	 *            文档
	 */
	void remove(Document document);

	/**
	 * 删除文件
	 * 
	 * @param document
	 *            文档
	 * @throws IOException 
	 */
	void destroy(Document document);

	// ******* 附件管理 ******
	void saveSomeDocuments(List<Document> documents, Set<DocumentTag> tags, Date uploadDate) throws IOException;

	void disable(AbstractCommonEntity entity);

	void enable(AbstractCommonEntity entity);

}
