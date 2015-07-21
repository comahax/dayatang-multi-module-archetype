#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.utils;

import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.domain.FrameworkContract;
import ${package}.domain.Person;
import ${package}.domain.Project;
import ${package}.domain.SingleContract;
import ${package}.domain.SubProject;
import ${package}.query.BaseQuery;

import static ${package}.utils.DocumentTagConstans.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件标签的生成器
 * 
 * @author zjzhai
 */
public class DocumentTagGenerater {

	private Set<DocumentTag> tags = new HashSet<DocumentTag>();

	public Set<DocumentTag> generate() {
		return tags;
	}

	public DocumentTagGenerater biddingRequest(long biddingRequestId) {
		DocumentTag tag = new DocumentTag(BIDDING_REQUEST, Long.toString(biddingRequestId));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater license(long license) {
		DocumentTag tag = new DocumentTag(LICENSE, Long.toString(license));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater biddingApprove(long approveInfoId) {
		DocumentTag tag = new DocumentTag(BIDDING_APPROVE, Long.toString(approveInfoId));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater biddingApply(long applyInfoId) {
		DocumentTag tag = new DocumentTag(BIDDING_APPLY, Long.toString(applyInfoId));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater project(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(PROJECT, Long.toString(id));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater subProject(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		SubProject subProject = SubProject.get(id);
		if (null == subProject) {
			return this;
		}
		DocumentTag tag = new DocumentTag(SUBPROJECT, Long.toString(id));

		Project project = subProject.getProject();
		if (null != project) {
			project(project.getId());
		}
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater frameworkContract(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(FRAMEWORK_CONTRACT, Long.toString(id));
		tags.add(tag);
		return this;

	}

	public DocumentTagGenerater uploadedBy(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(UPLOADED_BY, Long.toString(id));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater holderOrganization(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(HOLDER_ORGANIZATION, Long.toString(id));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater receiptInvoice(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(RECEIPT_INVOICE, Long.toString(id));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater invoice(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(INVOICE, Long.toString(id));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater contract(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(CONTRACT, Long.toString(id));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater singleContract(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(SINGLE_CONTRACT, Long.toString(id));
		tags.add(tag);
		contract(id);
		return this;
	}

	public DocumentTagGenerater subContract(Long id) {
		if (null == id || id <= 0l) {
			return this;
		}
		DocumentTag tag = new DocumentTag(SUB_CONTRACT, Long.toString(id));
		tags.add(tag);
		contract(id);

		return this;
	}

	public DocumentTagGenerater disable() {
		DocumentTag tag = new DocumentTag(DISABLED, Boolean.toString(true));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater able() {
		DocumentTag tag = new DocumentTag(DISABLED, Boolean.toString(false));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater subContractExpenditure(long subcontractExpenditureId) {
		DocumentTag tag = new DocumentTag(SUBCONTRACT_EXPENDITURE, Long.toString(subcontractExpenditureId));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater subContractInvoice(long invoiceId) {
		DocumentTag tag = new DocumentTag(SUBCONTRACT_INVOICE, Long.toString(invoiceId));
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater append(DocumentTag tag) {
		tags.add(tag);
		return this;
	}

	public DocumentTagGenerater append(Set<DocumentTag> tags) {
		this.tags.addAll(tags);
		return this;
	}

	public static DocumentTag createUploadByTag(Person person) {
		if (null == person) {
			return null;
		}
		return new DocumentTagGenerater().uploadedBy(person.getId()).getSingleResult();
	}

	public static DocumentTag createFrameworkContract(FrameworkContract contract) {
		if (null == contract) {
			return null;
		}
		return new DocumentTagGenerater().frameworkContract(contract.getId()).getSingleResult();
	}

	public static DocumentTag createSingleContract(SingleContract singleContract) {
		if (null == singleContract) {
			return null;
		}
		return new DocumentTagGenerater().singleContract(singleContract.getId()).getSingleResult();
	}

	public static DocumentTag createProjectTag(Project project) {
		if (null == project) {
			return null;
		}
		return new DocumentTagGenerater().project(project.getId()).getSingleResult();
	}

	public static DocumentTag createSubProjectTag(SubProject subProject) {
		if (null == subProject) {
			return null;
		}
		return new DocumentTagGenerater().subProject(subProject.getId()).getSingleResult();

	}

	public DocumentTag getSingleResult() {
		if (null == tags) {
			return null;
		}
		return new ArrayList<DocumentTag>(tags).get(0);
	}

	public Set<DocumentTag> getTags() {
		return tags;
	}

	public void setTags(Set<DocumentTag> tags) {
		this.tags = tags;
	}

}
