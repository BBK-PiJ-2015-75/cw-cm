package ContactManager;

public class ContactImpl implements Contact {

	public int id;
	public String name;
	public String notes;

	public ContactImpl(int id, String name, String notes) {

		if (id <= 0) {
			throw new IllegalArgumentException();
		}

		if (name == null) {
			throw new NullPointerException();
		}

		if (notes == null) {
			throw new NullPointerException();
		}

		this.id = id;
		this.name = name;
		this.notes = notes;
	}

	public ContactImpl(int id, String name) {

		if (id <= 0) {
			throw new IllegalArgumentException();
		}

		if (name == null) {
			throw new NullPointerException();
		}

		this.id = id;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public void addNotes(String note) {
		notes = note;

	}

}
