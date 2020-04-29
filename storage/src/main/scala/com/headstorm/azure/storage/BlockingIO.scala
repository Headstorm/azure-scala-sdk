package com.headstorm.azure.storage

import java.util.concurrent.{ Executors, ThreadFactory }
import cats.effect._
import cats.implicits._
import scala.concurrent.ExecutionContext

/** Builds execution contexts that are safe to use for blocking operations(e.g. IO, but NOT CPU intensive tasks)
 *
 * Instances of this class should *not* be passed implicitly.
 */
object BlockingIO {

  /** Lifts an execution context into Blocking that creates new threads as needed, but
   * will reuse previously constructed threads when they are available, and uses the
   * provided ThreadFactory to create new threads when needed
   */
  def blockingContext(name: String): Blocker = Blocker.liftExecutionContext {
    ExecutionContext.fromExecutor(
      Executors.newCachedThreadPool(
        new ThreadFactory {
          def newThread(r: Runnable): Thread = {
            val thread = new Thread(r)
            thread.setName(show"$name-pool-${thread.getId}")
            thread.setDaemon(true)
            thread
          }
        }
      )
    )
  }
}
