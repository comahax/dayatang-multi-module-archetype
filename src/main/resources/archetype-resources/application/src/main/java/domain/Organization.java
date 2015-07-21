#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizations")
@DiscriminatorColumn(name = "category", discriminatorType = DiscriminatorType.STRING)
public abstract class Organization extends AbstractCommonEntity {

    private static final long serialVersionUID = 5874917516966039318L;

    private String name;

    @Column(name = "home_page")
    private String homePage;

    private String tel;

    private String email;

    private String address;

    @Column(name = "left_value")
    private Integer leftValue = 100000;

    @Column(name = "right_value")
    private Integer rightValue = 100001;

    private Integer level = 0;

    /**
     * 编号
     */
    @Column(name = "serial_number")
    private String serialNumber;


    /**
     * 简称
     */
    private String abbr;


    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    public static Organization getByName(String name) {
        return getRepository().getSingleResult(QuerySettings.create(Organization.class).eq("name", name));
    }

    public static boolean isNameExist(String name) {
        for (Organization organization : findAll(Organization.class)) {
            if (name.equals(organization.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 按名字模糊查询
     *
     * @param name
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Organization> List<T> findByLikeName(String name, Class cls) {
        List<T> results = new ArrayList<T>();
        results.addAll(getRepository().find(QuerySettings.create(cls).containsText("name", name)));
        return results;
    }

    public static Organization get(long organizationId) {
        return Organization.get(Organization.class, organizationId);
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Organization)) {
            return false;
        }
        Organization that = (Organization) other;
        return new EqualsBuilder().append(getLeftValue(), that.getLeftValue()).append(getRightValue(), that.getRightValue())
                .append(getName(), that.getName()).append(getLevel(), that.getLevel()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getName()).append(getLeftValue()).append(getRightValue()).append(getLevel())
                .toHashCode();
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
