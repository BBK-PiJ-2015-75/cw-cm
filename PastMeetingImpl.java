package ContactManager;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting{

	public String notes;
	
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contact, String notes) {
	
		super(id, date, contact);	
		if(notes == null) {
			throw new NullPointerException ();
		}
		this.notes = notes;
	}

	
	
	@Override
	public String getNotes() {
		return notes;
	}

	
	
}
