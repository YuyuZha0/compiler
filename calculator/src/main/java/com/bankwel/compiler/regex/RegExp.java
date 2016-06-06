package com.bankwel.compiler.regex;

import javax.validation.constraints.NotNull;

public class RegExp {

	private char[] sequence;
	private int index;

	private static final char ALTERNATION = '|';
	private static final char KLEENE_CLOSURE = '*';
	private static final char LEFT_BRACKET = '(';
	private static final char RIGHT_BRACKET = ')';

	public RegExp(@NotNull String regex) {
		this.sequence = regex.toCharArray();
		index = 0;
		exp();
	}
	
	private void info(String msg){
		System.out.println(msg);
	}

	private void error(@NotNull String msg, Object... args) {
		if (args != null) {
			String[] msgs = msg.split("\\{\\}");
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < msgs.length; i++) {
				buffer.append(msgs[i]);
				if (i < args.length)
					buffer.append(args[i].toString());
				else if (i < msgs.length - 1)
					buffer.append("{}");
			}
			msg = buffer.toString();
		}
		System.out.println(msg);
	}

	private char lookahead() {
		if (index >= sequence.length)
			return '\0';
		return sequence[index];
	}

	private void forward() {
		index++;
	}

	private void match(char c) {
		if (c == lookahead())
			forward();
		else
			error("Syntax Error:Expected char is {}.", c);
	}

	private void exp() {
		concat();
		while (lookahead() == ALTERNATION) {
			match(ALTERNATION);
			concat();
			info("New Alter.");
		}
	}

	private void concat() {
		repeat();
		while (lookahead() == LEFT_BRACKET || isChar(lookahead())){
			repeat();
			info("Concat.");
		}
	}

	private void repeat() {
		factor();
		if (lookahead() == KLEENE_CLOSURE){
			match(KLEENE_CLOSURE);
			info("Kleene Closure.");
		}
	}

	private void factor() {
		if (lookahead() == LEFT_BRACKET) {
			match(LEFT_BRACKET);
			exp();
			match(RIGHT_BRACKET);
			info("Nested.");
		} else if (isChar(lookahead())) {
			forward();
			info("Single char.");
		} else {
			error("Syntax Error:{}.", lookahead());
		}

	}

	private boolean isChar(char c) {
		return ('a' <= c && 'z' >= c) || ('A' <= c && 'Z' >= c);
	}

	private static class Node {
		private int[] interval;
		private Node sibling;
		private Node sub;
	}
	
	public static void main(String[] args) {
		new RegExp("(a|b(asss)*)*");
	}
}
