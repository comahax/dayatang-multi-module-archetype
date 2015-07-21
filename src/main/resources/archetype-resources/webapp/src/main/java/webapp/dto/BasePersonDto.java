#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import org.apache.commons.lang3.StringUtils;

import ${package}.domain.Person;

/**
 * User: zjzhai Date: 13-4-7 Time: 下午5:09
 */
public class BasePersonDto {
	private Long id;

	private String name;

	/**
	 * 性别
	 */
	private String gender;

	private String title;

	private String mobile;

	private String tel;

	private String email;

	private String qq;

	private boolean disabled;

	public BasePersonDto() {
	}

	public BasePersonDto(Person person) {
		if (null == person) {
			return;
		}
		id = person.getId();
		name = person.getName();
		if (null != person.getGender()) {
			gender = person.getGender().getCnText();
		}
		title = person.getTitle();
		mobile = person.getMobile();
		tel = person.getTel();
		email = person.getEmail();
		qq = person.getQq();
		disabled = person.isDisabled();
	}

	public String getContact() {
		if (StringUtils.isNotEmpty(mobile)) {
			return "mobile:" + mobile;
		} else {
			if (StringUtils.isNotEmpty(tel)) {
				return "Tel:" + tel;
			} else {
				if (StringUtils.isNotEmpty(email)) {
					return "Email:" + email;
				} else {

					return StringUtils.isNotEmpty(qq) ? "QQ:" + qq : "";
				}
			}
		}

	}

	public boolean isDisabled() {
		return disabled;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public String getTitle() {
		return title;
	}

	public String getMobile() {
		return mobile;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public String getQq() {
		return qq;
	}

}
