/**
 * Created by tikhon.osipov on 26/05/2017.
 */

class VBParser(code: String) {
    /*fun parseSub(code: String): Sub {

    }*/
}

class Sub(val args: ArrayList<Argument>, val code: ItemBody) {

}

class Function(val args: ArrayList<Argument>, val code: ItemBody, val type: String) {

}

/**
 * Represents an argument to a Sub or Function
 */
class Argument(val ref: String, val type: String, val name: String) {
    override fun toString(): String {
        return "parsed argument: $ref $name as $type"
    }
}

/**
 * Parses arguments in sub or function into ArrayList<Argument>
 *
 * @param functionParameters arguments to sub or function
 * example: ByVal a as Integer, ByRef b(,) as String
 */
fun parseArguments(functionParameters: String): ArrayList<Argument> {
    val arguments: ArrayList<Argument> = ArrayList()
    var lastKnownDelimiterPos: Int = -1
    var startIndex = 0 //previous delimiter or start of the "functionParameters"

    //do..while here, because there could be not any delimiters (e.g. one parameter in sub or function)
    do {
        lastKnownDelimiterPos = functionParameters.indexOf(',', lastKnownDelimiterPos+1 /** first iteration: 0; */)
        //if we have two-dimensional array definition: ... mas(,) ...
        if(lastKnownDelimiterPos > 0 && functionParameters[lastKnownDelimiterPos-1] == '(') {
            //find next delimiter
            lastKnownDelimiterPos = functionParameters.indexOf(',', lastKnownDelimiterPos+1)
            startIndex ++ //omit delimiter ','
        }
        //parsing starts here
        val defString = extractOneArgumentStringFromAllParameters(
                functionParameters, startIndex, lastKnownDelimiterPos)

        val argParts = defString.split(" ") //example array contents after split: {ByRef; a; as; Integer} (always 4)
        arguments.add(Argument(/** reference*/ argParts[0].trim(), /** type*/ argParts[3].trim(), /** name*/ argParts[1].trim()))

        startIndex = lastKnownDelimiterPos+1
    } while(lastKnownDelimiterPos != -1)

    return arguments
}

/**
 * Gets all parameters string of Sub or Function and extracts only one (from startIndex to endIndex)
 *
 * @param allParameters parameters definition in Sub or Function
 * @param startIndex index of single parameter definition's start
 * @param endIndex index of single parameter definition's end
 * @return a single parameter definition
 */
fun extractOneArgumentStringFromAllParameters(allParameters: String, startIndex: Int, endIndex: Int): String {
    return (
            if(endIndex != -1) allParameters.substring(startIndex, endIndex)
            else allParameters.substring(startIndex)
            ).trim()
}

class ItemBody(code: String)
