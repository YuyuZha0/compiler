package com.bankwel.compiler.stack;

public class ArrayCharStack implements CharStack {

	private char[] space;
	private int top;

	public ArrayCharStack(int size) {
		space = new char[size];
		top = -1;
	}

	@Override
	public void push(char c) {
		if (top < space.length - 1)
			space[++top] = c;
	}

	@Override
	public char pop() {
		return space[top--];
	}

	@Override
	public boolean isEmpty() {
		return top == -1;
	}

	public static void main(String[] args) {
		ArrayCharStack stack = new ArrayCharStack(50);
		for (char c : "HelloWorld!".toCharArray()) {
			stack.push(c);
		}

		while (!stack.isEmpty())
			System.out.println(stack.pop());
	}

}
