#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Organization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrganizationDto {
	protected Long id;

	protected String name;

	protected String homePage;

	protected String tel;

	protected String email;

	protected String address;
    /**
     * 编号
     */
    private String serialNumber;

    /**
     * 简称
     */
    private String abbr;

    protected boolean disabled = false;

	protected Integer level = 0;

	public OrganizationDto(Organization organization) {
        if (null == organization) {
            return ;
        }
        id = organization.getId();
        name = organization.getName();
        homePage = organization.getHomePage();
        tel = organization.getTel();
        email = organization.getEmail();
        address = organization.getAddress();
        level = organization.getLevel();
        disabled = organization.isDisabled();
        serialNumber = organization.getSerialNumber();
        abbr = organization.getAbbr();
    }

	public OrganizationDto() {
	}

	public static <T extends Organization> List<OrganizationDto> idNameOf(Collection<T> organizations) {
		List<OrganizationDto> results = new ArrayList<OrganizationDto>();
		if (null == organizations) {
			return results;
		}
		for (T each : organizations) {
			results.add(idNameOf(each));
		}
		return results;
	}

	public static <T extends Organization> OrganizationDto idNameOf(T organization) {
		OrganizationDto result = new OrganizationDto();
		if (null == organization) {
			return result;
		}
		result.id = organization.getId();
		result.name = organization.getName();
		return result;
	}

	public static List<OrganizationDto> createBy(Collection<Organization> organizations) {
		List<OrganizationDto> results = new ArrayList<OrganizationDto>();

		if (null == organizations) {
			return results;
		}

		for (Organization each : organizations) {
			results.add(new OrganizationDto(each));
		}
		return results;
	}

	public String getName() {
		return name;
	}

	public String getHomePage() {
		return homePage;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public Long getId() {
		return id;
	}

	public Integer getLevel() {
		return level;
	}

    public boolean isDisabled() {
        return disabled;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getAbbr() {
        return abbr;
    }
}
