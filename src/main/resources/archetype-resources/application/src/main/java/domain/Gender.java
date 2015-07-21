#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

public enum Gender {
	MALE{
        public String getCnText(){
            return "男";
        }
    }, //男
	FEMALE{
        public String getCnText(){
            return "女";
        }
    };

    public abstract String getCnText();
}
