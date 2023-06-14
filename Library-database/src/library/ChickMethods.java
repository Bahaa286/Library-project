package library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javafx.collections.ObservableList;

public class ChickMethods {

	// return the maximum integer in a positive integer array
	public static int getMax(int[] arr) {

		int max = 0;

		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}

		return max;
	}

	// chick if name valid (all character between a-z or A-Z)
	public static boolean isNameValid(String name) {
		
		if(name.isEmpty())
			return false;

		boolean isValid = true;
		char a = ' ';
		for (int i = 0; i < name.length() && isValid; i++) {

			a = name.charAt(i);
			isValid = (((((int) 'a') <= ((int) a)) && (((int) a) <= ((int) 'z')))
					|| ((((int) 'A') <= ((int) a)) && (((int) a) <= ((int) 'Z'))));

		}

		return isValid;
	}

	public static char getGender(String s) {

		if ((s.trim()).matches("male") || (s.trim()).matches("m")) {
			return 'M';
		} else if ((s.trim()).matches("female") || (s.trim()).matches("f")) {
			return 'F';
		}

		return 'M';

	}
	
	public static String getGenderString(char gender) {
		return (gender == 'f' || gender == 'F')? "female":"male";
	}
	// ----------------------------------------------------------------//
	// chick if birth date valid

	public static boolean isBirthDateValid(String birthDate) {

		if (!isBirthDateTextValid(birthDate))
			return false;

		// today date
		String nowDate = getNowDate();

		String[] split = birthDate.split("-");

		String[] months = { "January", "February", "March", "Abrel", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		String month = months[Integer.parseInt(split[1]) - 1];
		
		if(!isBirthDateValid(Integer.parseInt(split[0]), month, Integer.parseInt(split[2])))
			return false;

		return isFirstDateBeforeSecond(birthDate, nowDate);

	}
	
	public static boolean isDateValidWithoutNowDate(String birthDate) {

		if (!isBirthDateTextValid(birthDate))
			return false;

		String[] split = birthDate.split("-");

		String[] months = { "January", "February", "March", "Abrel", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		String month = months[Integer.parseInt(split[1]) - 1];

		return isBirthDateValid(Integer.parseInt(split[0]), month, Integer.parseInt(split[2]));

	}

	// chick if birth date valid
	public static boolean isBirthDateTextValid(String date) {
		boolean isValid = date.matches("..-..-....") || date.matches(".-..-....") || date.matches("..-.-....")
				|| date.matches(".-.-....");

		if (!isValid)
			return false;

		String[] split = date.split("-");
		
		if(!(isDigit(split[0]) && isDigit(split[1]) && isDigit(split[2])))
			return false;

		int day = Integer.parseInt(split[0]); // day 
		int month = Integer.parseInt(split[1]); // month 
		int year = Integer.parseInt(split[2]); // year 
		
		return (day > 0) && (day <= 31) && (month > 0) && (month <= 12) && (year >= 1800);

	}

	// without year
	public static boolean isBirthDateValid(int day, String month) {

		if (day < 29)
			return true;

		String[] months = { "January", "February", "March", "Abrel", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		int monthNum = 0;

		// store the number of month in monthNum
		for (int i = 0; i < months.length && monthNum == 0; i++) {
			monthNum = (month.equals(months[i])) ? (i + 1) : monthNum;
		}

		boolean is30 = false, is31 = false, isFeb = false;

		boolean isEven = ((monthNum % 2) == 0);

		// determine the number of days in the month
		if (monthNum <= 7 && monthNum != 2) {
			is30 = isEven;
			is31 = !isEven;
		} else if (monthNum > 7) {
			is30 = !isEven;
			is31 = isEven;
		} else {
			isFeb = true;
		}

		// determine if the day valid or not
		if (isFeb) {
			return false;
		} else if (day == 31 && is30) {
			return false;
		}

		return true;

	}// end isBirthDateValid method

	// with year
	public static boolean isBirthDateValid(int day, String month, int year) {

		if (day < 29)
			return true;
		
		String[] months = { "January", "February", "March", "Abrel", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		int monthNum = 0;

		// store the number of month in monthNum
		for (int i = 0; i < months.length && monthNum == 0; i++) {
			monthNum = (month.equals(months[i])) ? (i + 1) : monthNum;
		}

		boolean is30 = false, is31 = false, isFeb = false;

		boolean isEven = ((monthNum % 2) == 0);
		
		// determine the number of days in the month
		if (monthNum <= 7 && monthNum != 2) {
			is30 = isEven;
			is31 = !isEven;
		} else if (monthNum > 7) {
			is30 = !isEven;
			is31 = isEven;
		} else {
			isFeb = true;
		}

		// determine if the day valid or not
		if (isFeb) {
			if (!isLeapYear(year)) {
				return false;
			} else {
				return (day > 29) ? false : true;
			}
		} else if (day == 31 && is30) {
			return false;
		}

		return true;

	}// end isBirthDateValid method

	public static boolean isLeapYear(int year) {
		return (year % 4) == 0;
	}

	// chick if firstDate before second date
	public static boolean isFirstDateBeforeSecond(String firstDate, String secondDate) {

		DateTimeFormatter firstFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter secondFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		if (firstDate.matches("..-..-....")) {
			firstFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		} else if (firstDate.matches(".-..-....")) {
			firstFormatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		} else if (firstDate.matches("..-.-....")) {
			firstFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
		} else if (firstDate.matches(".-.-....")) {
			firstFormatter = DateTimeFormatter.ofPattern("d-M-yyyy");
		} else {
			return false;
		}

		if (secondDate.matches("..-..-....")) {
			secondFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		} else if (secondDate.matches(".-..-....")) {
			secondFormatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		} else if (secondDate.matches("..-.-....")) {
			secondFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
		} else if (secondDate.matches(".-.-....")) {
			secondFormatter = DateTimeFormatter.ofPattern("d-M-yyyy");
		} else {
			return false;
		}

		// convert String to LocalDate
		LocalDate first = LocalDate.parse(firstDate, firstFormatter);
		LocalDate second = LocalDate.parse(secondDate, secondFormatter);

		return first.isBefore(second) || first.isEqual(second);
	}
	// ----------------------------------------------------------------//
	// chick if DatePicker value valid

	public static boolean isDatePickerValid(LocalDate date) {

		LocalDate today = LocalDate.now();

		return today.isAfter(date);

	}

	// ----------------------------------------------------------------//

	// chick if phone number valid
	public static boolean isPhoneNumberValid(String phone) {

		boolean isValid = true;

		// chick if phone number is contain 10 digits
		isValid = (phone.length() == 10);

		// chick if all characters are digits
		for (int i = 0; i < phone.length() && isValid; i++) {
			isValid = (Character.isDigit(phone.charAt(i)));
		}

		return isValid;

	}

	// chick if gender valid
	public static boolean isGenderValid(String gender) {

		return gender.trim().equalsIgnoreCase("male") || gender.trim().equalsIgnoreCase("female")
				|| gender.trim().equalsIgnoreCase("m") || gender.trim().equalsIgnoreCase("f");

	}

	// chick if gender valid
	public static boolean isGenderValid(char gender) {

		return gender == 'm' || gender == 'f' || gender == 'M' || gender == 'F';

	}

	// chick if the string is digits
	public static boolean isDigit(String s) {

		if (s.length() == 0)
			return false;

		boolean isValid = true;

		// each character in the string should be digit
		for (int i = 0; i < s.length() && isValid; i++) {
			isValid = Character.isDigit(s.charAt(i));
		}

		return isValid;

	}

	// chick if the string is double number
	public static boolean isFloat(String s) {

		if (s.length() == 0)
			return false;

		boolean isDigits = true;

		if (s.length() == 1 && s.charAt(0) == '.')
			return false;

		int numOfPoints = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '.') {
				numOfPoints++;
			}
		}

		if (numOfPoints > 1) {
			return false;
		}

		for (int i = 0; i < s.length() && isDigits; i++) {
			isDigits = (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.');
		}

		return isDigits;
	}

	// increment string of numbers characters
	public static String incrementStringNum(String s) {

		if (isDigit(s)) { // if the String's characters are digits

			int length = s.length(); // string length

			String newString = "";// new String after increment

			// array of character to store characters of old String old String
			char[] oldString = new char[length];

			// store all character of the String into array of character
			for (int i = 0; i < length; i++) {
				oldString[i] = s.charAt(i);
			} // end for

			// increment the string
			for (int i = length - 1; i >= 0; i--) {
				if (oldString[i] != '9') {
					oldString[i]++;
					break;
				} else {
					oldString[i] = '0';
				}
			} // end for

			// insert into newString
			for (int i = 0; i < length; newString += oldString[i++])
				;

			return newString;
		} // end if

		return s;

	}// end incrementStringNum

	// -----------------------------------------------------------//
	// date methods

	// return today date
	public static String getNowDate() {

		LocalDate date = LocalDate.now();

		return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

	}

	// return next month date
	public static String getNextMonthDate() {

		// today date
		String todayDate = getNowDate();
		String[] splitted = todayDate.split("-");

		int day = Integer.parseInt(splitted[0]); // day
		int month = Integer.parseInt(splitted[1]); // month
		int year = Integer.parseInt(splitted[2]); // year

		if (month == 12) // if month is 12 (December)
			return day + "-" + 1 + "-" + (++year);

		// is month's day 30 boolean
		boolean isMonth30 = is30Month(month);

		if (isMonth30) // if month even (next month's day could be 31
			return day + "-" + (++month) + "-" + year;

		if (month == 1) { // if month is 2 (February)
			if (day <= 28)
				return day + "-" + 2 + "-" + year;
			return 1 + "-" + 3 + "-" + year;
		}

		if (day == 31) // day is 31 (coulden't be day for even month
			return 1 + "-" + (month + 2) + "-" + year;

		return day + "-" + (++month) + "-" + year;

	}

	// increment date
	public static String incrementDate(String date, int days) {

		// date format
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		// calendar
		Calendar c = Calendar.getInstance();

		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, days);

		return sdf.format(c.getTime());

	}

	// chick if month is 30 day
	private static boolean is30Month(int num) {

		if (num < 7) // if month less than 7
			return (num % 2 == 0);
		return !(num % 2 == 0);

	}

	// get the difference days between two dates (DD-MM-YYYY)
	public static int differenceDaysBetweenTwoDates(String initialDate, String finalDate) {

		if (finalDate.length() == 0)
			return 0;

		// format date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

		// dates
		Date initial = null, finals = null;

		try {
			initial = sdf.parse(initialDate); // initial date
			finals = sdf.parse(finalDate); // final date

		} catch (ParseException e) {
			e.printStackTrace();
		}

		// difference between dates
		long diffInMillies = Math.abs(finals.getTime() - initial.getTime());

		if ((finals.getTime() - initial.getTime()) <= 0)
			return 0;

		// the difference in days unit
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		return (int) diff;

	} // end differenceDaysBetweenTwoDates method

	// -------------------------------------------------------------//
	// list methods

	// return array of string from ObservableList of string
	public static String[] ObservableListToArrayOfString(ObservableList<String> list) {

		// size
		int size = list.size();

		// array
		String[] array = new String[size];

		// insert element from ObservableList into array
		for (int i = 0; i < size; i++) {
			array[i] = list.get(i);
		}

		return array;
	} // end ObservableListToArrayOfString method

	public static String[] ArrayListToArrayOfString(ArrayList<String> list) {

		// size
		int size = list.size();

		// array
		String[] array = new String[size];

		// insert element from ObservableList into array
		for (int i = 0; i < size; i++) {
			array[i] = list.get(i);
		}

		return array;

	} // end ArrayListToArrayOfString method

	// chick if item is exist in the list of String
	public static boolean isItemExistInObservableList(ObservableList<String> list, String item) {

		// is exist boolean
		boolean isExist = false;

		// size
		int size = list.size();

		// if there is no items in the list
		if (list.isEmpty())
			return false;

		// for each item in the list
		for (int i = 0; i < size && !isExist; i++) {
			isExist = list.get(i).equals(item);
		} // end for

		return isExist;

	}// end isItemExistInObservableList

	// chick if item is exist in the list of String
	public static boolean isItemExistInArrayList(ArrayList<String> list, String item) {

		// is exist boolean
		boolean isExist = false;

		// size
		int size = list.size();

		// if there is no items in the list
		if (list.isEmpty())
			return false;

		// for each item in the list
		for (int i = 0; i < size && !isExist; i++) {
			isExist = list.get(i).equals(item);
		} // end for

		return isExist;

	}// end isItemExistInObservableList

	// return ArrayList from array
	public static ArrayList<String> getArrayListFromArray(String[] array) {

		// size
		int size = array.length;

		// ArrayList
		ArrayList<String> list = new ArrayList();

		// insert into list
		for (int i = 0; i < size; i++) {
			list.add(array[i]);
		} // end for

		return list;

	}

	// return ArrayList from ObservableLise of String
	public static ArrayList<String> getArrayListFromObservableListOfString(ObservableList<String> list) {

		// size
		int size = list.size();

		// ArrayList
		ArrayList<String> newList = new ArrayList<String>();

		// copy each element
		for (int i = 0; i < size; i++) {
			newList.add(list.get(i));
		} // end for

		return newList;

	}
} // end ChickMethods class
