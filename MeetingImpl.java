package ContactManager;

import java.util.Calendar;
import java.util.Set;

public abstract class MeetingImpl implements Meeting {

	public int id;
	public Calendar date;
	public Set<Contact> contact;

	public MeetingImpl(int id, Calendar date, Set<Contact> contact) {
		if (contact == null) {
			throw new NullPointerException();
		}

		else if (contact.size() < 1) {
			throw new IllegalArgumentException();
		}

		else if (id <= 0) {
			throw new IllegalArgumentException();
		}

		else if (date == null) {
			throw new NullPointerException();
		}

		this.id = id;
		this.date = date;
		this.contact = contact;

	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Calendar getDate() {
		return date;
	}

	@Override
	public Set<Contact> getContacts() {
		return contact;
	}

}
