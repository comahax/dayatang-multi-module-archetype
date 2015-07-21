#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.impl;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.Entity;
import com.dayatang.domain.EntityRepository;
import com.dayatang.domain.InstanceFactory;
import ${package}.application.CommonsApplication;
import ${package}.domain.AbstractCommonEntity;
import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.domain.InternalOrganization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CommonsApplicationImpl implements CommonsApplication {

    private Logger logger = LoggerFactory
            .getLogger(CommonsApplicationImpl.class);

    @Inject
    private EntityRepository repository;

    private EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    public void setRepository(EntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends AbstractEntity> T saveEntity(T entity) {
        if (null == entity) {
            return null;
        }
        entity.save();
        logger.info("Save entity " + entity.getClass().getSimpleName()
                + " with id: " + entity.getId());
        return entity;
    }

    public void removeEntity(AbstractEntity entity) {
        if (null == entity) {
            return;
        }
        entity.remove();
        logger.info("Remove entity " + entity.getClass().getSimpleName()
                + " with id: " + entity.getId());
    }

    public void createChildInternalOrganization(
            InternalOrganization internalOrganization,
            InternalOrganization parent) {
        parent.createChild(internalOrganization);
    }

    public void remove(Document document) {
        document.disable();
    }

    public void destroy(Document document) {
        document.remove();
    }

    /**
     * 同时保存多个文件,一个标签
     */
    public void saveSomeDocuments(List<Document> documents,
                                  Set<DocumentTag> tags, Date uploadDate) throws IOException {
        if (documents != null && !documents.isEmpty()) {
            for (Document each : documents) {
                if (each != null) {
                    each.setUploadDate(uploadDate);
                    each.saveSelfAndFile();
                    each.addTags(tags);
                }
            }
        }
    }

    @Override
    public <T extends Entity> T getEntity(Class<T> entityClass, Long id) {
        return getRepository().get(entityClass, id);
    }

    @Override
    public <T extends Entity> List<T> findAllEntities(Class<T> entityClass) {
        return getRepository().findAll(entityClass);
    }

    public void disable(AbstractCommonEntity entity) {
        if (null != entity) {
            entity.disable();
            entity.save();
        }

    }

    @Override
    public void enable(AbstractCommonEntity entity) {
        entity.enable();
        entity.save();
    }

}
