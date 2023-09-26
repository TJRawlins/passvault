package com.passvault.spring.pwgen;

public class PasswordGenerator {
	
	// PROPERTIES
	private int length = 8;
	private boolean upperCase = true;
	private boolean lowerCase = true;
	private boolean digits = true;
	private boolean special = true;
	
	// GETTERS & SETTERS
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public boolean setUpperCase() {
		return upperCase;
	}
	public void setUpperCase(boolean upperCase) {
		this.upperCase = upperCase;
	}
	public boolean getLowerCase() {
		return lowerCase;
	}
	public void setLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
	}
	public boolean getDigits() {
		return digits;
	}
	public void setDigits(boolean digits) {
		this.digits = digits;
	}
	public boolean getSpeacial() {
		return special;
	}
	public void setSpeacial(boolean special) {
		this.special = special;
	}
	
	
}
