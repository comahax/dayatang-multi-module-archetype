#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 专业工程
 * 
 * @author zjzhai
 * 
 */
@Entity
@DiscriminatorValue("SPECIALTY_PROJECT")
public class SpecialtyProject extends ProjectElement {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "specialtyProject_subproject_id")
	private SubProject subProject;

	@ManyToOne
	@JoinColumn(name = "specialtyProject_specialty_id")
	private Specialty specialty;

	/**
	 * 合作单位
	 */
	@ManyToOne
	@JoinColumn(name = "specialtyProject_team_id")
	private CooperationOrganization team;

	@ManyToOne
	@JoinColumn(name = "specialtyProject_teamLeader_id")
	private Person teamLeader;

	public SpecialtyProject() {
	}

	public SpecialtyProject(Specialty specialty) {
		setName(specialty.getName());
		this.specialty = specialty;
	}

	public SpecialtyProject(Specialty specialty, SubProject subProject) {
		this(specialty);
		this.subProject = subProject;

	}

	public void setSubProject(SubProject subProject) {
		this.subProject = subProject;
	}

	/**
	 * 分配施工队
	 * 
	 * @param team
	 * @param teamLeader
	 * @return
	 */
	public SpecialtyProject assignCooperation(CooperationOrganization team, Person teamLeader) {
		setTeam(team);
		setTeamLeader(teamLeader);
		save();
		return this;
	}



	/**
	 * 得到某个单点的所有专业
	 * 
	 * @param subProject
	 * @return
	 */
	public static List<Specialty> specialtiesOfSubProject(SubProject subProject) {
		String sql = "SELECT DISTINCT o.specialty FROM SpecialtyProject o WHERE o.subProject = :subProject";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subProject", subProject);
		return getRepository().find(sql, params, Specialty.class);
	}

	/**
	 * 某个单点是否有某个专业
	 * 
	 * @param subProject
	 * @return
	 */
	public static boolean hasSpecialtyOfSubProject(SubProject subProject, Specialty specialty) {
		String sql = "SELECT o FROM SpecialtyProject o WHERE o.subProject = :subProject AND o.specialty = :specialty ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subProject", subProject);
		params.put("specialty", specialty);
		List<SpecialtyProject> results = getRepository().find(sql, params, SpecialtyProject.class);
		return results != null && !results.isEmpty();
	}

	/**
	 * 根据专业生成专业工程
	 * 
	 * @param specialties
	 * @return
	 */
	public static Set<SpecialtyProject> createSpecialtyProjectsBySpecialty(Set<Specialty> specialties) {
		Set<SpecialtyProject> results = new HashSet<SpecialtyProject>();
		if (null == specialties) {
			return results;
		}
		for (Specialty each : specialties) {
			if (null == each) {
				continue;
			}
			results.add(new SpecialtyProject(each));
		}
		return results;
	}

	public boolean belongToASubProject() {
		return subProject != null;
	}

	public static Set<SpecialtyProject> createSpecialtyProjectsBySpecialty(Long[] specialties) {
		Set<SpecialtyProject> results = new HashSet<SpecialtyProject>();
		for (Specialty each : Specialty.getSpecialtiesFromId(specialties)) {
			if (null == each) {
				continue;
			}
			results.add(new SpecialtyProject(each));
		}
		return results;
	}

	/**
	 * 产值转移:专业-->单点
	 * 
	 * @param subProject
	 * @return
	 */
	public SpecialtyProject transferOutputValueTo(SubProject subProject) {
		for (OutputValue each : OutputValue.findBy(this)) {
			each.setSubProject(subProject);
			each.save();
		}
		return this;
	}

	/**
	 * 彻底删除专业工程，包括已经报的产值
	 */
	public void remove() {
		removeAllOutputValue();
		super.remove();
	}

	/**
	 * 彻底删除专业工程
	 */
	private void removeAllOutputValue() {
		for (OutputValue each : OutputValue.findBy(this)) {
			each.remove();
		}
	}

	public static SpecialtyProject get(long id) {
		return SpecialtyProject.get(SpecialtyProject.class, id);
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof SpecialtyProject))
			return false;
		SpecialtyProject that = (SpecialtyProject) other;
		return new EqualsBuilder().append(getSubProject(), that.getSubProject()).append(getSpecialty(), that.getSpecialty())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTeam()).append(getSpecialty()).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("team : ", getTeam()).append("specialty : ", getSpecialty()).toString();
	}

	public SubProject getSubProject() {
		return subProject;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public CooperationOrganization getTeam() {
		return team;
	}

	public void setTeam(CooperationOrganization team) {
		this.team = team;
	}

	public Person getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(Person teamLeader) {
		this.teamLeader = teamLeader;
	}


}
