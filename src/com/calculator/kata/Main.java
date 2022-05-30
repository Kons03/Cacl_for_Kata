package com.calculator.kata;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  //подгружаем сканнер для последующего ввода пользователем
        System.out.print("Введите выражение вида 1 + 1 или I + I: ");
        String userInput = sc.nextLine();
        String[] arrayUserInput = userInput.split(" "); //разделим вводимую строку на 3 символа по пробелам
        //условная конструкция, проверяющая на корректность ввода по числу знаков в строке (должно быть ровно 3)
        if (arrayUserInput.length > 3) {
            throw new InvalidInputException("Некорректный ввод! Вы ввели слишком много знаков!"); //создан класс для исключений
        } else if (arrayUserInput.length < 3) {
            throw new InvalidInputException("Некорректный ввод! Вы ввели слишком мало знаков!"); //создан класс для исключений
        }
        String num1 = arrayUserInput[0]; //число арабское или римское
        String num2 = arrayUserInput[2]; //число арабское или римское
        char operation = arrayUserInput[1].charAt(0); //знак операции
        int arabicNumber1; //арабское число
        int arabicNumber2; //арабское число
        int result; //арабское число - результат операции (+, -, *, /)
        //сделаем проверку: является ли указанные числа num1 & num2 цифрами
        if (Character.isDigit(num1.charAt(0)) && Character.isDigit(num2.charAt(0))) {
            arabicNumber1 = Integer.parseInt(num1);
            arabicNumber2 = Integer.parseInt(num2);
            //вложенная условная конструкция, проверяющая на корректность ввода (отсутствие чисел меньше 1 и больше 10)
            if (arabicNumber1 < 1 || arabicNumber1 > 10 || arabicNumber2 < 1 || arabicNumber2 > 10) {
                throw new InvalidInputException("Некорректный ввод! Числа не удовлетворяют заданному интервалу!"); //создан класс для исключений
            } else {
                result = calculator(arabicNumber1, arabicNumber2, operation);
                System.out.print("Полученный результат = " + result);
            }
            //если num1 и num2 не являются арабскими числами, то делаем проверку на их "римскость"
            //метод "romanToArabic" задан ниже
        } else {
            arabicNumber1 = romanToArabic(num1);
            arabicNumber2 = romanToArabic(num2);
            //аналогичная вложенная конструкция для проверки на соответствие диапазону [1,10]
            if (arabicNumber1 < 1 || arabicNumber1 > 10 || arabicNumber2 < 1 || arabicNumber2 > 10) {
                throw new InvalidInputException("Некорректный ввод! Числа не удовлетворяют заданному интервалу!"); //создан класс для исключений
            }
            else if (arabicNumber1 - arabicNumber2 == 0 && operation == '-') {
                throw new InvalidInputException("Некорректный ввод! Числа не удовлетворяют заданному интервалу!"); //создан класс для исключений
            }
//            else if (arabicNumber1  == "IIII" || arabicNumber2 == 'IIII') {
//                throw new InvalidInputException("Некорректный ввод!"); //создан класс для исключений
//            }
            else {
                result = calculator(arabicNumber1, arabicNumber2, operation);
                System.out.print("Полученный результат = " + arabToRoman(result));
            }
        }
    }

    //новый метод "calculator", который показывает все 4 арифметических действия, иначе ("default") - ошибка
    public static int calculator(int num1, int num2, char operation) {
        Integer result = null;
        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                throw new InvalidInputException("Некорректный ввод арифметического знака!"); //создан класс для исключений
        }
        return result;
    }

    //новый метод "arabToRoman", переводящий позволяющий вычислять выражения с римскими цифрами
    private static String arabToRoman(int num) {
        String c[] = {"", "C"};
        String x[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String i[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String hundreds = c[(num % 1000) / 100];
        String tens = x[(num % 100) / 10];
        String ones = i[num % 10];
        return hundreds + tens + ones;
    }

    //новый метод "romanToArabic", позволяющий считать римские цифры
    private static int romanToArabic(String str) {
        int result = 0;
        for (int i = 0; i < str.length(); i++) {
            int s1 = value(str.charAt(i));
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));
                if (s1 >= s2) {
                    result = result + s1;
                } else {
                    result = result + s2 - s1;
                    i++;
                }
            } else {
                result = result + s1;
                i++;
            }
        }
        return result;
    }

    //новый метод "value", переводящий римские цифры в арабские, для экономии места не прописывались все значения
    //к отличным от значений ниже пришли с помощью "romanToArabic"
    //отрицательные числа не прописывались, так как в римской системе счисления отсутствуют отрицательные числа
    private static int value(char r) {
        if (r == 'I')
            return 1;
        if (r == 'V')
            return 5;
        if (r == 'X')
            return 10;
        if (r == 'L')
            return 50;
        if (r == 'C')
            return 100;
        return -1;
    }
}