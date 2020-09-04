package loaders

import java.io.File

import breeze.linalg._
import breeze.stats.{mean, stddev}

object CSVLoader {

  def load(filePath:File):DenseMatrix[Double] = {
    val data = csvread(filePath, skipLines = 1, escape = '-')
    data
  }

  def loadTimestamped(filePath:File):(DenseMatrix[Double],DenseMatrix[Double]) = {
    val data = csvread(filePath, skipLines = 1, escape = '-')
    val ret = (data(::,0).toDenseMatrix, data(::,0).toDenseMatrix)
    ret
  }

  def loadAndSplit(filePath:File, ratio:Double = 0.75):(DenseMatrix[Double],DenseMatrix[Double]) = {
      val data = load(filePath)
      val trainSize:Int = (data.rows * ratio).toInt + 1
      val testSize:Int = (data.rows * (1 - ratio)).toInt

      def toDenseMatrix(data:DenseMatrix[Double], start:Int, end:Int) = {
        var partial = new Array[Double](data.rows * data.cols)
        data(*,::).foreach(dv => {
          partial :+ dv.toArray.slice(start,end)
        })
        val splitted = new DenseMatrix(end - start, data.cols, partial)
        splitted
      }
      val trainData = toDenseMatrix(data, 0, trainSize)
      val testData = toDenseMatrix(data, trainSize, trainSize + testSize)

      (trainData, testData)
  }

  def rescale(data:DenseVector[Double]) = {
    (data - mean(data)) / stddev(data)
  }

  def rescale(data:DenseMatrix[Double]) = {
    (data - mean(data)) / stddev(data)
  }
}
