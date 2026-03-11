package capitalism.DataStructure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTime {
	private int year;
	private int month;
	private int day;
	@Setter(lombok.AccessLevel.NONE)
	private String dayoftheweek = "";

	public DTime(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		if (year > 0 && month > 0 && day > 0)
			findDayOfTheWeek(year, month, day);
	}

	public String toString() {
		return "" + year + "/" + month + "/" + day + "/" + dayoftheweek + "요일";
	}

	String str_day[] = {"일", "월", "화", "수", "목", "금", "토"};
	int month_day[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	private void IsLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
			month_day[1] = 29;
		else {
			month_day[1] = 28;
		}
	}

	//2020 1.1-->수 기준요일로 계산
	private void findDayOfTheWeek(int year, int month, int day) {
		int totalDay = 0;
		if (year < 2020) {
			for (int i = 2019; i > year; i--) {
				IsLeapYear(i);
				for (int j = 0; j <= 11; j++) {
					totalDay += month_day[j];
				}
			}
			IsLeapYear(year);
			for (int i = month; i <= 11; i++) {
				totalDay += month_day[i];
			}
			totalDay += month_day[month - 1] - day + 1;
			dayoftheweek = str_day[(10 - (totalDay) % 7) % 7];
		} else {
			for (int i = 2020; i < year; i++) {
				IsLeapYear(i);
				for (int j = 0; j <= 11; j++) {
					totalDay += month_day[j];
				}
			}
			IsLeapYear(year);
			for (int i = 0; i < month - 1; i++) {
				totalDay += month_day[i];
			}
			totalDay += day - 1;
			dayoftheweek = str_day[(totalDay + 3) % 7];
		}
	}
}
