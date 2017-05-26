/**
 * Created by tikhon.osipov on 26/05/2017
 */
fun main( args: Array<String>) {
    val code = "Sub a(ByVal s as String, ByRef m() as String) \n 'this is a comment \n End Sub"

    testArgumentsParsing("ByVal s as String, ByRef m(,) as String, ByVal yeah_1 as EditText, ByRef m1(,) as String")
    testArgumentsParsing("ByRef s as String")
    testArgumentsParsing("ByVal yeah_1 as EditText, ByRef m1() as String")
    testArgumentsParsing("ByRef m(,) as String, ByRef m1(,) as String")
}

fun testArgumentsParsing(argsString: String) {

    println(" ~ start ArgumentsParsing test ~ \n")
    println(argsString)
    val parsedArgs: ArrayList<Argument> = parseArguments(argsString)

    for(item in parsedArgs) println(item)
    println("\n --- end ArgumentsParsing testing --- \n")
}