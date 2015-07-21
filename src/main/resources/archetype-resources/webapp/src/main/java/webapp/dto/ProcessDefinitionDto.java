#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

public class ProcessDefinitionDto {

	/**
	 * 流程定义ID
	 */
	private String id;

	private String category;

	private String name;

	private String key;

	private String description;

	private int version;

	/**
	 * 发布ID
	 */
	private String deploymentId;

	/**
	 * 流程定义资源的名称
	 */
	private String resouceName;

	/**
	 * 图片资源的名称
	 */
	private String diagramResourceName;

	public ProcessDefinitionDto(String id, String deploymentId, String category, String name, String key, String description, int version, String resouceName, String diagramResourceName) {
		super();
		this.id = id;
		this.deploymentId = deploymentId;
		this.category = category;
		this.name = name;
		this.key = key;
		this.description = description;
		this.version = version;
		this.resouceName = resouceName;
		this.diagramResourceName = diagramResourceName;
	}

	public String getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public int getVersion() {
		return version;
	}

	public String getResouceName() {
		return resouceName;
	}

	public String getDiagramResourceName() {
		return diagramResourceName;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

}
