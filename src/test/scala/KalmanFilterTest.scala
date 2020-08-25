
import breeze.linalg.DenseMatrix
import filters.KalmanFilter
import org.scalatest._

class KalmanFilterTest extends FlatSpec {

  "KalmanFilter" should "work" in  {
      val dt = 1.0 / 30
      val A = DenseMatrix(1, dt, 0, 0, 1, dt, 0, 0, 1).reshape(3,3)
      val C = DenseMatrix(1.0,0.0,0.0).reshape(1,3)
      val Q = DenseMatrix(.05, .05, .0, .05, .05, .0, .0, .0, .0).reshape(3,3)
      val R = DenseMatrix(3.0)
      val P = DenseMatrix(.1, .1, .1, .1, 10000, 10, .1, 10, 100).reshape(3,3)

      val kf = new KalmanFilter(A,C,Q,R,P)

      val measurements = List[Double](1.04202710058, 1.10726790452, 1.2913511148, 1.48485250951, 1.72825901034,
        1.74216489744, 2.11672039768, 2.14529225112, 2.16029641405, 2.21269371128,
        2.57709350237, 2.6682215744, 2.51641839428, 2.76034056782, 2.88131780617,
        2.88373786518, 2.9448468727, 2.82866600131, 3.0006601946, 3.12920591669,
        2.858361783, 2.83808170354, 2.68975330958, 2.66533185589, 2.81613499531,
        2.81003612051, 2.88321849354, 2.69789264832, 2.4342229249, 2.23464791825,
        2.30278776224, 2.02069770395, 1.94393985809, 1.82498398739, 1.52526230354,
        1.86967808173, 1.18073207847, 1.10729605087, 0.916168349913, 0.678547664519,
        0.562381751596, 0.355468474885, -0.155607486619, -0.287198661013, -0.602973173813)

      val x0 = DenseMatrix(measurements(0), 0, -9.81).reshape(3,1)

      kf.init(x0)

      for (measure <- measurements) {
        kf.update(DenseMatrix(measure))
      }
      println(kf.result())
  }
}
