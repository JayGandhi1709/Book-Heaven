package com.example.BookHeaven.model;

//import java.util.HashMap;
//import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
	@Id
	String id;

	String name;

	@Indexed(unique = true)
	String email;

	@With // This will generate a withPassword method
	// @JsonIgnore // This will prevent the password from being returned in the
	// response
	String password;

	String role = "USER";

	// Constructor
	public User(String id, String name, String email, String password, String role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role != null ? role : "USER";
	}

	// Method to convert object to HashMap
	// public Map<String, Object> toHashMap() {
	// Map<String, Object> map = new HashMap<>();
	// map.put("id", this.id);
	// map.put("name", this.name);
	// map.put("email", this.email);
	// return map;
	// }

	// Getters and Setters

	// public String getId() {
	// return id;
	// }

	// public void setId(String id) {
	// this.id = id;
	// }

	// public String getName() {
	// return name;
	// }

	// public void setName(String name) {
	// this.name = name;
	// }

	// public String getEmail() {
	// return email;
	// }

	// public void setEmail(String email) {
	// this.email = email;
	// }

	// public String getPassword() {
	// return password;
	// }

	// public void setPassword(String password) {
	// this.password = password;
	// }

	// public String getRole() {
	// return role;
	// }

	// public void setRole(String role) {
	// this.role = role != null ? role : "user";
	// }

}
