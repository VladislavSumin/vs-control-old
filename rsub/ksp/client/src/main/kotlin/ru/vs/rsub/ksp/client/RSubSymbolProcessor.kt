package ru.vs.rsub.ksp.client

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.serialization.json.Json
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubClientAbstract
import ru.vs.rsub.RSubConnector

class RSubSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    logger: KSPLogger
) : SymbolProcessor {
    private val proxyGenerator = RSSubInterfaceProxyGenerator(logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(RSubClient::class.qualifiedName!!)
            .forEach(this::processRSubClients)

        return emptyList()
    }

    private fun processRSubClients(client: KSAnnotated) {
        generateRSubClientImpl(client as KSClassDeclaration)
    }

    private fun generateRSubClientImpl(client: KSClassDeclaration) {
        val name = client.simpleName.asString() + "Impl"
        val clazz = with(proxyGenerator) {
            val constructor = generateConstructor()
            TypeSpec.classBuilder(name)
                .addOriginatingKSFile(client.containingFile!!)
                .addModifiers(KModifier.INTERNAL)
                .superclass(RSubClientAbstract::class)
                .addSuperinterface(client.toClassName())
                .primaryConstructor(constructor)
                .addSuperclassConstructorParameter(constructor.parameters.joinToString { it.name })
                .generateProxyClassesWithProxyInstances(client.getAllProperties())
                .build()
        }

        FileSpec.builder(client.packageName.asString(), name)
            .addType(clazz)
            .build()
            .writeTo(codeGenerator, false)
    }

    @Suppress("MagicNumber")
    private fun generateConstructor(): FunSpec {
        return FunSpec.constructorBuilder()
            .addParameter("connector", RSubConnector::class)
            .addParameter(
                ParameterSpec.builder("reconnectInterval", Long::class)
                    .defaultValue("%L", 3000)
                    .build()
            )
            .addParameter(
                ParameterSpec.builder("connectionKeepAliveTime", Long::class)
                    .defaultValue("%L", 6000)
                    .build()
            )
            .addParameter(
                ParameterSpec.builder("json", Json::class)
                    .defaultValue("%T", Json::class.asTypeName())
                    .build()
            )
            .addParameter(
                ParameterSpec.builder("scope", CoroutineScope::class)
                    .defaultValue("%T", GlobalScope::class.asTypeName())
                    .build()
            )
            .build()
    }
}
