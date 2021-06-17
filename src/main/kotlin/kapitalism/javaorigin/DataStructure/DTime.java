package kapitalism.javaorigin.DataStructure;

public class DTime {
	private int year;
	private int month;
	private int day;
	private String dayoftheweek = "";
	
	public DTime(int year , int month , int day)
	{
		this.year = year;
		this.month = month;
		this.day = day;
		if(year > 0 && month > 0 && day >0)
			findDayOfTheWeek(year,month,day);
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

	public String toString()
	{
		return ""+year+"/"+month+"/"+day+"/"+dayoftheweek+"요일";
	}

	String str_day[] = { "일" , "월" , "화" , "수" , "목" , "금" , "토" };
	int month_day[] = { 31,28,31,30,31,30,31,31,30,31,30,31 };

	// 윤년인지 체크
	private void IsLeapYear(int year)
	{
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
			month_day[1] = 29;
		else { //윤년이 아닌경우도 생각해줘야..
			month_day[1] = 28;
		}
	}

	//2020 1.1-->수 기준요일로 계산
	private void findDayOfTheWeek(int year, int month, int day) {
		int totalDay = 0;
		if (year < 2020) { //2020년 1월1일로부터 몇일 전인지 체크
			for(int i = 2019; i > year; i--) {
				IsLeapYear(i);
				for (int j = 0; j <= 11; j++) { //해당 년도의 모든 일을 더하기
					totalDay += month_day[j];
				}
			}
			IsLeapYear(year);
			//달의 차이 계산
			for (int i = month; i <= 11;i++) { //해당월 이후부터 12월까지의 일 계산
				totalDay += month_day[i];
			}
			totalDay += month_day[month - 1] - day+1;
			dayoftheweek = str_day[(10 - (totalDay) % 7) % 7];
		}
		else { //year>=2020 /2020년 1월1일로부터 몇일이 흘렀는지 체크
			for (int i = 2020; i < year; i++) { //그 년도 전까지
				IsLeapYear(i);
				for (int j = 0; j <= 11; j++) { //해당 년도의 모든 일을 더하기
					totalDay += month_day[j];
				}
			}
			IsLeapYear(year);
			//달의 차이 계산
			for (int i = 0; i < month-1; i++) { //그 달의 날짜는 더하면 안됨
				totalDay += month_day[i];
			}
			//일 차 계산
			totalDay += day-1;
			dayoftheweek =  str_day[(totalDay + 3) % 7];
		}
	}
}
