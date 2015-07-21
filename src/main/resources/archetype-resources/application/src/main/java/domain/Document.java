#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.configuration.Configuration;
import com.dayatang.configuration.ConfigurationFactory;
import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.InstanceFactory;
import ${package}.Constants;
import com.dayatang.utils.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 文件类
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "documents")
public class Document extends AbstractEntity {

	private static final long serialVersionUID = 6559462429349958531L;

	/**
	 * 文件的无效标签
	 */
	private final static String DISABLED = "DISABLED";

	/**
	 * 文件名
	 */
	@Column(name = "file_name")
	private String name;

	/**
	 * 文件大小
	 */
	@Column(name = "file_size")
	private long size;

	/**
	 * 上传时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "upload_date")
	private Date uploadDate;

	/**
	 * 文件类型
	 */
	@Column(name = "content_type")
	private String contentType;

	/**
	 * 相对路径
	 */
	@Column(name = "relative_path")
	private String relativePath;

	/**
	 * 每一个文件都会带有一批标签
	 */
	@ElementCollection
	@CollectionTable(name = "document_tags")
	private Set<DocumentTag> tags = new HashSet<DocumentTag>();

	@Transient
	private File file;

	public Document() {
		super();
	}

	public Document(File file, String contentType, String fileName) {
		Assert.notNull(file);
		Assert.notEmpty(fileName);
		this.file = file;
		this.contentType = contentType;
		this.name = fileName;
		this.size = file.length();
	}

	public static List<Document> createBy(List<File> uploads, List<String> contentTypes, List<String> uploadsFileName) {
		List<Document> results = new ArrayList<Document>();
		if (uploads == null || uploads.isEmpty() || uploadsFileName == null || uploadsFileName.isEmpty()) {
			return null;
		}
		for (int i = 0; i < uploads.size(); i++) {
			results.add(new Document(uploads.get(i), contentTypes.get(i), uploadsFileName.get(i)));
		}
		return results;
	}

	public static List<Document> findByTags(Set<DocumentTag> tags) {
		List<Document> results = new ArrayList<Document>();
		if (null == tags || tags.isEmpty()) {
			return results;
		}

		DocumentTag firstTag = tags.iterator().next();
		List<Document> documents = findByOneTag(firstTag);
		for (Document each : documents) {
			if (each.containsAllTags(tags)) {
				results.add(each);
			}
		}
		return results;
	}

	public static List<Document> findByOneTag(String tagKey, String tagValue) {
		DocumentTag tag = new DocumentTag(tagKey, tagValue);
		return findByOneTag(tag);
	}

	public static List<Document> findByOneTag(DocumentTag tag) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagKey", tag.getTagKey());
		params.put("tagValue", tag.getTagValue());
		return getRepository().find(
				"SELECT o FROM Document o, IN(o.tags) t WHERE t.tagKey = :tagKey AND t.tagValue = :tagValue", params,
				Document.class);
	}

	/**
	 * 要求tags中的tag都必须有的时候才返回真
	 * 
	 * @param tags
	 * @return
	 */
	public boolean containsAllTags(final Set<DocumentTag> tags) {
		if (null == tags || tags.isEmpty()) {
			return false;
		}
		for (DocumentTag each : tags) {
			if (!this.getTags().contains(each)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 只要求包含tags中的其中一个tag就返回真
	 * 
	 * @param tags
	 * @return
	 */
	public boolean containOneOfTags(final List<DocumentTag> tags) {
		if (null == tags || tags.isEmpty()) {
			return false;
		}
		for (DocumentTag each : tags) {
			if (this.getTags().contains(each)) {
				return true;
			}
		}
		return false;
	}

	public boolean containOneTag(DocumentTag tag) {
		return getTags().contains(tag);
	}

	public void disable() {
		addTag(new DocumentTag(DISABLED, Boolean.toString(true)));
		save();
	}

	public void remove() {
		File file = new File(wholePath());
		FileUtils.deleteQuietly(file);
		super.remove();
	}

	public void removeAllTags() {
		setTags(new HashSet<DocumentTag>());
		save();
	}

	public boolean getDisableTagValue() {
		for (DocumentTag tag : tags) {
			if (DISABLED.equals(tag.getTagKey())) {
				return Boolean.valueOf(tag.getTagValue());
			}
		}
		return false;
	}

	// 持久化文件基本信息
	public void saveSelfAndFile() throws IOException {
		this.save();
		this.relativePath = generatRelativePath(getId(), getName());
		copy(this.file, wholePath());
		this.save();
	}

	private void copy(File sourceFile, String destPath) throws IOException {
		File destFile = new File(destPath);
		// TODO 需进行空间剩余检查
		FileUtils.copyFile(sourceFile, destFile);
	}

	public String wholePath() {
		String rootPath = getRootPath();
		if (rootPath.endsWith(File.separator)) {
			return rootPath + relativePath;
		}
		return rootPath + File.separator + this.relativePath;
	}

	public InputStream getInputStream() throws FileNotFoundException {
		return new FileInputStream(new File(wholePath()));
	}

	private String getRootPath() {
		String defaultPath = "/tmp";
		Configuration configuration = getConfiguration();
		if (configuration == null) {
			return defaultPath;
		}
		return configuration.getString(Constants.UPLOAD_DIR, defaultPath);
	}

	private Configuration getConfiguration() {
		Configuration configuration = InstanceFactory.getInstance(Configuration.class);
		if (configuration == null) {
			return new ConfigurationFactory().fromClasspath("/conf.properties");
		}
		return configuration;
	}

	// 生成全路径
	private String generatRelativePath(long documentId, String fileName) {
		String folderName = foldName(documentId);
		String actualFileName = "" + documentId;
		String suffix = FilenameUtils.getExtension(fileName);
		String result = new StringBuilder().append(folderName).append(File.separator).append(actualFileName).append(".")
				.append(suffix).toString();
		return result;
	}

	private String foldName(long documentId) {
		return "" + (documentId / Constants.FILE_MAX_IN_A_FOLD);
	}

	public Document addTags(Collection<DocumentTag> tags) {
		getTags().addAll(tags);
		return this;
	}

	public Document addTag(DocumentTag tag) {
		if (getTags().contains(tag)) {
			resetTag(tag);
		}
		getTags().add(tag);
		return this;
	}

	public Document resetTag(DocumentTag tag) {
		getTags().remove(tag);
		getTags().add(tag);
		return this;
	}

	public Document removeTag(DocumentTag tag) {
		Set<DocumentTag> newTags = getTags();
		newTags.remove(tag);
		setTags(newTags);
		return this;

	}

	public Document addTag(String key, Long value) {
		DocumentTag tag = new DocumentTag(key, Long.toString(value));
		this.getTags().add(tag);
		return this;
	}

	public File getFile() {
		if (isNew()) {
			return null;
		}
		return new File(wholePath());
	}

	public static Document get(long documentId) {
		return Document.get(Document.class, documentId);
	}

	public boolean equals(final Object other) {
		if (!(other instanceof Document))
			return false;
		Document that = (Document) other;
		return new EqualsBuilder().append(name, that.name).append(relativePath, that.getRelativePath()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).append(relativePath).toHashCode();
	}

	public String toString() {
		return name + " at " + relativePath;
	}

	public Set<DocumentTag> getTags() {
		return tags;
	}

	public void setTags(Set<DocumentTag> tags) {
		this.tags = tags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String type) {
		this.contentType = type;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * 人性化显示文件的大小
	 * 
	 * @return
	 */
	public String getSizeShow() {
		long KB = 1024;
		long MB = 1024 * KB;

		if (size < MB) {
			return new BigDecimal(size).divide(new BigDecimal(KB), 2, RoundingMode.CEILING).toString() + "KB";
		}
		return new BigDecimal(size).divide(new BigDecimal(MB), 2, RoundingMode.CEILING).toString() + "MB";

	}

}
