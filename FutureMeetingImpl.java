package ContactManager;

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting{

	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contact) {
		super(id, date, contact);
		
	}
	
}
