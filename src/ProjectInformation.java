public @interface ProjectInformation {
	String authorsName() default "Philipp Bonadykov";
	String lastModified() default "3/28/2018";
	double programVersion();
	String[] reviewers();
}
