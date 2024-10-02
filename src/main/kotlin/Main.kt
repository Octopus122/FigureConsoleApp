package org.example

const val PI = 3.14159265358

fun main() {
    ConsoleServiceImpl.work()
}

internal interface Figure
{
    fun getArea(): Double
    fun getPerimeter(): Double
}

data class Square(val property: Double): Figure
{
    init {
        println(this.toString())
    }
    override fun getArea(): Double {
        return property*property
    }

    override fun getPerimeter(): Double {
        return property*4
    }
}

data class Circle(val property: Double): Figure
{
    init {
        println(this.toString())
    }
    override fun getArea(): Double {
        return PI*property*property
    }

    override fun getPerimeter(): Double {
        return property*2*PI
    }
}

// ConsoleService

interface ConsoleService {


    fun work()

}

class WrongOperationTypeException (message: String) : Exception(message)
class BadPropertyException (message: String) : Exception(message)
class BadFigureTypeException (message: String) : Exception(message)

object ConsoleServiceImpl: ConsoleService
{
    override fun work() {
        while(true) {

            println("Введите тип операции, которую хотите исполнить:\n1) добавить фигуру\n2) получить площадь всех фигур\n3) получить периметр всех фигур\n4) завершить выполнение")
            try {
                val operation = getOperation(readln())


                when (operation) {

                    Operation.INSERT -> addFigure()

                    Operation.GET_AREA -> getArea()

                    Operation.GET_PERIMETER -> getPerimiter()

                    Operation.EXIT -> break

                }
            }
            catch (e: WrongOperationTypeException){
                println("Введен неизвестный тип операции: ${e.message}")
                continue
            }
        }
    }
    private enum class Operation(val operationNum: String)
    {
        INSERT("1"),
        GET_AREA("2"),
        GET_PERIMETER("3"),
        EXIT("4")
    }

    private fun getOperation(input: String): Operation
    {
        val result = when (input.lowercase()){
            Operation.INSERT.operationNum -> Operation.INSERT
            Operation.GET_AREA.operationNum -> Operation.GET_AREA
            Operation.GET_PERIMETER.operationNum -> Operation.GET_PERIMETER
            Operation.EXIT.operationNum -> Operation.EXIT
            else -> throw WrongOperationTypeException(input)
        }
        return result
    }
    private fun addFigure()
    {
        println("Введите тип фигуры >>")
        val figureType = readln().lowercase()
        println("Введите параметр property >>")
        try {
            when (figureType) {
                "square" -> FigureServiceImpl.addSquare(readln().toDouble())
                "circle" -> FigureServiceImpl.addCircle(readln().toDouble())
                else -> throw BadFigureTypeException(figureType)
            }
        }
        catch(e: BadPropertyException){
            println("Введено неверное значение параметра property: ${e.message}")
        }
        catch(e: BadFigureTypeException){
            println("Введен неверный тип фигуры: ${e.message}")
        }
    }
    private fun getArea()
    {
        FigureServiceImpl.getArea()
    }
    private fun getPerimiter()
    {
        FigureServiceImpl.getPerimeter()
    }
}

interface FigureService {

    fun addSquare(property: Double)
    fun addCircle(property: Double)
    fun getPerimeter(): Double
    fun getArea(): Double
}
object FigureServiceImpl : FigureService
{
    private val Figures: MutableList<Figure> = mutableListOf()
    override fun addSquare(property: Double) {
        if (property < 0) throw BadPropertyException(property.toString())
        Figures.addLast(Square(property))
    }
    override fun addCircle(property: Double) {
        if (property < 0) throw BadPropertyException(property.toString())
        Figures.addLast(Circle(property))
    }
    override fun getPerimeter(): Double {
        var perimeter = 0.0
        Figures.forEach()
        {
            perimeter += it.getPerimeter()
        }
        return perimeter
    }
    override fun getArea(): Double {
        var area = 0.0
        Figures.forEach()
        {
            area += it.getArea()
        }
        return area
    }
}



