#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pager;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;
import com.dayatang.domain.InstanceFactory;
import com.dayatang.domain.QuerySettings;

import java.io.Serializable;
import java.util.List;

public final class PageList<T> implements Serializable {

    private static final long serialVersionUID = 7636400405542683379L;
    private List<T> datas;
    private Page page;

    private static EntityRepository getRepository() {
        return InstanceFactory.getInstance(EntityRepository.class);
    }

    private PageList() {

    }

    private PageList(List<T> datas, Page page) {
        this.datas = datas;
        this.page = page;
    }

    // 参数是否要换下秩序
    public static <T extends AbstractEntity> PageList<T> create(QuerySettings<T> settings, int currentPage, int pageSize) {
        int totalCount =  getRepository().find(settings).size();
        Page page = new Page(totalCount, pageSize, currentPage);
        List<T> datas = getRepository().find( settings.setFirstResult(page.getFirstIndex()).setMaxResults(pageSize));
        return new PageList<T>(datas, page);
    }

    public static <T> PageList<T> create(List<T> datas, int currentPage, int pageSize) {
        List<T> results = datas;
        if (results == null || results.isEmpty()) {
            return null;
        }
        Page page = new Page(results.size(), pageSize, currentPage);
        List<T> resultDates = results.subList(page.getFirstIndex(), page.getLastIndex());
        return new PageList<T>(resultDates, page);
    }

    public static <T> PageList<T> createByOtherDatas(List<T> datas, int currentPage, int pageSize) {
        List<T> results = datas;
        if (results == null || results.isEmpty()) {
            return null;
        }
        Page page = new Page(results.size(), pageSize, currentPage);
        List<T> resultDates = results.subList(page.getFirstIndex(), page.getLastIndex());
        return new PageList<T>(resultDates, page);
    }

    /**
     * 记录的总条数
     *
     * @return
     */
    public long getTotal() {
        if (getPage() == null) {
            return 0;
        }
        return getPage().getRowCount();
    }

    public List<T> getData() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
