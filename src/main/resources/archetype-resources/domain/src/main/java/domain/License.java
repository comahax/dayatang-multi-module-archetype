#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zjzhai
 * Date: 13-6-3
 * Time: 下午5:14
 */
@Entity
@Table(name = "licenses")
public class License extends AbstractEntity {



	private static final long serialVersionUID = -5906420192865624899L;

	@ManyToOne
    @JoinColumn(name = "organization_id")
    @NotNull
    private InternalOrganization organization;

    //证书类型，如资格证书，运营证，荣誉证书等等。用字典定义。
    @Column(name = "cred_type")
    private String credType;

    //证书编号
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cred_number", unique = true)
    private String credNumber;

    //颁发机构
    @Column(name = "authority")
    private String authority;

    //颁发日期
    @Temporal(TemporalType.DATE)
    @Column(name = "award_date")
    private Date awardDate;

    //有效期
    @Temporal(TemporalType.DATE)
    @Column(name = "period_date")
    private Date periodDate;


    public License() {
    }


    public static void changeLicenseType(String oldSerialNumber, String newSerialNumber) {
        String sql = "UPDATE License o SET o.credType = :newType WHERE e.credType = :oldType";
        Map<String ,Object> params = new HashMap<String, Object>();
        params.put("newType", newSerialNumber);
        params.put("oldType", oldSerialNumber);
        getRepository().executeUpdate(sql,params);
    }

    public InternalOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(InternalOrganization organization) {
        this.organization = organization;
    }

    public String getCredType() {
        return credType;
    }

    public void setCredType(String credType) {
        this.credType = credType;
    }

    public String getCredNumber() {
        return credNumber;
    }

    public void setCredNumber(String credNumber) {
        this.credNumber = credNumber;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    @Override
    public String toString() {
        return "License{" +
                "credType='" + credType + '${symbol_escape}'' +
                ", credNumber='" + credNumber + '${symbol_escape}'' +
                ", authority='" + authority + '${symbol_escape}'' +
                ", awardDate=" + awardDate +
                ", periodDate=" + periodDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof License)) return false;

        License license = (License) o;

        if (authority != null ? !authority.equals(license.authority) : license.authority != null) return false;
        if (awardDate != null ? !awardDate.equals(license.awardDate) : license.awardDate != null) return false;
        if (credNumber != null ? !credNumber.equals(license.credNumber) : license.credNumber != null) return false;
        if (credType != null ? !credType.equals(license.credType) : license.credType != null) return false;
        if (periodDate != null ? !periodDate.equals(license.periodDate) : license.periodDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = credType != null ? credType.hashCode() : 0;
        result = 31 * result + (credNumber != null ? credNumber.hashCode() : 0);
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        result = 31 * result + (awardDate != null ? awardDate.hashCode() : 0);
        result = 31 * result + (periodDate != null ? periodDate.hashCode() : 0);
        return result;
    }


}
