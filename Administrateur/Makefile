run:
	cd railroad && mvn clean compile exec:java

build-jar:
	mvn clean package

jar: build-jar
	cd target && java -jar RailRoadInkProject.jar

coverage:
	mvn clean test-compile org.pitest:pitest-maven:mutationCoverage
	firefox railroad/target/pit-reports/index.html
