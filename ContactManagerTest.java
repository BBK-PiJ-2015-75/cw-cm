package ContactManager;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.*;


import org.junit.Before;
import org.junit.Test;


//tests ContactManagerImpl methods
public class ContactManagerTest {

	ContactManagerImpl cm;
	ArrayList<Contact> cList;
	ArrayList<Meeting> mList;
	Set<Contact> testMeetingContacts = new HashSet<Contact>();
	Contact Paul = new ContactImpl(1, "Paul");
	
	
	Calendar testCalendar = Calendar.getInstance();
	testCalender.set(2017, 6, 11);

	
	
	@Before
	public void setUp() throws Exception {
		cm = new ContactManagerImpl();
		
	}

	// checks if cmcontactsList is empty
	@Test
	public void testArrayListContacts(){
		assertNotNull(cm.contactsList);
	}

	// tests if cmmeetingslist is empty
	@Test
	public void testArrayListMeetings(){
		assertNotNull(cm.meetingsList);
	}
	
	// test id produced from adding new contact
	@Test
	public void testAddContact(){
		assertEquals(cm.addNewContact("Olivia", "PA"), 1);
	}
	
	// IAE if the string name is empty
	@Test (expected = IllegalArgumentException.class)
	public void testAddContactEmptyName() {
		cm.addNewContact("", "anything");
	}
	
	//IAE if the string for notes is empty
	@Test (expected = IllegalArgumentException.class)
	public void testAddContactEmptyNotes() {
		cm.addNewContact("name", "");
	}
	
	// NPE if name is null
	@Test (expected = NullPointerException.class)
	public void testAddContactNullName() {
		cm.addNewContact( null , "anything");
	}
	
	//NPE if notes are null
	@Test (expected = NullPointerException.class)
	public void testAddContactNullNotes() {
		cm.addNewContact("name", null);
	}
	
	
	// test id produced from adding new future meeting
	@Test
	public void testAddFutureMeeting(){
		assertEquals(cm.addFutureMeeting(testMeetingContacts, testCalendar), 2);
	}
	
	
	// test NPE results if no date is set
	@Test (expected = NullPointerException.class)
	public void testAddFutureMeetingNoDate() {
		cm.addFutureMeeting(testMeetingContacts, null);
	}
	
	
	// test Illegal argument E results if date is set in the past
	@Test (expected = IllegalArgumentException.class)
	public void testFutureMeetingPastDate(){
		testCalendar.set(2014, 3, 1);
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
	}
	
	
	// test Illegal argument E results if contact is unknown
	@Test (expected = IllegalArgumentException.class)
	public void testFutureMeetingUnknownContact(){
		cm.addFutureMeeting(fakeFutureMeeting, testCalendar);
	}
	
	
	@Test
	public void testGetPastMeetinIDg() {
		assertEquals(cm.meetingsList.get(1), 1);
	}
	
	@Test
	public void testGetFutureMeetingID() {
		assertEquals(cm.meetingsList.get(1), 1);
	}
	
	@Test
	public void testGetMeeting() {
		assertEquals(cm.meetingsList.get(1), 1);
	}
	
	
	// tests NPE if 
		@Test (expected = NullPointerException.class)
		public void testGetFutureMeetingListFromContactID() {
		assertEquals((cm.contactsList.get(null) meeting1, meeting2) meeting1, meeting2);
			
			
	// a list of meetings where a particular contact is assigned
	@Test
	public void testGetFutureMeetingListFromContactID() {
		assertEquals((cm.contactsList.get(1) meeting1, meeting2) meeting1, meeting2);
	}
	
	// a list of meetings on a specified date
	@Test
	public void testgetMeetingListOnDate(Calender date) {
		assertEquals(cm.meetingsList.date), meeting 1, meeting 2, meeting 3);
	}
	
	// test NPE if the date is null
	@Test (expected = NullPointerException.class)
	public void testgetMeetingListOnNullDate() {
		cm.MeetingList(null);
	}
		
	// a list of past meetings by contact
	
	@Test
	public void testGetPastMeetingListForContactId() {
	assertEquals(cm.PastMeetingList(id), meeting1, meeting2 etc);
	}
	
	
}
	

	
	
	

	
	











