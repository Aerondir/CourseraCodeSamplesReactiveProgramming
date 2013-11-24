package coursera.rx

import rx.lang.scala.Observable

import scala.languageFeature.postfixOps._
import scala.concurrent.duration._

/*
List(0, 1, 2, 3, 4)
10
 */
object Blocking {
  def dontDoThisAtHomeKids(): Unit = {

    val xs: Observable[Long] = Observable.interval(1 second).take(5)

    val ys: List[Long] = xs.toBlockingObservable.toList
    println(ys)

    val zs: Observable[Long] = xs.sum

    val sum: Long = zs.toBlockingObservable.single
    println(sum)

  }

}
