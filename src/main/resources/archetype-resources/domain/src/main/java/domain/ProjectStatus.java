#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

public enum ProjectStatus {
	DRAFT{
        @Override
        public String getCnText() {
            return "草稿";//完成可以编辑
        }
    },
	APPROVING{
        @Override
        public String getCnText() {
            return "立项审批中"; //不可以编辑
        }
    },
	APPROVED{
        @Override
        public String getCnText() {
            return "立项审批通过";//不可以编辑 
        }
    },
    REJECTED{
        @Override
        public String getCnText() {
            return "立项审批否决";//完全可以编辑
        }
    },

	TERMINATED{
        @Override
        public String getCnText() {
            return "中止";//不可以编辑
        }
    },
	CONSTRUCTING{
        @Override
        public String getCnText() {
            return "建设中";//不可以编辑
        }
    },
	FINISHED{
        @Override
        public String getCnText() {
            return "竣工";
        }
    },		//（建设已完成，其余工作未完成）
	CLOSED{
        @Override
        public String getCnText() {
            return "关闭";
        }
    };			//（一切结束）

    public abstract String getCnText();


}
