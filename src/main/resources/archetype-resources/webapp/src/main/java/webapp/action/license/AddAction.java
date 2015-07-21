#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.license;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ${package}.domain.Document;
import ${package}.domain.InternalOrganization;
import ${package}.domain.License;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.dto.LicenseDto;

/**
 * User: tune
 * Date: 13-6-4
 * Time: 上午11:23
 */
public class AddAction extends UploadBaseAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3916002171937557767L;

	private List<Document> results = new ArrayList<Document>();

    private LicenseDto license;

    @Override
    public String execute() throws Exception {
        Long id = license.getId();

        License ls = null;

        if(id != null && id > 0){
            ls = License.get(License.class,id);
        }

        if(ls == null){
            ls = new License();
        }

        ls.setAuthority(license.getAuthority());
        ls.setAwardDate(license.getAwardDate());
        ls.setCredNumber(license.getCredNumber());
        ls.setCredType(license.getCredType());
        InternalOrganization internalOrganization = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), Long.valueOf(license.getInternalOrganizationDtoId()));
        ls.setOrganization(internalOrganization);
        ls.setPeriodDate(license.getPeriodDate());

        try {
            projApplication.saveEntity(ls);
            license.setId(ls.getId());
        } catch (Exception e) {
            errorInfo = "添加失败";
        }
        return JSON;
    }

    /**
     * 上传文件
     * @throws IOException
     */
    public void upload() throws IOException{
        Long id = license.getId();

        License ls = License.get(License.class,id);

        if(ls != null){
            results = saveDocuments(ls);
        }
        System.out.println(results);
    }

    // 保存附件
    private List<Document> saveDocuments(License license) throws IOException {
        return saveDocumentsNowWith(createDefaultDocTagGenerater().license(license.getId()).generate());
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<Document> getResults() {
        return results;
    }

    public LicenseDto getLicense() {
        return license;
    }

    public void setLicense(LicenseDto license) {
        this.license = license;
    }

    public String getErrorInfo(){
        return errorInfo;
    }
}
