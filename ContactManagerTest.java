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

	Calendar testFutureCalendar = Calendar.getInstance();
	Calendar testPastCalendar = Calendar.getInstance();

	@Before
	public void setUp() throws Exception {
		cm = new ContactManagerImpl();
		setDates();
		testMeetingContacts.add(Paul);
		cm.contactsList.add(Paul);
	}

	private void setDates() {

		Random dateSet = new Random();

		testFutureCalendar.set(dateSet.nextInt(5) + 2017, (dateSet.nextInt(12) + 1), (dateSet.nextInt(28) + 1));
		testPastCalendar.set((2015 - (dateSet.nextInt(5))), (dateSet.nextInt(12) + 1), (dateSet.nextInt(28) + 1));

	}

	// check if contactsList is empty
	@Test
	public void testArrayListContacts() {
		assertNotNull(cm.contactsList);
	}

	@Test(expected = NullPointerException.class)
	public void testLoadFileDoesntExist() {
		cm.loadFile(new File("randomFileName"));

	}

	@Test
	public void testLoadingFile() {
		assertTrue(cm.contactsList.size() > 0);
	}

	// meetingslist is empty
	@Test
	public void testArrayListMeetings() {
		assertNotNull(cm.meetingsList);
	}

	// INT ADD FUTURE MEETING

	// Illegal argument Exc results if date is set in the past
	@Test(expected = IllegalArgumentException.class)
	public void testAddFutureMeetingPastDate() {
		cm.addFutureMeeting(testMeetingContacts, testPastCalendar);
	}

	// Illegal argument E results if contact is unknown
	@Test(expected = IllegalArgumentException.class)
	public void testAddFutureMeetingUnknownContact() {
		Contact Fiona = new ContactImpl(2, "Fiona");
		Set<Contact> fakeContacts = new HashSet<Contact>();
		fakeContacts.add(Fiona);
		cm.addFutureMeeting(fakeContacts, testFutureCalendar);
	}

	// NPE results if no date is set
	@Test(expected = NullPointerException.class)
	public void testAddFutureMeetingNoDate() {
		cm.addFutureMeeting(testMeetingContacts, null);
	}

	// id produced from adding new future meeting
	@Test
	public void testAddFutureMeeting() {
		assertEquals(cm.addFutureMeeting(testMeetingContacts, testFutureCalendar), 1);
	}

	// BOOLEAN CONTACT EXISTS IN LIST

	@Test
	public void testExistsInList() {
		assertTrue(cm.existsInList(cm.contactsList, Paul));
	}

	// PAST MEETING GET PAST MEETING FOR MEETING ID

	// illegal argument exception if mtg with that id is for a date in the
	// future
	@Test(expected = IllegalStateException.class)
	public void testGetPastMeetingDateInFuture() {
		Set<Contact> testSet = new HashSet<Contact>();
		ContactManagerImpl cm4 = new ContactManagerImpl();
		testSet.add(Paul);
		String notes = "This should fail";
		cm4.addNewPastMeeting(testSet, testFutureCalendar, notes);
		int id = cm4.meetingsList.size();
		cm4.getPastMeeting(id);
	}

	@Test
	public void testPastMeetingGetPastMeetingID() {
		Set<Contact> testPastContacts = new HashSet<Contact>();
		ContactManagerImpl cm3 = new ContactManagerImpl();
		testPastContacts.add(Paul);
		String pastMeetingMessage = "Past meeting notes";
		cm3.addNewPastMeeting(testPastContacts, testPastCalendar, pastMeetingMessage);

		assertEquals(cm3.getPastMeeting(1).getId(), 1);
	}

	// FUTURE MEETING GET FUTURE MEETING FOR MEETING ID

	// illegal argument exception if mtg with that id is for a date in the past
	@Test(expected = IllegalArgumentException.class)
	public void testGetFutureMeetingDateInPast() {
		Set<Contact> testSet = new HashSet<Contact>();
		testSet.add(Paul);
		String notes = "This should fail";
		cm.addNewPastMeeting(testSet, testPastCalendar, notes);

		int id = cm.meetingsList.size();
		cm.getFutureMeeting(id);
	}

	@Test
	public void testFutureMeetingGetFutureMeetingID() {
		cm.addFutureMeeting(testMeetingContacts, testFutureCalendar);
		assertEquals(cm.getFutureMeeting(1).getId(), 1);
	}

	// MEETING GET MEETING FOR MEETING ID

	@Test
	public void testMeetingGetMeeting() {
		cm.addFutureMeeting(testMeetingContacts, testFutureCalendar);
		assertEquals(cm.getMeeting(1).getId(), 1);
	}

	// LIST GET FUTURE MEETING LIST FOR CONTACT

	// NPE contacts null
	@Test(expected = NullPointerException.class)
	public void testListGetFutureMeetingListContactNull() {
		cm.addFutureMeeting(testMeetingContacts, testFutureCalendar);
		List<Meeting> meetings = cm.getFutureMeetingList(null);
	}

	// get meeting date for a contact
	@Test
	public void testListGetFutureMeetingListFromContactID() {
		cm.addFutureMeeting(testMeetingContacts, testFutureCalendar);
		List<Meeting> meetings = cm.getFutureMeetingList(Paul);
		assertEquals(meetings.get(0).getDate(), testFutureCalendar);
	}

	// LIST<MEETING> GET MEETING LIST ON DATE

	// test NPE if the date is null
	@Test(expected = NullPointerException.class)
	public void testListGetMeetingListOnNullDate() {
		cm.getMeetingListOn(null);
	}

	// a list of meetings on a specified date
	@Test
	public void testListGetMeetingListOnDate() {
		cm.addFutureMeeting(testMeetingContacts, testFutureCalendar);
		List<Meeting> meetings = cm.getMeetingListOn(testFutureCalendar);
		assertEquals(meetings.get(0).getDate(), testFutureCalendar);
	}

	// LIST<PASTMEETING> GET PAST MEETING FOR CONTACT

	// test NPE if the contact is null
	@Test(expected = NullPointerException.class)
	public void testgetPastMeetingListForNullContact() {
		cm.getPastMeetingListFor(null);
	}

	// a list of past meetings by contact
	@Test
	public void testPastMeetingGetPastMeetingListForContact() {

		cm.addNewPastMeeting(testMeetingContacts, testPastCalendar, "Ok");
		assertEquals(cm.getPastMeetingListFor(Paul).get(0).getDate(), testPastCalendar);
	}

	// VOID ADD NEW PAST MEETING

	// IAE if list of contacts is empty
	@Test(expected = IllegalArgumentException.class)
	public void TestAddNewPastMeetingEmptyContacts() {
		Set<Contact> emptyContacts = new HashSet<Contact>();
		String pastMeetingMessage = "Past meeting notes";
		cm.addNewPastMeeting(emptyContacts, testPastCalendar, pastMeetingMessage);
	}

	// NPE if the contact is null
	@Test(expected = NullPointerException.class)
	public void testTestAddNewPastMeetingNullContacts() {
		String notes = "Some notes";
		cm.addNewPastMeeting(null, testPastCalendar, notes);
	}

	// null - date
	@Test(expected = NullPointerException.class)
	public void testTestAddNewPastMeetingNullCalendar() {
		Set<Contact> someContacts = new HashSet<Contact>();
		someContacts.add(Paul);
		String notes = "Some notes";
		cm.addNewPastMeeting(someContacts, null, notes);
	}

	// null - text
	@Test(expected = NullPointerException.class)
	public void testTestAddNewPastMeetingNullText() {
		Set<Contact> someContacts = new HashSet<Contact>();
		someContacts.add(Paul);
		String notes = null;
		cm.addNewPastMeeting(someContacts, testPastCalendar, notes);
	}

	@Test
	public void testAddNewPastMeeting() {
		Set<Contact> contactPastMeeting = new HashSet<Contact>();
		contactPastMeeting.add(Paul);
		String mtgNotes = "This should work";
		cm.addNewPastMeeting(contactPastMeeting, testPastCalendar, mtgNotes);

		assertEquals(cm.getMeetingListOn(testPastCalendar).get(0).getDate(), testPastCalendar);
	}

	// PAST MEETING ADD PAST MEETING MEETING NOTES

	@Test(expected = IllegalArgumentException.class)
	public void testPastMeetingAddMeetingNotesMeetingDoesNotExist() {
		String notes = "Notes are here";
		cm.addMeetingNotes(22, notes);
	}

	@Test(expected = NullPointerException.class)
	public void testPastMeetingAddMeetingNotesNotesAreNull() {

		Calendar date = cm.setDate(2012, 8, 1);
		String notes = null;
		int meetingID = (cm.meetingsList.size() + 1);
		PastMeetingImpl pastMeeting = new PastMeetingImpl(meetingID, date, testMeetingContacts, notes);
		cm.meetingsList.add(pastMeeting);

		String addNotes = null;
		cm.addMeetingNotes(meetingID, addNotes);
	}

	@Test(expected = IllegalStateException.class)
	public void testPastMeetingAddMeetingNotesMeetingDateInFuture() {

		Calendar date = cm.setDate(2017, 8, 1);
		String notes = "Notes";
		int meetingID = (cm.meetingsList.size() + 1);
		PastMeetingImpl pastMeeting = new PastMeetingImpl(meetingID, date, testMeetingContacts, notes);
		cm.meetingsList.add(pastMeeting);

		String addNotes = "More notes";
		cm.addMeetingNotes(meetingID, addNotes);

	}

	@Test
	public void testPastMeetingAddMeetingNotes() {
		Calendar date = cm.setDate(2014, 8, 1);
		String notes = "Notes";
		int meetingID = (cm.meetingsList.size() + 1);
		PastMeetingImpl pastMeeting = new PastMeetingImpl(meetingID, date, testMeetingContacts, notes);
		cm.meetingsList.add(pastMeeting);

		String addNotes = "More notes";
		assertEquals(cm.addMeetingNotes(meetingID, addNotes).getNotes(), addNotes);

	}

	// INT ADD NEW CONTACT

	// IAE if the string name is empty
	@Test(expected = IllegalArgumentException.class)
	public void testAddContactEmptyName() {
		cm.addNewContact("", "anything");
	}

	// IAE if the string for notes is empty
	@Test(expected = IllegalArgumentException.class)
	public void testAddContactEmptyNotes() {
		cm.addNewContact("name", "");
	}

	// NPE if name is null
	@Test(expected = NullPointerException.class)
	public void testAddContactNullName() {
		cm.addNewContact(null, "anything");
	}

	// NPE if notes are null
	@Test(expected = NullPointerException.class)
	public void testAddContactNullNotes() {
		cm.addNewContact("name", null);
	}

	// test id produced from adding new contact
	@Test
	public void testAddContact() {
		assertEquals(cm.addNewContact("Olivia", "PA"), 2);
	}

	// SET<CONTACT> GET CONTACT IDS

	@Test(expected = IllegalArgumentException.class)
	public void testSetContactGetContactNoIDS() {
		cm.getContacts();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetContactGetContactIDDoesntExist() {
		cm.getContacts(500);
	}

	@Test
	public void testSetContactGetContactIDS() {
		assertEquals(cm.getContacts(1).size(), 1);
		assertEquals(cm.getContacts(1).iterator().next().getId(), 1);
	}

	// VOID FLUSH

	@Test
	public void testFlushZeroContents() throws FileNotFoundException {
		ContactManagerImpl cm2 = new ContactManagerImpl();
		cm2.flush();
		File newFile = new File("output.txt");
		assertNotNull(newFile);

	}

	@Test
	public void testFlushWithContents() throws FileNotFoundException {
		cm.flush();
		File newFile = new File("output.txt");
		assertNotNull(newFile);
		assertTrue(newFile.length() > 0);

	}

}
