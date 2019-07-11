package quix.core.sql

import org.specs2.mutable.SpecWithJUnit
import quix.core.sql.PrestoSqlOps.{splitToStatements => split}

class PrestoSqlOpsTest extends SpecWithJUnit {

  "PlainPrestoSqlSupport.splitToStatements" should {
    "handle single line statements" in {
      split("select 1") must contain("select 1")
    }

    "handle delimited statements" in {
      split("select 1;select 2") must contain("select 1", "select 2")
    }

    "handle delimited statements with empty partial statement" in {
      split("select 1;select 2;") must contain("select 1", "select 2")
    }

    "handle delimited statements with many empty lines between them" in {
      val newlines = List.fill(10)("\n").mkString
      val sql = "select 1;" + newlines + "select 2"
      split(sql) must contain("select 1", "select 2")
    }

    "handle empty strings" in {
      split("       \n\n\n\n\n") must beEmpty
    }

  }

}