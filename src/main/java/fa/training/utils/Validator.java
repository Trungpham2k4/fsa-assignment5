package fa.training.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class Validator {
    public static boolean isBlank(String str) {
        return str != null && str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isValidPhoneNumber(String phone) {
        String normalizePhone = phone.replaceAll("\\s+", "");
        return normalizePhone.trim().matches(Constants.PHONE_NUMBER_REGEX);
    }

    public static boolean isValidDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        try{
            LocalDate.parse(date, dateTimeFormatter);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        return !email.isBlank() && email.matches(Constants.EMAIL_REGEX);
    }

    public static boolean isValidRate(double rate) {
        return rate >= 0 && rate <= 100;
    }

    public static boolean isValidPositiveNumber(double number) {
        return number > 0;
    }

    public static boolean isExistName(String name, List<String> listNames) {
        return listNames.contains(name);
    }
}
