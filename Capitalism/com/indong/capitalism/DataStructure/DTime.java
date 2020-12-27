package com.indong.capitalism.DataStructure;

public class DTime {
	private int year;
	private int month;
	private int day;
	private String dayoftheweek;
	
	public DTime(int year , int month , int day , String dayoftheweek)
	{
		this.year = year;
		this.month = month;
		this.day = day;
		this.dayoftheweek = dayoftheweek;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getDayoftheweek() {
		return dayoftheweek;
	}

	public void setDayoftheweek(String dayoftheweek) {
		this.dayoftheweek = dayoftheweek;
	}
	
	public String toString()
	{
		return ""+year+"/"+month+"/"+day+"/"+dayoftheweek+"요일";
	}
}