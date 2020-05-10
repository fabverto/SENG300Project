package service.domain;

//entity represents anything with a name and an integer ID for databasing purposes
public abstract class Entity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
	private String name;
	
	protected Entity() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
