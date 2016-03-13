package ContactManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ContactManagerImpl implements ContactManager {

	ArrayList<Contact> contactsList;
	ArrayList<Meeting> meetingsList;
	String path;

	public ContactManagerImpl() {
		contactsList = new ArrayList<Contact>();
		meetingsList = new ArrayList<Meeting>();
		loadFile(new File("output.txt"));
	}

	public void loadFile(File textFile) {

		Scanner scan = null;

		try {
			scan = new Scanner(textFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String[] tempArray = null;

		if (scan.nextLine().equals("Contacts:")) {

			String nextLinx;
			while (scan.hasNextLine() && !(nextLinx = scan.nextLine()).equals("Meeting:")) {

				tempArray = nextLinx.split(";");
				int id = Integer.parseInt(tempArray[0]);
				String name = tempArray[1];
				String notes = tempArray[2];

				ContactImpl c = new ContactImpl(id, name, notes);
				contactsList.add(c);
			}

			while (scan.hasNextLine()) {

				nextLinx = scan.nextLine();
				tempArray = nextLinx.split(";");
				int id = Integer.parseInt(tempArray[0]);
				SimpleDateFormat format = new SimpleDateFormat("EEEE MMM DD HH:mm:ss z yyyy");
				Date date = null;
				try {
					date = format.parse(tempArray[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String notes = tempArray[2];

				Set<Contact> newSet = new HashSet<Contact>();
				while (scan.hasNextLine() && !(nextLinx = scan.nextLine()).equals("Meeting:")) {
					tempArray = nextLinx.split(";");
					int id2 = Integer.parseInt(tempArray[0]);
					String name2 = tempArray[1];
					String notes2 = tempArray[2];

					ContactImpl c = new ContactImpl(id2, name2, notes2);
					newSet.add(c);

				}

				Date current = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if (date.before(current)) {
					meetingsList.add(new PastMeetingImpl(id, calendar, newSet, notes));
				} else {
					meetingsList.add(new FutureMeetingImpl(id, calendar, newSet));
				}
			}
		}
	}

	// method to set a meeting date
	public Calendar setDate(int year, int month, int day) {
		Calendar setCalendar = Calendar.getInstance();
		setCalendar.set(year, month, day); // the date
		return setCalendar;
	}

	/***
	 * @throws IllegalArgumentException
	 *             if the meeting is set for a time in the past, of if any
	 *             contact is unknown / non-existent.
	 * @throws NullPointerException
	 *             if the meeting or the date are null
	 */

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (date == null) {
			throw new NullPointerException();
		}
		for (Contact c : contacts) {
			if (!existsInList(contactsList, c))
				throw new IllegalArgumentException();
		}

		Date current = new Date();

		Date given = date.getTime();

		if (given.before(current)) {
			throw new IllegalArgumentException();
		}

		FutureMeetingImpl newFutureMeeting = new FutureMeetingImpl((meetingsList.size() + 1), date, contacts);
		int futureMeetingID = (meetingsList.size() + 1);
		meetingsList.add(newFutureMeeting);
		return futureMeetingID;
	}

	public boolean existsInList(ArrayList<Contact> list, Contact c) {
		for (Contact d : list) {
			if ((d.getId() == c.getId()) && (d.getName().equals(c.getName()))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @throws IllegalStateException
	 *             if there is a meeting with that ID happening in the future
	 */

	@Override
	public PastMeeting getPastMeeting(int pastMeetingID) {
		PastMeetingImpl meeting;
		try {
			if (getMeeting(pastMeetingID) instanceof PastMeeting) {
				meeting = (PastMeetingImpl) getMeeting(pastMeetingID);
			} else {
				System.out.println("Meeting id is not for a past meeting");
				throw new IllegalArgumentException();

			}
		} catch (NullPointerException e) {
			throw new IllegalStateException();
		}

		Date current = new Date();
		Date ofMeeting = meeting.getDate().getTime();
		if (ofMeeting.after(current)) {
			throw new IllegalStateException();
		}

		return meeting;
	}

	/***
	 * @throws IllegalArgumentException
	 *             if there is a meeting with that ID happening in the past
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		FutureMeeting meeting;
		try {
			if (getMeeting(id) instanceof FutureMeeting) {
				meeting = (FutureMeetingImpl) getMeeting(id);
			} else {
				System.out.println("Meeting id is not for a future meeting");
				throw new IllegalArgumentException();
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}
		return meeting;
	}

	@Override
	public Meeting getMeeting(int id) {
		if ((meetingsList.isEmpty()) || (meetingsList.get(id - 1) == null)) {
			throw new NullPointerException();
		}

		return meetingsList.get(id - 1);
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (contact == null) {
			throw new NullPointerException();
		}
		List<Meeting> futureMeetings = new ArrayList<Meeting>();

		for (int i = 0; i < meetingsList.size(); i++) {
			if (meetingsList.get(i) instanceof FutureMeeting) {
				if (meetingsList.get(i).getContacts().contains(contact)) {
					futureMeetings.add(meetingsList.get(i));
				}
			}
		}
		return futureMeetings;
	}

	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {
		if (date == null) {
			throw new NullPointerException();
		}

		List<Meeting> dateList = new ArrayList<Meeting>();

		for (int i = 0; i < meetingsList.size(); i++) {
			if (meetingsList.get(i).getDate().equals(date)) {
				dateList.add(meetingsList.get(i));
			}

		}

		return dateList;
	}

	@Override
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {
		if (contact == null) {
			throw new NullPointerException();
		}

		if (!existsInList(contactsList, contact)) {
			throw new IllegalArgumentException();
		}

		ArrayList<PastMeeting> contactMeetings = new ArrayList<PastMeeting>();

		for (int i = 0; i < meetingsList.size(); i++) {

			if ((meetingsList.get(i) instanceof PastMeeting) && meetingsList.get(i).getContacts().contains(contact)) {
				contactMeetings.add((PastMeeting) meetingsList.get(i));
			}

		}

		return contactMeetings;
	}

	/**
	 * * @throws IllegalArgumentException if the list of contacts is empty, or
	 * any of the contacts does not exist
	 * 
	 * @throws NullPointerException
	 *             if any of the arguments is null
	 */

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

		if (contacts.isEmpty()) {
			throw new IllegalArgumentException();
		}

		if ((date == null) || (text == null) || (contacts == null)) {
			throw new NullPointerException();
		}

		int pastMeetingID = (meetingsList.size() + 1);
		PastMeetingImpl pastMeeting = new PastMeetingImpl(pastMeetingID, date, contacts, text);
		meetingsList.add(pastMeeting);

	}

	/***
	 * @throws IllegalArgumentException
	 *             if the meeting does not exist
	 * @throws IllegalStateException
	 *             if the meeting is set for a date in the future
	 * @throws NullPointerException
	 *             if the notes are null
	 */

	@Override
	public PastMeeting addMeetingNotes(int id, String text) {

		PastMeetingImpl meeting;
		try {
			if (getMeeting(id) instanceof PastMeeting) {
				meeting = (PastMeetingImpl) getMeeting(id);
			} else {
				System.out.println("Meeting id is not for a past meeting");
				throw new IllegalArgumentException();
			}

		} catch (NullPointerException e) {
			throw new IllegalArgumentException();

		}

		if (text == null) {
			throw new NullPointerException();
		}

		Date current = new Date();
		if (meeting.getDate().getTime().after(current)) {
			throw new IllegalStateException();
		}

		meeting.notes = text;
		return meeting;
	}

	/***
	 * @throws IllegalArgumentException
	 *             if the name or the notes are empty strings
	 * @throws NullPointerException
	 *             if the name or the notes are null
	 */

	@Override
	public int addNewContact(String name, String notes) {

		if ((name == null) || (notes == null)) {
			throw new NullPointerException();
		}

		if ((name.equals("")) || (notes.equals(""))) {
			throw new IllegalArgumentException();
		}

		ContactImpl newPerson = new ContactImpl((contactsList.size() + 1), name, notes);
		int newPersonID = (contactsList.size() + 1);
		contactsList.add(newPerson);

		return newPersonID;
	}

	/**
	 * Returns a list with the contacts whose name contains that string.
	 *
	 * If the string is the empty string, this methods returns the set that
	 * contains all current contacts.
	 *
	 * @param name
	 *            the string to search for
	 * @return a list with the contacts whose name contains that string.
	 * @throws NullPointerException
	 *             if the parameter is null
	 */
	@Override
	public Set<Contact> getContacts(String name) {

		if (name == null) {
			throw new NullPointerException();
		}

		Set<Contact> matches = new HashSet<Contact>();

		for (int i = 0; i < contactsList.size(); i++) {
			String checkName = contactsList.get(i).getName();
			if (checkName.contains(name)) {
				matches.add(contactsList.get(i));
			}
		}
		return matches;
	}

	/**
	 * 4 Returns a list containing the contacts that correspond to the IDs. Note
	 * that this method can be used to retrieve just one contact by passing only
	 * one ID.
	 *
	 * @param ids
	 *            an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs.
	 * @throws IllegalArgumentException
	 *             if no IDs are provided or if any of the provided IDs does not
	 *             correspond to a real contact
	 */

	@Override
	public Set<Contact> getContacts(int... ids) {

		if (ids.length == 0) {
			throw new IllegalArgumentException();
		}

		Set<Contact> iDMatches = new HashSet<Contact>();

		for (int parameter : ids) {

			for (int i = 0; i < contactsList.size(); i++) {

				int compare = contactsList.get(i).getId();
				if (parameter == compare) {
					iDMatches.add(contactsList.get(i));
				}
			}
		}

		if (iDMatches.isEmpty()) {
			throw new IllegalArgumentException();
		}

		return iDMatches;
	}

	/**
	 * Save all data to disk.
	 *
	 * This method must be executed when the program is closed and when/if the
	 * user requests it.
	 * 
	 * @throws FileNotFoundException
	 *             // create a printer writer, in order to create a file, output
	 *             all the // contacts // and meetings to the file.
	 */

	@Override
	public void flush() throws FileNotFoundException {

		if (path == null) {
			path = "output.txt";
		}

		try {
			File fileOut = new File(path);
			PrintWriter writer = new PrintWriter(fileOut);

			writer.print("Contacts: ");
			if (!contactsList.isEmpty()) {
				for (int i = 0; i < contactsList.size(); i++) {
					writer.print(contactsList.get(i).getId() + ";");
					writer.print(contactsList.get(i).getName() + ";");
					writer.println((contactsList.get(i).getNotes() == null ? "-" : contactsList.get(i).getNotes()));
				}
			}
			writer.println();

			if (!meetingsList.isEmpty()) {
				for (int i = 0; i < meetingsList.size(); i++) {

					writer.println("Meeting: ");
					writer.print(meetingsList.get(i).getId() + ";");
					writer.printf("%tc", meetingsList.get(i).getDate() + ";");
					if (meetingsList.get(i) instanceof PastMeeting) {
						writer.println(((PastMeeting) meetingsList.get(i)).getNotes());
					} else {
						writer.println("-");
					}

					for (Contact d : meetingsList.get(i).getContacts()) {
						writer.println(
								d.getId() + ";" + d.getName() + ";" + (d.getNotes() == null ? "-" : d.getNotes()));
					}
				}
			}
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not find database/storage.");
			System.out.println(e.getMessage());
			throw e;

		}

	}

}