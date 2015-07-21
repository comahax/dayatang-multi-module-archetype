#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import ${package}.domain.Document;
import ${package}.domain.FrameworkContract;
import ${package}.query.FrameworkContractQuery;
import ${package}.webapp.action.UploadBaseAction;

import java.io.IOException;
import java.util.List;

/**
 * User: tune
 * Date: 13-6-20
 * Time: 下午5:15
 */
public class UploadDocsAction extends UploadBaseAction {

	private static final long serialVersionUID = -9069062249809008433L;
	private long frameworkId = 0l;

    /**
     * 上传文件
     *
     * @throws java.io.IOException
     */
    @Override
    public String execute() throws Exception {
        if ( frameworkId <= 0l) {
            return JSON;
        }

        FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();

        if (null == frameworkContract) {
            return JSON;
        }

        saveDocuments(frameworkContract);
        return JSON;
    }

    // 保存附件
    private List<Document> saveDocuments(FrameworkContract frameworkContract) throws IOException {
        return saveDocumentsNowWith(createDefaultDocTagGenerater().frameworkContract(frameworkContract.getId()).generate());
    }


    public void setFrameworkId(long frameworkId) {
        this.frameworkId = frameworkId;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}