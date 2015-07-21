#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import org.apache.struts2.json.annotations.JSON;

import java.util.*;

/**
 * User: zjzhai Date: 13-4-11 Time: 下午7:50
 */
public class DocumentDto {

	private Long id;

	/**
	 * 文件名
	 */
	private String name;

	/**
	 * 文件大小
	 */
	private String size;

	/**
	 * 上传时间
	 */
	private Date uploadDate;

	/**
	 * 文件类型
	 */
	private String contentType;

	private Set<DocumentTag> tags = new HashSet<DocumentTag>();

	public DocumentDto(Document doc) {
		id = doc.getId();
		name = doc.getName();
		size = doc.getSizeShow();
		uploadDate = doc.getUploadDate();
		contentType = doc.getContentType();
	}

	public static List<DocumentDto> createBy(Collection<Document> docs) {
		List<DocumentDto> results = new ArrayList<DocumentDto>();
		if (null == docs || docs.isEmpty()) {
			return results;
		}
		for (Document each : docs) {
			results.add(new DocumentDto(each));
		}
		return results;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getUploadDate() {
		return uploadDate;
	}

	public String getContentType() {
		return contentType;
	}

	public Set<DocumentTag> getTags() {
		return tags;
	}
}
