package test.scala.coursera.rx

import rx.lang.scala.{Scheduler, Subscription, Observable}
import scala.language.postfixOps
import scala.concurrent.duration._
import org.junit.Test
import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import coursera.Utils._

class Schedulers extends JUnitSuite {

  def fromIterableBad[T](seq: Iterable[T]) : Observable[T] = {

     Observable(observer => {
     try {
       seq.foreach(observer.onNext(_))
     } catch  {
       case e: Throwable => observer.onError(e)
     }

     Subscription()
   })
  }

  def from(start: Integer): Iterable[Integer] = {
    new Iterable[Integer]() {
      def iterator: Iterator[Integer] = {
        var n = start-1;
        new Iterator[Integer] () {
          def hasNext: Boolean =  { n += 1; true }
          def next(): Integer = n
        }
      }
    }
  }

//  @Test(timeout=1) def attemptI(): Unit = {
//    val nats: Observable[Integer] = fromIterableBad(from(0))
//    val subscription = nats.subscribe(println(_))
//    subscription.unsubscribe()
//  }

  @Test def helloScheduler(): Unit = {
    val scheduler: Scheduler = rx.lang.scala.concurrency.Schedulers.newThread
    println(s"running on ${Thread.currentThread().getName}")
    val subscription = scheduler.schedule{
      println(s"running on ${Thread.currentThread().getName}")
    }
    subscription.unsubscribe()
  }

  @Test(timeout=10) def attemptII(): Unit = {
    val scheduler: Scheduler = rx.lang.scala.concurrency.Schedulers.newThread
    val nats: Observable[Integer] = Observable(observer => {
      scheduler.schedule{ from(0).foreach(observer.onNext(_)) }
    })
    val subscription = nats.subscribe(println(_))
    subscription.unsubscribe()
    println("You won the lottery")
  }


}


