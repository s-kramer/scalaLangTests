name := "ScalaLangTest"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

scapegoatVersion := "1.3.0"

addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.17")
