package com.bankwel.compiler.calculator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class DoubleSupported {

	private char[] mode;
	private int index;
	private List<String> instructions;

	private static final char EMPTY = '\0';
	private static final char PLUS = '+';
	private static final char MINUS = '-';
	private static final char MUL = '*';
	private static final char DIV = '/';
	private static final char POW = '^';
	private static final char PAREN = '(';
	private static final char THESIS = ')';
	private static final char DOT = '.';

	public DoubleSupported(@NotNull String s) {
		mode = s.replaceAll("\\s", "").toCharArray();
		index = 0;
		instructions = new ArrayList<String>();
	}

	private void instruct(String... args) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(args[i]);
			buffer.append(" ");
		}
		instructions.add(buffer.toString());
	}

	private void error(String msg) {
		System.out.println(msg);
	}

	private char lookahead() {
		if (index == mode.length)
			return EMPTY;
		return mode[index];
	}

	private char step() {
		index++;
		return lookahead();
	}

	private void expect(char c) {
		if (lookahead() == c)
			step();
		else
			error("Error:Expected char:" + c);
	}

	private void expr() {
		term();
		while (lookahead() == PLUS || lookahead() == MINUS) {
			if (lookahead() == PLUS) {
				expect(PLUS);
				term();
				instruct("ADD");
			} else if (lookahead() == MINUS) {
				expect(MINUS);
				term();
				instruct("SUB");
			}
		}
	}

	private void term() {
		power();
		while (lookahead() == MUL || lookahead() == DIV) {
			if (lookahead() == MUL) {
				expect(MUL);
				power();
				instruct("MUL");
			} else if (lookahead() == DIV) {
				expect(DIV);
				power();
				instruct("DIV");
			}
		}
	}

	private void power() {
		factor();
		while (lookahead() == POW) {
			expect(POW);
			instruct("POW");
		}
	}

	private void factor() {
		if (lookahead() == PAREN) {
			expect(PAREN);
			expr();
			expect(THESIS);
		} else {
			number();
		}
	}

	private void number() {
		char lh = lookahead();
		if (lh == PLUS || lh == MINUS || isDigit(lh)) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(lh);
			lh = step();
			while (lh == DOT || isDigit(lh)) {
				buffer.append(lh);
				lh = step();
			}
			instruct("LOAD", buffer.toString());
		} else {
			error("Error:Except a number.");
		}
	}

	private boolean isDigit(char c) {
		return '0' <= c && '9' >= c;
	}

	public void compute() {
		expr();
		instructions.forEach(instruction -> {
			System.out.println(instruction);
		});
	}

	public static void main(String[] args) {
		new DoubleSupported("1+1+1+1+1/(9-2)").compute();
	}
}
