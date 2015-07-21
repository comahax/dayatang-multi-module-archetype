#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;
import ${package}.commons.PinyinConventUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 　区域 leftValue和rightValue用于计算父子关系。如果节点A的leftValue处于节点B的leftValue和
 * rightValue之间，则节点A是节点B的后代节点（直接或间接）。level属性代表节点的层级：其中
 * 根节点的level是0，一级子节点的level是1，以此类推。
 *
 * @author zjzhai
 */
@Entity
@Table(name = "areas")
public class Area extends AbstractEntity {

    private static final long serialVersionUID = -3806045330540534925L;

    /**
     * 代表“中国”的地区。在本系统中是所有地区的根节点
     */
    private static Long ROOT_ID = -1L;

    // 省
    public static Integer PROVINCE_LEVEL = 1;

    // 市
    public static Integer CITY_LEVEL = 2;

    // 县
    public static Integer COUNTY_LEVEL = 3;

    // 编码
    private String code;
    // 名称
    private String name;

    @Column(name = "left_value")
    private Integer leftValue;

    @Column(name = "right_value")
    private Integer rightValue;

    private Integer level;

    @Column(name = "parent_code")
    private String parentCode;

    public Area getProvince() {
        // 原本在县级
        if (level == COUNTY_LEVEL) {
            if (getParent() == null) {
                return null;
            }
            return getParent().getParent();
        }
        if (level == CITY_LEVEL) {
            return getParent();
        }
        return this;
    }

    public Area getCity() {
        if (level == COUNTY_LEVEL) {
            return getParent();
        }
        if (level == PROVINCE_LEVEL) {
            return null;
        }
        return this;
    }

    public Area getCounty() {
        if (COUNTY_LEVEL == level) {
            return this;
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        Area parent = getParent();
        if (parent == null || parent.getLevel() == 0) {
            return name;
        }
        return parent.getFullName() + name;
    }

    public Integer getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(Integer leftValue) {
        this.leftValue = leftValue;
    }

    public Integer getRightValue() {
        return rightValue;
    }

    public void setRightValue(Integer rightValue) {
        this.rightValue = rightValue;
    }

    /**
     * 得到省市的拼音缩写
     *
     * @return
     */
    public String getShortPinyin() {
        if (PROVINCE_LEVEL.equals(getLevel())) {
            return PinyinConventUtils.conventToShortPinyin(getName().substring(0, 2));
        }
        return PinyinConventUtils.conventToShortPinyin(getProvince().getName().substring(0, 2)) + PinyinConventUtils.conventToShortPinyin(getCity().getName().substring(0, 2));
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public static Area root() {
        return get(ROOT_ID);
    }

    /**
     * 获得上级地区。例如吴县的上级地区是苏州市，苏州市的上级地区是江苏省等。
     *
     * @return
     */
    public Area getParent() {
        QuerySettings<Area> querySettings = QuerySettings.create(Area.class).lt("leftValue", leftValue)
                .gt("rightValue", rightValue).eq("level", level - 1);
        return getRepository().getSingleResult(querySettings);
    }

    /**
     * 获得下级地区。
     *
     * @return
     */
    public List<Area> getChildren() {
        QuerySettings<Area> querySettings = QuerySettings.create(Area.class).gt("leftValue", leftValue)
                .lt("rightValue", rightValue).eq("level", level + 1);
        return getRepository().find(querySettings);
    }

    public static Area findByAreaName(String areaName) {
        QuerySettings<Area> settings = QuerySettings.create(Area.class).eq("name", areaName);
        return getRepository().getSingleResult(settings);
    }

    public static Area get(long id) {
        return getRepository().get(Area.class, id);
    }

    public boolean equals(final Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Area))
            return false;
        Area castOther = (Area) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getCode()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("code", code).toString();
    }


}
