#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xmfang
 */
@Entity
@Table(name = "dictionaries", uniqueConstraints = {@UniqueConstraint(columnNames = {"category", "serial_number"}),
        @UniqueConstraint(columnNames = {"category", "text"})})
public class Dictionary extends AbstractEntity {

    private static final long serialVersionUID = 6102498801737105639L;

    @NotNull
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @NotNull
    private String text;

    @Column(name = "parent_sn")
    private String parentSn;

    @Column(name = "sort_order")
    private int sortOrder;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DictionaryCategory category;

    @Column(length = 256)
    private String remark;

    /**
     * 字典的文本已经存在
     */
    public static final String THE_TEXT_OF_DICTIONARY_IS_EXIST = "THE_TEXT_OF_DICTIONARY_IS_EXIST";

    /**
     * 字典的编号已经存在
     */
    public static final String THE_SERIALNUMBER_OF_DICTIONARY_IS_EXIST = "THE_SERIALNUMBER_OF_DICTIONARY_IS_EXIST";

    /**
     * 字典的类型不能为空
     */
    public static final String THE_CATEGORY_OF_DICTIONARY_IS_REQUIRED = "THE_CATEGORY_OF_DICTIONARY_IS_REQUIRED";


    public Dictionary() {
    }

    /**
     * 找到没有父的字典类型
     *
     * @param category
     * @return
     */
    public static List<Dictionary> findByNullParentOf(DictionaryCategory category) {
        return getRepository().find(QuerySettings.create(Dictionary.class).eq("category", category).asc("sortOrder").isNull("parentSn"));
    }


    public static List<Dictionary> findByCategory(DictionaryCategory category) {
        return getRepository().find(QuerySettings.create(Dictionary.class).eq("category", category).asc("sortOrder"));
    }

    public static Dictionary get(Long id) {
        return Dictionary.get(Dictionary.class, id);
    }


    public static boolean isTextExist(String text, DictionaryCategory category) {
        QuerySettings<Dictionary> settings = QuerySettings.create(Dictionary.class);
        settings.eq("text", text).eq("category", category);
        return getRepository().find(settings).size() > 0;
    }

    public static boolean isSerialNumberExist(String serialNumber, DictionaryCategory category) {
        QuerySettings<Dictionary> settings = QuerySettings.create(Dictionary.class);
        settings.eq("serialNumber", serialNumber).eq("category", category);
        return getRepository().find(settings).size() > 0;
    }

    /**
     * 根据序列号获取字典文本
     *
     * @param serialNumber
     * @return
     */
    public static String getDictionaryTextBySerialNumBer(String serialNumber) {
        Dictionary dictionary = getRepository().getSingleResult(
                QuerySettings.create(Dictionary.class).eq("serialNumber", serialNumber));
        return dictionary != null ? dictionary.getText() : null;
    }

    public static String getDictionaryTextBySerialNumBerAndCategory(String serialNumber, DictionaryCategory category) {
        Dictionary dictionary = getDictionaryBySerialNumBerAndCategory(serialNumber, category);
        return dictionary != null ? dictionary.getText() : null;
    }

    public static Dictionary getDictionaryBySerialNumBerAndCategory(String serialNumber, DictionaryCategory category) {
        return getRepository().getSingleResult(
                QuerySettings.create(Dictionary.class).eq("serialNumber", serialNumber).eq("category", category));
    }

    public static Dictionary getDictionaryByTextAndCategory(String text, DictionaryCategory category) {
        return getRepository().getSingleResult(
                QuerySettings.create(Dictionary.class).eq("text", text).eq("category", category));
    }

    /**
     * 得到子
     *
     * @return
     */
    public List<Dictionary> findChildren() {
        return getRepository().find(QuerySettings.create(Dictionary.class).eq("parentSn", serialNumber));
    }

    /**
     * 此字典有无子字典
     *
     * @param dictionary
     * @return
     */
    public static boolean hasChildrenOf(Dictionary dictionary) {
       String sql = "SELECT count(o.id)  FROM Dictionary o WHERE o.parentSn = :parentSn";
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("parentSn", dictionary.getSerialNumber());
        Long result = getRepository().getSingleResult(sql, params, Long.class) ;
        return result != null && result > 0;
    }

    public static Map<String, String> getMap(DictionaryCategory category) {
        Map<String, String> results = new HashMap<String, String>();
        for (Dictionary dictionary : findByCategory(category)) {
            results.put(dictionary.getSerialNumber(), dictionary.getText());
        }
        return results;
    }

    @Override
    public void remove() {
        for (Dictionary each : getRepository().find(QuerySettings.create(Dictionary.class).eq("parentSn", serialNumber))) {
            each.remove();
        }
        super.remove();
    }

    @Override
    public String toString() {
        return serialNumber + ":" + text;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Dictionary))
            return false;
        Dictionary castOther = (Dictionary) other;
        return new EqualsBuilder().append(serialNumber, castOther.serialNumber).append(category, castOther.category).append(text, castOther.getText()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(serialNumber).append(category).toHashCode();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParentSn() {
        return parentSn;
    }

    public void setParentSn(String parentSn) {
        this.parentSn = parentSn;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public DictionaryCategory getCategory() {
        return category;
    }

    public void setCategory(DictionaryCategory category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
