#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import static ${package}.utils.DocumentTagConstans.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.domain.InternalOrganization;

public class DocumentQuery extends BaseQuery<Document> {

	public DocumentQuery() {
		super(QuerySettings.create(Document.class));
	}

	public static DocumentQuery create() {
		return new DocumentQuery();
	}

	public DocumentQuery nameContains(String name) {
		querySettings.containsText("name", name);
		return this;
	}

	/**
	 * 没有查询条件返回个人全部的文档,否则根据查询条件返回对应的文档
	 * 
	 * @param PersonId
	 * @param searchName
	 * @return
	 */
	public static List<Document> findPersnalDocuments(long PersonId, String searchName) {
		List<Document> documents = Document.findByOneTag(UPLOADED_BY, longToString(PersonId));
		List<Document> results = new ArrayList<Document>();
		if (StringUtils.isEmpty(searchName)) {
			return documents;
		}
		for (Document document : documents) {
			if (!document.getName().contains(searchName)) {
				continue;
			}
			results.add(document);
		}
		return results;
	}

	public static Document getAuthenticateSuccessOf(InternalOrganization scope, long documentId) {
		Document result = Document.get(documentId);
		if (null == result) {
			return null;
		}
		Set<DocumentTag> tags = result.getTags();
		for (DocumentTag each : tags) {
			if (!HOLDER_ORGANIZATION.equals(each.getTagKey())) {
				continue;
			}
			long internalId = Long.valueOf(each.getTagValue());
			InternalOrganization internal = InternalOrganizationQuery.create().subordinateOf(scope).id(internalId).enabled()
					.getSingleResult();
			if (null == internal) {
				return null;
			}
			break;
		}
		return result;
	}

	public static List<Document> frameworkContractOf(Long id) {
		return findByOneTag(FRAMEWORK_CONTRACT, id);
	}

	public static List<Document> getSingleContractOf(Long id) {
		return findByOneTag(SINGLE_CONTRACT, id);
	}

	public static List<Document> findByTags(Set<DocumentTag> tags) {
		List<Document> results = new ArrayList<Document>();
		results.addAll(Document.findByTags(tags));
		return results;
	}

	public static List<Document> receiptInvoice(long id) {
		return findByOneTag(RECEIPT_INVOICE, id);
	}

	public static List<Document> licenseOf(long id) {
		return findByOneTag(LICENSE, id);
	}

	public static List<Document> projectOf(long id) {
		return findByOneTag(PROJECT, id);
	}

	public static List<Document> singleContractOf(long id) {
		return findByOneTag(SINGLE_CONTRACT, id);
	}

	public static List<Document> subProjectOf(long id) {
		return findByOneTag(SUBPROJECT, id);
	}

	public static List<Document> biddingRequest(long id) {
		return findByOneTag(BIDDING_REQUEST, id);
	}

	public static List<Document> biddingApproveOf(long approveInfoId) {
		return findByOneTag(BIDDING_APPROVE, approveInfoId);
	}

	public static List<Document> biddingApplyOf(long id) {
		return findByOneTag(BIDDING_APPLY, id);
	}

	public static List<Document> subcontractExpenditureOf(long id) {
		return findByOneTag(SUBCONTRACT_EXPENDITURE, id);
	}

	public static List<Document> subContractOf(Long id) {
		return findByOneTag(SUB_CONTRACT, id);
	}

	private static String longToString(long longVal) {
		return Long.toString(longVal);
	}

	private static List<Document> findByOneTag(String tag, long idValue) {
		return Document.findByOneTag(tag, Long.toString(idValue));
	}

	public static List<Document> personalOf(Long id) {
		return findByOneTag(UPLOADED_BY, id);
	}

}
