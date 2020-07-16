FROM openjdk:8

WORKDIR /app/src

COPY src .
COPY ./documents/Data_Set /data/documents
COPY documents/stopwords.txt /data/

RUN javac *.java

CMD java Main /data/documents /data/stopwords.txt