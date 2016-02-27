package ContactManager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
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
		cm.contactsList.add(Paul);
	}
	

	// checks if cmcontactsList is empty
	@Test
	public void testArrayListContacts(){
		assertNotNull(cm.contactsList);
	}
	
	
	@Test
	public void testLoadingFile() {
		assertTrue(cm.contactsList.size() > 0);
	}
	
	
	


	
	// tests if cmmeetingslist is empty
	@Test
	public void testArrayListMeetings(){
		assertNotNull(cm.meetingsList);
	}
	

	
	// ADD FUTURE MEETING
	
	// test Illegal argument E results if date is set in the past
	@Test (expected = IllegalArgumentException.class)
	public void testAddFutureMeetingPastDate(){
		testCalendar.set(2014, 5, 6);
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
	
	
	
	// test NPE results if no date is set
	@Test (expected = NullPointerException.class)
	public void testAddFutureMeetingNoDate() {
		cm.addFutureMeeting(testMeetingContacts, null);
	}	
	
	
		// test id produced from adding new future meeting
	@Test
	public void testAddFutureMeeting(){
		assertEquals(cm.addFutureMeeting(testMeetingContacts, testCalendar), 1);
	}
	
	
	
	
	
	
	
	// CONTACT EXISTS IN LIST
		
	@Test
	public void testExistsInList() {
		assertTrue(cm.existsInList(cm.contactsList, Paul));
	}
	

	
	
	
	
	// GET PAST MEETING FOR MEETING ID
	
	@Test
	public void testPastMeetingGetPastMeetingID() {
		Set<Contact> testPastContacts = new HashSet<Contact>();
		ContactManagerImpl cm3 = new ContactManagerImpl();
		testPastContacts.add(Paul);
		testCalendar.set(2014, 3, 4);
		String pastMeetingMessage = "Past meeting notes";
		cm3.addNewPastMeeting(testPastContacts, testCalendar, pastMeetingMessage);
		
		assertEquals(cm3.getPastMeeting(1).getId(), 1);
	}
		
	
	
	
	
	
	// GET FUTURE MEETING FOR MEETING ID
	
	@Test
	public void testFutureMeetingGetFutureMeetingID() {
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		assertEquals(cm.getFutureMeeting(1).getId(), 1);
	}
		
	
	
	
	
	
	// GET MEETING FOR MEETING ID
	
	@Test
	public void testMeetingGetMeeting() {
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		assertEquals(cm.getMeeting(1).getId(), 1);
	}
		
	
	
	
	
	
	
	// GET FUTURE MEETING LIST FOR CONTACT
	
	// NPE
	@Test(expected = NullPointerException.class)
	public void testListGetFutureMeetingListContactNull() {
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		List<Meeting> meetings = cm.getFutureMeetingList(null);
	}	
		
	
		// test to get meeting date for a contact 
	@Test 
	public void testListGetFutureMeetingListFromContactID() {	
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		List<Meeting> meetings = cm.getFutureMeetingList(Paul);
		assertEquals( meetings.get(0).getDate(), testCalendar );
	}
		
	
	
	
	
	
	
	// GET MEETING LIST ON DATE
	
		// test NPE if the date is null
	@Test (expected = NullPointerException.class)
	public void testListGetMeetingListOnNullDate() {
		cm.getMeetingListOn(null);
	}
	
	
	
	// a list of meetings on a specified date
	@Test
	public void testListGetMeetingListOnDate() {
		cm.addFutureMeeting(testMeetingContacts, testCalendar);
		List<Meeting> meetings = cm.getMeetingListOn(testCalendar);
		assertEquals(meetings.get(0).getDate(), testCalendar);
	}
	
	

	
	
	

	// GET PAST MEETING FOR CONTACT
		
	// a list of past meetings by contact
	@Test
	public void testPastMeetingGetPastMeetingListForContact() {
		Calendar pastCalendar = Calendar.getInstance();
		pastCalendar.set(2014, 3, 29);
		
		cm.addNewPastMeeting(testMeetingContacts, pastCalendar, "Ok");
		assertEquals(cm.getPastMeetingListFor(Paul).get(0).getDate(), pastCalendar);
	}
	

	
	
	
	
	
	
	
	
	// ADD NEW PAST MEETING
	
	// IAE if list of contacts is empty
	@Test (expected = IllegalArgumentException.class)
	public void TestAddNewPastMeetingEmptyContacts() {
		Set<Contact> emptyContacts = new HashSet<Contact>();
		testCalendar.set(2014, 3, 4);
		String pastMeetingMessage = "Past meeting notes";
		cm.addNewPastMeeting(emptyContacts, testCalendar, pastMeetingMessage);	
	}
	
	
	
	
	
	
	
	// ADD PAST MEETING MEETING NOTES
	

	@Test (expected = IllegalArgumentException.class)
	public void testPastMeetingAddMeetingNotesMeetingDoesNotExist(){
		String notes = "Notes are here";
		cm.addMeetingNotes(22, notes);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testPastMeetingAddMeetingNotesNotesAreNull(){
			
		Calendar date = cm.setDate(2012, 8, 1);
		String notes = null;
		int meetingID = (cm.meetingsList.size() + 1);
		PastMeetingImpl pastMeeting = new PastMeetingImpl(meetingID, date, testMeetingContacts, notes);
		cm.meetingsList.add(pastMeeting);
			
		String addNotes = null;
		cm.addMeetingNotes(meetingID, addNotes);
		}
	
		
		
		
	@Test (expected = IllegalStateException.class)
	public void testPastMeetingAddMeetingNotesMeetingDateInFuture(){
				
		Calendar date = cm.setDate(2017, 8, 1);
		String notes = "Notes";
		int meetingID = (cm.meetingsList.size() + 1);
		PastMeetingImpl pastMeeting = new PastMeetingImpl(meetingID, date, testMeetingContacts, notes);
		cm.meetingsList.add(pastMeeting);
		
		String addNotes = "More notes";
		cm.addMeetingNotes(meetingID, addNotes);
		
	}
		
	
	
	@Test
	public void testPastMeetingAddMeetingNotes(){
		Calendar date = cm.setDate(2014, 8, 1);
		String notes = "Notes";
		int meetingID = (cm.meetingsList.size() + 1);
		PastMeetingImpl pastMeeting = new PastMeetingImpl(meetingID, date, testMeetingContacts, notes);
		cm.meetingsList.add(pastMeeting);
		
		String addNotes = "More notes";
		assertEquals(cm.addMeetingNotes(meetingID, addNotes).getNotes(),addNotes );
	
	}
	
	



// ADD NEW CONTACT
	
	
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
	
	
		// test id produced from adding new contact
	@Test
	public void testAddContact(){
		assertEquals(cm.addNewContact("Olivia", "PA"), 2);
	}
	
	
	
	
	// SET GET CONTACT IDS
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetContactGetContactNoIDS() {
		cm.getContacts();
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetContactGetContactIDDoesntExist() {
		cm.getContacts(500);
	}
	
	
	
	@Test
	public void testSetContactGetContactIDS() {
		assertEquals(cm.getContacts(1).size(), 1);
		assertEquals(cm.getContacts(1).iterator().next().getId(), 1);
	}
	
	
	
	
	// FLUSH
	

	@Test
	public void testFlushZeroContents() throws FileNotFoundException{
		ContactManagerImpl cm2 = new ContactManagerImpl();
		cm2.flush();
		File newFile = new File("output.txt");
		assertNotNull(newFile);
		
	}
	
	
	@Test
	public void testFlushWithContents() throws FileNotFoundException{
		cm.flush();
		File newFile = new File("output.txt");
		assertNotNull(newFile);
		assertTrue(newFile.length()> 0);

		
	}
	
	
}
	
