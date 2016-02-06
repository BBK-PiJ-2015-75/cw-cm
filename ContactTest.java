package ContactManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// tests ContactImpl
public class ContactTest {

	Contact testContact;
	
	
	// wont compile until I implement the contact imple.
	@Before
	public void setUp() throws Exception {
		testContact = new ContactImpl(77, "Peter", "Waiting for dates");
	}

	
	@Test
	public void testAddNotesAndGetNotes() {
		testContact.addNotes("This should be the result"); 
		assertEquals(testContact.getNotes(), "This should be the result");
	}

	
	@Test
	public void testGetId() {
		assertEquals(testContact.getId(), 77);
	}
	
	@Test
	public void testGetName() {
		assertEquals(testContact.getName(), "Peter");
		assertNotEquals(testContact.getName(), "Anna");
	}
	

	
	
	
	
	
	
	
	
}
