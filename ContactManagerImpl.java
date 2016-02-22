package ContactManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;
import java.io.*;



public class ContactManagerImpl implements ContactManager{
	
	ArrayList<Contact> contactsList;
	ArrayList<Meeting> meetingsList;

	public ContactManagerImpl () {
		contactsList = new ArrayList<Contact>();
		meetingsList = new ArrayList<Meeting>();
		

	}
	
	

	
	/*** @throws IllegalArgumentException if the meeting is set for a time
* in the past, of if any contact is unknown / non-existent.
* @throws NullPointerException if the meeting or the date are null*/
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (date == null) {
			throw new NullPointerException();
		}
		for(Contact c: contacts) {
			if (!existsInList(contactsList, c))
				throw new IllegalArgumentException();
		}
		
		Date current = new Date();
		
		Date given = date.getTime();

		if(given.before(current)) {
			throw new IllegalArgumentException();
		}
		
		
		// the meeting
		FutureMeetingImpl newFutureMeeting = new FutureMeetingImpl((meetingsList.size() + 1),
				date,contacts );
		//a variable to assign the meeting id to, add id to the meetingslist
		int futureMeetingID = (meetingsList.size() + 1);  // next available id no
		meetingsList.add(newFutureMeeting);
		return futureMeetingID;
	}

		
	
	public boolean existsInList(ArrayList<Contact> list, Contact c) {
		for(Contact d : list ) {
			if((d.getId() == c.getId()) && (d.getName().equals(c.getName()))) {
				return true;
			}
		}
		return false;
	}




	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public PastMeeting addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public int addNewContact(String name, String notes) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void flush() throws FileNotFoundException {
		// TODO Auto-generated method stub
		
	}


}
	