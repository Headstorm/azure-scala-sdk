import mill._, scalalib._

object storage extends ScalaModule {

   def scalaVersion = "2.13.1"

   def ivyDeps = Agg("com.azure:azure-storage-blob:12.6.0")

   object test extends Tests {

      val scalaTestVersion = "3.1.1"

      def ivyDeps = Agg(ivy"org.scalatest::scalatest::$scalaTestVersion")
      def testFrameworks = Seq("org.scalatest.tools.Framework")

   }

}
