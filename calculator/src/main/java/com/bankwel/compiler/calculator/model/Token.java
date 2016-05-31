package com.bankwel.compiler.calculator.model;

import java.io.Serializable;

public class Token implements Serializable {

	private static final long serialVersionUID = -2825238625369807057L;

	private String represent;
	private Term term;

	public Token(String represent, Term term) {
		this.represent = represent;
		this.term = term;
	}

	public String getRepresent() {
		return represent;
	}

	public void setRepresent(String represent) {
		this.represent = represent;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public enum Term {

		NUMBER(0, "number", "(^\\+|^-)?\\d+(\\.\\d+)?"), 
		FIRST_OPRATOR(1, "first", "\\+|-"), 
		SECOND_OPOPRATOR(2, "second", "\\*|/"), 
		THIRD_OPRATOR(3, "third", "\\^"), 
		LEFT_BRACKET(4, "lbracket",	"\\("), 
		RIGHT_BRACKET(5, "rbracket", "\\)"), 
		EQUAL(6, "equal", "=");

		public final int value;
		public final String name;
		public final String regex;

		private Term(int value, String name, String regex) {
			this.value = value;
			this.name = name;
			this.regex = regex;
		}
	}

	@Override
	public String toString() {
		return represent + "/" + term.name;
	}

}
