package ContactManager;

import static org.junit.Assert.*;

import java.io.File;
import java.util.*;


import org.junit.Before;
import org.junit.Test;


//tests ContactManagerImpl methods
public class ContactManagerTest {

	ContactManagerImpl cm;
	Set<Contact> testMeetingContacts = new HashSet<Contact>();
	Contact Paul = new ContactImpl(1, "Paul");
	
	
	Calendar testCalendar = Calendar.getInstance();
	

	
	
	@Before
	public void setUp() throws Exception {
		cm = new ContactManagerImpl();
		testCalendar.set(2017, 6, 11);
		testMeetingContacts.add(Paul);
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
	public void testAddFutureMeetingUnknownContact(){
		Contact Fiona = new ContactImpl(2, "Fiona");
		Set<Contact> fakeContacts = new HashSet<Contact>();
		fakeContacts.add(Fiona);
		cm.addFutureMeeting(fakeContacts, testCalendar);
	}
	
	
	@Test
	public void testExistsInList() {
		assertTrue(cm.contactsList.get(0), 0);
		
	}
	
	
	
	
	@Test
	public void testGetPastMeetinIDg() {
		assertEquals(cm.meetingsList.get(1), 1);
	}
	
	// IAE if list of contacts is empty
	@Test (expected = IllegalArgumentException.class)
	public void TestAddNewPastMeetingEmptyContacts() {
		Set<Contact> emptyContacts = new HashSet<Contact>();
		testCalendar.set(2014, 3, 4);
		String pastMeetingMessage = "Past meeting notes";
		cm.addNewPastMeeting(emptyContacts, testCalendar, pastMeetingMessage);	
	}
	
	// unfinished
	@Test (expected = IllegalStateException.class)
	public void testPastMeetingAddMeetingNotesMeetingDoesNotExist(){
		
		
	}
	
	// unfinished
		@Test (expected = NullPointerException.class)
		public void testPastMeetingAddMeetingNotesMeetingDoesNotExist(){
			
			
		}
	
		
	// unfinished
		@Test (expected = NullPointerException.class)
		public void testPastMeetingAddMeetingNotesMeetingDoesNotExist(){
					
		}
		
		

	
	@Test
	public void testGetFutureMeetingID() {
		assertEquals(cm.meetingsList.get(1), 1);
	}
	
	
	@Test
	public void testGetMeeting() {
		assertEquals(cm.meetingsList.get(1), 1);
	}
	
	
	// test to get meeting date for a contact 
	@Test 
	public void testGetFutureMeetingListFromContactID() {
		
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		List<Meeting> meetings = cm.getFutureMeetingList(Paul);
		assertEquals( meetings.get(0).getDate(), testCalendar );
	}
		
	
	// NPE
	@Test(expected = NullPointerException.class)
	public void testGetFutureMeetingListNPE() {
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		List<Meeting> meetings = cm.getFutureMeetingList(Paul);
	}
	
	
	// a list of meetings on a specified date
	@Test
	public void testGetMeetingListOnDate() {
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		List<Meeting> meetings = cm.getMeetingListOn(testCalendar);
		assertEquals(meetings.get(0).getDate(), testCalendar);
	}
	
	
	// test NPE if the date is null
	@Test (expected = NullPointerException.class)
	public void testGetMeetingListOnNullDate() {
		cm.getMeetingListOn(null);
	}
		
	
	// a list of past meetings by contact
	@Test
	public void testGetPastMeetingListForContact() {
		Calendar pastCalendar = Calendar.getInstance();
		pastCalendar.set(2014, 3, 29);
		
		cm.addNewPastMeeting(testMeetingContacts, pastCalendar, "Ok");
		assertEquals(cm.getPastMeetingListFor(Paul).get(0).getDate(), pastCalendar);
	}
	
	
	
	@Test (expected = FileNotFoundException.class) 
		public void testFlushFileNotFoundException() {
		
	}
	
	
	
	@Test
	public void testFlushZeroContents(){
		ContactManagerImpl cm2 = new ContactManagerImpl();
		cm2.flush();
		File newFile = new File("projectFile.txt");
		assertNotNull(newFile);
		
	}
	
	
	@Test
	public void testFlushWithContents(){
		cm.flush();
		File newFile = new File("projectFile.txt");
		assertNotNull(newFile);
		assertTrue(newFile.length()> 0);

		
	}
	
	
}
	
