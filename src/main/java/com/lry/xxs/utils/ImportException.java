package com.lry.xxs.utils;

public class ImportException extends Exception {

	private static final long serialVersionUID = 5096119032978308241L;

	public ImportException() {
		super();
	}

	public ImportException(String msg) {
		super(msg);
	}

	public ImportException(String msg, Throwable abl) {
		super(msg, abl);
	}

	public ImportException(Throwable abl) {
		super(abl);
	}

}
