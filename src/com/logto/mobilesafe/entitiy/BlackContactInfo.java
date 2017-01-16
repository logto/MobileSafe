package com.logto.mobilesafe.entitiy;
/**
 * 代表一个黑名单号码的信息
 * @author Lenovo
 *
 */
public class BlackContactInfo {
	private String number;
	private String mode;
	
	public BlackContactInfo(){
		
	}
	public BlackContactInfo(String number, String mode) {
		super();
		this.number = number;
		this.mode = mode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlackContactInfo other = (BlackContactInfo) obj;
		if (mode == null) {
			if (other.mode != null)
				return false;
		} else if (!mode.equals(other.mode))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	};
	
	@Override
	public String toString() {
		return "BlackContactInfo [number=" + number + ", mode=" + mode + "]";
	}
	

}
