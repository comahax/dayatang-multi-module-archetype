#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Dictionary;
import ${package}.domain.License;
import org.apache.struts2.json.annotations.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: tune
 * Date: 13-6-4
 * Time: 上午10:30
 */
public class LicenseDto {

    public Long id;

    //组织机构
    public String internalOrganizationDtoId;

    public InternalOrganizationDto internalOrganizationDto;

    //证书类型，如资格证书，运营证，荣誉证书等等。用字典定义。
    private String credType;

    //证书类型文本
    private String credTypeText;

    //证书编号
    private String credNumber;

    //颁发机构
    private String authority;

    //颁发日期
    private Date awardDate;

    //有效期
    private Date periodDate;

    public LicenseDto() {
    }

    public LicenseDto(License licnese) {
        if (null == licnese) {
            return;
        }
        id = licnese.getId();
        internalOrganizationDto = new InternalOrganizationDto(licnese.getOrganization());
        internalOrganizationDtoId = String.valueOf(internalOrganizationDto.getId());
        credType = licnese.getCredType();
        Dictionary dictionary = Dictionary.get(Dictionary.class, Long.valueOf(credType));
        credTypeText = dictionary.getRemark();
        credNumber = licnese.getCredNumber();
        authority = licnese.getAuthority();
        awardDate = licnese.getAwardDate();
        periodDate = licnese.getPeriodDate();
    }


    public static List<LicenseDto> createBy(List<License> licenses) {
        List<LicenseDto> results = new ArrayList<LicenseDto>();

        if(null == licenses || licenses.size() < 1){
            return results;
        }


        for(License each : licenses){
            results.add(new LicenseDto(each));
        }
        return results;
    }

    public String getInternalOrganizationDtoId() {
        return internalOrganizationDtoId;
    }

    public void setInternalOrganizationDtoId(String internalOrganizationDtoId) {
        this.internalOrganizationDtoId = internalOrganizationDtoId;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setCredType(String credType) {
        this.credType = credType;
    }

    public void setCredNumber(String credNumber) {
        this.credNumber = credNumber;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public Long getId() {
        return id;
    }

    public String getCredType() {
        return credType;
    }

    public String getCredNumber() {
        return credNumber;
    }

    public String getAuthority() {
        return authority;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getAwardDate() {
        return awardDate;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getPeriodDate() {
        return periodDate;
    }

    public InternalOrganizationDto getInternalOrganizationDto() {
        return internalOrganizationDto;
    }

    public void setInternalOrganizationDto(InternalOrganizationDto internalOrganizationDto) {
        this.internalOrganizationDto = internalOrganizationDto;
    }

    public String getCredTypeText() {
        return credTypeText;
    }

    public void setCredTypeText(String credTypeText) {
        this.credTypeText = credTypeText;
    }
}
