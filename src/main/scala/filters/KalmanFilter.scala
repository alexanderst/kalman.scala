package filters

import breeze.linalg.DenseMatrix

class KalmanFilter
(
   var A:DenseMatrix[Double], // State transition matrix
   var C:DenseMatrix[Double], // Control Input matrix
   val Q:DenseMatrix[Double], // Process noise covariance
   val R:DenseMatrix[Double], // Measurement noise covariance
   var P:DenseMatrix[Double] // Estimate error covariance
) {

   var Xmeasured = DenseMatrix.zeros[Double](A.rows, A.cols)
   var Xpredicted = DenseMatrix.zeros[Double](A.rows, A.cols)
   val I = DenseMatrix.eye[Double](A.cols)
   var X_measurements = DenseMatrix.zeros[Double] _
   var dt = 0.0

   def init(measurements:DenseMatrix[Double]): Unit = {
      Xmeasured = measurements
   }

   def init(dt:Double, measurements:DenseMatrix[Double]): Unit = {
      Xmeasured = measurements
      this.dt = dt
   }


   def update(y:DenseMatrix[Double]): Unit = {
      // Predict phase (time update)
      // Predict value based by measurement. Omit control here as noise
      Xpredicted = A * Xmeasured // + control_input_vector * control_vector
      // Calculate covariance error
      P = A * P * A.t + Q

      // Measurement Update phase (correction with feedback)
      // Calculate gain
      val K = P * C.t * (C * P * C.t + R)

      Xpredicted = Xmeasured + K * (y - C * Xpredicted)
      P = (I - K * C) * P
      Xmeasured = Xpredicted
   }

   def result() = {
      Xpredicted
   }
}
