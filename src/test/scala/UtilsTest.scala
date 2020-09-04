
import java.io.File

import loaders.CSVLoader
import org.scalatest._

class UtilsTest extends FlatSpec {

  "IBM prices CSV models" should "be loaded to DenseMatrix" in {
      val data = CSVLoader.load(new File("IBM.csv"))
      assert(data.rows == 253)
      assert(data.cols == 7)
  }

  "Split dataset" should "just work" in {
      val (trainData, testData) = CSVLoader.loadAndSplit(new File("IBM.csv"))
      assert(trainData.rows == 190)
      assert(testData.rows == 63)
  }
}
