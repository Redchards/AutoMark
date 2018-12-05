package com.auto.mark.utils;

import java.io.File;

public class PathBuilder {
	private StringBuilder builder;

	public PathBuilder() {
		builder = new StringBuilder();
	}

	public PathBuilder(CharSequence seq) {
		builder = new StringBuilder(seq);
	}

	public PathBuilder(int capacity) {
		builder = new StringBuilder(capacity);
	}

	public PathBuilder(String str) {
		builder = new StringBuilder(str);
	}

	public PathBuilder append(String path) {
		if ((builder.length() == 0) || (String.valueOf(CharSequenceHelper.getLastOf(builder)).equals(File.separator))) {
			builder.append(path);
		} else {
			builder.append(File.separator).append(path);
		}

		return this;
	}

	public PathBuilder prepend(String path) {
		StringBuilder tmpBuilder = new StringBuilder(path);

		if ((builder.length() == 0) || (String.valueOf(CharSequenceHelper.getLastOf(builder)).equals(File.separator))) {
			builder.reverse();

			builder.append(tmpBuilder.reverse());

			builder.reverse();
		} else {
			builder.reverse();

			builder.append(File.separator).append(tmpBuilder.reverse());

			builder.reverse();
		}

		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
