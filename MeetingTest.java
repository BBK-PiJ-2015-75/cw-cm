package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class MeetingTest {

	// tests MeetingImpl
	public Meeting generalMeeting;
	
	
	@Before
	public void setUp() throws Exception {
		Set<Contact> contacts = new HashSet<Contact>();
		Contact Ed = new ContactImpl(45, "Ed");
		contacts.add(Ed);
		Calendar testCalender = Calendar.getInstance();
		testCalender.set(2012, 4, 12);
		generalMeeting = new PastMeetingImpl(33, testCalender , contacts, "Successful");
	
	}
	
	@Test
	public void testGetId() {
		assertEquals(generalMeeting.getId(),33);
	}

	@Test
	public void testGetDate() {
		Calendar expectedCalender = Calendar.getInstance();
		expectedCalender.set(2012, 4, 12);
		
		assertEquals(generalMeeting.getDate(), expectedCalender );

	}
	
	// test to get the contacts created in the hashset
	@Test
	public void testGetContact() {
		Contact Diane = new ContactImpl(12, "Diane");
		Contact Paula = new ContactImpl(13, "Paula");
		Contact Ed = new ContactImpl(45, "Ed");
		
		Set<Contact> result = generalMeeting.getContacts();
		
		// assert true(method call)
		boolean res1 = result.contains(Diane);
		assertFalse(result.contains(Diane));
		Iterator <Contact> it = result.iterator();
		assertTrue(it.next().getName().equals("Ed"));	
		assertEquals(result.size(), 1);
	}
	
	
	// return the notes made for a meeting
	@Test
	public void testGetNotes() {
		String res = ((PastMeetingImpl)generalMeeting).getNotes();
		assertEquals(res, "Successful");

	}


}


