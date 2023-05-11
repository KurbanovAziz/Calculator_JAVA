import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String input = "2 * 10";
        String result = calc(input);
        System.out.println(result);
    }

    private static final Map<String, Integer> romanNumerals = new HashMap<>() {{
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
    }};

    public static String calc(String input) {
        String[] parts = input.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input format. Expected: 'a op b'");
        }

        String a = parts[0];
        String op = parts[1];
        String b = parts[2];

        int result;

        if (isRomanNumeral(a) && isRomanNumeral(b)) {
            int arabicA = romanToArabic(a);
            int arabicB = romanToArabic(b);

            result = switch (op) {
                case "+" -> arabicA + arabicB;
                case "-" -> arabicA - arabicB;
                case "*" -> arabicA * arabicB;
                case "/" -> arabicA / arabicB;
                default -> throw new IllegalArgumentException("Invalid operator: " + op);
            };

            if (result <= 0) {
                throw new IllegalArgumentException("Invalid Roman numeral result: " + result);
            }

            return arabicToRoman(result);
        } else if (isInteger(a) && isInteger(b)) {
            int intA = Integer.parseInt(a);
            int intB = Integer.parseInt(b);

            result = switch (op) {
                case "+" -> intA + intB;
                case "-" -> intA - intB;
                case "*" -> intA * intB;
                case "/" -> intA / intB;
                default -> throw new IllegalArgumentException("Invalid operator: " + op);
            };

            return Integer.toString(result);
        } else {
            throw new IllegalArgumentException("Mixed Roman and Arabic numerals are not allowed");
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRomanNumeral(String s) {
        return romanNumerals.containsKey(s);
    }

    private static int romanToArabic(String s) {
        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            int current = romanNumerals.get(Character.toString(s.charAt(i)));

            if (i < s.length() - 1) {
                int next = romanNumerals.get(Character.toString(s.charAt(i + 1)));

                if (next > current) {
                    current = -current;
                }
            }

            result += current;
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
            String numeral = entry.getKey();
            int value = entry.getValue();

            while (number >= value) {
                result.append(numeral);
                number -= value;
            }

            if (number == 0) {
                break;
            }
        }

        return result.toString();
    }
}