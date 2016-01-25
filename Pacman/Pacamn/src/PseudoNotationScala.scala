import scala.annotation.tailrec
import scala.collection.JavaConversions._

object PseudoNotationScala {
  def writePseudoNotation(toScan: java.util.ArrayList[String], fileName: String, Player: String) {
    val coordList = toScan.toList
    var x = 0
    var x1 = 0
    var y = 0
    var y1 = 0

    def matcher(i: Int, j: Int) {
      x = coordList.get(i).toInt;
      x1 = coordList.get(j).toInt;
      x1 - x match {
        case -25 => {
          NotationWrite.update(fileName, Player + " ������ ��� �����");
        }
        case 25 => {
          NotationWrite.update(fileName, Player + " ������ ��� ������");
        }
        case 0 => {
          y = coordList.get(i + 1).toInt;
          y1 = coordList.get(j + 1).toInt;
          y1 - y match {
            case -25 => {
              NotationWrite.update(fileName, Player + " ������ ��� �����");
            }
            case 25 => {
              NotationWrite.update(fileName, Player + " ������ ��� ����");
            }
            case 0 => {
              NotationWrite.update(fileName, Player + " ����� � ������");
            }
            case _ => {
              NotationWrite.update(fileName, "������� ���� Pacmana");
            }
          }
        }
        case _ => {
          NotationWrite.update(fileName, "������� ���� Pacmana");
        }
      }
    }

    @tailrec
    def outerLoop(i: Int, j: Int) {
      if (j < coordList.size) {
        matcher(i, j)
        outerLoop(i + 2, j + 2)
      }
    }
    outerLoop(0, 2)
  }
}