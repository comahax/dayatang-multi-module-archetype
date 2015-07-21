#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目流水号分配表
 * User: zjzhai
 * Date: 13-6-13
 * Time: 上午11:34
 */
@Entity
@Table(name = "project_serial_number_assignments")
public class ProjectSerialNumberAssignment extends AbstractEntity {

	private static final long serialVersionUID = 1064442795898979657L;

	/**
     * 初始流水号
     */
    public static final String INIT_SERIAL_NUMBER = "000";

    /**
     * 公司ID
     */
    @Column(name = "company_id")
    private Long companyId;


    /**
     * 年代代码
     * 2017年的年代代码是：17
     */
    @Column(name = "year_code")
    private String yearCode;


    /**
     * 3位流水号
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 得到下一个流水号
     *
     * @return
     */
    public String getNextSerialNumber() {
        int serialNumberInteger = Integer.valueOf(getSerialNumber());
        System.out.println((serialNumberInteger + "").length());
        return StringUtils.repeat("0", 3 - (serialNumberInteger + 1 + "").length()) + (serialNumberInteger + 1) + "";
    }

    /**
     * 创建一个初始化的流水号
     * @param companyId
     * @param yearCode
     * @return
     */
    public static ProjectSerialNumberAssignment createInitProjectSerialNumberAssignment(Long companyId, String yearCode){
        ProjectSerialNumberAssignment result = new ProjectSerialNumberAssignment();
        result.setCompanyId(companyId);
        result.setSerialNumber(ProjectSerialNumberAssignment.INIT_SERIAL_NUMBER);
        result.setYearCode(yearCode);
        return result;
    }

    public ProjectSerialNumberAssignment next() {
        ProjectSerialNumberAssignment result = new ProjectSerialNumberAssignment();
        result.setCompanyId(companyId);
        result.setSerialNumber(getNextSerialNumber());
        result.setYearCode(yearCode);
        return result;
    }

    /**
     * 得到最新的
     *
     * @return
     */
    public static ProjectSerialNumberAssignment getLastest(Long companyId, String yearCode) {
        return getRepository().getSingleResult(QuerySettings.create(ProjectSerialNumberAssignment.class).eq("companyId", companyId).eq("yearCode", yearCode).desc("id"));
    }


    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getYearCode() {
        return yearCode;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectSerialNumberAssignment)) return false;

        ProjectSerialNumberAssignment that = (ProjectSerialNumberAssignment) o;

        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null) return false;
        if (yearCode != null ? !yearCode.equals(that.yearCode) : that.yearCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId != null ? companyId.hashCode() : 0;
        result = 31 * result + (yearCode != null ? yearCode.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectSerialNumberAssignment{" +
                "companyId=" + companyId +
                ", yearCode='" + yearCode + '${symbol_escape}'' +
                ", serialNumber='" + serialNumber + '${symbol_escape}'' +
                '}';
    }
}
