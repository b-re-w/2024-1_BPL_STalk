package device.nn

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value
import java.nio.file.Paths


//fun runPythonCode(code: String): Value {
//    val context = Context.newBuilder().allowAllAccess(true).build()
//
//
//    Context.create().use { context ->
//        val function: Value = context.eval("python", """
//            import java
//
//            print(dir())
//
//            def add(a):
//                return a + 1
//
//            add
//        """.trimIndent())
//        println(function.canExecute())
//        val x: Int = function.execute(41).asInt()
//        println(x == 42)
//    }
//}



actual object Llama3 {
    val currentPath = System.getProperty("user.dir")

    val venvExePath = Paths.get(currentPath, "venv", "bin", "graalpy").toString()

    val context: Context = Context
        .newBuilder("python")
        .allowAllAccess(true)
        .option("python.Executable", venvExePath).build()

    actual fun printInfo() {
        context.eval("python", """
            from platform import python_implementation
            import sys
            
            version = sys.version.replace('\n', '')
            print(f"{python_implementation()} {version} on {sys.platform}")
        """.trimIndent())
        println("Path:\n\tRoot: $currentPath")
        println("\tVenv: $venvExePath")
        context.eval("python", "import site; print('\tSite:', site.getsitepackages())")

        /*val function: Value = context.eval(
            "python", """

                import java
                from pip import main as pip

                def install(*args):
                    return pip(['install', *args])

                install
            """.trimIndent()
        )
        println(function.canExecute())
        val x = function.execute("torch", "--index-url", "https://download.pytorch.org/whl/cu121")
        println(x)*/
    }
}
