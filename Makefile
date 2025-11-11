.PHONY: run-all
run-all:
		mvn package -DskipTests
		docker-compose up -d --build