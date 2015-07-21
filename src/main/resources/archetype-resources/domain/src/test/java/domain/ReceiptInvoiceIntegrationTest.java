#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;
import ${package}.domain.ReceiptInvoice;
import ${package}.pager.Page;
import ${package}.pager.PageList;
import ${package}.query.ReceiptInvoiceQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import ${package}.AbstractIntegrationTest;
import ${package}.query.SingleContractQuery;
@Ignore
public class ReceiptInvoiceIntegrationTest extends AbstractIntegrationTest {

	
	@Test
	public void test() {
		ReceiptInvoice invoice = ReceiptInvoice.get(1l);
		assertNotNull(invoice);
		SingleContract contract = SingleContract.get(SingleContract.class,1l);

		Receipt receipt = new Receipt();
		receipt.setAmount(new BigDecimal(3000));
		receipt.setReceivedDate(new Date());

		receipt.setInvoice(invoice);
		receipt.setContract(contract);
		receipt.save();
	}

	@Test
	public void test1() {
		PageList<ReceiptInvoice> pageList = ReceiptInvoice.findBy(null, null, false, InternalOrganization.get(74l), 1, 20);
		System.out.println("{}{}{}{}{}");

	}

	@Test
	public void test2() {
		SingleContract contract = SingleContract.get(SingleContract.class, 39l);
		assertNull(contract.getFrameworkContract());
		FrameworkContract framework = FrameworkContract.get(58l);
		assertNotNull(framework);
		contract.setFrameworkContract(framework);
		contract.save();
		assertNotNull(contract.getFrameworkContract());
	}

}
