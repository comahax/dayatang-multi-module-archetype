#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;

public class DocumentTest {
	@Mock
	private EntityRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		AbstractEntity.setRepository(repository);
	}

	@After
	public void tearDown() throws Exception {
		AbstractEntity.setRepository(null);
	}

	@Test
	public void testAddTagAndResetTag() {
		DocumentTag tag = new DocumentTag("a", 1);
		DocumentTag tag2 = new DocumentTag("b", 1);
		DocumentTag tag3 = new DocumentTag("b", 2);
		DocumentTag tag4 = new DocumentTag("c", 1);

		Document document = new Document();
		document.setName("name");
		document.save();
		verify(repository).save(document);

		document.addTag(tag);
		document.addTag(tag2);

		assertEquals(2, document.getTags().size());
		for (DocumentTag each : document.getTags()) {
			if ("b".equals(each.getTagKey())) {
				assertEquals("1", each.getTagValue());
			}
		}

		document.addTag(tag3);
		assertEquals(2, document.getTags().size());
		for (DocumentTag each : document.getTags()) {
			if ("b".equals(each.getTagKey())) {
				assertEquals("2", each.getTagValue());
			}
		}

		document.resetTag(tag4);
		assertEquals(3, document.getTags().size());
		assertTrue(document.getTags().contains(tag4));

	}

	@Test
	public final void testFindByTags() {
		DocumentTag tag = new DocumentTag("a", 1);
		DocumentTag tag1 = new DocumentTag("b", 2);
		DocumentTag tag2 = new DocumentTag("c", 3);
		Set<DocumentTag> tags = new HashSet<DocumentTag>();
		tags.add(tag);
		tags.add(tag1);
		tags.add(tag2);

		Document document = new Document();
		document.setName("docName");
		document.setId(1L);
		document.addTags(tags);
		Document document1 = new Document();
		document1.setName("docName1");
		document1.setId(2L);
		document1.addTags(tags);

		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		documents.add(document1);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagKey", tag.getTagKey());
		params.put("tagValue", tag.getTagValue());
		String sql = "SELECT o FROM Document o, IN(o.tags) t WHERE t.tagKey = :tagKey AND t.tagValue = :tagValue";
		when(repository.find(sql, params, Document.class)).thenReturn(documents);

		Map<String, Object> params1 = new HashMap<String, Object>();
		params.put("tagKey", tag1.getTagKey());
		params.put("tagValue", tag1.getTagValue());
		String sql1 = "SELECT o FROM Document o, IN(o.tags) t WHERE t.tagKey = :tagKey AND t.tagValue = :tagValue";
		when(repository.find(sql1, params1, Document.class)).thenReturn(documents);

		Map<String, Object> params2 = new HashMap<String, Object>();
		params.put("tagKey", tag2.getTagKey());
		params.put("tagValue", tag2.getTagValue());
		String sql2 = "SELECT o FROM Document o, IN(o.tags) t WHERE t.tagKey = :tagKey AND t.tagValue = :tagValue";
		when(repository.find(sql2, params2, Document.class)).thenReturn(documents);

		List<Document> results = Document.findByTags(tags);
		assertEquals(2, results.size());
	}

	@Test
	public final void testFindByOneTag() {
		Document document = new Document();
		document.setName("docName");
		document.setId(1L);

		List<Document> documents = new ArrayList<Document>();
		documents.add(document);

		DocumentTag tag = new DocumentTag("a", 1);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagKey", tag.getTagKey());
		params.put("tagValue", tag.getTagValue());
		String sql = "SELECT o FROM Document o, IN(o.tags) t WHERE t.tagKey = :tagKey AND t.tagValue = :tagValue";
		when(repository.find(sql, params, Document.class)).thenReturn(documents);

		List<Document> results = Document.findByOneTag(tag);
		assertEquals(1, results.size());
		assertEquals(document, results.get(0));

		List<Document> results1 = Document.findByOneTag(tag.getTagKey(), tag.getTagValue());
		assertEquals(1, results1.size());
		assertEquals(document, results1.get(0));
		assertEquals(document.getId(), results1.get(0).getId());

	}

	@Test
	public final void testContainAllTags() {
		DocumentTag tag = new DocumentTag("a", 1);
		DocumentTag tag1 = new DocumentTag("b", 2);
		DocumentTag tag2 = new DocumentTag("c", 3);
		Set<DocumentTag> tags = new HashSet<DocumentTag>();
		tags.add(tag);
		tags.add(tag1);
		tags.add(tag2);

		Document document = new Document();
		document.setName("docName");
		document.setId(1L);
		document.addTags(tags);

		assertTrue(document.containsAllTags(tags));
	}

	@Test
	public final void testContainOneOfTags() {
		DocumentTag tag = new DocumentTag("a", 1);
		DocumentTag tag1 = new DocumentTag("b", 2);
		DocumentTag tag2 = new DocumentTag("c", 3);
		List<DocumentTag> tags = new ArrayList<DocumentTag>();
		tags.add(tag);
		tags.add(tag1);
		tags.add(tag2);

		Document document = new Document();
		document.setName("docName");
		document.setId(1L);
		document.addTag(tag);

		assertTrue(document.containOneOfTags(tags));

		document.removeAllTags();
		assertFalse(document.containOneOfTags(tags));
	}

	@Test
	public final void removeAllTags() {

		DocumentTag tag = new DocumentTag("a", 1);
		DocumentTag tag1 = new DocumentTag("b", 2);
		DocumentTag tag2 = new DocumentTag("c", 3);
		List<DocumentTag> tags = new ArrayList<DocumentTag>();
		tags.add(tag);
		tags.add(tag1);
		tags.add(tag2);

		Document document = new Document();
		document.setName("docName");
		document.setId(1L);
		document.addTags(tags);

		assertTrue(document.getTags().containsAll(tags));
		assertEquals(3, document.getTags().size());

		document.removeAllTags();
		assertEquals(0, document.getTags().size());
		verify(repository).save(document);

	}
	
	@Test
	public void testDocSizeShow(){
		Document doc = new Document();
		long KB = 1024;
		long MB = 1024 * KB;
		doc.setSize(500 * KB);
		assertEquals("500.00KB", doc.getSizeShow());
		
		doc.setSize(2 * MB);
		
		assertEquals("2.00MB", doc.getSizeShow());
		
	}

}
