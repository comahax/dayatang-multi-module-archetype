#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;


import com.dayatang.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 项目基类
 *
 * @author yyang
 */
@Entity
@Table(name = "projects")
@DiscriminatorColumn(name = "category", discriminatorType = DiscriminatorType.STRING)
public abstract class ProjectElement extends AbstractCoreEntity {

    private static final long serialVersionUID = 771684669552813616L;
    /**
     * 工程项目名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 项目编号
     */
    @Column(name = "project_number")
    private String projectNumber;

    /**
     * 开工日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 完工日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "finish_date")
    private Date finishDate;

    /**
     * 计划完工日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "pre_finish_date")
    private Date predictFinishDate;

    /**
     * 所属区域
     */
    @ManyToOne
    @JoinColumn(name = "project_area_id")
    private Area area;

    /**
     * 项目关闭日期,关闭指的是项目结算完成
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "close_date")
    private Date closeDate;

    /**
     * 负责部门
     */
    @ManyToOne
    @JoinColumn(name = "project_responsible_division_id")
    private InternalOrganization responsibleDivision;

    /**
     * 备注
     */
    @Lob
    @Column(name = "project_remark")
    private String remark;

    /**
     * 剩余工期
     *
     * @return
     */
    public Long getRemainingDuration() {
        if (isFinished()) {
            return null;
        }

        Long duration = getDuration();
        if (duration == null || DateUtils.isDateBefore(new Date(), getStartDate()) || getFinishDate() != null) {
            return null;
        }
        Long result = duration - DateUtils.getDayDiff(startDate, new Date());
        return result >= 0 ? result : null;
    }

    /**
     * 计算工程进度
     *
     * @return
     */
    public BigDecimal getDurationProcesser() {

        if (null == getDuration() || BigDecimal.ZERO.equals(getDuration())) {
            return BigDecimal.ZERO;
        }

        if (getRemainingDuration() == null) {
            return new BigDecimal(100);
        }
        BigDecimal remainingDuration = new BigDecimal(getRemainingDuration());
        BigDecimal duration = new BigDecimal(getDuration());

        return duration.subtract(remainingDuration).divide(duration, 2, RoundingMode.CEILING).multiply(new BigDecimal(100));

    }

    /**
     * 计算工期
     *
     * @return
     */
    public Long getDuration() {
        if (null == predictFinishDate || null == startDate || new Date().before(startDate)
                || predictFinishDate.before(startDate)) {
            return null;
        }
        Long result = new Long(DateUtils.getDayDiff(startDate, predictFinishDate));
        return result >= 0 ? result : null;
    }

    /**
     * 竣工
     *
     * @param date
     */
    public void finish(Date date) {
        if (date == null) {
            return;
        }
        this.finishDate = date;
        save();
    }

    /**
     * 关闭
     *
     * @param date
     */
    public void close(Date date) {
        if (date == null) {
            return;
        }
        this.closeDate = date;
        save();
    }

    public boolean isClosed() {
        return getCloseDate() != null;
    }

    public boolean isUnFinished() {
        return !isFinished();
    }

    public boolean isFinished() {
        return getFinishDate() != null;
    }

    /**
     * 撤消工程
     *
     * @param lastUpdator
     * @param lastUpdated
     */
    public void cancel(Person lastUpdator, Date lastUpdated) {
        lastUpdate(lastUpdator, lastUpdated);
        disabled();
        save();
    }

    /**
     * 恢复 撤消了的工程
     *
     * @param lastUpdator
     * @param lastUpdated
     */
    public void resume(Person lastUpdator, Date lastUpdated) {
        lastUpdate(lastUpdator, lastUpdated);
        abled();
        save();
    }

    /**
     * 项目意外中止
     */
    public void terminated() {


    }

    public static ProjectElement get(long id) {
        return ProjectElement.get(ProjectElement.class, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Date getPredictFinishDate() {
        return predictFinishDate;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public InternalOrganization getResponsibleDivision() {
        return responsibleDivision;
    }

    public void setResponsibleDivision(InternalOrganization responsibleDivision) {
        this.responsibleDivision = responsibleDivision;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date shutDownDate) {
        this.closeDate = shutDownDate;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public void setPredictFinishDate(Date predictFinishDate) {
        this.predictFinishDate = predictFinishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}
