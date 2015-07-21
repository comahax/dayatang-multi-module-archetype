#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.ValueObject;

/**
 * 文件的标签
 * 
 * @author zjzhai
 * 
 */
@Embeddable
public class DocumentTag implements ValueObject {

	private static final long serialVersionUID = -6852592124345755412L;

	private String tagKey;

	private String tagValue;

	public DocumentTag() {
		super();
	}

	public DocumentTag(String tagKey, String tagValue) {
		super();
		this.tagKey = tagKey;
		this.tagValue = tagValue;
	}

	public DocumentTag(String tagKey, long tagValue) {
		super();
		this.tagKey = tagKey;
		this.tagValue = Long.toString(tagValue);
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(tagKey).toHashCode();
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof DocumentTag))
			return false;
		DocumentTag that = (DocumentTag) other;
		return new EqualsBuilder().append(tagKey, that.tagKey).isEquals();
	}

	public String toString() {
		return tagKey + "" + tagValue;
	}

	public String getTagKey() {
		return tagKey;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

}
