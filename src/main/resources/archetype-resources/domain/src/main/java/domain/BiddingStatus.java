#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

public enum BiddingStatus {
	BIDDING_UNKNOW, // 未知
	BIDDING_WIN, // 中标
	BIDDING_FAIL // 不中标
}
