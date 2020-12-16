run: compile
	@java --module-path ./JavaFXActivity/JavaFXForLinux/  --add-modules javafx.controls --add-modules javafx.web FrontEnd

compile:
	@wget http://pages.cs.wisc.edu/~cs400/JavaFXActivity.tar.gz
	@tar -xzvf JavaFXActivity.tar.gz
	@javac --module-path ./JavaFXActivity/JavaFXForLinux/ --add-modules javafx.controls --add-modules javafx.web FrontEnd.java

test:
	@javac -cp .:junit5.jar GarysRevengeTester.java
	@java -jar junit5.jar -cp . --scan-classpath -n GarysRevengeTester

clean:
	$(RM) *.class

