package com.bankwel.compiler.calculator;

import javax.validation.constraints.NotNull;

public class Demo {

	private char[] input;
	private int index;
	private char token;

	public Demo(@NotNull String input) {
		input = input.replaceAll("\\s+", "");
		this.input = input.toCharArray();
		this.index = 0;
		this.token = getchar();
	}

	private void info(String msg) {
		System.out.println("Info:" + msg);
	}

	private void error(String msg) {
		System.out.println("Error:" + msg);
	}

	private char getchar() {
		if (index == input.length)
			return '\0';
		return input[index++];
	}

	private void match(char expected) {
		if (expected == token)
			token = getchar();
		else
			error("Unexpected character '" + expected + "'.");
	}

	private int exp() {
		int temp = term();
		while (token == '+' || token == '-') {
			if (token == '+') {
				match('+');
				temp += term();
				info("Plus");
			} else if (token == '-') {
				match('-');
				temp -= term();
				info("Minus");
			}
		}
		return temp;
	}

	private int term() {
		int temp = factor();
		while (token == '*') {
			match('*');
			temp *= factor();
			info("Times");
		}
		return temp;
	}

	private int factor() {
		int temp = 0;
		if (token == '(') {
			info("Bracket Begin");
			match('(');
			temp = exp();
			match(')');
			info("Bracket End");
		} else if (isDigit(token)) {
			temp = parseInt();
			info("Parse Int");
		} else
			error("Unresolveable character '" + token + "'.");

		return temp;
	}

	private boolean isDigit(char digit) {
		return digit <= '9' && digit >= '0';
	}

	private int parseInt() {
		StringBuffer buffer = new StringBuffer();
		while (isDigit(token)) {
			buffer.append(token);
			token = getchar();
		}
		return Integer.parseInt(buffer.toString());
	}

	public int compile() {
		return exp();

	}

	public static void main(String[] args) {
		System.out.println(new Demo("1+(1-3*2)*2-1").compile());
	}
}
