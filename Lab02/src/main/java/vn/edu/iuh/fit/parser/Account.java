package vn.edu.iuh.fit.parser;

import java.time.LocalDate;

public class Account {
	private int id;
	private String name;
	private int age;
	private LocalDate dob;
	private Customer owner;

	public Account(int id, String name, int age, LocalDate dob) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.dob = dob;
	}

	public Account() {
		super();
	}

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", age=" + age + ", dob=" + dob + ", owner=" + owner + "]";
	}

	public static void name() {
		
	}
}
