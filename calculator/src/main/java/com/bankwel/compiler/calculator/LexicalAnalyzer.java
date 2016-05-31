package com.bankwel.compiler.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bankwel.compiler.calculator.model.Token;
import com.bankwel.compiler.calculator.model.Token.Term;

public class LexicalAnalyzer {

	private static final String GROUPE = "(^\\+|^-)?\\d+(\\.\\d+)?|[\\\\+-\\\\*/^\\(\\)]";
	private static final Logger logger = LoggerFactory.getLogger(LexicalAnalyzer.class);

	public static List<Token> tokenize(@NotNull CharSequence cs) {
		List<Token> tokens = new ArrayList<Token>();
		Pattern pattern = Pattern.compile(GROUPE);
		Matcher matcher = pattern.matcher(cs);
		while (matcher.find()) {
			String group = matcher.group();
			Token token = parse(group);
			if (token == null) {
				logger.error("Couldn't parse symbol'{}' in Expression.\n", group);
				return null;
			} else
				tokens.add(token);
		}
		return tokens;
	}

	private static Token parse(String group) {
		for (Term term : Term.values()) {
			if (matches(group, term.regex))
				return new Token(group, term);
		}
		return null;
	}

	private static boolean matches(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(input).matches();
	}

	public static void main(String[] args) {
		String input = "(18.72+3*7)-6=";
		System.out.println(tokenize(input));
	}
}
