name := "ScalaLangTest"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

scapegoatVersion := "1.3.0"

addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.17")

// settle for warning as Wart.TraversableOps doesn't seem to honor the SuppressWarning annotation at the moment
wartremoverWarnings ++= Warts.allBut(Wart.Var, Wart.While, Wart.Overloading, Wart.NonUnitStatements, Wart.Throw,
  Wart.DefaultArguments, Wart.TraversableOps)