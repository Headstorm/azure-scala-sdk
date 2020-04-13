import mill._, scalalib._

// Using SbtModule because it uses the standard src/main/scala, src/test/scala layout
object storage extends SbtModule {

   def scalaVersion = "2.13.1"

   def ivyDeps = Agg(ivy"com.azure:azure-storage-blob:12.6.0")

   object test extends Tests {

      val scalaTestVersion = "3.1.1"

      def ivyDeps = Agg(ivy"org.scalatest::scalatest:$scalaTestVersion")
      def testFrameworks = Seq("org.scalatest.tools.Framework")

   }

}
