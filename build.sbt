name := "Etude"

organization := "org.watermint"

version := "0.0.1"

scalaVersion := "2.10.3"

resolvers ++= Seq(
  "sonatype-public" at "https://oss.sonatype.org/content/groups/public",
  "atlassian-public" at "https://maven.atlassian.com/repository/public",
  "Maven Central Server" at "http://repo1.maven.org/maven2",
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "com.github.scala-incubator.io" % "scala-io-file_2.10" % "0.4.2",
  "com.github.scopt" %% "scopt" % "3.1.0",
  "com.typesafe" % "config" % "1.0.2",
  "nu.validator.htmlparser" % "htmlparser" % "1.4",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "org.apache.httpcomponents" % "httpclient" % "4.3.1",
  "org.scala-lang" %% "scala-pickling" % "0.8.0-SNAPSHOT",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.specs2" %% "specs2" % "2.2.3" % "test",
  "junit" % "junit" % "4.7" % "test"
)
